package com.example.bank.repository;

import com.example.bank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT t.* FROM transaction t JOIN account a ON t.destination_account_number = a.account_number " +
            "or t.source_account_number = a.account_number " +
            "WHERE a.user_id = :user_id ORDER BY t.transaction_date DESC",
            countQuery = "SELECT COUNT(*) FROM transaction t JOIN account a ON t.destination_account_number = a.account_number " +
                    "WHERE a.user_id = :user_id",
            nativeQuery = true)
    Page<Transaction> getAllTransactionOfAnUser(@Param("user_id") Long user_id, Pageable pageable);

}
