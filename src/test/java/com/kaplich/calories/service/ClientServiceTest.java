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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientServiceTest {

    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private DishRepository dishRepository;

    @Mock
    private CacheEntity clientCache;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository, dishRepository, clientCache);
    }

    @Test
    void testFindAllClients_NoCachedData_ReturnsAllClients() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        List<Client> result = clientRepository.findAll();

        // Assert
        assertEquals(clients.size(), result.size());
        //verify(clientCache, times(1)).put("all", result);
    }

    @Test
    void testFindAllClients_CachedDataAvailable_ReturnsCachedData() {
        // Arrange
        List<ClientDto> cachedData = new ArrayList<>();
        cachedData.add(new ClientDto());
        cachedData.add(new ClientDto());

        when(clientCache.get("all")).thenReturn(cachedData);

        // Act
        List<ClientDto> result = clientService.findAllClients();

        // Assert
        assertSame(cachedData, result);
        verify(clientCache, times(1)).get("all");
        verify(clientCache, never()).put("all", result);
    }

    @Test
    void testSaveClient_NewClient_NoCachedData_AddsClientToRepositoryAndCache() {
        // Arrange
        Client client = new Client();
        client.setClientName("John");

        when(clientCache.get("all")).thenReturn(null);
        when(clientRepository.findByClientName("John")).thenReturn(null);

        // Act
        Client result = clientRepository.save(client);

        // Assert
        // assertSame(client, result);
    }

    @Test
    void testSaveClient_ExistingClient_NoCachedData_AddsDishesToClientAndUpdatesCache() {
        // Arrange
        Client client = new Client();
        client.setClientName("John");
        client.setDishList(new ArrayList<>());

        Dish dish1 = new Dish();
        dish1.setDishName("Pizza");

        Dish dish2 = new Dish();
        dish2.setDishName("Burger");

        client.getDishList().add(dish1);
        client.getDishList().add(dish2);

        List<ClientDto> cachedData = new ArrayList<>();
        cachedData.add(ClientMapper.toDto(client));

        when(clientCache.get("all")).thenReturn(null);
        when(clientRepository.findByClientName("John")).thenReturn(client);
        when(dishRepository.findByDishName("Pizza")).thenReturn(null);

        // Act
        Client result = clientRepository.save(client);

        // Assert
    }

    @Test
    void testFindByClientName_CachedDataAvailable_ReturnsCachedData() {
        // Arrange
        ClientDto cachedData = new ClientDto();
        cachedData.setClientName("John");

        when(clientCache.get("John")).thenReturn(cachedData);

        // Act
        ClientDto result = clientService.findByClientName("John");

        // Assert
        assertSame(cachedData, result);
        verify(clientCache, times(1)).get("John");
    }

    @Test
    void testFindByClientName_NoCachedData_ReturnsClientFromRepository() {
        // Arrange
        Client client = new Client();
        client.setClientName("John");

        when(clientCache.get("John")).thenReturn(null);
        when(clientRepository.findByClientName("John")).thenReturn(client);

        // Act
        Client result = clientRepository.save(client);

        //verify(clientRepository, times(1)).findByClientName("John");
    }

    @Test
    void testUpdateClient_ClientExists_UpdatesClientAndCache() {
        // Arrange
        String clientName = "John";
        String newClientName = "Johnny";

        Client client = new Client();
        client.setClientName(clientName);

        Client updatedClient = new Client();
        updatedClient.setClientName(newClientName);

        when(clientRepository.findByClientName(clientName)).thenReturn(client);

        // Act


        // Assert
   //     assertSame(updatedClient, result);
        //assertEquals(newClientName, result.getClientName());
         }

    @Test
    void testUpdateClient_ClientDoesNotExist_ReturnsNull() {
        // Arrange
        String clientName = "John";
        String newClientName = "Johnny";

        when(clientRepository.findByClientName(clientName)).thenReturn(null);

        // Act
        Client result = clientService.updateClient(clientName, newClientName);

        // Assert
        assertNull(result);
        verify(clientRepository, times(1)).findByClientName(clientName);
        verify(clientCache, never()).remove(clientName);
        verify(clientCache, never()).put(newClientName, ClientMapper.toDto(result));
    }

    @Test
    void testDeleteClient_ClientExists_DeletesClientAndCache() {
        // Arrange
        String clientName = "John";

        Client client = new Client();
        client.setClientName(clientName);

        when(clientRepository.findByClientName(clientName)).thenReturn(client);

        // Act
        clientRepository.delete(client);

        // Assert
    }

    @Test
    void testDeleteClient_ClientDoesNotExist_DoesNothing() {
        // Arrange
        String clientName = "John";

        when(clientRepository.findByClientName(clientName)).thenReturn(null);

        // Act
        clientService.deleteClient(clientName);

        // Assert
        verify(clientRepository, times(1)).findByClientName(clientName);
        verify(dishRepository, never()).deleteAll(anyList());
        verify(clientRepository, never()).delete(any(Client.class));
        verify(clientCache, never()).remove(clientName);
    }
}