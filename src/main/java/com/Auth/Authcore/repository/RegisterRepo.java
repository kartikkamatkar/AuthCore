package com.Auth.Authcore.repository;

import com.Auth.Authcore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegisterRepo extends JpaRepository<User, Long>
{

    Optional<User> findByName(String name);

    Optional<User>findByEmail(String email);

    boolean existsByEmail(String email);

}