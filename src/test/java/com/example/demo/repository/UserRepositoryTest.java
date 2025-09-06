package com.example.demo.repository;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User("lokesh");
        userRepository.save(user);

        User found = userRepository.findByUsername("lokesh");
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("lokesh");
    }
}
