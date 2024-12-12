package org.example.backend.dto;

import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnalysisWithShareOfSymbolsDto implements AnalysisResponseDto {
    @NonNull
    private Map<String, Double> shareOfSymbols;
}
