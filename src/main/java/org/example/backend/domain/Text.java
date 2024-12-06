package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "texts")
@Getter
@Setter
@ToString
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NonNull
    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the same object
        if (o == null || getClass() != o.getClass()) return false; // Check class compatibility
        Text text = (Text) o;
        return Objects.equals(id, text.id) &&
                Objects.equals(title, text.title) &&
                Objects.equals(this.text, text.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text); // Generate a consistent hash code
    }
}
