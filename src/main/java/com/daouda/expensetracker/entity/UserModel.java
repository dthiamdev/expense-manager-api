package com.daouda.expensetracker.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {

    @NotBlank(message = "name should not be null")
    private String name;

    @NotNull(message = "Please provide a password")
    @Size(message = "The password should be at least 5 characters")
    private String password;

    @NotNull(message = "Please provide a email")
    @Email(message = "Please provide a valid email")
    private String email;

    private Long age = 0L;
}
