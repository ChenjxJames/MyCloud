package com.chenjianxiong.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author :
 * @date :
 * @description : 首页
 */
@Controller
public class IndexController {

    @GetMapping(value="/")
    public String index() {
        return "index";
    }
}
