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

    @Autowired
    private RedisService redisService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    public String loginuser(User user)
    {
        Optional<User> dbuser =
            repo.findByEmail(user.getEmail());

        if(user.getPassword()==null){
            return "password required";
        }
        if(dbuser.isEmpty())
        {
            return "User not found";
        }

        boolean isMatch = encoder.matches(
                user.getPassword(),
                dbuser.get().getPassword()
        );

        if(isMatch)
        {
            String otp=otpService.otpService();
            redisService.saveOtp(dbuser.get().getEmail(),otp);
            emailService.sendOtp(dbuser.get().getEmail(),otp);
            return "OTP Sent";
        }

        return "Invalid Password";
    }
}