package com.example.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController controller;

    Task task = new Task(
            42L,
            "Learn Mockito",
            "Practice mocks");

    @Test
    void getTasks() {

        controller.getTasks();
    }

    @Test
    void returnsTask() {
        Task task = new Task(
                42L,
                "Learn Mockito",
                "Practice mocks");

        when(taskService.getTask(42L)).thenReturn(task);

        // act

        ResponseEntity<TaskResponse> response = controller.getTask(42L);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(42L, response.getBody().id());
        assertEquals("Learn Mockito", response.getBody().title());
        assertEquals("Practice mocks", response.getBody().description());
    }

    @Test
    void addTask() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        CreateTaskRequest request = new CreateTaskRequest("POSTMAN", "this is a description from postman");

        ResponseEntity<TaskResponse> response = controller.createTask(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("POSTMAN", response.getBody().title());
        assertEquals("this is a description from postman", response.getBody().description());
        assertFalse(response.getBody().completed());
    }

    @Test
    void deleteTask() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        CreateTaskRequest request = new CreateTaskRequest("POSTMAN", "this is a description from postman");

        controller.createTask(request);

        ResponseEntity<Void> response = controller.deleteTask(1L);

        // Add assertions to verify the task was deleted
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, service.getTaskCount());
    }

    @Test
    void marksTaskAsCompleted() throws Exception {
        // create task
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        CreateTaskRequest request = new CreateTaskRequest("POSTMAN", "this is a description from postman");
        controller.createTask(request);

        // PATCH /tasks/{id}
        UpdateTaskRequest updateRequest = new UpdateTaskRequest(true);
        ResponseEntity<TaskResponse> response = controller.completeTask(1L, updateRequest);

        // expect 200
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // expect completed == true
        assertEquals(true, response.getBody().completed());
    }

}
