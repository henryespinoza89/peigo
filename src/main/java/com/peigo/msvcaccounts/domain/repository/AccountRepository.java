package com.peigo.msvcaccounts.domain.repository;

import com.peigo.msvcaccounts.domain.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account,Long> {

    @Query(value = "SELECT * FROM ACCOUNT ACC WHERE ACC.ACCOUNT_NUMBER  = :accountNumber",
            nativeQuery = true)
    Optional<Account> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
