package com.self.ddyoung.rice.controller;

import com.self.ddyoung.rice.dao.UserDAO;
import com.self.ddyoung.rice.model.ResultMsg;
import com.self.ddyoung.rice.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sanbian on 2017/8/8.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserDAO userDAO;

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="getUser", method= RequestMethod.GET)
    public ResultMsg getUser(Long id)
    {
        UserDO userDO = userDAO.findOne(id);
        return new ResultMsg(userDO);
    }


    /***
     * 新增用户信息
     * @param userDO
     * @return
     */
    @Modifying
    @RequestMapping(value="addUser", method=RequestMethod.POST)
    public ResultMsg addUser(@RequestBody UserDO userDO)
    {
        userDAO.save(userDO);
        return new ResultMsg(userDO);
    }

}
