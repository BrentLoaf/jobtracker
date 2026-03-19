package com.brentloaf.jobtracker.structure.client;

import jakarta.annotation.Nullable;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Status {
    PENDING("Pending"),
    ONGOING("On-going"),
    COMPLETED("Completed");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public static @Nullable Status getStatus(String status) {
        return Arrays.stream(Status.values()).filter(s -> s.getTitle().equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }

    public String getTitle() {
        return title;
    }
}
