package com.daouda.expensetracker.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class LoginModel {

    @NotBlank(message = "Email should not be null")
    @Email(message = "Email should be a valid email")
    private String email;
    @NotNull(message = "Please provide a password")
    @Size(message = "The password should be at least 5 characters")
    private String password;
}
