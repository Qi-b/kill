package com.kill.service.impl;

import com.kill.config.RabbitSenderService;
import com.kill.entity.ItemKill;
import com.kill.entity.ItemKillSuccess;
import com.kill.enums.SysConstant;
import com.kill.mapper.ItemKillMapper;
import com.kill.mapper.ItemKillSuccessMapper;
import com.kill.service.IKillService;
import com.kill.util.RedisUtil;
import com.kill.util.SnowFlake;

import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class KillService implements IKillService {

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;
    @Autowired
    private ItemKillMapper itemKillMapper;

    private SnowFlake snowFlake = new SnowFlake(2,3);
    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 检测用户是否已经秒杀过此商品
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItem(Integer killId, Integer userId) throws Exception {
        Boolean result = false;
        final String lockKey = new StringBuffer().append(killId).append(userId).append("-RedissonLock").toString();
        RLock lock = redissonClient.getLock(lockKey);
        String key = "detail_" + killId;
        System.out.println(key);

        try {
            boolean b = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if(b) {
                System.out.println(b);
                System.out.println(itemKillSuccessMapper.countByKillUserId(killId, userId));
                if (itemKillSuccessMapper.countByKillUserId(killId, userId) <= 0) {
                    ItemKill itemKill = itemKillMapper.selectById(killId);
                    System.out.println(itemKill);
                    if (itemKill != null && 1 == itemKill.getCanKill()) {
                        int i = itemKillMapper.updateKillItem(killId);


                        if (i > 0) {
                            commonRecordKillSuccessInfo(itemKill, userId);
                            itemKill.setTotal(itemKill.getTotal()-1);
                            int time = (int) (itemKill.getEndTime().getTime() - itemKill.getStartTime().getTime()) / 1000;
                            redisUtil.set(key, itemKill, time);
                            result = true;
                        }
                    }
                }
            }
            else {
                throw new Exception("你已经抢购过此商品");
            }
        }finally {
            lock.unlock();
        }

        return result;
    }

    private void commonRecordKillSuccessInfo(ItemKill itemKill,Integer userId){
        ItemKillSuccess itemKillSuccess = new ItemKillSuccess();
        String orderNo = String.valueOf(snowFlake.nextId());
        itemKillSuccess.setCode(orderNo);
        itemKillSuccess.setItemId(itemKill.getItemId());
        itemKillSuccess.setKillId(itemKill.getId());
        itemKillSuccess.setUserId(userId.toString());
        itemKillSuccess.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        itemKillSuccess.setCreateTime(DateTime.now().toDate());
        if (itemKillSuccessMapper.countByKillUserId(itemKill.getId(),userId) <= 0){
            int res=itemKillSuccessMapper.insertSelective(itemKillSuccess);

            if (res>0){
                //TODO:进行异步邮件消息的通知=rabbitmq+mail
                rabbitSenderService.sendKillSuccessEmailMsg(orderNo);

                //TODO:入死信队列，用于 “失效” 超过指定的TTL时间时仍然未支付的订单
                rabbitSenderService.sendKillSuccessOrderExpireMsg(orderNo);
            }
        }

    }

    @Override
    public Boolean killItem2(Integer killId, Integer userId) throws Exception {
        Boolean result = false;
        final String lockKey = new StringBuffer().append(killId).append(userId).append("-RedissonLock").toString();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean b = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if(b) {

                if (itemKillSuccessMapper.countByKillUserId(killId, userId) <= 0) {
                    ItemKill itemKill =(ItemKill) redisUtil.get("detail" + killId);
                    if (itemKill != null && 1 == itemKill.getCanKill()) {
                        int i = itemKillMapper.updateKillItem(killId);
                        redisUtil.decr("detail"+killId,1 );
                        if (i > 0) {
                            commonRecordKillSuccessInfo(itemKill, userId);
                            result = true;
                        }
                    }
                } else {
                    throw new Exception("你已经抢购过此商品");
                }
            }
        }finally {
            lock.unlock();
        }

        return result;
    }

//    @Override
//    public Boolean killItemV2(Integer killId, Integer userId) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Boolean killItemV3(Integer killId, Integer userId) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Boolean killItemV4(Integer killId, Integer userId) throws Exception {
//        return null;
//    }
//
//    @Override
//    public Boolean killItemV5(Integer killId, Integer userId) throws Exception {
//        return null;
//    }
}
