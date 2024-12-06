package org.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class AnalysisRequestDto {
    @NonNull
    @NotNull(message = "Text must not be null")
    private String text;

    @Builder.Default
    private String searchString = "";

    @Builder.Default
    private boolean withShareOfSymbol = false;

    @Builder.Default
    private boolean withSameWordAtSentenceStart = false;
}
