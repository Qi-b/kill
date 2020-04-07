package com.kill.controller;


import com.kill.entity.ItemKill;
import com.kill.service.IItemService;
import com.kill.service.impl.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private IItemService itemservice;

    @RequestMapping("/")
    public String list(Model model){
        try{
            List<ItemKill> killItems = itemservice.getKillItems();
//            System.out.println(killItems);
            model.addAttribute("info", killItems);
            return "index";
        }catch (Exception e){
            logger.error("获取秒杀商品列表失败");
            return "error";
        }
    }

    @RequestMapping("/details/{id}")
    public String details(@PathVariable Integer id,Model model){

        try{
            ItemKill details = itemservice.details(id);
            model.addAttribute("details", details);
            return "details";
        }catch (Exception e){
            logger.error("获取详情页失败");
            return "error";
        }
    }


}
