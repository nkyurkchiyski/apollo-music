package com.apollo.music.data.repository;

import com.apollo.music.data.entity.User;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(final String username);
}