package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.mapper.ClientMapper;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.repository.ClientRepository;
import com.kaplich.calories.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CacheEntity clientCache;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository, dishRepository, clientCache);
    }

    @Test
    void findAllClients_ShouldReturnClientDtoList() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setClientName("John");
        List<Client> clients = Collections.singletonList(client);

        when(clientRepository.findAll()).thenReturn(clients);
    }

    @Test
    void saveClient_WithExistingClient_ShouldUpdateClientInCache() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setClientName("John");
        client.setDishList(Collections.emptyList());

        when(clientRepository.findByClientName(client.getClientName())).thenReturn(client);

        // Act
        Client result = clientService.saveClient(client);

        // Assert
        assertEquals(client, result);
        // Add more assertions based on the expected behavior
    }

    @Test
    void saveClient_WithNonExistingClient_ShouldSaveClientInRepositoryAndCache() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setClientName("John");
        client.setDishList(Collections.emptyList());

        when(clientRepository.findByClientName(client.getClientName())).thenReturn(null);

        // Act
        Client result = clientService.saveClient(client);

        // Assert
        assertEquals(client, result);
        verify(clientRepository).save(client);
        verify(clientCache).put(client.getClientName(), client);
        // Add more assertions based on the expected behavior
    }

    @Test
    void findByClientName_WithCachedClientDto_ShouldReturnCachedClientDto() {
        // Arrange
        String clientName = "John";
        ClientDto cachedClientDto = new ClientDto();
        cachedClientDto.setClientName(clientName);

        when(clientCache.get(clientName)).thenReturn(cachedClientDto);

        // Act
        ClientDto result = clientService.findByClientName(clientName);

        // Assert
        assertEquals(cachedClientDto, result);
        verify(clientRepository, never()).findByClientName(clientName);
        // Add more assertions based on the expected behavior
    }

    @Test
    void findByClientName_WithNonCachedClientDto_ShouldReturnClientDtoFromRepository() {
        // Arrange
        String clientName = "John";
        Client client = new Client();
        client.setClientName(clientName);

        when(clientCache.get(clientName)).thenReturn(null);
        when(clientRepository.findByClientName(clientName)).thenReturn(client);

        // Add more assertions based on the expected behavior
    }

    @Test
    void updateClient_WithExistingClient_ShouldUpdateClientAndCache() {
        // Arrange
        String clientName = "John";
        String newClientName = "Johnny";
        Client existingClient = new Client();
        existingClient.setClientName(clientName);
        Client updatedClient = new Client();
        updatedClient.setClientName(clientName);

        when(clientRepository.findByClientName(clientName)).thenReturn(existingClient);
    }

    @Test
    void updateClient_WithNonExistingClient_ShouldThrowException() {
        // Arrange
        String clientName = "John";
        String newClientName = "Johnny";

        when(clientRepository.findByClientName(clientName)).thenReturn(null);
        // Add more assertions based on the expected behavior
    }

    // Add more test methods to achieve 100% code coverage

}