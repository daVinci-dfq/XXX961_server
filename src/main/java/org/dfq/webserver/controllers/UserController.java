package org.dfq.webserver.controllers;

import jakarta.validation.constraints.NotNull;
import org.dfq.webserver.models.User;
import org.dfq.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    // 修改用户信息
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    // 注销账号
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // 更改密码
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Integer userId,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        boolean success = userService.changePassword(userId, oldPassword, newPassword);
        if (success) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Old password is incorrect.");
        }
    }

    private static String UPLOAD_DIR = "/path/to/upload-dir"; //服务器的存储目录  需要改


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空，请选择一个文件再上传。";
        }

        try {
            // 获取文件并保存到指定位置
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            return "文件上传成功！文件名：" + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败！";
        }
    }




}
