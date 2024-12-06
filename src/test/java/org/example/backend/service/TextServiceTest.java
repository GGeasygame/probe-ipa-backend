package org.example.backend.service;

import org.example.backend.domain.Text;
import org.example.backend.dto.TextDto;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mapper.TextMapper;
import org.example.backend.repository.TextRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextServiceTest {

    private final TextMapper textMapper = new TextMapper();
    @Mock
    private TextRepository textRepository;

    private TextService testee;

    @BeforeEach
    void setUp() {
        testee = new TextService(textRepository, textMapper);
        lenient().when(textRepository.save(any())).then(invocation -> invocation.getArgument(0));
    }


    @Test
    @DisplayName("WHEN no text THEN return empty list")
    void getTexts_no_texts() {
        // act
        List<TextDto> actual = testee.getTexts();

        // assert
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    @DisplayName("WHEN 1 text THEN return 1 text")
    void getTexts_one_text() {
        // arrange
        List<Text> expected = Collections.singletonList(new Text(1, "title", "text"));
        when(textRepository.findAll()).thenReturn(expected);

        // act
        List<TextDto> actual = testee.getTexts();

        // assert
        assertEquals(textMapper.toDtoList(expected), actual);
    }

    @Test
    @DisplayName("WHEN 3 text THEN return 3 text")
    void getTexts_three_texts() {
        // arrange
        List<Text> expected = List.of(
                new Text(1, "title", "text"),
                new Text(2, "title", "text"),
                new Text(3, "title", "text")
        );
        when(textRepository.findAll()).thenReturn(expected);

        // act
        List<TextDto> actual = testee.getTexts();

        // assert
        assertEquals(textMapper.toDtoList(expected), actual);
    }

    @Test
    @DisplayName("WHEN add text THEN save to repository")
    void saveText() {
        // arrange
        TextDto text = new TextDto(1, "title", "text");

        // act
        testee.addText(text);

        // assert
        verify(textRepository, times(1)).save(textMapper.toEntity(text));
    }

    @Test
    @DisplayName("WHEN updating text THEN save update to repository")
    void updateText() {
        // arrange
        TextDto text = new TextDto("example title", "example text");
        Integer id = 1;
        when(textRepository.findById(id)).thenReturn(Optional.of(new Text(1, "title", "text")));

        // act
        testee.updateText(id, text);

        // assert
        text.setId(id);
        verify(textRepository, times(1)).save(textMapper.toEntity(text));
    }

    @Test
    @DisplayName("WHEN updating text THEN save update to repository")
    void updateText_with_no_text_in_db() {
        // arrange
        TextDto text = new TextDto("title", "text");
        Integer id = 1;
        when(textRepository.findById(id)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> testee.updateText(id, text));
    }
}