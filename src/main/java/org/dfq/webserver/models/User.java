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
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Getter
    @Setter
    @NotBlank
    private String username;

    @Getter
    @NotBlank
    private String password;

    @Getter
    @Setter
    @NotBlank
    private String phone;

    @Getter
    @Setter
    @NotBlank
    private String email;

    @Setter
    @Getter
    @NotBlank
    private String role;

    private Date createTime;

    private Date lastLogin;

    public User() {
    }

    public User(Integer Id, String username) {
        this.userId = Id;
        this.username = username;
        this.password = password;
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
