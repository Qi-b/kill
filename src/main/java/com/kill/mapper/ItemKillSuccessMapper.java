package com.kill.mapper;

import com.kill.dto.KillSuccessUserInfo;
import com.kill.entity.ItemKillSuccess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemKillSuccessMapper {
    int deleteByPrimaryKey(String code);

    int insert(ItemKillSuccess record);

    int insertSelective(ItemKillSuccess record);

    ItemKillSuccess selectByPrimaryKey(String code);

    int updateByPrimaryKey(ItemKillSuccess record);

    int updateByPrimaryKeySelective(ItemKillSuccess record);

    int countByKillUserId(@Param("killId") Integer killId,@Param("userId") Integer userId);

    int expireOrder(@Param("code") String code);

    KillSuccessUserInfo selectByCode(@Param("code") String code);

}
