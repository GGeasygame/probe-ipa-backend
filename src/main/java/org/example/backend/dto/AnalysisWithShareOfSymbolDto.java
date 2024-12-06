package org.example.backend.dto;

import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnalysisWithShareOfSymbolDto implements AnalysisResponseDto {
    @NonNull
    private Map<String, Double> shareOfSymbol;
}
