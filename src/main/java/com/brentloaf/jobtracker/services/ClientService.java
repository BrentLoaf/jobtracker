package com.brentloaf.jobtracker.services;

import com.brentloaf.jobtracker.structure.account.Account;
import com.brentloaf.jobtracker.structure.client.Client;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClientService {

    private final HashMap<UUID, List<Client>> clientsViaAccounts = new HashMap<>();
    private final List<Account> accounts = new ArrayList<>();

    public List<UUID> getAllIds(UUID accountId) {
        List<Client> clients = clientsViaAccounts.get(accountId);
        if (clients == null) return List.of();

        return clients.stream().map(Client::getId).toList();
    }

    public @Nullable Client getClient(UUID accountId, UUID id) {
        List<Client> clients = clientsViaAccounts.get(accountId);
        if (clients == null) return null;

        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addClient(UUID accountId, Client client) {
        List<Client> clients = clientsViaAccounts.get(accountId);
        if (clients == null) return;

        clients.add(client);
    }

    public void deleteClient(UUID accountId, UUID id) {
        List<Client> clients = clientsViaAccounts.get(accountId);
        if (clients == null) return;

        clients.removeIf(c -> c.getId().equals(id));
    }

    public void addAccount(Account account) {
        accounts.add(account);
        clientsViaAccounts.put(account.getId(), new ArrayList<>());
    }

    public List<Client> searchClient(UUID accountId, String value, String field) {
        List<Client> clients = clientsViaAccounts.get(accountId);
        if (clients == null) return List.of();

        return clients.stream()
                .filter(c -> switch (field.toLowerCase()) {
                    case "name" -> c.getName().toLowerCase().contains(value);
                    case "email" -> c.getEmail().equalsIgnoreCase(value);
                    case "phone-number" -> {
                        String phoneNumber = c.getPhoneNumber();
                        if (phoneNumber == null || phoneNumber.isBlank()) yield false;
                        yield phoneNumber.contains(value);
                    }
                    default -> false;
                })
                .toList();
    }

    public boolean isNameTaken(String name) {
        return accounts.stream()
                .anyMatch(a -> a.getName().equalsIgnoreCase(name));
    }

    public @Nullable Account getAccount(UUID id) {
        return accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
