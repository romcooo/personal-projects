package com.romco.bracketeer.service;

import com.romco.bracketeer.domain.User;
import com.romco.bracketeer.persistence.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    // == DAO
    @Autowired
    UserDao userDao;

    // == constructors
    @Autowired
    public UserServiceImpl() {
    }
    
    // == methods
    public boolean registerUser(String username,
                               String password,
                               String email) {
        User newUser = new User(username, password);
        return userDao.create(newUser) > 0;
    }
    
    public boolean loginUser(String username,
                             String password) {
        // TODO
        return false;
    }
    
}
