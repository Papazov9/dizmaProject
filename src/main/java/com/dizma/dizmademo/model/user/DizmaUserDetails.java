package com.dizma.dizmademo.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class DizmaUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private final Collection<? extends GrantedAuthority> authorities;

    public DizmaUserDetails(Long id, String username, String password, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public DizmaUserDetails setId(Long id) {
        this.id = id;
        return this;
    }

    public DizmaUserDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public DizmaUserDetails setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public DizmaUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DizmaUserDetails setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
