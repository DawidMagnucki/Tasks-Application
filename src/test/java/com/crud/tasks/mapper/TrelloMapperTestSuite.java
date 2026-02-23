package com.crud.tasks.mapper;


import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloMapperTestSuite {

    private TrelloMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TrelloMapper();
    }

    @Test
    public void shouldMapTrelloBoardsDtoToTrelloBoards() {
        //Given
        List<TrelloBoardDto> boardDto = new ArrayList<>();
        boardDto.add(new TrelloBoardDto("1", "Test Board", new ArrayList<>()));
        //When
        List<TrelloBoard> mappedTrelloBoards = mapper.mapToBoards(boardDto);
        //Then
        assertNotNull(mappedTrelloBoards);
        assertEquals(1, mappedTrelloBoards.size());
        assertNotEquals(0, mappedTrelloBoards.get(0).getId());
        assertEquals("Test Board", mappedTrelloBoards.get(0).getName());
    }

    @Test
    public void shouldMapTrelloBoardsToTrelloBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "Test Board", new ArrayList<>()));
        //When
        List<TrelloBoardDto> mappedTrelloBoardsDto = mapper.mapToBoardsDto(trelloBoards);
        //Then
        assertNotNull(mappedTrelloBoardsDto);
        assertEquals(1, mappedTrelloBoardsDto.size());
        assertNotEquals(0, mappedTrelloBoardsDto.get(0).getId());
        assertEquals("Test Board", mappedTrelloBoardsDto.get(0).getName());
    }

    @Test
    public void shouldMapTrelloListDtoToTrelloList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "Test List", false));
        //When
        List<TrelloList> mappedTrelloLists = mapper.mapToList(trelloListDto);
        //Then
        assertNotNull(mappedTrelloLists);
        assertEquals(1, mappedTrelloLists.size());
        assertNotEquals(null, mappedTrelloLists.get(0).getName());
        assertEquals("Test List", mappedTrelloLists.get(0).getName());
    }

    @Test
    public void shouldMapTrelloListsToTrelloListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "Test List", false));
        //When
        List<TrelloListDto> mappedTrelloListDto = mapper.mapToListDto(trelloLists);
        //Then
        assertNotNull(mappedTrelloListDto);
        assertEquals(1, mappedTrelloListDto.size());
        assertNotEquals(null, mappedTrelloListDto.get(0).getName());
        assertEquals("Test List", mappedTrelloListDto.get(0).getName());
    }

    @Test
    public void shouldMapTrelloCardToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card 1", "Test Description", "Test pos", "1");
        //When
        TrelloCardDto mappedTrelloCard = mapper.mapToCardDto(trelloCard);
        //Then
        assertNotNull(mappedTrelloCard);
        assertEquals("Card 1", mappedTrelloCard.getName());
        assertNotEquals(0, mappedTrelloCard.getListId());
    }

    @Test
    public void shouldMapTrelloCardDtoToTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto =  new TrelloCardDto("Card 1", "Test Description", "Test pos", "1");
        //When
        TrelloCard mappedTrelloCardDto = mapper.mapToCard(trelloCardDto);
        //Then
        assertNotNull(mappedTrelloCardDto);
        assertEquals("Card 1", mappedTrelloCardDto.getName());
        assertNotEquals(null, mappedTrelloCardDto.getDescription());

    }
}
