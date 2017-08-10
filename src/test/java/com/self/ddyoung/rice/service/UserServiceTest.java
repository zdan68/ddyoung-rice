package com.self.ddyoung.rice.service;

import com.self.ddyoung.rice.model.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by sanbian on 2017/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserService.class})
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setCreateTime(new Date());
        userDO.setModifyTime(new Date());
        userDO.setUserName("1111");
        userDO.setPassword("1111");
        userDO.setEmail("zdan68@163.com");
        userDO.setNickName("nick1111");
        userService.addUser(userDO);
        System.out.println(userDO);
    }
}