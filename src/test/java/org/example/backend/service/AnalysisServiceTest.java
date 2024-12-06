package org.example.backend.service;

import org.example.backend.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AnalysisServiceTest {
    private final AnalysisService analysisService = new AnalysisService();

    @Test
    @DisplayName("WHEN text with share of symbols THEN analyse")
    void analyseWithShareOfSymbol() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("example text!")
                .withShareOfSymbol(true).build();
        AnalysisWithShareOfSymbolDto expected = AnalysisWithShareOfSymbolDto.builder()
                .shareOfSymbol(Map.of(
                        "e", 0.23076923076923078,
                        "x", 0.15384615384615385,
                        "a", 0.07692307692307693,
                        "m", 0.07692307692307693,
                        "p", 0.07692307692307693,
                        "l", 0.07692307692307693,
                        "t", 0.15384615384615385,
                        " ", 0.07692307692307693,
                        "!", 0.07692307692307693
                )).build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithShareOfSymbolDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN no text with share of symbols THEN return with empty map")
    void analyseWithShareOfSymbol_withEmptyText() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("")
                .withShareOfSymbol(true).build();
        AnalysisWithShareOfSymbolDto expected = AnalysisWithShareOfSymbolDto.builder()
                .shareOfSymbol(Collections.emptyMap()).build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithShareOfSymbolDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with search string THEN return response")
    void analyseWithSearchString() {
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("example text!")
                .searchString("ex").build();
        AnalysisWithSearchStringDto expected = AnalysisWithSearchStringDto.builder()
                .timesFound(2)
                .highlightedTextHtml("<span>ex</span>ample t<span>ex</span>t!")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSearchStringDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN no text with search string THEN return response")
    void analyseWithSearchString_withEmptyText() {
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("")
                .searchString("ex").build();
        AnalysisWithSearchStringDto expected = AnalysisWithSearchStringDto.builder()
                .timesFound(0)
                .highlightedTextHtml("")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSearchStringDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with empty search string THEN return response")
    void analyseWithSearchString_withEmptySearchString() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("example text!")
                .searchString("").build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("WHEN text with search string that does not fit THEN return response")
    void analyseWithSearchString_withNotFittingSearchString() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("example text!")
                .searchString("stu").build();
        AnalysisWithSearchStringDto expected = AnalysisWithSearchStringDto.builder()
                .timesFound(0)
                .highlightedTextHtml("example text!")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSearchStringDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with same word at sentence start THEN return response with highlighting")
    void analyseWithSearchString_withSameWordAtSentenceStart() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("This is a sentence. This is another sentence. But here is a different sentence. This too. But how cool. But this is also cool.")
                .withSameWordAtSentenceStart(true).build();
        AnalysisWithSameWordAtSentenceStartDto expected = AnalysisWithSameWordAtSentenceStartDto.builder()
                .highlightedTextHtml("<span>This</span> is a sentence. <span>This</span> is another sentence. But here is a different sentence. This too. <span>But</span> how cool. <span>But</span> this is also cool.")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSameWordAtSentenceStartDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with different word at sentence start THEN return response without highlighting")
    void analyseWithSearchString_withDifferentWordAtSentenceStart() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("This is a sentence. But here is a different sentence.")
                .withSameWordAtSentenceStart(true).build();
        AnalysisWithSameWordAtSentenceStartDto expected = AnalysisWithSameWordAtSentenceStartDto.builder()
                .highlightedTextHtml("This is a sentence. But here is a different sentence.")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSameWordAtSentenceStartDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with same word at sentence start but with exclamation mark and question mark THEN return response with highlighting")
    void analyseWithSearchString_withSameWordAtSentenceStartWithExclamationMarkAndQuestionMark() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("This is a sentence! This is another sentence? But here is a different sentence! This too? But how cool? But this is also cool.")
                .withSameWordAtSentenceStart(true).build();
        AnalysisWithSameWordAtSentenceStartDto expected = AnalysisWithSameWordAtSentenceStartDto.builder()
                .highlightedTextHtml("<span>This</span> is a sentence! <span>This</span> is another sentence? But here is a different sentence! This too? <span>But</span> how cool? <span>But</span> this is also cool.")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSameWordAtSentenceStartDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }

    @Test
    @DisplayName("WHEN text with same word at sentence start but not capitalised THEN return response with highlighting")
    void analyseWithSearchString_withSameWordAtSentenceStartButNotCapitalised() {
        // arrange
        AnalysisRequestDto analysisRequest = AnalysisRequestDto.builder()
                .text("This is a sentence. this is another sentence. but here is a different sentence. This too. but how cool. But this is also cool.")
                .withSameWordAtSentenceStart(true).build();
        AnalysisWithSameWordAtSentenceStartDto expected = AnalysisWithSameWordAtSentenceStartDto.builder()
                .highlightedTextHtml("<span>This</span> is a sentence. <span>this</span> is another sentence. but here is a different sentence. This too. <span>but</span> how cool. <span>But</span> this is also cool.")
                .build();

        // act
        List<AnalysisResponseDto> actual = analysisService.analyse(analysisRequest);

        // assert
        assertEquals(1, actual.size());
        assertInstanceOf(AnalysisWithSameWordAtSentenceStartDto.class, actual.getFirst());
        assertEquals(expected, actual.getFirst());
    }
}