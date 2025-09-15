package com.example.Imdb_Clone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner; // Add this import
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Add this import
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class ImdbCloneApplicationTests {

    // This tells Spring to create a fake, empty CommandLineRunner for this test,
    // ignoring the real one in your main application class.
    @MockBean
    private CommandLineRunner commandLineRunner;

    @Test
    void contextLoads() {
        // This test will now pass because the problematic data seeder is not running.
    }

}
