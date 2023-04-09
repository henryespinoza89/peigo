package com.peigo.msvcaccounts.services.impl;

import com.peigo.msvcaccounts.common.exceptions.PaymentException;
import com.peigo.msvcaccounts.common.utils.Utils;
import com.peigo.msvcaccounts.domain.entity.Account;
import com.peigo.msvcaccounts.domain.entity.Payment;
import com.peigo.msvcaccounts.domain.repository.AccountRepository;
import com.peigo.msvcaccounts.domain.repository.PaymentRepository;
import com.peigo.msvcaccounts.domain.response.PaymentResponse;
import com.peigo.msvcaccounts.services.PaymentService;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.peigo.msvcaccounts.common.utils.Constant.*;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Single<PaymentResponse> paymentTransaction(Payment payment) throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "PaymentServiceImpl", "paymentTransaction");
        return validateTransactionPayment(payment)
                .map(o -> {
                    paymentRepository.save(payment);
                    return Single.just(createCustomResponse(payment));
                }).toFuture().get();
    }

    private Single<Payment> validateTransactionPayment(Payment payment) {
        log.info("Starting {}.{} method", "PaymentServiceImpl", "validateTransactionPayment");
        if(payment.getAccountOrigin() == null || payment.getAccountOrigin().trim().isEmpty() || payment.getAccountOrigin().isEmpty()) throw new PaymentException(ACCOUNT_ORIGIN_REQUIRED);
        if(payment.getAccountDestination() == null || payment.getAccountDestination().trim().isEmpty() || payment.getAccountDestination().isEmpty()) throw new PaymentException(ACCOUNT_DESTINATION_REQUIRED);
        if(payment.getBalanceTransfer() == null || (payment.getBalanceTransfer().compareTo(BigDecimal.ZERO) < 0)) throw new PaymentException(BALANCE_ERROR);
        return completeTransaction(payment);
    }

    private Single<Payment> completeTransaction(Payment payment) {
        Account accountOrigin;
        Account accountDestination;

        accountOrigin = findAccountWithAccountNumber(payment.getAccountOrigin());
        payment.setBalanceAccountOrigin(accountOrigin.getBalance().subtract(payment.getBalanceTransfer()));
        accountOrigin.setBalance(accountOrigin.getBalance().subtract(payment.getBalanceTransfer()));
        accountRepository.save(accountOrigin);

        accountDestination = findAccountWithAccountNumber(payment.getAccountDestination());
        payment.setBalanceAccountDestination(payment.getBalanceTransfer().add(accountDestination.getBalance()));
        accountDestination.setBalance(payment.getBalanceTransfer().add(accountDestination.getBalance()));
        accountRepository.save(accountDestination);

        payment.setOperationNumber(Utils.generateNumberOperarion());
        return Single.just(payment);
    }

    private Account findAccountWithAccountNumber(String account) {
        Optional<Account> findAccountNumber = Optional
                .ofNullable(accountRepository.findByAccountNumber(account)
                        .orElseThrow(() -> new PaymentException(ACCOUNT_NOT_FOUND)));
        return findAccountNumber.get();
    }

    private PaymentResponse createCustomResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setOperationNumber(payment.getOperationNumber());
        response.setBalanceAccountOrigin(payment.getBalanceAccountOrigin());
        response.setBalanceAccountDestination(payment.getBalanceAccountDestination());
        return response;
    }
}
