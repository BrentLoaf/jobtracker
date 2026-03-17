package com.brentloaf.jobtracker.structure.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Job {

    private String description;
    private Status status;
}
