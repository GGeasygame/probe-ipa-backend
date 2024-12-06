package org.example.backend.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnalysisWithSearchStringDto implements AnalysisResponseDto {
    @NonNull
    private Integer timesFound;
    @NonNull
    private String highlightedTextHtml;
}
