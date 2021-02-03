package com.romco.bracketeer.config;

import com.romco.bracketeer.auth.CustomAuthenticationProvider;
import com.romco.bracketeer.service.CustomUserServiceImpl;
import com.romco.bracketeer.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;


@Configuration
@EnableWebSecurity
//idk what the below does
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserServiceImpl userDetailsService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // not sure if I can use BCrypt right now considering how the rest of this process is set up
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
            .withUser("bracketeer")
            .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("bracketeer"))
            .roles("USER");

    }

    //TODO adjust the hardcoded mappings below to refer to the Mappings class
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(Mappings.UserManagement.LOGIN) // TODO this ain't right, it should be from ViewNames
                .loginProcessingUrl("/login")
                .failureUrl("/login-error")
                .permitAll()
                .and().csrf().disable()
                .authenticationProvider(customAuthenticationProvider);
//                .and()
//                .logout()
//                .permitAll()
    }
    
    // TODO?
    public void authorize() {

    }
}