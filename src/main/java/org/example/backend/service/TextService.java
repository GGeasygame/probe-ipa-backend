package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Text;
import org.example.backend.dto.TextRequestDto;
import org.example.backend.dto.TextResponseDto;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mapper.TextMapper;
import org.example.backend.repository.TextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextService {

    private final TextRepository textRepository;
    private final TextMapper textMapper;

    public List<TextResponseDto> getTexts() {
        return textMapper.toDtoList(textRepository.findAll());
    }

    @Transactional
    public TextResponseDto addText(TextRequestDto textRequestDto) {
        Text textEntity = textMapper.toEntity(textRequestDto);
        log.info("Text Entity before Save: " + textEntity);  // Log entity before saving
        Text savedText = textRepository.saveAndFlush(textEntity);
        log.info("Saved Text Entity: " + savedText);  // Log saved entity
        return textMapper.toDto(savedText);
    }

    public TextResponseDto updateText(Integer id, TextRequestDto text) {
        Text existingText = textRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Text not found with id: " + id));

        existingText.setTitle(text.getTitle());
        existingText.setText(text.getText());

        Text updatedText = textRepository.saveAndFlush(existingText);

        return textMapper.toDto(updatedText);
    }
}
