package com.Auth.Authcore.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String password;
    private String email;
    private String name ;
    public User(){

    }
    public User(String password, String email, String name) {
        this.password = password;
        this.email = email;
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
