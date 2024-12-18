package com.dxerp.ebs.dto;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Integer id;
    private String password;
    private Boolean checkAdmin;
    private Number roleId; 
    private Boolean isAdmin; 
    private MultipartFile profileImage;


    public Integer getId() {
        return id;
    }


    public Number getRoleId() {
        return roleId;
    }

    public void setRoleId(Number roleId) {
        this.roleId = roleId;
    }

    public Boolean getCheckAdmin() {
        return isAdmin;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}