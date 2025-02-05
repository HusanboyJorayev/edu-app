package org.example.eduapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eduapp.exceptions.ResourceNotFoundException;
import org.example.eduapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND THIS USERNAME => {}," + username));
    }
}
