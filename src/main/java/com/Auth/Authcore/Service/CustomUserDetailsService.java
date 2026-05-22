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
    /*
     HUMAN NOTE:
     CHANGE: use `.roles(...)` instead of `.authorities(...)` when building
     UserDetails.
     WHY: Spring Security's `hasRole("ADMIN")` matcher expects roles to be
     stored as "ROLE_ADMIN" under the hood. `.roles(...)` adds the
     `ROLE_` prefix automatically when building UserDetails. If we used
     `.authorities(user.getRole())` and `user.getRole()` was "ADMIN",
     `hasRole("ADMIN")` would not match because the authority would be
     "ADMIN" (missing the `ROLE_` prefix).
     HOW TO EDIT: change `user.getRole()` values to plain names ("ADMIN",
     "USER") and leave `.roles(...)` here, or switch to `.authorities(...)`
     but store roles like "ROLE_ADMIN" in the database.
     */
    @Autowired
    private RegisterRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        User user = repo.findByName(username)
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
