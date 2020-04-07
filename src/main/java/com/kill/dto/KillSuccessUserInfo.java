package com.kill.dto;

import com.kill.entity.ItemKillSuccess;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KillSuccessUserInfo extends ItemKillSuccess {
    private String userName;

    private String phone;

    private String email;

    private String itemName;

}
