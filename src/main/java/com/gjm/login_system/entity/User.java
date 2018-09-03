package com.gjm.login_system.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Username field can't be empty")
    private String username;

    @NotEmpty(message = "Password field can't be empty")
    private String password;

    @Email(message = "Give valid e-mail")
    @NotEmpty(message = "E-mail field can't be empty")
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
