package com.brentloaf.jobtracker.structure.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Client {

    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String phoneNumber;
    private List<Job> jobs = new ArrayList<>();

}
