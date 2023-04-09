package com.peigo.msvcaccounts.services;

import com.google.gson.Gson;
import com.peigo.msvcaccounts.common.exceptions.PaymentException;
import com.peigo.msvcaccounts.domain.entity.Client;
import com.peigo.msvcaccounts.domain.repository.ClientRepository;
import com.peigo.msvcaccounts.services.impl.ClientServiceImpl;
import io.reactivex.observers.TestObserver;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;

    @Test
    @DisplayName("create client")
    void createClient()
            throws IOException, ExecutionException, InterruptedException {
        client = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/createClient.json"),
                                StandardCharsets.UTF_8), Client.class);
        when(clientRepository.save(isA(Client.class))).thenReturn(client);
        Assertions.assertNotNull(client);
        io.reactivex.observers.TestObserver<Client> test = clientService.createClient(client).test();
        test.assertSubscribed();
    }

    @Test
    @DisplayName("create client - without firstName")
    void createClientWithoutName()
            throws IOException, ExecutionException, InterruptedException {
        client = new Gson()
                .fromJson(
                        FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:json/createClientWithoutFirstName.json"),
                                StandardCharsets.UTF_8), Client.class);
        AtomicReference<TestObserver> test = null;
        PaymentException thrown = Assertions.assertThrows(PaymentException.class, () -> {
            Assertions.assertNotNull(client);
            test.set(clientService.createClient(client).test());
        });
        Assertions.assertEquals("The field first name is required", thrown.getMessage());
    }
}
