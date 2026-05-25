package com.Auth.Authcore.entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

@Entity
@Table(name ="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min =6,message = "password must be 6 digit ")
    private String password;

    @Column(unique = true)
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Name Required ")
    private String name;

    private String role;

    public User()
    {
    }

    public User(String password,
                String email,
                String name,
                String role)
    {
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}