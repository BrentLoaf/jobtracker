package com.brentloaf.jobtracker.structure.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@RequiredArgsConstructor
public class Account {

    @JsonProperty(access = READ_ONLY)
    private UUID id = UUID.randomUUID();
    @NotBlank
    private String name;

    public @NotBlank String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
