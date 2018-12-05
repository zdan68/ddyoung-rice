package com.self.ddyoung.rice.service.user;

import com.self.ddyoung.rice.dao.UserDAO;
import com.self.ddyoung.rice.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sanbian on 2017/8/8.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDO addUser(UserDO userDO){
        userDAO.save(userDO);
        return userDO;
    }

    public UserDO queryByUserName(String userName){
        return userDAO.findByUserName(userName);
    }
}
