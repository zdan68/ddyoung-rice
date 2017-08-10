package com.self.ddyoung.rice.controller;

import com.self.ddyoung.rice.dao.UserDAO;
import com.self.ddyoung.rice.model.UserDO;
import com.self.ddyoung.rice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author <a href="mailto:zdan68@163.com">Sanbian</a>
 * @version 1.0
 * @since 17/8/1 下午5:41
 */
@RestController
public class HelloWorldController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/hello")
    public String index(){
        return "Hello world";
    }

    @RequestMapping("/add")
    public UserDO testAddUser() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setCreateTime(new Date());
        userDO.setModifyTime(new Date());
        userDO.setUserName("1111");
        userDO.setPassword("1111");
        userDO.setEmail("zdan68@163.com");
        userDO.setNickName("nick1111");
        return userService.addUser(userDO);
    }

    @RequestMapping("/queryByUserName")
    public UserDO queryByUserName(@RequestParam  String userName){
        return userService.queryByUserName(userName);
    }
}
