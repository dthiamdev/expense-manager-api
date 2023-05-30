package com.daouda.expensetracker.controller;

import com.daouda.expensetracker.entity.Expense;
import com.daouda.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/expenses")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Expense> getAllExpenses(Pageable pageable){
        return expenseService.getAllExpenses(pageable).toList();
    }

    @GetMapping("/expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    @PostMapping("/expenses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Expense saveExpense(@Valid @RequestBody Expense expense){
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense){
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/expenses")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteExpense(@RequestParam Long id){
        expenseService.deleteExpenseById(id);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getAllExpenseByCategory(@RequestParam String category, Pageable pageable){
        return expenseService.readAllExpenseByCategory(category, pageable);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getAllExpenseByName(@RequestParam String keyword,Pageable pageable ){
        return expenseService.readExpenseByName(keyword, pageable);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getAllExpenseByDate(@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate , Pageable pageable ){
        return expenseService.readAllExpenseByDate(startDate, endDate, pageable);
    }
}
