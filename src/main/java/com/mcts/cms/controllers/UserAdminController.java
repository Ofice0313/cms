package com.mcts.cms.controllers;

import com.mcts.cms.dto.security.UpdateUserRoleDTO;
import com.mcts.cms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PutMapping(value = "/role")
    public ResponseEntity<String> updateUserRole(@RequestBody UpdateUserRoleDTO request) {
        userService.updateUserRole(request.getEmail(), request.getRole());
        return ResponseEntity.ok("User role updated successfully");
    }
}