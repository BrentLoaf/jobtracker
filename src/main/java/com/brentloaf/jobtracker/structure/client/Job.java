package com.brentloaf.jobtracker.structure.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@RequiredArgsConstructor
public class Job {

    @JsonProperty(access = READ_ONLY)
    private UUID id = UUID.randomUUID();
    @NotBlank
    private String description;
    @JsonProperty(access = READ_ONLY)
    private Status status;

    public void updateStatus(Status status) {
        this.status = status;
    }
}
