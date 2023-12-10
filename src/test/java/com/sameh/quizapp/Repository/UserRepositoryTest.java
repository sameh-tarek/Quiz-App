package com.sameh.quizapp.Repository;

import com.sameh.quizapp.entity.User;
import com.sameh.quizapp.entity.enums.Role;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        user = new User("Sameh",
                Role.USER, "sameh@gmail.com",
                "password123",
                true);
        userRepository.save(user);
    }

    @DisplayName("Find By Email")
    @Test
    public void TestFindByEmail() {
        assertEquals(true, userRepository.findByEmail("sameh@gmail.com").isPresent(),
                "Should be true");

        assertEquals(false, userRepository.findByEmail("ahmed@gmail.com").isPresent(),
                "Should be false");
    }

}