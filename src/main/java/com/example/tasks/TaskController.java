package com.example.tasks;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted());
    }

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.getTasks().stream().map(this::toResponse).toList();
    }

    // POST /tasks
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {

        Task task = taskService.createTask(request.title(), request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(task));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id) {
        Task task = taskService.getTask(id);
        TaskResponse response = toResponse(task);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/completed")
    public List<TaskResponse> getCompletedTasks() {
        return taskService.getCompletedTasks().stream().map(this::toResponse).toList();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> completeTask(
            @PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        Task task = taskService.completeTask(id, request.completed());
        return ResponseEntity.ok(toResponse(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {
        boolean deleted = taskService.deleteById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
