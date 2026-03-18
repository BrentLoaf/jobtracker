package com.brentloaf.jobtracker.services;

import com.brentloaf.jobtracker.structure.client.Client;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {

    private final Set<Client> clients = ConcurrentHashMap.newKeySet();

    public List<UUID> getAllIds() {
        return clients.stream().map(Client::getId).toList();
    }

    public @Nullable Client getClient(UUID id) {
        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void deleteClient(UUID id) {
        clients.removeIf(c -> c.getId().equals(id));
    }

    public List<Client> searchClient(String value, String field) {
        return clients.stream()
                .filter(c -> switch (field.toLowerCase()) {
                    case "name" -> c.getName().contains(value);
                    case "email" -> c.getEmail().equalsIgnoreCase(value);
                    case "phone-number" -> {
                        String phoneNumber = c.getPhoneNumber();
                        if (phoneNumber == null || phoneNumber.isBlank()) yield false;
                        yield phoneNumber.equalsIgnoreCase(value);
                    }
                    default -> false;
                })
                .toList();
    }
}
