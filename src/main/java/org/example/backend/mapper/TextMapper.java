package org.example.backend.mapper;

import org.example.backend.domain.Text;
import org.example.backend.dto.TextRequestDto;
import org.example.backend.dto.TextResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextMapper {
    public Text toEntity(TextRequestDto dto) {
        return Text.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
    }

    public TextResponseDto toDto(Text text) {
        return TextResponseDto.builder()
                .id(text.getId())
                .title(text.getTitle())
                .text(text.getText())
                .build();
    }

    public List<TextResponseDto> toDtoList(List<Text> texts) {
        return texts.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Text> toEntityList(List<TextRequestDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
