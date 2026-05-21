package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService
{
    @Autowired
    private RegisterRepo repo;
    public String loginuser(User user){
        Optional<User> dbuser=repo.findByUsername(user.getName());
        if(dbuser.isEmpty()){
            return "User not found";
        }
            if(dbuser.get().getPassword().equals(user.getPassword())){
                return "Login Successfully";
            }
            return "Invalid Password";
    }
}
