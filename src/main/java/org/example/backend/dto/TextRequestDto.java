package org.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextRequestDto {
    @NonNull
    @NotNull(message = "Title must not be null")
    private String title;

    @NonNull
    @NotNull(message = "Text must not be null")
    private String text;
}
