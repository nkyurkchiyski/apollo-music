package com.apollo.music.data.service;

import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractEntityService<User> {

    private final UserRepository repo;

    @Autowired
    public UserService(final UserRepository repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<User, String> getRepository() {
        return repo;
    }
}
