package org.dfq.webserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dfq.webserver.models.ControllerRes;
import org.dfq.webserver.models.ServiceRes;
import org.dfq.webserver.models.User;
import org.dfq.webserver.service.Impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService UserService;

    @PostMapping("/register")
    public ControllerRes register(User user) {

        // 注册
        ServiceRes serviceRes = UserService.register(user);

        return new ControllerRes(serviceRes.getCode(), serviceRes.getMsg());
    }

    @PostMapping("/login")
    public ControllerRes login(User user, HttpServletResponse response) {

        // 登录
        ServiceRes serviceRes = UserService.login(user);

        // 登录成功后往响应头插入jwt
        if(serviceRes.getJwt() != null) response.addHeader("access-token", serviceRes.getJwt());

        return new ControllerRes(serviceRes.getCode(), serviceRes.getMsg());
    }

    @PutMapping("/pwd")
    public ControllerRes changePassWord(User user, HttpServletRequest request) {

        // 取出jwt中的用户
        User jwtUser = (User)request.getAttribute("jwt-user");

        // 合并jwt中用户的用户名与传入用户的新密码
        // 此处不能直接使用传入的用户名，防止恶意修改其他用户的密码
        user.setUserId(jwtUser.getUserId());

        // 改密
        ServiceRes serviceRes = UserService.changePassWord(user);

        return new ControllerRes(serviceRes.getCode(), serviceRes.getMsg());

    }

}

