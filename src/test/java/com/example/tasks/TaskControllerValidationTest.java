package com.example.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerValidationTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void rejectsTaskWithBlankTitle() throws Exception {

                mockMvc.perform(
                                post("/tasks")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("""
                                                                {
                                                                "title": "",
                                                                "description": ""
                                                                }
                                                                """))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.status").value(400))
                                .andExpect(jsonPath("$.message").value("Validation failed"))
                                .andExpect(
                                                jsonPath("$.errors.title")
                                                                .value("Title is required"))
                                .andExpect(
                                                jsonPath("$.errors.description")
                                                                .value("Description is required"));
        }

        @Test
        void acceptsTaskWithValidTitleAndDescription() throws Exception {

                mockMvc.perform(
                                post("/tasks")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("""
                                                                {
                                                                    "title": "Valid Title",
                                                                    "description": "Valid Description"
                                                                }
                                                                """))
                                .andExpect(
                                                status().isCreated());
        }

        @Test
        void returnsNotFoundForNonExistentTask() throws Exception {
                mockMvc.perform(
                                get("/tasks/999"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.message").value("Task not found with ID: 999"));
        }
}