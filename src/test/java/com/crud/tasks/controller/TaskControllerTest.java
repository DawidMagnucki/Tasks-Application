package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalHttpErrorHandler())
                .build();
    }

    @Test
    void shouldGetTasks() throws Exception {
        // Given
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Test Title", "Test Content"));
        when(dbService.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtos);

        // When & Then
        mockMvc.perform(get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Title")));
    }

    @Test
    void shouldGetTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Test Title", "Test Content");
        when(dbService.getTaskById(anyLong())).thenReturn(new Task());
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Title")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given & When & Then
        mockMvc.perform(delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(dbService, times(1)).deleteTaskById(1L);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Updated Title", "Updated Content");
        when(taskMapper.mapToTask(any())).thenReturn(new Task());
        when(dbService.saveTask(any())).thenReturn(new Task());
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        String jsonContent = objectMapper.writeValueAsString(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated Title")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "New Task", "New Content");
        when(taskMapper.mapToTask(any())).thenReturn(new Task());

        String jsonContent = objectMapper.writeValueAsString(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(dbService, times(1)).saveTask(any());
    }

    @Test
    void shouldHandleTaskNotFoundException() throws Exception {
        // Given
        when(dbService.getTaskById(anyLong())).thenThrow(new TaskNotFoundException());

        // When & Then
        mockMvc.perform(get("/v1/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Task with given ID doesn't exist."));
    }

}
