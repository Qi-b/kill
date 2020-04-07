package com.kill.mapper;

import com.kill.entity.ItemKill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemKillMapper {
    List<ItemKill> selectAll();

    ItemKill selectById(@Param("id") Integer id);
//
    int updateKillItem(@Param("killId") Integer killId);
//
//    ItemKill selectByIdV2(@Param("id") Integer id);


}
