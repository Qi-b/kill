package com.kill.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ItemKillSuccess {
    private String code;
    private Integer itemId;
    private Integer killId;
    private String userId;
    private Byte status;
    private Date createTime;
}
