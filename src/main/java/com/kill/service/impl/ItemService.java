package com.kill.service.impl;

import com.kill.entity.ItemKill;
import com.kill.mapper.ItemKillMapper;
import com.kill.service.IItemService;
import com.kill.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {

    @Autowired
    public ItemKillMapper itemKillMapper;

    @Autowired
    private RedisUtil redisUtil;

    /*
    获取秒杀商品
     */
    @Override
    public List<ItemKill> getKillItems() throws Exception {
        String key = "allItem";
        boolean hasKey = redisUtil.hasKey(key);
        List<ItemKill> itemKills = itemKillMapper.selectAll();
        System.out.println(itemKills);
        return itemKills;
    }
    /*
    获取秒杀详情
     */
    @Override
    public ItemKill details(Integer id) throws Exception {
        String key = "detail_"+id;
        boolean haskey = redisUtil.hasKey(key);
        if(haskey){
            ItemKill itemKill = (ItemKill)redisUtil.get(key);
            System.out.println("从缓存中获得数据："+itemKill.getItemId());
            System.out.println("------------------------------------"+key);
            return itemKill;
        }
        else {
            ItemKill itemKill = itemKillMapper.selectById(id);
            if(itemKill==null){
                throw new Exception("秒杀商品已过期");
            } else{
                int time = (int) (itemKill.getEndTime().getTime() - itemKill.getStartTime().getTime()) / 1000;
                redisUtil.set(key, itemKill, time);
                return itemKill;
                }
        }
    }
}
