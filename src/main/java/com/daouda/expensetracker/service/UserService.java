package com.daouda.expensetracker.service;

import com.daouda.expensetracker.entity.User;
import com.daouda.expensetracker.entity.UserModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(UserModel userModel);

    User readUserByName(String name);

    User readUserById();
    List<User> readAllUsers(Pageable pageable);

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();


}
