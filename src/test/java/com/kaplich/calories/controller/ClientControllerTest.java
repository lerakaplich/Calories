package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        // Arrange
        List<ClientDto> expectedClients = Collections.singletonList(new ClientDto());
        when(clientService.findAllClients()).thenReturn(expectedClients);

        // Act
        List<ClientDto> actualClients = clientController.findAllClients();

        // Assert
        assertEquals(expectedClients, actualClients);
        verify(clientService).findAllClients();
    }

    @Test
    void testSaveClient() {
        // Arrange
        Client client = new Client();
        Client savedClient = new Client();
        when(clientService.saveClient(client)).thenReturn(savedClient);

        // Act
        Client actualClient = clientController.saveClient(client);

        // Assert
        assertEquals(savedClient, actualClient);
        verify(clientService).saveClient(client);
    }

    @Test
    void testFindByClientName() {
        // Arrange
        String clientName = "John";
        ClientDto expectedClientDto = new ClientDto();
        when(clientService.findByClientName(clientName)).thenReturn(expectedClientDto);

        // Act
        ClientDto actualClientDto = clientController.findByClientName(clientName);

        // Assert
        assertEquals(expectedClientDto, actualClientDto);
        verify(clientService).findByClientName(clientName);
    }

    @Test
    void testUpdateClient() {
        // Arrange
        String clientName = "John";
        String newClientName = "Jane";
        Client updatedClient = new Client();
        when(clientService.updateClient(clientName, newClientName)).thenReturn(updatedClient);

        // Act
        Client actualClient = clientController.updateClient(clientName, newClientName);

        // Assert
        assertEquals(updatedClient, actualClient);
        verify(clientService).updateClient(clientName, newClientName);
    }

    @Test
    void testDeleteClient() {
        // Arrange
        String clientName = "John";

        // Act
        assertDoesNotThrow(() -> clientController.deleteClient(clientName));

        // Assert
        verify(clientService).deleteClient(clientName);
    }

    @Test
    void testHardcodedBadRequest() {
        // Arrange & Act & Assert
        assertThrows(HttpClientErrorException.class, clientController::hardcodedBadRequest);
    }

    @Test
    void testHardcodedInternalServerError() {
        // Arrange & Act & Assert
        assertThrows(HttpServerErrorException.class, clientController::hardcodedInternalServerError);
    }
}