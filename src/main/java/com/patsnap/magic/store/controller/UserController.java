package com.patsnap.magic.store.controller;

import com.patsnap.magic.store.common.Constant;
import com.patsnap.magic.store.common.ServerResponse;
import com.patsnap.magic.store.entity.User;
import com.patsnap.magic.store.request.UserRequestInfo;
import com.patsnap.magic.store.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Constant.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ServerResponse<String> register(@Valid @RequestBody UserRequestInfo userRequestInfo) {
        return iUserService.register(userRequestInfo);
    }

    @RequestMapping(value = "update_information",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session, UserRequestInfo userRequestInfo){
        User currentUser = (User)session.getAttribute(Constant.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        currentUser = new User();
        currentUser.setPhone(userRequestInfo.getPhone());

        ServerResponse<User> response = iUserService.updateInformation(currentUser);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Constant.CURRENT_USER,response.getData());
        }
        return response;
    }
}
