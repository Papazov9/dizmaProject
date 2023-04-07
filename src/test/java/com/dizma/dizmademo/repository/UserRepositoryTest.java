package com.dizma.dizmademo.repository;

import com.dizma.dizmademo.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    private static final String USERNAME = "test123";

    @Autowired
    private UserRepository userRepository;

    private User test;

    @BeforeEach
    void setUp() {
        this.test = new User()
                .setUsername(USERNAME)
                .setEmail(USERNAME + "@test.bg")
                .setFirstName(USERNAME)
                .setLastName(USERNAME+"ov")
                .setAge(21)
                .setPassword("123")
                .setPhoneNumber("0893848");
    }

    @AfterEach
    void destroy() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfUserExistWithCorrectUsername() {
        User user = userRepository.save(test);

        Assertions.assertTrue(userRepository.findByUsername(user.getUsername()).isPresent());
    }

    @Test
    void checkWithWrongUsername() {
        User user = userRepository.save(test);
        String newUsername = user.getUsername() + USERNAME;

        Assertions.assertFalse(userRepository.findByUsername(newUsername).isPresent());
    }

    @Test
    void checkWithUserNotExists() {
        Assertions.assertFalse(userRepository.findByUsername(test.getUsername()).isPresent());
    }
}
