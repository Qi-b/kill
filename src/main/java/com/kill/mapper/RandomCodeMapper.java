package com.kill.mapper;

import com.kill.entity.RandomCode;

public interface RandomCodeMapper {
    int deleteById(Integer id);

    int insert(RandomCode record);

    int insertSelective(RandomCode record);

    RandomCode selectById(Integer id);

    int updateByPrimaryKeySelective(RandomCode record);

    int updateByPrimaryKey(RandomCode record);
}
