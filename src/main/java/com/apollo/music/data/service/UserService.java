package com.apollo.music.data.service;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    public boolean existsWithUsername(final String username) {
        final User probe = new User();
        probe.setUsername(username);
        final Example<User> example = Example.of(probe, ExampleUtils.getExactIgnoreCaseExampleMatcher(EntityConfiguration.USERNAME_FIELD_NAME));
        return getRepository().exists(example);
    }


    public boolean existsWithEmail(final String email) {
        final User probe = new User();
        probe.setEmail(email);
        final Example<User> example = Example.of(probe, ExampleUtils.getExactIgnoreCaseExampleMatcher(EntityConfiguration.EMAIL_FIELD_NAME));
        return getRepository().exists(example);
    }
}
