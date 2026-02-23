package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SpringTrelloControllerTest {


    @Autowired
    private TrelloController trelloController;

    @Test
    void shouldGetBoards() {
        // When
        ResponseEntity<List<TrelloBoardDto>> response = trelloController.getTrelloBoards();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
