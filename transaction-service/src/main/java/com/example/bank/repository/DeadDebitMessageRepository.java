package com.example.bank.repository;

import com.example.bank.model.DeadDeductMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadDebitMessageRepository extends JpaRepository<DeadDeductMessage, Long> {
}
