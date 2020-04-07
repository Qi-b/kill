package com.kill.controller;

import com.kill.dto.KillDto;
import com.kill.mapper.ItemKillSuccessMapper;
import com.kill.service.IKillService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class KillController {
    private static final Logger log = LoggerFactory.getLogger(KillController.class);

    @Autowired
    private IKillService iKillService;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;


    @RequestMapping(value = "/details/execute",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String execute(@RequestBody @Validated KillDto dto, BindingResult result,HttpSession session) throws Exception {
        Integer uid = (Integer) session.getAttribute("uid");
        System.out.println(uid);
        try {
            boolean res = iKillService.killItem(dto.getKillId(),uid);
            System.out.println(res);
            if (!res) {
                return "0";
            }
        }
        catch (Exception e){
            return "0";
        }
        return "1";
    }

    @RequestMapping("/details/killSuccess")
    public String killSuccess(){
        return "killSuccess";
    }

    @RequestMapping("/details/error")
    public String killError(){
        return "error";
    }

}
