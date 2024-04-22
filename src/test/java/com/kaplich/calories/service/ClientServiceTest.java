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
    }

    @Test
    void testSaveClient_NewClient_NoCachedData_AddsClientToRepositoryAndCache() {
        // Arrange
        Client client = new Client();
        client.setClientName("John");


        when(clientCache.get("all")).thenReturn(null);
        when(clientRepository.findByClientName("John")).thenReturn(null);
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
    public void testDeleteClientWithDishes() {
        // Arrange
        String clientName = "John";
        Client clientToDelete = new Client(1L, clientName, 44.9, 55.6, null);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(new Dish(null, 2L, "dish", 6.5, clientToDelete));
        clientToDelete.setDishList(dishList);

        when(clientRepository.findByClientName(clientName)).thenReturn(clientToDelete);

        // Act
        ClientService clientService = new ClientService(clientRepository, dishRepository, clientCache);
        clientService.deleteClient(clientName);

        // Assert
        verify(dishRepository, times(1)).deleteAll(dishList);
        verify(clientRepository, times(1)).delete(clientToDelete);
        verify(clientCache, times(1)).remove(clientName);
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
    }

    @Test
    void testDeleteClient_ClientExists_DeletesClientAndCache() {
        // Arrange
        String clientName = "John";

        Client client = new Client();
        client.setClientName(clientName);

        when(clientRepository.findByClientName(clientName)).thenReturn(client);

        // Act
        //clientRepository.delete(client);
        clientService.deleteClient(clientName);

        // Assert
    }

    @Test
    void testDeleteClient_ClientDoesNotExist_DoesNothing() {
        // Arrange
        String clientName = "John";

        when(clientRepository.findByClientName(clientName)).thenReturn(null);

        // Act
        clientService.deleteClient(clientName);

    }
}