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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public ServerResponse<User> login(String username, String password) {


        if(this.checkUserName(username)){
            return ServerResponse.createByErrorMessage("用户名不合存在或者密码错误");
        }

        User user = new User();
        user.setUserName(username);
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        user.setPassword(md5Password);
        User userInDB =  userDao.findOne(Example.of(user));

        if(userInDB == null){
            return ServerResponse.createByErrorMessage("用户名不合存在或者密码错误");
        }

        user.setPassword("");
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(UserRequestInfo userReq) {

        if(this.checkUserName(userReq.getUserName())){
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        if(this.checkEmail(userReq.getEmail())){
            return ServerResponse.createByErrorMessage("邮箱地址已存在");
        }

        User user = new User();
        user.setUserName(userReq.getUserName());
        user.setRole(Constant.Role.ROLE_USER);
        user.setEmail(userReq.getEmail());
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setQuestion(userReq.getQuestion());
        user.setAnswer(userReq.getAnswer());
        user.setPhone(userReq.getPhone());

        User savedUser = userDao.save(user);
        if(savedUser.getId() == null){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
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

    private boolean checkUserName(String userName){
        if (StringUtils.isBlank(userName)){
            return false;
        }
        User user = new User();
        user.setUserName(userName);
        return userDao.exists(Example.of(user));
    }

    private boolean checkEmail(String email){
        if (StringUtils.isBlank(email)){
            return false;
        }
        User user = new User();
        user.setEmail(email);
        return userDao.exists(Example.of(user));
    }
}
