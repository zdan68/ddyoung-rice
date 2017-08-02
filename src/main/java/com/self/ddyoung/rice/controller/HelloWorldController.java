package com.self.ddyoung.rice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zdan68@163.com">Sanbian</a>
 * @version 1.0
 * @since 17/8/1 下午5:41
 */
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello world";
    }
}
