package com.example.bank.repository;

import com.example.bank.model.DeadTransferMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadTransferMessageRepository extends JpaRepository<DeadTransferMessage, Long> {
}
