package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
    @Autowired
    private RegisterRepo repo;
    public String registerUser(User user){
        if(repo.existsByEmail(user.getEmail())){
            return "Email is Already Exist";
        }
        repo.save(user);
        return "Registration Successfully ";
    }

}
