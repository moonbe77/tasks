package com.example.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTaskReposotory implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) {

        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Task> task = this.findById(id);

        if (task.isEmpty()) {
            return false;
        }

        return tasks.remove(task.get());
    }

    @Override
    public List<Task> findCompleted() {
        return tasks.stream()
                .filter(task -> task.isCompleted())
                .toList();
    }
}
