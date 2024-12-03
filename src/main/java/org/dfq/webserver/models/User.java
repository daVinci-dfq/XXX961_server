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
@Table(name = "user")
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

    //头像avatar   修改
    //个性签名      修改

    public User(Integer Id, String username) {
        this.userId = Id;
        this.username = username;
    }
    public User() {}
}
