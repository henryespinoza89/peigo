package com.peigo.msvcaccounts.controller;

import com.peigo.msvcaccounts.common.route.Route;
import com.peigo.msvcaccounts.domain.entity.Client;
import com.peigo.msvcaccounts.services.ClientService;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(Route.Client.CREATE_CIENT)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Single<Client> createClient(@RequestBody Client client)
            throws ExecutionException, InterruptedException {
        log.info("Starting {}.{} method", "clientService", "addClient");
        return clientService.createClient(client);
    }
}
