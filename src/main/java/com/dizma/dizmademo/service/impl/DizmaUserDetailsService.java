package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class DizmaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DizmaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("The user was not found!"));


    }

    private UserDetails map(User user) {
        return new DizmaUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserRoles().stream().map(this::map).toList()
        );
    }

    private GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name());
    }
}
