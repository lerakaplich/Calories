package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


}