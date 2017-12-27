package com.patsnap.magic.store.config;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.service.impl.UserServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Owen on 2017/12/26.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() {
        return new UserServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()   //开始请求权限配置
                .antMatchers("/user/register","/user/login").permitAll()
                .antMatchers("/user/**").hasAnyRole(Constant.Role.ROLE_ADMIN, Constant.Role.ROLE_USER) //拥有ROLE_ADMIN,ROLE_USER都可以访问
                .antMatchers("/manager/**").hasRole(Constant.Role.ROLE_ADMIN);  //只有拥有ROLE_ADMIN的才有权限访问
//                .anyRequest().authenticated() //其余需要登录认证
                  //.and().formLogin().loginPage("/user/login").permitAll(); //登录可以任意访问
//                .and().logout().permitAll(); //登出可
        http.csrf().disable();
    }
}
