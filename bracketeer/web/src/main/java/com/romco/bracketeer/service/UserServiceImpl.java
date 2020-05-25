package com.romco.bracketeer.service;

import com.romco.bracketeer.persistence.dao.JavaTest;
import com.romco.bracketeer.persistence.daoimpl.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    // == DAO
    @Autowired
    JavaTest javaTest;
//    UserDao userDaoImpl;
    UserDaoImpl userDao;

}
