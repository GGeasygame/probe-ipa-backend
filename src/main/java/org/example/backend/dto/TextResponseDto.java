package org.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextResponseDto {

    @NonNull
    @NotNull(message = "Id must not be null")
    private Integer id;

    @NonNull
    @NotNull(message = "Title must not be null")
    private String title;

    @NonNull
    @NotNull(message = "Text must not be null")
    private String text;
}
