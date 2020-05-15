//package com.romco.bracketeer.service
//
//import com.romco.bracketeer.persistence.daoimpl.UserDaoImpl
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//
//class UserDetailsServiceImpl: UserDetailsService {
//
//    // DAO
//    @Autowired
//    val userDao = UserDaoImpl()
//
//    override fun loadUserByUsername(username: String): UserDetails {
//        val user = userDao.retrieveByUsername(username) ?: throw UsernameNotFoundException(username)
//
//        TODO("Not yet implemented")
//    }
//}