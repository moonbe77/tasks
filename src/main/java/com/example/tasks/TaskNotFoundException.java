package com.example.tasks;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found with ID: " + id);
    }

    // public Task getTask(Long id) {
    // return repository.findById(id)
    // .orElseThrow(() -> new TaskNotFoundException(id));
    // }

}
