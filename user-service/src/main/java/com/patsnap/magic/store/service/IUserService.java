package com.patsnap.magic.store.service;

import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("user")
public interface IUserService {

    @RequestMapping(value = "/login")
    @ResponseBody
    ServerResponse<User> login(String username, String password);

    @RequestMapping(value = "/register")
    @ResponseBody
    ServerResponse<User> register(UserRequestInfo user);

    @RequestMapping(value = "reset_password", method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    @RequestMapping(value = "/update_information",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ServerResponse<User> updateInformation(User user);
}
