package com.example.gamestorespring.model;

import com.example.gamestorespring.entity.RoleEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public class UserModel {
    private long id;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @Email
    private String email;
    private String phone;
    private String name;
    private String surname;

    private RoleEntity role;

    @AssertTrue(message = "Field2 should be greater than Field1")
    public boolean isField2GreaterThanField1() {
        return password.equals(repeatPassword);
    }

    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserModel() {
    }
}
