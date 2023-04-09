package com.peigo.msvcaccounts.controller;

import com.peigo.msvcaccounts.domain.entity.Payment;
import com.peigo.msvcaccounts.domain.response.PaymentResponse;
import com.peigo.msvcaccounts.services.PaymentService;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<PaymentResponse> paymentTransaction(@RequestBody Payment payment)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentController", "paymentTransaction");
        return paymentService.paymentTransaction(payment);
    }
}
