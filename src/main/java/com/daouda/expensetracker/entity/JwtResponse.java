package com.daouda.expensetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse {

    private final String jwtToken;
}
