package com.Auth.Authcore.Service;

import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.jwt.JwtUtil;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil=new JwtUtil();
    private RegisterRepo repo;

    public String loginuser(User user) {
        Optional<User> dbuser = repo.findByname(user.getName());
        if (dbuser.isEmpty()) {
            return "User not found";
        }
        boolean isMatch = encoder.matches(
                user.getPassword(),
                dbuser.get().getPassword());
        if (isMatch) {
            return jwtUtil.generateToken(
                    user.getName()
            );
        }
        return "Invalid Password";
    }
}

