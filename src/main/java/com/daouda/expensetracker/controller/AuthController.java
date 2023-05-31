package com.daouda.expensetracker.controller;

import com.daouda.expensetracker.entity.JwtResponse;
import com.daouda.expensetracker.entity.LoginModel;
import com.daouda.expensetracker.entity.User;
import com.daouda.expensetracker.entity.UserModel;
import com.daouda.expensetracker.security.CustomUserDetailsService;
import com.daouda.expensetracker.service.UserService;
import com.daouda.expensetracker.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    private ResponseEntity<User> register(@Valid @RequestBody UserModel user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    private ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {

        // to authenticate the user with spring security
        authenticate(login.getEmail(),login.getPassword());

        // we need to generate jwt token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        final  String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

        }catch (DisabledException e){
            throw new Exception("User disabled");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad credentials");
        }
    }


}
