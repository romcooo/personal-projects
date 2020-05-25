package com.romco.bracketeer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/tournament/**", "/static/**", "/css/**", "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
    }
}