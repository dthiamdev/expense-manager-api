package com.daouda.expensetracker.service;

import com.daouda.expensetracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {

    Page<Expense> getAllExpenses(Pageable pageable);

    Expense getExpenseById(Long id);

    Expense updateExpense(Long id, Expense expense);

    void deleteExpenseById(Long id);

    Expense saveExpense(Expense expense);

    List<Expense> readExpenseByName(String name, Pageable pageable);

    List<Expense> readAllExpenseByCategory(String category, Pageable pageable);

    List<Expense> readAllExpenseByDate(Date startDate, Date endDate, Pageable pageable);
}
