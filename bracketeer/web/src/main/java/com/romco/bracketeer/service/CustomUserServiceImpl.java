package com.romco.bracketeer.service;

import com.romco.bracketeer.domain.Role;
import com.romco.bracketeer.domain.User;
import com.romco.bracketeer.persistence.dao.RoleDao;
import com.romco.bracketeer.persistence.dao.UserDao;
import com.romco.bracketeer.security.HashUtilKt;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Slf4j
@Service
public class CustomUserServiceImpl implements CustomUserService, UserDetailsService {

    // == DAO
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    // == constructors
    @Autowired
    public CustomUserServiceImpl() {
        // autowired empty constructor
    }
    
    // == methods
    public boolean registerUser(@NonNull String username,
                                @NonNull String password,
                                @NonNull String email) {
        log.info("in registerUser");
        String passwordHash = HashUtilKt.encodePassword(password);
        User newUser =  User.Companion.buildUser(username, passwordHash, new Date());
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

        Collection<Role> roles = roleDao.retrieveByUser(user.getId());
        log.info(roles.toString());
        
        return HashUtilKt.verifyUser(user, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.retrieveByUsername(username);
        UserDetails userDetails = null; //TODO
        return null;
    }
}
