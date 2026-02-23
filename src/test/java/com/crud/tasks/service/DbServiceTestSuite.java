package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    void shouldGetAllTasks() {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Test", "Content"));
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }

    @Test
    void shouldGetTaskById() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "Test", "Content");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTaskById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTaskById(1L));
    }

    @Test
    void shouldSaveTask() {
        // Given
        Task task = new Task(1L, "Test", "Content");
        when(repository.save(task)).thenReturn(task);

        // When
        Task result = dbService.saveTask(task);

        // Then
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(task);
    }

    @Test
    void shouldDeleteTask() throws TaskNotFoundException {
        // Given
        Long taskId = 1L;

        // When
        dbService.deleteTaskById(taskId);

        // Then
        verify(repository, times(1)).deleteById(taskId);
    }
}
