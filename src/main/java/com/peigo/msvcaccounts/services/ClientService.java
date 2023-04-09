package com.peigo.msvcaccounts.services;

import com.peigo.msvcaccounts.domain.entity.Client;
import io.reactivex.Single;

import java.util.concurrent.ExecutionException;

public interface ClientService {

    Single<Client> createClient(Client client) throws ExecutionException, InterruptedException;
}
