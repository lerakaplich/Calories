package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.service.ClientService;
import com.kaplich.calories.service.CounterService;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;
    static final Logger LOGGER = LogManager.getLogger(ClientController.class);
    private static final String CLIENTS_VIEW = "clients";
    private static final String HOME_VIEW = "home";

    @GetMapping
    public String findAllClients(Model model) {
        List<ClientDto> clients = service.findAllClients();
        model.addAttribute("clients", clients);
        return CLIENTS_VIEW;
    }

    @PostMapping("/save")
    public String saveClient(@Valid @ModelAttribute("client") final Client client,
                             final BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "createClient";
        }
        service.saveClient(client);
        redirectAttributes.addFlashAttribute("message", "Клиент успешно сохранен");
        return "redirect:/clients";
    }

    @GetMapping("/save")
    public String createClient(final @ModelAttribute("client") Client client) {
        return "createClient";
    }

    @GetMapping("/findByName")
    public ClientDto findByClientName(@RequestParam final String nameOfClient) {

        return service.findByClientName(nameOfClient);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(final String clientName,
                                               final String newClientName) {
        Client updatedClient = service.updateClient(clientName, newClientName);
        return ResponseEntity.ok(updatedClient);
    }

    @PostMapping(value = "/{nameOfClient}", params = "_method=DELETE")
    public String deleteClient(@PathVariable final String nameOfClient, final Model model) {
        service.deleteClient(nameOfClient);
        return "redirect:/clients";
    }


    @GetMapping("/delete/{nameOfClient}")
    public String showDeleteForm(@PathVariable String nameOfClient, Model model) {
        final ClientDto clientDto = service.findByClientName(nameOfClient);
        if (clientDto!= null) {
            model.addAttribute("client", clientDto);
            return "deleteClient";
        } else {
            // Обработайте случай, когда клиент не найден
            return "error"; // Замените на вашу страницу ошибки
        }
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
    public void bulkSaveClients(@RequestBody final ArrayList<Client> clientList) {
        service.bulkSaveClients(clientList);
    }
}
