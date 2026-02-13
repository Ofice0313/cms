package com.mcts.cms.services;

import com.mcts.cms.entities.Permission;
import com.mcts.cms.entities.User;
import com.mcts.cms.repositories.PermissionRepository;
import com.mcts.cms.repositories.UserRepository;
import com.mcts.cms.services.exceptions.BusinessException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if(user != null) return user;
        else throw new UsernameNotFoundException("email " + email + "not found!");
    }

    public void updateUserRole(String email, String role) {
        ensureAdmin();

        if (email == null || email.isBlank()) {
            throw new BusinessException("Email is required");
        }

        String roleDescription = normalizeRole(role);
        Permission permission = permissionRepository.findByDescription(roleDescription);
        if (permission == null) {
            throw new ResourceNotFoundException("Role not found");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        user.setPermissionsList(permissions);
        userRepository.save(user);
    }

    private void ensureAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            throw new AccessDeniedException("Access denied");
        }
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (!isAdmin) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            throw new BusinessException("Role is required");
        }
        String upper = role.trim().toUpperCase();
        if (upper.startsWith("ROLE_")) {
            return upper;
        }
        return "ROLE_" + upper;
    }
}
