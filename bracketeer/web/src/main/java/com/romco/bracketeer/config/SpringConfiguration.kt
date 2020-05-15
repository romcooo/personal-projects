//package com.romco.bracketeer.config
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//
//@Configuration
//@EnableWebSecurity
//class SpringConfiguration: WebSecurityConfigurerAdapter() {
//    override fun configure(http: HttpSecurity?) {
//        super.configure(http)
//        http?.authorizeRequests()
//                ?.antMatchers("**")?.permitAll()
//                ?.anyRequest()?.authenticated()
//    }
//}