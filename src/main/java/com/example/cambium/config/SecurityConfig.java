package com.example.cambium.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/persons").hasAnyRole("SUPER_USER", "USER")
                .antMatchers(HttpMethod.GET, "/persons/*").hasAnyRole("USER", "SUPER_USER")
                .antMatchers(HttpMethod.POST, "/persons").hasRole("SUPER_USER")
                .antMatchers(HttpMethod.DELETE, "/persons/*").hasRole("SUPER_USER")
                .anyRequest().authenticated()
                .and().httpBasic().and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password!").roles("USER")
                .and()
                .withUser("super_user").password("{noop}password!").roles("SUPER_USER");

    }
}

