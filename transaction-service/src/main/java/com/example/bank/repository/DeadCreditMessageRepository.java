package com.example.bank.repository;

import com.example.bank.model.DeadCreditMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadCreditMessageRepository extends JpaRepository<DeadCreditMessage, Long> {
}
