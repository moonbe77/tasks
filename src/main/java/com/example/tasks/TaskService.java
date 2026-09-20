package com.example.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String title, String description) {
        Long id = repository.findAll().size() + 1L;
        Task task = new Task(id, title, description);

        repository.save(task);

        return task;
    }

    public List<Task> getTasks() {
        return repository.findAll();
    }

    public int getTaskCount() {
        return repository.findAll().size();
    }

    public int getCompletedTaskCount() {

        // final List<Task> completed = tasks.stream().filter(task ->
        // task.isCompleted()).toList();
        final List<Task> completed = new ArrayList<>();

        for (Task task : repository.findAll()) {
            if (task.isCompleted()) {
                completed.add(task);
            }

        }

        return completed.size();

    }

    public List<Task> getCompletedTasks() {

        // final List<Task> completed = tasks.stream().filter(task ->
        // task.isCompleted()).toList();

        final List<Task> completed = new ArrayList<>();

        for (Task task : repository.findAll()) {
            if (task.isCompleted()) {
                completed.add(task);
            }

        }

        return completed;

    }

    public Optional<Task> getTaskById(Long id) {

        return repository.findById(id);

    }

    public Task getTask(Long id) {
        Optional<Task> task = repository.findById(id);

        if (task.isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        return task.get();

    }

    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

}
