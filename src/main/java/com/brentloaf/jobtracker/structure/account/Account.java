package com.brentloaf.jobtracker.structure.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@RequiredArgsConstructor
public class Account {

    @JsonProperty(access = READ_ONLY)
    private UUID id = UUID.randomUUID();
    @NotBlank
    private String name;
    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @Getter(AccessLevel.NONE)
    private String password;

    public boolean isPassword(String attempt) {
        return password.equals(attempt);
    }
}
