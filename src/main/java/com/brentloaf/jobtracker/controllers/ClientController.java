package com.brentloaf.jobtracker.controllers;

import com.brentloaf.jobtracker.services.ClientService;
import com.brentloaf.jobtracker.structure.client.Client;
import com.brentloaf.jobtracker.structure.client.Job;
import com.brentloaf.jobtracker.structure.client.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(path = "/all-ids")
    public ResponseEntity<List<UUID>> getAllIds() {
        return ResponseEntity.ok(clientService.getAllIds());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> getClient(@PathVariable UUID id) {
        Client client = clientService.getClient(id);
        if (client == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client);
    }

    @PostMapping()
    public ResponseEntity<Void> addClient(@RequestBody Client client) {
        clientService.addClient(client);
        return ResponseEntity.created(URI.create("/clients/" + client.getId().toString())).build();
    }

    @GetMapping(path = "/search/{value}/{field}")
    public ResponseEntity<List<Client>> searchClients(@PathVariable String value, @PathVariable String field) {
        if (!(field.equals("name")
                || field.equals("email")
                || field.equals("phone-number"))) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(clientService.searchClient(value, field));
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

    @GetMapping(path = "/{id}/jobs")
    public ResponseEntity<List<Job>> getJobs(@PathVariable UUID id) {
        Client client = clientService.getClient(id);
        if (client == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client.getJobs());
    }

    @GetMapping(path = "/{clientId}/jobs/{jobId}")
    public ResponseEntity<Job> getJob(@PathVariable UUID clientId, @PathVariable UUID jobId) {
        Client client = clientService.getClient(clientId);
        if (client == null) return ResponseEntity.notFound().build();

        Job job = client.getJob(jobId);
        if (job == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(job);
    }

    @PostMapping(path = "/{id}/jobs")
    public ResponseEntity<Void> addJob(@PathVariable UUID id, @RequestBody Job job) {
        Client client = clientService.getClient(id);
        if (client == null) return ResponseEntity.notFound().build();

        client.addJob(job);
        return ResponseEntity.created(URI.create("/clients/" + id.toString() + "/jobs/" + job.getId().toString())).build();
    }

    @DeleteMapping(path = "/{clientId}/jobs/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID clientId, @PathVariable UUID jobId) {
        Client client = clientService.getClient(clientId);
        if (client == null) return ResponseEntity.notFound().build();

        if (client.getJob(jobId) == null) return ResponseEntity.notFound().build();

        client.removeJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
