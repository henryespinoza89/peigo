package com.peigo.msvcaccounts.domain.repository;

import com.peigo.msvcaccounts.domain.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> {

    Client findClientByDni(String dni);
}
