package com.romco.bracketeer.config;

import com.romco.bracketeer.service.CustomUserServiceImpl;
import com.romco.bracketeer.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;


@Configuration
@EnableWebSecurity
//idk what the below does
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    //TODO adjust the hardcoded mappings below to refer to the Mappings class
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin")
                        .hasAuthority("ROLE_ADMIN")
                .antMatchers("/", "/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage(Mappings.UserManagement.LOGIN) // TODO this ain't right, it should be from ViewNames
                    .loginProcessingUrl("/login")
                    .failureUrl("/login?error=true")
                    .permitAll()
                    .and()
                .csrf().disable() // TODO add csrf into thymeleaf templates and then remove this https://www.marcobehler.com/guides/spring-security
                .httpBasic();
//                .and()
//                .logout()
//                .permitAll()
    }

}