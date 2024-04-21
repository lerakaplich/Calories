package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.mapper.ClientMapper;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.repository.ClientRepository;
import com.kaplich.calories.repository.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;
    private CacheEntity clientCache;

    public List<ClientDto> findAllClients() {
        Object cachedObject = clientCache.get("all");
        if (cachedObject instanceof List<?> list
                && !list.isEmpty() && list.get(0) instanceof ClientDto) {
            return (List<ClientDto>) list;
        }

        List<ClientDto> clientDtoList = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            ClientDto clientDto = ClientMapper.toDto(client);
            clientDtoList.add(clientDto);
        }
        clientCache.put("all", clientDtoList);
        return clientDtoList;
    }

    public Client saveClient(final Client client) {

        if (client == null || client.getClientName() == null
                || client.getClientName().isEmpty()) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        Object cachedObject = clientCache.get("all");
        if (cachedObject instanceof List<?> list && !list.isEmpty()
                && list.get(0) instanceof Client) {
            return (Client) list;
        }

        Client clientT = clientRepository.
                findByClientName(client.getClientName());
        if (clientT != null) {
            for (Dish dish : client.getDishList()) {
                Dish dishT = dishRepository.
                        findByDishName(dish.getDishName());
                if (dishT == null) {
                    client.getDishList().add(dish);
                    clientCache.remove(client.getClientName());
                    clientCache.put("all", ClientMapper.toDto(client));
                }
            }
        } else {
            clientRepository.save(client);
            clientCache.put(client.getClientName(), client);
        }
        return client;
    }

    public ClientDto findByClientName(final String nameOfClient) {
        Object cachedObject = clientCache.get(nameOfClient);
        if (cachedObject instanceof ClientDto clientDto) {
            return clientDto;
        }
        Client client = clientRepository.findByClientName(nameOfClient);
        return ClientMapper.toDto(client);
    }

    public Client updateClient(final String clientName,
                               final String newClientName) {
        Client newClient = clientRepository.findByClientName(clientName);
        if (newClient == null) {
            return null;
        }
        newClient.setClientName(newClientName);
        clientCache.remove(clientName);
        clientCache.put(newClientName, ClientMapper.toDto(newClient));
        return newClient;
    }

    public void deleteClient(final String nameOfClient) {
        Client clientToDelete = clientRepository.findByClientName(nameOfClient);
        if (clientToDelete != null) {
            List<Dish> dishToDelete = clientToDelete.getDishList();
            if (dishToDelete != null && !dishToDelete.isEmpty()) {

                dishRepository.deleteAll(dishToDelete);

            }
            clientRepository.delete(clientToDelete);
            clientCache.remove(clientToDelete.getClientName());
        }
    }

}

