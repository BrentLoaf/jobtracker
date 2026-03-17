package com.brentloaf.jobtracker.services;

import com.brentloaf.jobtracker.structure.client.Client;
import jakarta.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ClientService {

    private static HashSet<Client> clients = new HashSet<>();

    public static List<UUID> getAllIds() {
        return clients.stream().map(Client::getId).toList();
    }

    public static @Nullable Client getClient(UUID id) {
        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
