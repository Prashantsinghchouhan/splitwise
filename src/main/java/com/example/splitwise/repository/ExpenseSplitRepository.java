package com.example.splitwise.repository;

import com.example.splitwise.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, String> {
}
