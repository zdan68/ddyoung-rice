package com.self.ddyoung.rice.dao;

import com.self.ddyoung.rice.model.UserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sanbian on 2017/8/7.
 */
@Component
public interface UserDAO extends JpaRepository<UserDO, Long>{

    UserDO findByUserName(String userName);

    UserDO findByUserNameOrEmail(String username, String email);

}
