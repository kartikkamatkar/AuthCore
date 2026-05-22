package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RegisterRepo repo;
    public String registerUser(User user){
        if(repo.existsByEmail(user.getEmail())){
            return "Email is Already Exist";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        repo.save(user);
        System.out.println("Saved ");
        return "Registration Successfully ";
    }

}
