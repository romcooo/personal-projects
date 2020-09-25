package com.romco.bracketeer.service;

import com.romco.bracketeer.domain.User;
import com.romco.bracketeer.persistence.dao.UserDao;
import com.romco.bracketeer.security.HashUtilKt;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // == DAO
    @Autowired
    UserDao userDao;

    // == constructors
    @Autowired
    public UserServiceImpl() {
        // autowired empty constructor
    }
    
    // == methods
    public boolean registerUser(@NonNull String username,
                                @NonNull String password,
                                @NonNull String email) {
        log.info("in registerUser");
        String passwordHash = HashUtilKt.encodePassword(password);
        User newUser = new User(username, passwordHash, new Date());
        log.info(newUser.toString());
        // TODO add email to user and db etc.
        // TODO also take care of storing the appropriate roles in a many-to-many way here!
        return userDao.create(newUser) > 0;
    }
    
    public boolean loginUser(String username,
                             String password) {
        // TODO ?
        User user = userDao.retrieveByUsername(username);
        if (user == null) {
            return false;
        }
        
        return HashUtilKt.verifyUser(user, password);
    }
    
}
