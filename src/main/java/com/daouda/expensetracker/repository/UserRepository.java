package com.daouda.expensetracker.repository;

import com.daouda.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);



}
