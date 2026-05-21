package com.Auth.Authcore.repository;

import com.Auth.Authcore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepo extends JpaRepository<User, Long>
{

    List<User> findByName(String name);
    List<User>findByEmail(String email);

    List<User> findByPassword(String password);
    boolean existsByEmail(String email);
}