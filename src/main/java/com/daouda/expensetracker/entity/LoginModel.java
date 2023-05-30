package com.daouda.expensetracker.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginModel {

    @NotBlank(message = "Email should not be null")
    @Email(message = "Email should be a valid email")
    private String email;
    @NotNull(message = "Please provide a password")
    @Size(message = "The password should be at least 5 characters")
    private String password;
}
