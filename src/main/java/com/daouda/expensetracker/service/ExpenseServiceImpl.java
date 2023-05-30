package com.daouda.expensetracker.service;

import com.daouda.expensetracker.Exception.ResourceNotFoundException;
import com.daouda.expensetracker.entity.Expense;
import com.daouda.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserService userService;
    @Override
    public Page<Expense> getAllExpenses(Pageable pageable) {

        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),pageable);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if (expense.isPresent()){
            return  expense.get();
        }
        throw new ResourceNotFoundException("Expense does not exist with this id:"+id);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());

        return expenseRepository.save(existingExpense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);

    }

    @Override
    public Expense saveExpense(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> readExpenseByName(String keyword, Pageable pageable){
        return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword,pageable).toList();
    }

    @Override
    public List<Expense> readAllExpenseByCategory(String category, Pageable pageable) {
        return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category,pageable).toList();
    }

    @Override
    public List<Expense> readAllExpenseByDate(Date startDate,Date endDate, Pageable pageable) {
        if (startDate == null){
            startDate = new Date(0);
        }
        if (endDate ==  null){
            endDate = new Date(System.currentTimeMillis());
        }
        return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate,endDate,pageable).toList();
    }
}
