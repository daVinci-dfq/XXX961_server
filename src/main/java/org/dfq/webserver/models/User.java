package org.dfq.webserver.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank
    @JoinColumn(name = "user_name")
    private String username;

    @NotBlank
    @JoinColumn(name = "password")
    private String password;

    @NotBlank
    @JoinColumn(name = "phone")
    private String phone;

    @NotBlank
    @JoinColumn(name = "email")
    private String email;

    @NotBlank
    @JoinColumn(name = "role")
    private String role;


    // 更改密码
    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

}
