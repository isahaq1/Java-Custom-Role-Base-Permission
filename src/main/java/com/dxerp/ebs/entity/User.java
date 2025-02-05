package com.dxerp.ebs.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "is_admin", nullable = false)
    private boolean checkAdmin = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "profile_image") // Stores the URL or file path
    private String profileImage;

    //   @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(
    //     name = "role_permissions",
    //     joinColumns = @JoinColumn(name = "role_id")
    // )
    // private Set<Role> roles = new HashSet<>();
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean ischeckAdmin() {
        return checkAdmin;
    }

    public void setisAdmin(boolean checkAdmin) {
        this.checkAdmin = checkAdmin;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    
}
