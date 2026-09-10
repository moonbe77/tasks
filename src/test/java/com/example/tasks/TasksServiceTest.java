package com.example.tasks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TasksServiceTest {

    @Test
    void countsCompletedTasks() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        // Act
        service.createTask("title", "description");
        service.createTask("title2", "description2");
        assertEquals(2, service.getTaskCount());

        Task task = service.getTaskById(1L).get();
        task.complete();

        assertEquals(1, service.getCompletedTaskCount());
    }

    @Test
    void returnsCompletedTasks() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        // Act
        service.createTask("title", "description");
        service.createTask("title2", "description2");
        assertEquals(2, service.getTaskCount());

        Task task = service.getTaskById(1L).get();
        task.complete();
        assertEquals(1, service.getCompletedTaskCount());
        assertEquals(task, service.getCompletedTasks().get(0));
    }

    @Test
    void newlyCreatedTaskIsNotCompleted() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        service.createTask("title", "description");
        assertEquals(0, service.getCompletedTaskCount());

    }

    @Test
    void findTaskById() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        service.createTask("Learn Java", "description");

        Optional<Task> foundTask = service.getTaskById(1L);
        assertEquals(true, foundTask.isPresent());

        assertEquals("Learn Java", foundTask.get().getTitle());
    }

    @Test
    void returnsEmptyIfNotExists() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        service.createTask("title", "description");
        Optional<Task> foundTask = service.getTaskById(2L);
        assertEquals(false, foundTask.isPresent());
    }

    @Test
    void deleteTaskById() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        service.createTask("title", "description");
        assertEquals(1, service.getTaskCount());

        boolean deleted = service.deleteById(1L);
        assertEquals(true, deleted);
        assertEquals(0, service.getTaskCount());
    }

    @Test
    void deleteTaskByIdReturnsFalseIfNotExists() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);

        service.createTask("title", "description");
        assertEquals(1, service.getTaskCount());

        boolean deleted = service.deleteById(2L);
        assertEquals(false, deleted);
        assertEquals(1, service.getTaskCount());
    }
}
