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
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CacheEntity clientCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository, dishRepository, clientCache);
    }

    @Test
    void testFindAllClients_WithCachedData() {
        // Arrange
        List<ClientDto> cachedData = new ArrayList<>();
        cachedData.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedData);

        // Act
        List<ClientDto> clients = clientService.findAllClients();

        // Assert
        assertEquals(cachedData, clients);
    }

    @Test
    void testFindAllClients_WithoutCachedData() {
        // Arrange
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);
        when(clientCache.get("all")).thenReturn(null);

        // Act
        List<ClientDto> clients = clientService.findAllClients();

        // Assert
        assertEquals(clientList.size(), clients.size());
        verify(clientCache).put(eq("all"), anyList());
    }

    @Test
    void testSaveClient_InvalidClient() {
        // Arrange
        Client invalidClient = new Client();

        // Act
        Client result = clientService.saveClient(invalidClient);

        // Assert
        assertNull(result);
        verify(clientRepository, never()).save(any());
        verify(clientCache, never()).put(any(), any());
    }

    @Test
    void testSaveClient_ExistingClient() {
        // Arrange
        String clientName = "John";
        Client existingClient = new Client();
        existingClient.setClientName(clientName);
        when(clientRepository.findByClientName(clientName)).thenReturn(existingClient);

        // Act
        Client result = clientService.saveClient(existingClient);

        // Assert
        assertEquals(existingClient, result);
        verify(clientRepository, never()).save(any());
        verify(clientCache, never()).put(any(), any());
    }

    @Test
    void testSaveClient_NewClientWithExistingDish() {
        // Arrange
        String clientName = "John";
        String dishName = "Pizza";
        Dish existingDish = new Dish();
        existingDish.setDishName(dishName);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(existingDish);

        Client newClient = new Client();
        newClient.setClientName(clientName);
        newClient.setDishList(dishList);

        when(clientRepository.findByClientName(clientName)).thenReturn(null);
        when(dishRepository.findByDishName(dishName)).thenReturn(existingDish);

        // Act
        Client result = clientService.saveClient(newClient);

        // Assert
        assertEquals(newClient, result);
        verify(clientRepository, never()).save(any());
        verify(clientCache, never()).put(any(), any());
    }

    @Test
    void testSaveClient_NewClientWithNewDish() {
        // Arrange
        String clientName = "John";
        String dishName = "Pizza";
        Dish newDish = new Dish();
        newDish.setDishName(dishName);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(newDish);

        Client newClient = new Client();
        newClient.setClientName(clientName);
        newClient.setDishList(dishList);

        when(clientRepository.findByClientName(clientName)).thenReturn(null);
        when(dishRepository.findByDishName(dishName)).thenReturn(null);

        // Act
        Client result = clientService.saveClient(newClient);

        // Assert
        assertEquals(newClient, result);
        verify(clientRepository).save(newClient);
        verify(clientCache).put(clientName, newClient);
    }

    @Test
    void testFindByClientName_WithCachedData() {
        // Arrange
        String clientName = "John";
        ClientDto cachedData = new ClientDto();
        when(clientCache.get(clientName)).thenReturn(cachedData);

        // Act
        ClientDto result = clientService.findByClientName(clientName);

        // Assert
        assertEquals(cachedData, result);
    }

    @Test
    void testFindByClientName_WithoutCachedData() {
        // Arrange
        String clientName = "John";
        Client client = new Client();
        when(clientRepository.findByClientName(clientName)).thenReturn(client);
        when(clientCache.get(clientName)).thenReturn(null);

        // Act
        ClientDto result = clientService.findByClientName(clientName);

        // Assert
        assertEquals(ClientMapper.toDto(client), result);
        verify(clientCache).put(clientName, ClientMapper.toDto(client));
    }

    @Test
    void testUpdateClient_ExistingClient() {
        // Arrange
        String clientName = "John";
        String newClientName = "Jack";
        Client existingClient = new Client();
        existingClient.setClientName(clientName);
        when(clientRepository.findByClientName(clientName)).thenReturn(existingClient);

        // Act
        Client result = clientService.updateClient(clientName, newClientName);

        // Assert
        assertEquals(newClientName, result.getClientName());
        verify(clientCache).remove(clientName);
        verify(clientCache).put(newClientName, ClientMapper.toDto(result));
    }

    @Test
    void testUpdateClient_NonExistingClient() {
        // Arrange
        String clientName = "John";
        String newClientName = "Jack";
        when(clientRepository.findByClientName(clientName)).thenReturn(null);

        // Act
        Client result = clientService.updateClient(clientName, newClientName);

        // Assert
        assertNull(result);
        verify(clientCache, never()).remove(any());
        verify(clientCache, never()).put(any(), any());
    }

    @Test
    void testDeleteClient_ExistingClient() {
        // Arrange
        String clientName = "John";
        Client existingClient = new Client();
        existingClient.setClientName(clientName);
        when(clientRepository.findByClientName(clientName)).thenReturn(existingClient);

        // Act
        clientService.deleteClient(clientName);

        // Assert
        verify(dishRepository).deleteAll(existingClient.getDishList());
        verify(clientRepository).delete(existingClient);
        verify(clientCache).remove(clientName);
    }

    @Test
    void testDeleteClient_NonExistingClient() {
        // Arrange
        String clientName = "John";
        when(clientRepository.findByClientName(clientName)).thenReturn(null);

        // Act
        clientService.deleteClient(clientName);

        // Assert
        verify(dishRepository, never()).deleteAll(any());
        verify(clientRepository, never()).delete(any());
        verify(clientCache, never()).remove(any());
    }
}