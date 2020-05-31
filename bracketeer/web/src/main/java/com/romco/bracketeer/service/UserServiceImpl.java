package com.romco.bracketeer.service;

import com.romco.bracketeer.domain.User;
import com.romco.bracketeer.persistence.dao.UserDao;
import com.romco.bracketeer.security.HashUtilKt;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // == DAO
    @Autowired
    UserDao userDao;

    // == constructors
    @Autowired
    public UserServiceImpl() {
    }
    
    // == methods
    public boolean registerUser(@NonNull String username,
                                @NonNull String password,
                                @NonNull String email) {
        log.info("in registerUser");
        String passwordHash = HashUtilKt.encodePassword(password);
        User newUser = new User(username, passwordHash);
        return userDao.create(newUser) > 0;
    }
    
    public boolean loginUser(String username,
                             String password) {
        // TODO

        return false;
    }
    
}
