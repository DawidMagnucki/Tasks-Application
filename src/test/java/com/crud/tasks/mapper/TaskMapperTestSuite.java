package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTestSuite {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Title", "Test Content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, task.getId());
        assertEquals("Test Title", task.getTitle());
        assertEquals("Test Content", task.getContent());
    }

    @Test
    void shouldMapToTaskDto() {
        //Given
        Task task = new Task(1L, "Test Title", "Test Content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("Test Title", taskDto.getTitle());
        assertEquals("Test Content", taskDto.getContent());
    }

    @Test
    void shouldMapToTaskDtoList() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test Title", "Test Content"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());
        assertEquals("Test Title", taskDtoList.get(0).getTitle());
    }

    @Test
    void shouldMapEmptyList() {
        //Given
        List<Task> taskList = List.of();

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNotNull(taskDtoList);
        assertTrue(taskDtoList.isEmpty());
    }
}
