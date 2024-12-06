package org.example.backend.mapper;

import org.example.backend.domain.Text;
import org.example.backend.dto.TextDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMapperTest {

    private TextMapper textMapper;

    @BeforeEach
    void setUp() {
        textMapper = new TextMapper();
    }

    @Test
    @DisplayName("WHEN mapping to entity and all fields present THEN map with all fields")
    void toEntity_allFieldsPresent() {
        // arrange
        TextDto textDto = new TextDto(1, "title", "text");
        Text expected = new Text(1, "title", "text");

        // act
        Text actual = textMapper.toEntity(textDto);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping to entity and id not present THEN map without id")
    void toEntity_idNotPresent() {
        // arrange
        TextDto textDto = new TextDto("title", "text");
        Text expected = new Text("title", "text");

        // act
        Text actual = textMapper.toEntity(textDto);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping to dto and all fields present THEN map with all fields")
    void toDto_allFieldsPresent() {
        // arrange
        TextDto expected = new TextDto(1, "title", "text");
        Text text = new Text(1, "title", "text");

        // act
        TextDto actual = textMapper.toDto(text);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("WHEN mapping to dto id not present THEN map without id")
    void toDto_idNotPresent() {
        // arrange
        TextDto expected = new TextDto("title", "text");
        Text text = new Text("title", "text");

        // act
        TextDto actual = textMapper.toDto(text);

        // assert
        assertEquals(expected, actual);
    }
}