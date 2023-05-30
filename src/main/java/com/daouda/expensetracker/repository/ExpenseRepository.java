package com.daouda.expensetracker.repository;

import com.daouda.expensetracker.entity.Expense;
import com.daouda.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable pageable);

    Page<Expense> findByUserIdAndNameContaining(Long userId, String name, Pageable pageable);

    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable pageable);

    Page<Expense> findByUserId(Long userId, Pageable pageable);
    Optional<Expense> findByUserIdAndId(Long userId, Long id);


}
