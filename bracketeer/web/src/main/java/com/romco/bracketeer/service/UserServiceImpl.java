package com.romco.bracketeer.service;

import com.romco.bracketeer.persistence.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    // == DAO
    @Autowired
    UserDao userDao;
//    UserDaoImpl userDao;

}
