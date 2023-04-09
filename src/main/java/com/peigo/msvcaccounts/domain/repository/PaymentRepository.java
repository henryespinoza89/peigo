package com.peigo.msvcaccounts.domain.repository;

import com.peigo.msvcaccounts.domain.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Long> {
}
