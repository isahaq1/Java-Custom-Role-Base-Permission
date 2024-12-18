package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.UserDTO;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.entity.User;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Set;



public interface UserService {
    ApiResponse<User> registerUser(UserDTO userDTO);
    List<User> getAllUsers();
    ApiResponse<User> addUser(UserDTO userDTO);
    
    String verify(User user);

    Page<User> getAuthUsers(int page, int size);
    UserDTO getUserById(Long id);
    User updateUser(Long id, UserDTO userDTO, MultipartFile profileImage);
    void deleteUser(Integer id);
   
}