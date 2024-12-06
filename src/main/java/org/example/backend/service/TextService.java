package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Text;
import org.example.backend.dto.TextDto;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mapper.TextMapper;
import org.example.backend.repository.TextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextService {

    private final TextRepository textRepository;
    private final TextMapper textMapper;

    public List<TextDto> getTexts() {
        return textMapper.toDtoList(textRepository.findAll());
    }

    public TextDto addText(TextDto textDto) {
        return textMapper.toDto(textRepository.save(textMapper.toEntity(textDto)));
    }

    public TextDto updateText(Integer id, TextDto text) {
        Text existingText = textRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Text not found with id: " + id));

        existingText.setTitle(text.getTitle());
        existingText.setText(text.getText());

        Text updatedText = textRepository.save(existingText);

        return textMapper.toDto(updatedText);
    }
}
