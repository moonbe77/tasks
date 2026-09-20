package com.example.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TaskControllerTests {

    @Test
    void getTasks() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        controller.getTasks();
    }

    @Test
    void addTask() {
        TaskRepository repository = new InMemoryTaskReposotory();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        CreateTaskRequest request = new CreateTaskRequest("POSTMAN", "this is a description from postman");

        ResponseEntity<Task> response = controller.createTask(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("POSTMAN", response.getBody().getTitle());
        assertEquals("this is a description from postman", response.getBody().getDescription());
        assertFalse(response.getBody().isCompleted());
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
        ResponseEntity<Task> response = controller.completeTask(1L,updateRequest);

        // expect 200
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // expect completed == true
        assertEquals(true, response.getBody().isCompleted());
    }

}
