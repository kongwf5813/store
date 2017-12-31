package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.dao.IUserDao;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;
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
     *
     * @param username 用户名
     * @param password 密码
     * @return ServerResponse<User>
     */
    @Override
    public ServerResponse<User> login(String username, String password) {

        if (StringUtils.isBlank(username)
                || userDao.findByUsername(username) == null) {
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
     *
     * @param userReq 用户请求参数
     * @return ServerResponse<String>
     */
    @Override
    public ServerResponse<String> register(UserRequestInfo userReq) {

        if (StringUtils.isBlank(userReq.getUserName())
                || userDao.findByUsername(userReq.getUserName()) == null) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        if (StringUtils.isBlank(userReq.getEmail())
                || userDao.findByEmail(userReq.getEmail()) == null) {
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
     * 更新用户信息接口实现
     * 只能更新手机号
     *
     * @param user 待更新的用户
     * @return ServerResponse<User>
     */
    @Override
    public ServerResponse<User> updateInformation(User user) {
        if (!userDao.exists(user.getId())) {
            return ServerResponse.createByErrorMessage("用户不存在,请重新登录");
        }

        User userSaved = userDao.save(user);
        if (userSaved != null) {
            return ServerResponse.createBySuccess("更新个人信息成功", userSaved);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    /**
     * 重置密码用户接口实现
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user session中的用户
     * @return ServerResponse<String>
     */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {

        //防止横向越权,要校验一下这个用户的旧密码
        User userInDB = userDao.findByUsernameAndPassword(user.getUsername(), MD5Util.MD5EncodeUtf8(passwordOld));
        if (userInDB == null) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        if (userDao.save(user) != null) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

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
