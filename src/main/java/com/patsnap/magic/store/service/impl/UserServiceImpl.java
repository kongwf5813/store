package com.patsnap.magic.store.service.impl;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.IUserDao;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;
import com.patsnap.magic.store.service.IUserService;
import com.patsnap.magic.store.util.MD5Util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserDao userDao;

    /**
     * 用户登录接口实现
     * @param username 用户名
     * @param password 密码
     * @return ServerResponse<User>
     */
    @Override
    public ServerResponse<User> login(String username, String password) {

        if (!this.checkUserName(username)) {
            return ServerResponse.createByErrorMessage("用户名不合存在或者密码错误");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userDao.findByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名不合存在或者密码错误");
        }

        return ServerResponse.createBySuccess("登录成功", null);
    }

    /**
     * 用户注册接口实现
     * @param userReq 用户请求参数
     * @return ServerResponse
     */
    @Override
    public ServerResponse<String> register(UserRequestInfo userReq) {

        if (this.checkUserName(userReq.getUserName())) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        if (this.checkEmail(userReq.getEmail())) {
            return ServerResponse.createByErrorMessage("邮箱地址已存在");
        }

        User user = new User();
        user.setUsername(userReq.getUserName());
        user.setRole(Constant.Role.ROLE_USER);
        user.setEmail(userReq.getEmail());
        user.setPassword(MD5Util.MD5EncodeUtf8(userReq.getPassword()));
        user.setQuestion(userReq.getQuestion());
        user.setAnswer(userReq.getAnswer());
        user.setPhone(userReq.getPhone());

        User savedUser = userDao.save(user);
        if (savedUser.getId() == null) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    /**
     * Spring Security默认用户认证实现
     * JPA实现
     * @param s 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("用户没有权限");
        }
        return user;
    }

    @Override
    public ServerResponse selectQuestion(String username) {
        return null;
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        return null;
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return null;
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        return null;
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        return null;
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        return null;
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        return null;
    }

    private boolean checkUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }

        User user = userDao.findByUsername(userName);
        return user != null;
    }

    private boolean checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User user = userDao.findByEmail(email);
        return user != null;
    }
}
