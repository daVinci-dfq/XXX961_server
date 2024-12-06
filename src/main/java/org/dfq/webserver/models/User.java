package org.dfq.webserver.models;

/**
 * 用户类
 */

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * 用户类
 * @author dfq
 * @see UserRepository
 * @see UserService
 */

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // 主键

    @NotBlank
    private String username; // 用户名

    @NotBlank
    private String password; // 密码

    @NotBlank
    private String email; // 邮箱

    @NotBlank
    private String role; // 角色

    public User(Integer Id, String username) {
        this.userId = Id;
        this.username = username;
        this.password = password;
    }

    public User() {

    }


    // 更改密码
    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

}
