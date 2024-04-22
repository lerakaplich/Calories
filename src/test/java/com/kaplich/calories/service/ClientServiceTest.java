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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Mock
    private ClientMapper clientMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository, dishRepository, clientCache);
    }

    @Test
    void testFindAllClientsFromCache() {
        // Arrange
        List<ClientDto> cachedList = new ArrayList<>();
        cachedList.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedList);

    }

    @Test
    void testFindAllClientsWithoutCache() {
        // Arrange
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);
        when(clientCache.get("all")).thenReturn(null);
    }

    @Test
    void testFindAllClients_WithCachedData() {
        // Arrange
        List<ClientDto> cachedData = new ArrayList<>();
        cachedData.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedData);
    }

    @Test
    void testFindAllClients_WithoutCachedData() {
        // Arrange
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        clientService.findAllClients();
        when(clientRepository.findAll()).thenReturn(clientList);
        when(clientCache.get("all")).thenReturn(null);
    }
    @Test
    void testMappingClientsToClientDtoList() {
        // Arrange
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);
        //when(clientMapper.)

        ClientService clientService = new ClientService(clientRepository, dishRepository, clientCache);

    }






    @Test
    void testFindAllClients_CacheHitWithInvalidList() {
        List<String> cachedList = new ArrayList<>();
        cachedList.add("invalid");
        when(clientCache.get("all")).thenReturn(cachedList);

        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);


    }

    @Test
    void testFindAllClients_CacheHitWithValidList() {
        List<ClientDto> cachedList = new ArrayList<>();
        cachedList.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedList);
    }

    @Test
    void testFindAllClients_CacheMiss() {
        when(clientCache.get("all")).thenReturn(null);

        List<Client> clientList = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(clientList);

        List<ClientDto> expectedDtoList = Arrays.asList(new ClientDto(), new ClientDto());
    }

    @Test
    void testSaveClient_NullClient() {
        Client savedClient = clientRepository.save(new Client());

    }

    @Test
    void testSaveClient_EmptyClientName() {
        Client client = new Client();
        client.setClientName("");

        Client savedClient = clientService.saveClient(client);

    }

    @Test
    void testSaveClient_CacheHitWithValidList() {
        List<Client> cachedList = new ArrayList<>();
        cachedList.add(new Client());
        when(clientCache.get("all")).thenReturn(cachedList);

        Client savedClient = clientRepository.save(new Client());

    }

    @Test
    void testSaveClient_ClientAlreadyExists() {
        Client existingClient = new Client();
        existingClient.setClientName("existingClient");
        when(clientRepository.findByClientName("existingClient")).thenReturn(existingClient);

        Dish dish = new Dish();
        dish.setDishName("newDish");
        Client client = new Client();
        client.setClientName("existingClient");
        client.setDishList(Arrays.asList(dish));

        Client savedClient = clientRepository.save(new Client());

    }

    @Test
    void testSaveClient_NewClient() {
        when(clientRepository.findByClientName("newClient")).thenReturn(null);

        Dish dish = new Dish();
        dish.setDishName("newDish");
        Client client = new Client();
        client.setClientName("newClient");
        client.setDishList(Arrays.asList(dish));

        Client savedClient = clientService.saveClient(client);

        assertNotNull(savedClient);
        assertEquals(client, savedClient);
        verify(clientRepository, times(1)).save(client);
        verify(clientCache, times(1)).put("newClient", client);
        verify(dishRepository, never()).findByDishName(anyString());
    }

    @Test
    void testFindAllClients_CacheHitWithEmptyList() {
        List<ClientDto> cachedList = new ArrayList<>();
        when(clientCache.get("all")).thenReturn(cachedList);

        List<Client> clientList = new ArrayList<>();
        when(clientRepository.findAll()).thenReturn(clientList);

        List<ClientDto> clientDtoList = clientService.findAllClients();

        assertNotNull(clientDtoList);
        assertTrue(clientDtoList.isEmpty());

        verify(clientRepository, times(1)).findAll();
        verify(clientCache, times(1)).put("all", clientDtoList);
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