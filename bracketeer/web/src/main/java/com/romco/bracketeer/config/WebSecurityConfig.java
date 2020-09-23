package com.romco.bracketeer.config;

import com.romco.bracketeer.util.Mappings;
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
                .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(Mappings.UserManagement.LOGIN)
                .permitAll()
                .and().csrf().disable();
//                .and()
//                .logout()
//                .permitAll()
    }
    
    // TODO?
    public void authorize() {
    
    }
}