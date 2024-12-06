package org.example.backend.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnalysisWithSameWordAtSentenceStartDto implements AnalysisResponseDto {
    @NonNull
    private String highlightedTextHtml;
}
