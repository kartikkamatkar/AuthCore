package com.Auth.Authcore.Service;
import com.Auth.Authcore.dto.AuthResponse;
import com.Auth.Authcore.entity.User;
import com.Auth.Authcore.jwt.JwtUtil;
import com.Auth.Authcore.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService
{
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RegisterRepo repo;

    public AuthResponse loginuser(User user)
    {
        Optional<User> dbuser =
            repo.findByName(user.getName());

        if(dbuser.isEmpty())
        {
            return new AuthResponse("User not found",
            null);
        }

        boolean isMatch = encoder.matches(
                user.getPassword(),
                dbuser.get().getPassword()
        );

        if(isMatch)
        {
            String accessToken=jwtUtil.generateToken(user.getName());
            String refreshToken="refresh-Token-demo";
            return new AuthResponse(accessToken,refreshToken);
        }

        return new AuthResponse("Invalid Password",null);
    }
}