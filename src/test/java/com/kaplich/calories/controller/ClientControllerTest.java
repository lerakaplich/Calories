package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        clientController = new ClientController(clientService);
    }

    @Test
    void testFindAllClients() {
        List<ClientDto> expectedClients = new ArrayList<>();
        when(clientService.findAllClients()).thenReturn(expectedClients);

        List<ClientDto> result = clientController.findAllClients();

        assertEquals(expectedClients, result);
        verify(clientService, times(1)).findAllClients();
    }

    @Test
    void testSaveClient() {
        Client client = new Client();
        Client savedClient = new Client();
        when(clientService.saveClient(client)).thenReturn(savedClient);

        Client result = clientController.saveClient(client);

        assertEquals(savedClient, result);
        verify(clientService, times(1)).saveClient(client);
    }

    @Test
    void testFindByClientName() {
        String clientName = "John Doe";
        ClientDto expectedClientDto = new ClientDto();
        when(clientService.findByClientName(clientName)).thenReturn(expectedClientDto);

        ClientDto result = clientController.findByClientName(clientName);

        assertEquals(expectedClientDto, result);
        verify(clientService, times(1)).findByClientName(clientName);
    }

    @Test
    void testUpdateClient() {
        String clientName = "John Doe";
        String newClientName = "Jane Smith";
        Client updatedClient = new Client();
        when(clientService.updateClient(clientName, newClientName)).thenReturn(updatedClient);

        Client result = clientController.updateClient(clientName, newClientName);

        assertEquals(updatedClient, result);
        verify(clientService, times(1)).updateClient(clientName, newClientName);
    }

    @Test
    void testDeleteClient() {
        String clientName = "John Doe";

        clientController.deleteClient(clientName);

        verify(clientService, times(1)).deleteClient(clientName);
    }

    @Test
    void testHardcodedBadRequest() {
        assertThrows(HttpClientErrorException.class, () -> clientController.hardcodedBadRequest());
    }

    @Test
    void testHardcodedInternalServerError() {
        assertThrows(HttpServerErrorException.class, () -> clientController.hardcodedInternalServerError());
    }
}