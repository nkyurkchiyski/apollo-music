package com.apollo.music.data.generator;

import com.apollo.music.data.entity.Role;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(final PasswordEncoder passwordEncoder,
                                      final UserRepository userRepository) {
        return args -> {
            final Logger logger = LoggerFactory.getLogger(getClass());

            logger.info("Generating demo data");
            createDefaultUsers(passwordEncoder, userRepository, logger);

            logger.info("Generated demo data");
        };
    }

    private void createDefaultUsers(final PasswordEncoder passwordEncoder,
                                    final UserRepository userRepository,
                                    final Logger logger) {
        if (userRepository.count() != 0L) {
            logger.info("Using existing database");
            return;
        }

        logger.info("... generating 2 User entities...");
        User user = new User();
        user.setName("John Normal");
        user.setUsername("user");
        user.setEmail("user@abv.bg");
        user.setPassword(passwordEncoder.encode("user"));
        user.setImageUrl(
                "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        User admin = new User();
        admin.setName("Emma Powerful");
        admin.setUsername("admin");
        admin.setEmail("admin@abv.bg");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setImageUrl(
                "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
        admin.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));
        userRepository.save(admin);
    }

}