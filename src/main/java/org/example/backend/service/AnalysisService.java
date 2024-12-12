package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {

    public static final String REGEX_SENTENCE_SPLIT = "(?<=[.!?])\\s+";
    public static final String REGEX_WORD = "\\b\\w+";

    public List<AnalysisResponseDto> analyse(AnalysisRequestDto analysisRequestDto) {
        ArrayList<AnalysisResponseDto> analysis = new ArrayList<>();
        String text = analysisRequestDto.getText();

        if (analysisRequestDto.isWithShareOfSymbols()) {
            analysis.add(analyseWithShareOfSymbol(text));
        }
        if (!analysisRequestDto.getSearchString().isEmpty()) {
            analysis.add(analyseWithSearchString(text, analysisRequestDto.getSearchString()));
        }
        if (analysisRequestDto.isWithSameWordAtSentenceStart()) {
            analysis.add(analyseWithSameWordAtSentenceStart(text));
        }
        return analysis;
    }

    private AnalysisWithShareOfSymbolsDto analyseWithShareOfSymbol(String text) {
        if (text.isEmpty()) {
            return AnalysisWithShareOfSymbolsDto
                    .builder()
                    .shareOfSymbols(Map.of())
                    .build();
        }

        return AnalysisWithShareOfSymbolsDto.builder()
                .shareOfSymbols(computeShareOfSymbols(text))
                .build();
    }

    private Map<String, Double> computeShareOfSymbols(String text) {
        List<String> symbols = Arrays.asList(text.split(""));
        HashMap<String, Double> shares = new HashMap<>();

        symbols.forEach(symbol -> shares.put(symbol, shares.getOrDefault(symbol, 0d) + 1));

        shares.forEach((key, value) -> shares.put(key, value / symbols.size()));
        return shares;
    }

    private AnalysisWithSearchStringDto analyseWithSearchString(String text, String searchString) {
        StringBuilder highlightedText = new StringBuilder();

        int foundIndex = text.indexOf(searchString);
        int timesFound = 0;
        while (foundIndex != -1) {
            timesFound++;
            highlightedText.append(text, 0, foundIndex)
                    .append("<span>")
                    .append(searchString)
                    .append("</span>");
            text = text.substring(foundIndex + searchString.length());
            foundIndex = text.indexOf(searchString);
        }
        highlightedText.append(text);

        return AnalysisWithSearchStringDto.builder()
                .timesFound(timesFound)
                .highlightedTextHtml(highlightedText.toString())
                .build();
    }

    private AnalysisWithSameWordAtSentenceStartDto analyseWithSameWordAtSentenceStart(String text) {
        String[] sentences = text.split(REGEX_SENTENCE_SPLIT);

        if (text.isBlank() || sentences.length < 2) {
            return AnalysisWithSameWordAtSentenceStartDto.builder()
                    .highlightedTextHtml(text)
                    .build();
        }

        return AnalysisWithSameWordAtSentenceStartDto.builder()
                .highlightedTextHtml(highlightSameWordsAtSentenceStart(sentences))
                .build();
    }

    private static String highlightSameWordsAtSentenceStart(String[] sentences) {
        StringBuilder highlightedText = new StringBuilder();
        String previousFirstWord = "";

        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i];
            Optional<String> firstWordOptional = getFirstWord(sentence);
            if (firstWordOptional.isEmpty()) {
                return "";
            }
            String firstWord = firstWordOptional.get();

            boolean highlight = isSameAsPreviousSentence(firstWord, previousFirstWord);
            if (!highlight && i < sentences.length - 1) {
                highlight = isSameAsFollowingSentence(firstWord, getFirstWord(sentences[i + 1]));
            }

            if (highlight) {
                highlightedText.append(sentence.replaceFirst("\\b" + Pattern.quote(firstWord) + "\\b",
                        "<span>" + firstWord + "</span>"));
            } else {
                highlightedText.append(sentence);
            }

            previousFirstWord = firstWord;

            if (i < sentences.length - 1) {
                highlightedText.append(" ");
            }
        }
        return highlightedText.toString();
    }

    private static Optional<String> getFirstWord(String sentence) {
        Matcher matcher = Pattern.compile(REGEX_WORD).matcher(sentence);
        return matcher.find() ? Optional.of(matcher.group()) : Optional.empty();
    }

    private static boolean isSameAsPreviousSentence(String currentWord, String previousWord) {
        return currentWord.equalsIgnoreCase(previousWord);
    }

    private static boolean isSameAsFollowingSentence(String currentWord, Optional<String> followingWord) {
        return followingWord.map(word ->
                        word.equalsIgnoreCase(currentWord))
                .orElse(false);
    }
}
