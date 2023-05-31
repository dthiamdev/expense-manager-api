package com.daouda.expensetracker.service;

import com.daouda.expensetracker.Exception.ItemAlreadyExistException;
import com.daouda.expensetracker.Exception.ResourceNotFoundException;
import com.daouda.expensetracker.entity.User;
import com.daouda.expensetracker.entity.UserModel;
import com.daouda.expensetracker.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;
    @Override
    public User createUser(UserModel userModel) {
        if (userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistException("User already registered with this email:"+userModel.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User readUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResourceNotFoundException("User not found with this name:"+name);
    }

    @Override
    public List<User> readAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    @Override
    public User readUserById() {
        Long userId = getLoggedInUser().getId();
      return  userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with this id:"+userId));
    }

    @Override
    public User updateUser(UserModel user) {
        User existingUser = readUserById();
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User user = readUserById();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

       return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found with this email:"+email));
    }


}
