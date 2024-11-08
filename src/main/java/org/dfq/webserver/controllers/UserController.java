package org.dfq.webserver.controllers;

import org.dfq.webserver.models.User;
import org.dfq.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User loggedInUser = userService.login(username, password);
        return ResponseEntity.ok(loggedInUser);
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


    // 仅管理员可以访问
    //获取全部用户列表
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUserList")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
