package com.example.tasks;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    void save(Task task);

    List<Task> findAll();

    Optional<Task> findById(Long id);

    boolean deleteById(Long id);

}
