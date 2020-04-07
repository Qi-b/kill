package com.kill.service;

import org.springframework.stereotype.Service;


public interface IKillService {

    Boolean killItem(Integer killId, Integer userId) throws Exception;
    Boolean killItem2(Integer killId, Integer userId) throws Exception;

}
