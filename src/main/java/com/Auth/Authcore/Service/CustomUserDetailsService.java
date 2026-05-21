package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.repository.RegisterRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService
{
    @Autowired
    private RegisterRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        User user = repo.findByname(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User Not Found"
                        ));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}