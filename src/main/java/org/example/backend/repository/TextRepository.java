package org.example.backend.repository;

import org.example.backend.domain.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Integer> {
}
