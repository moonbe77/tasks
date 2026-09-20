package com.example.tasks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TasksServiceTest {
    TaskRepository repository = new InMemoryTaskReposotory();
    TaskService service = new TaskService(repository);

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskReposotory();
        service = new TaskService(repository);
    }

    @Test
    void countsCompletedTasks() {

        // Act
        service.createTask("title", "description");
        service.createTask("title2", "description2");
        assertEquals(2, service.getTaskCount());

        Task task = service.getTaskById(1L).get();
        task.setCompleted(true);

        assertEquals(1, service.getCompletedTaskCount());
    }

    @Test
    void returnsCompletedTasks() {

        // Act
        service.createTask("title", "description");
        service.createTask("title2", "description2");
        assertEquals(2, service.getTaskCount());

        Task task = service.getTaskById(1L).get();
        task.setCompleted(true);
        assertEquals(1, service.getCompletedTaskCount());
        assertEquals(task, service.getCompletedTasks().get(0));
    }

    @Test
    void newlyCreatedTaskIsNotCompleted() {

        service.createTask("title", "description");
        assertEquals(0, service.getCompletedTaskCount());

    }

    @Test
    void findTaskById() {

        service.createTask("Learn Java", "description");

        Optional<Task> foundTask = service.getTaskById(1L);
        assertEquals(true, foundTask.isPresent());

        assertEquals("Learn Java", foundTask.get().getTitle());
    }

    @Test
    void returnsEmptyIfNotExists() {

        service.createTask("title", "description");
        Optional<Task> foundTask = service.getTaskById(2L);
        assertEquals(false, foundTask.isPresent());
    }

    @Test
    void deleteTaskById() {

        service.createTask("title", "description");
        assertEquals(1, service.getTaskCount());

        boolean deleted = service.deleteById(1L);
        assertEquals(true, deleted);
        assertEquals(0, service.getTaskCount());
    }

    @Test
    void deleteTaskByIdReturnsFalseIfNotExists() {

        service.createTask("title", "description");
        assertEquals(1, service.getTaskCount());

        boolean deleted = service.deleteById(2L);
        assertEquals(false, deleted);
        assertEquals(1, service.getTaskCount());
    }

    @Test
    void getCompletedTaskCount() {

        Task task1 = service.createTask("title", "description");
        Task task2 = service.createTask("title2", "description2");
        Task task3 = service.createTask("title3", "description3");

        task1.setCompleted(true);
        task3.setCompleted(true);

        assertEquals(3, service.getTaskCount());

        assertEquals(2, service.getCompletedTasks().size());
    }
}
