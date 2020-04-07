package com.kill.service;

import com.kill.entity.ItemKill;

import java.util.List;

public interface IItemService {

    List<ItemKill> getKillItems() throws Exception;

    ItemKill details(Integer id) throws Exception;
}
