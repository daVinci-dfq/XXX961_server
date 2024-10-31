package org.dfq.webserver.service;

import jakarta.persistence.Access;
import org.dfq.webserver.models.User;
import org.dfq.webserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 用户注册
    public User register(User user) {
        // 检查用户名或邮箱是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null ||
                userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("用户名或邮箱已经被占用!");
        }
        return userRepository.save(user);
    }

    // 用户登录
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("请输入正确的用户名或密码!");
        }
        return user;
    }


    // 修改用户信息
    public User updateUser(Integer userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        return userRepository.save(existingUser);
    }

    // 注销账号
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    // 更改密码
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return existingUser.changePassword(oldPassword, newPassword);
    }
}
