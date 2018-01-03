package com.patsnap.magic.store.service;

import com.patsnap.magic.store.dao.IUserDao;
import com.patsnap.magic.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Owen on 2018/1/3.
 */
public class UserDetailServiceImpl implements UserDetailsService{


    @Autowired
    private IUserDao userDao;

    /**
     * Spring Security默认用户认证接口，通过JPA方式实现
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户没有权限");
        }
        return user;
    }
}
