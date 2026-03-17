package com.brentloaf.jobtracker.controllers;

import com.brentloaf.jobtracker.services.ClientService;
import com.brentloaf.jobtracker.structure.client.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    @GetMapping(path = "/all-Ids")
    public List<UUID> getAllIds() {
        return ClientService.getAllIds();
    }

    @GetMapping(path = "/{id}")
    public Client getClient(@PathVariable UUID id) {
        return ClientService.getClient(id);
    }
}
