package com.brentloaf.jobtracker.structure.client;

public enum Status {
    PENDING("Pending"),
    ONGOING("On-going"),
    COMPLETED("Completed");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
