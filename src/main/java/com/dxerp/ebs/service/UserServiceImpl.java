package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.UserDTO;
import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.entity.User;
import com.dxerp.ebs.repository.UserRepository;
import com.dxerp.ebs.repository.RoleRepository;
import com.dxerp.ebs.util.ApiResponse;
import com.dxerp.ebs.util.AuthResponse;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private static final String UPLOAD_DIR = "uploads/users/";

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder PasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService,EmailService emailService,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
    }
   

    @Override
   
    public ApiResponse<User> registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        // user.setisAdmin(userDTO.getCheckAdmin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        var data = userRepository.save(user);

         // Send verification email
         String verificationLink = "http://localhost:3000/auth/verify-email?email=" + userDTO.getEmail();
         emailService.sendEmail(userDTO.getEmail(), "Verify Your Account", verificationLink);
         return new ApiResponse<>(true, "User Successfully Created", data);
        
    }

    @Override
     @PreAuthorize("hasPermission(null, 'users/create')")
    public ApiResponse<User> addUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
         user.setisAdmin(userDTO.getCheckAdmin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
           if (userDTO.getRoleId() != null) {
           Role role = roleRepository.findById(userDTO.getRoleId());
         
                user.setRole(role);
            
         }

    
      
        
        var data = userRepository.save(user);

         // Send verification email
         String verificationLink = "http://localhost:3000/auth/verify-email?email=" + userDTO.getEmail();
         emailService.sendEmail(userDTO.getEmail(), "Verify Your Account", verificationLink);
         return new ApiResponse<>(true, "User Successfully Created", data);
        
    }

      public String verify(User user) {
        Authentication authenticate
                = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );

       
        if(authenticate.isAuthenticated())
            return jwtService.generateToken(user);
        return "failure";
    }

    @Override

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasPermission(null, 'users/list')")
    public Page<User> getAuthUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleId(user.getRole().getId());
        userDTO.setIsAdmin(user.ischeckAdmin());
        return userDTO;
    }

    public User updateUser(Long id, UserDTO userDTO, MultipartFile profileImage) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setisAdmin(userDTO.getCheckAdmin());
        
        // Find role by roleId
        if (userDTO.getRoleId() != null) {
            Role role = roleRepository.findById(userDTO.getRoleId());
          
                 user.setRole(role);
             
          }


      

          if (profileImage != null && !profileImage.isEmpty()) {
            // Logic to save the file (e.g., to local storage, S3, etc.)
            String filePath = saveProfileImage(profileImage);
            user.setProfileImage(filePath);
        }

        return userRepository.save(user);
    }

       private String saveProfileImage(MultipartFile file) {
        // Example: Save the file locally (can replace with cloud storage logic)
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            // Path filePath = Paths.get("uploads/" + fileName);
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return "/users/" + fileName;
            // return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile image", e);
        }
    }



    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
    
}