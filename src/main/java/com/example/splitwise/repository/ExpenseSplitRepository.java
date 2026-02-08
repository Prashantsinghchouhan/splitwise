package com.example.splitwise.repository;

import com.example.splitwise.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, String> {
    List<ExpenseSplit> findByExpenseId(String expenseId);
}
