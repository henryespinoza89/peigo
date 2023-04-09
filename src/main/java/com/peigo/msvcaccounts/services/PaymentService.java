package com.peigo.msvcaccounts.services;

import com.peigo.msvcaccounts.domain.entity.Payment;
import com.peigo.msvcaccounts.domain.response.PaymentResponse;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface PaymentService {
    Single<PaymentResponse> paymentTransaction(Payment payment) throws ExecutionException, InterruptedException;
}
