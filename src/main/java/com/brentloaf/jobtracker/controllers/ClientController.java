package com.brentloaf.jobtracker.controllers;

import com.brentloaf.jobtracker.services.ClientService;
import com.brentloaf.jobtracker.structure.account.Account;
import com.brentloaf.jobtracker.structure.client.Client;
import com.brentloaf.jobtracker.structure.client.Job;
import com.brentloaf.jobtracker.structure.client.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    @GetMapping(path = "/all-ids/{accountId}")
    public ResponseEntity<List<UUID>> getAllIds(@PathVariable UUID accountId) {
        return ResponseEntity.ok(clientService.getAllIds(accountId));
    }

    @GetMapping(path = "/{clientId}/{accountId}")
    public ResponseEntity<Client> getClient(@PathVariable UUID clientId, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client);
    }

    @PostMapping(path = "/{accountId}")
    public ResponseEntity<Void> addClient(@RequestBody Client client, @PathVariable UUID accountId) {
        clientService.addClient(accountId, client);
        return ResponseEntity.created(URI.create("/clients/" + client.getId() + "/" + accountId)).build();
    }

    @DeleteMapping(path = "/{clientId}/{accountId}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID clientId, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        clientService.deleteClient(accountId, clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/search/{value}/{field}/{accountId}")
    public ResponseEntity<List<Client>> searchClients(@PathVariable String value, @PathVariable String field, @PathVariable UUID accountId) {
        if (!(field.equals("name")
                || field.equals("email")
                || field.equals("phone-number"))) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(clientService.searchClient(accountId, value, field));
    }

    @GetMapping(path = "/status")
    public ResponseEntity<String> getTitle(@RequestParam Status status) {
        return ResponseEntity.ok(status.getTitle());
    }

    @GetMapping(path = "/status/{statusTitle}")
    public ResponseEntity<Status> getTitle(@PathVariable String statusTitle) {
        Status status = Status.getStatus(statusTitle);
        if (status == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(status);
    }

    @GetMapping(path = "/{clientId}/jobs/{accountId}")
    public ResponseEntity<List<Job>> getJobs(@PathVariable UUID clientId, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client.getJobs());
    }

    @GetMapping(path = "/{clientId}/jobs/{jobId}/{accountId}")
    public ResponseEntity<Job> getJob(@PathVariable UUID clientId, @PathVariable UUID jobId, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        Job job = client.getJob(jobId);
        if (job == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(job);
    }

    @PostMapping(path = "/{clientId}/jobs/{accountId}")
    public ResponseEntity<Void> addJob(@PathVariable UUID clientId, @RequestBody Job job, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        client.addJob(job);
        return ResponseEntity.created(URI.create("/clients/" + clientId + "/jobs/" + job.getId() + "/" + accountId)).build();
    }

    @DeleteMapping(path = "/{clientId}/jobs/{jobId}/{accountId}")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID clientId, @PathVariable UUID jobId, @PathVariable UUID accountId) {
        Client client = clientService.getClient(accountId, clientId);
        if (client == null) return ResponseEntity.notFound().build();

        if (client.getJob(jobId) == null) return ResponseEntity.notFound().build();

        client.removeJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/account")
    public ResponseEntity<Void> addAccount(@RequestBody Account account) {
        if (clientService.isNameTaken(account.getName())) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        clientService.addAccount(account);
        return ResponseEntity.created(URI.create("/clients/account/" + account.getId())).build();
    }

    @GetMapping(path = "/account/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable UUID accountId) {
        Account account = clientService.getAccount(accountId);
        if (account == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(account);
    }
}
