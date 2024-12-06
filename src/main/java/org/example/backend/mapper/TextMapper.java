package org.example.backend.mapper;

import org.example.backend.domain.Text;
import org.example.backend.dto.TextDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextMapper {
    public Text toEntity(TextDto dto) {
        return Text.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
    }

    public TextDto toDto(Text text) {
        return TextDto.builder()
                .id(text.getId())
                .title(text.getTitle())
                .text(text.getText())
                .build();
    }

    public List<TextDto> toDtoList(List<Text> texts) {
        return texts.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Text> toEntityList(List<TextDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
