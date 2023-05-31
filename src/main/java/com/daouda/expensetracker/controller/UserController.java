package com.daouda.expensetracker.controller;

import com.daouda.expensetracker.entity.User;
import com.daouda.expensetracker.entity.UserModel;
import com.daouda.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    private ResponseEntity<List<User>> readAllUsers(Pageable pageable){
        return new ResponseEntity<List<User>>(userService.readAllUsers(pageable),HttpStatus.OK);
    }

    @GetMapping("/users/name")
    private ResponseEntity<User> readUserByName(@RequestParam String name){
        return new ResponseEntity<User>(userService.readUserByName(name),HttpStatus.OK);
    }

    @GetMapping("/profile")
    private ResponseEntity<User> readUser(){
        return new ResponseEntity<User>(userService.readUserById(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    private ResponseEntity<User> updateUser(@RequestBody UserModel user){
        return new ResponseEntity<User>(userService.updateUser(user),HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    private ResponseEntity<HttpStatus> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
