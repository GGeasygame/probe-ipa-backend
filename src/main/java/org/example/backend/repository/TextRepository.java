package org.example.backend.repository;

import org.example.backend.domain.Text;
import org.springframework.data.repository.CrudRepository;

public interface TextRepository extends CrudRepository<Text, Integer> {
}
