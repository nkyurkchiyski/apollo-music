package com.apollo.music.security;

import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.UserRepository;
import com.apollo.music.data.service.SongService;
import com.apollo.music.jade.AgentManager;
import com.apollo.music.jade.agent.UserSongSeekerAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final SongService songService;

    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository,
                                  final SongService songService) {
        this.userRepository = userRepository;
        this.songService = songService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            AgentManager.createNewAgent(user.getEmail(), UserSongSeekerAgent.class);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    getAuthorities(user));
        }
    }

    private static List<GrantedAuthority> getAuthorities(final User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());

    }

}
