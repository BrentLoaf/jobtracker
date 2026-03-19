package com.brentloaf.jobtracker.structure.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@RequiredArgsConstructor
public class Client {

    @JsonProperty(access = READ_ONLY)
    private UUID id = UUID.randomUUID();
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @Nullable
    private String phoneNumber;
    private final List<Job> jobs = new ArrayList<>();

    public void addJob(Job job) {
        jobs.add(job);
    }

    public void removeJob(UUID id) {
        jobs.removeIf(j -> j.getId().equals(id));
    }

    public @Nullable Job getJob(UUID id) {
        return jobs.stream()
                .filter(j -> j.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public UUID getId() {
        return id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
