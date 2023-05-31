package com.daouda.expensetracker.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



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
