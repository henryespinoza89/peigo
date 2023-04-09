package com.peigo.msvcaccounts.services.impl;

import com.peigo.msvcaccounts.common.exceptions.PaymentException;
import com.peigo.msvcaccounts.common.utils.Utils;
import com.peigo.msvcaccounts.domain.entity.Account;
import com.peigo.msvcaccounts.domain.entity.Client;
import com.peigo.msvcaccounts.domain.repository.ClientRepository;
import com.peigo.msvcaccounts.services.ClientService;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.peigo.msvcaccounts.common.utils.Constant.*;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Single<Client> createClient(Client client) throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "ClientServiceImpl", "createClient");
        return validateRequestClient(client)
            .map(cus -> {
                clientRepository.save(client);
                return Single
                    .just(client)
                    .doOnError(throwable ->
                            log.info("Error {}.{} method, with error {}", "ClientServiceImpl",
                                    "createClient", throwable.getMessage()));
            }).toFuture().get();
    }

    private Single<Client> validateRequestClient(Client client){
        if (client.getFirstName() == null || client.getFirstName().trim().isEmpty() || client.getFirstName().isEmpty()) throw new PaymentException(CUSTOMER_FIRST_NAME_REQUIRED);
        if (client.getLastName() == null || client.getLastName().trim().isEmpty() || client.getLastName().isEmpty()) throw new PaymentException(CUSTOMER_LAST_NAME_REQUIRED);
        if (client.getDni() == null || client.getDni().trim().isEmpty() || client.getDni().isEmpty()) throw new PaymentException(CUSTOMER_DNI_REQUIRED);
        Client dniFindDataBase =  clientRepository.findClientByDni(client.getDni());
        if (dniFindDataBase != null) {
            throw new PaymentException(CUSTOMER_WITH_DNI_EXISTING);
        }
        return associateClientWithAccountNumber(client);
    }

    private Single<Client> associateClientWithAccountNumber(Client client) {
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setClient(client);
        account.setAccountNumber(Utils.generateBankAccount());
        accountList.add(account);

        client.setAccount(accountList);
        return Single.just(client);
    }
}
