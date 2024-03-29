package com.example.bank.repository;

import com.example.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select * from Account where user_id = :userId", nativeQuery = true)
    List<Account> getAccountByUserId(@Param("userId") Long userId);

    boolean existsAccountsByUserId(Long userId);

    @Query(value = "select * from Account a where a.account_number = :destinationAccount", nativeQuery = true)
    Account getUserIdByAccountNumber(@Param("destinationAccount") String destinationAccount);

    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Account findByAccountNumber(@Param("accountNumber") String accountNumber);

    void deleteAccountByAccountNumber(String accountNumber);

    boolean existsAccountByAccountNumberEquals(String accountNumber);
}
