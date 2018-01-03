package com.patsnap.magic.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Owen on 2018/1/3.
 */
@Service
public class UserHystrixServiceImpl {

    @Autowired
    private IUserService userService;

    @HystrixCommand(fallbackMethod = "fallback")
    public ServerResponse<User> login(String username, String password) {
        return userService.login(username, password);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ServerResponse register(UserRequestInfo userReq) {
        return userService.register(userReq);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ServerResponse<User> updateInformation(User user) {
        return userService.updateInformation(user);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        return userService.resetPassword(passwordOld, passwordNew, user);
    }

    public ServerResponse<User> fallback() {
        return ServerResponse.createByErrorMessage("当前服务不可用，请稍后尝试");
    }
}
