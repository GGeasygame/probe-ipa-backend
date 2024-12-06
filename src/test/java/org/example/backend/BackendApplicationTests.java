package org.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
    }

    @Test
    void checkBeans() {
        boolean isBeanPresent = context.containsBean("textMapper");
        System.out.println("TextMapper bean present: " + isBeanPresent);
    }
}
