package com.example.tasks;

public record TaskResponse(
        Long id,
        String title,
        String description,
        boolean completed) {
}
