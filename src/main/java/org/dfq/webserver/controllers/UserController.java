package org.dfq.webserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.dfq.webserver.models.User;
import org.dfq.webserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService UserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        System.out.println("注册成功");
        // 注册
        return UserService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("登录成功");

        // 登录
        return UserService.login(user);
    }

    @PutMapping("/changePassWord")
    public ResponseEntity<String> changePassWord(User user, HttpServletRequest request) {
        System.out.println("改密成功");
        // 取出jwt中的用户
        User jwtUser = (User)request.getAttribute("jwt-user");

        // 合并jwt中用户的用户名与传入用户的新密码
        // 此处不能直接使用传入的用户名，防止恶意修改其他用户的密码
        user.setUserId(jwtUser.getUserId());

        // 改密
        return UserService.changePassWord(user);

    }

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/code")
    public ResponseEntity<String> senderMail() {
        System.out.println("发送成功");
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人 你的邮箱
        message.setFrom("3151823430@qq.com");
        // 接收人 接收者邮箱
        message.setTo(new String[]{"1543443156@qq.com"});
        //邮件标题
        message.setSubject("小红薯：");
        //邮件内容
        message.setText("尊敬的用户：你好，欢迎来到小红薯，您的验证码为:678253");
        javaMailSender.send(message);

        return ResponseEntity.ok("发送成功");
    }

}

