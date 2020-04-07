package com.kill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private Integer id;
    private String name;
    private String code;
    private String stock;
    private Date purchaseTime;
    private Integer isActive;
    private Date createTime;
    private Date updateTime;
}
