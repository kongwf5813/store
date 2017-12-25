package com.patsnap.magic.store.controller;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ResponseCode;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@RequestMapping(value = "/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@NotNull String username, @NotNull String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Constant.Role.ROLE_ADMIN) {
                //说明登录的是管理员
                session.setAttribute(Constant.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.INVALID_AUTHORITY.getCode(), ResponseCode.INVALID_AUTHORITY.getDesc());
            }
        }
        return response;
    }
}
