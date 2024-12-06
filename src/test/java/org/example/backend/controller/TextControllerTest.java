package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TextControllerTest {

    @Autowired
    private TextController testee;

    @Test
    void contextLoads() {
        assertThat(testee).isNotNull();
    }
}