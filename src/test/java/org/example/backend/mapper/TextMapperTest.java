package org.example.backend.mapper;

import org.example.backend.domain.Text;
import org.example.backend.dto.TextRequestDto;
import org.example.backend.dto.TextResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMapperTest {

    private TextMapper textMapper;

    @BeforeEach
    void setUp() {
        textMapper = new TextMapper();
    }

    @Test
    @DisplayName("WHEN mapping to entity THEN map to entity")
    void toEntity() {
        // arrange
        TextRequestDto textRequestDto = new TextRequestDto("title", "text");
        Text expected = new Text("title", "text");

        // act
        Text actual = textMapper.toEntity(textRequestDto);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping to dto THEN map to dto")
    void toDto() {
        // arrange
        TextResponseDto expected = new TextResponseDto(1, "title", "text");
        Text text = new Text(1, "title", "text");

        // act
        TextResponseDto actual = textMapper.toDto(text);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping list to dto THEN map list")
    void toDto_withList() {
        // arrange
        List<TextResponseDto> expected = List.of(
                new TextResponseDto(1, "title", "text"),
                new TextResponseDto(2, "title", "text"),
                new TextResponseDto(3, "title", "text")
        );
        List<Text> texts = List.of(
                new Text(1, "title", "text"),
                new Text(2, "title", "text"),
                new Text(3, "title", "text")
        );

        // act
        List<TextResponseDto> actual = textMapper.toDtoList(texts);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping list to entity THEN map list")
    void toEntity_withList() {
        // arrange
        List<TextRequestDto> texts = List.of(
                new TextRequestDto("title", "text"),
                new TextRequestDto("title", "text"),
                new TextRequestDto("title", "text")
        );
        List<Text> expected = List.of(
                new Text("title", "text"),
                new Text("title", "text"),
                new Text("title", "text")
        );

        // act
        List<Text> actual = textMapper.toEntityList(texts);

        // assert
        assertEquals(expected, actual);
    }
}