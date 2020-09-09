package com.chenxiaoyu.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：
 * @date ：Created in 2020/6/14 23:21
 * @description：
 */
@Controller
public class IndexController {

    @GetMapping(value="/")
    public String index() {
        return "index";
    }
}
