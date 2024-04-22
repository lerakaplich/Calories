package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService service;
    @GetMapping
    public List<ClientDto> findAllClients() {

        return service.findAllClients();
    }

    @PostMapping("/save")
    public Client saveClient(@RequestBody final Client client) {

        return service.saveClient(client);
    }

    @GetMapping("/findByName")
    public ClientDto findByClientName(@RequestParam final String nameOfClient) {

        return service.findByClientName(nameOfClient);
    }

    @PutMapping("/update")
    public Client updateClient(final String clientName,
                               final String newClientName) {

        return service.updateClient(clientName, newClientName);
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestParam final String nameOfClient) {

        service.deleteClient(nameOfClient);
    }
    @GetMapping("/400_error")
    public void hardcodedBadRequest() {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/500_error")
    public void hardcodedInternalServerError() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/bulkSave")
    public void bulkSaveClients(@RequestBody ArrayList<Client> clientList){
        service.bulkSaveClients(clientList);
    }
}
