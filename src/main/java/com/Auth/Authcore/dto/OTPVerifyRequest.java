package com.Auth.Authcore.dto;

public class OTPVerifyRequest
{
    private String email;
    private String otp;
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getOtp(){
        return otp;

    }
    public void setOtp(){
        this.otp=otp;
    }
}
