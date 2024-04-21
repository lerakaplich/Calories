package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Dish;

import java.util.ArrayList;
import java.util.List;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientDto toDto(final Client client) {
        if (client == null) {
            return null;
        }
        List<String> dishDtoList = new ArrayList<>();
        for (Dish dish : client.getDishList()) {
            dishDtoList.add(dish.getDishName());
        }
        return ClientDto.builder()
                .clientName(client.getClientName())
                .height(client.getHeight())
                .weight(client.getWeight())
                .dishDtoList(dishDtoList)
                .build();
    }

    public static Client toEntity(final ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client client = new Client();
        client.setClientName(clientDto.getClientName());
        client.setWeight(clientDto.getWeight());
        client.setHeight(clientDto.getHeight());

        if (clientDto.getDishDtoList() == null) {
            return client;
        }

        List<Dish> dishes = new ArrayList<>();
        client.setDishList(dishes);
        return client;
    }
}
