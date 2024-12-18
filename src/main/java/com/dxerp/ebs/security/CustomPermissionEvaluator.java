package com.dxerp.ebs.security;
import org.springframework.stereotype.Component;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import  com.dxerp.ebs.CustomUserDetails;
import com.dxerp.ebs.entity.User;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        // Get the logged-in user
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User user = customUserDetails.getUser();  
        
       
        if (user.ischeckAdmin() == true) {
            return true; // Admin can access all resources
        }
        // Check if the role has the necessary menu (permission)
        boolean hasAccess = user.getRole().getMenus().stream()
            // Check if the menu URL matches the requested permission (e.g., menu URL)
            .anyMatch(menu -> menu.getUrl().equals(permission));

              if (!hasAccess) {
            // If the user does not have access, throw an UnauthorizedAccessException
            throw new UnauthorizedAccessException("Unauthorized: You do not have access to this resource.");
        }

        return hasAccess;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        
        return false; // Not used in this example
    }
}
