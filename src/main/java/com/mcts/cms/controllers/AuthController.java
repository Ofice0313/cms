package com.mcts.cms.controllers;

import com.mcts.cms.dto.security.AccountCredentialsDTO;
import com.mcts.cms.dto.security.TokenDTO;
import com.mcts.cms.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Operation(summary = "Authenticates an user and returns a token")
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
        logger.info("Tentativa de login para usuário: {}", credentials != null ? credentials.getUserName() : "null");
        if(credentialsIsInvalid(credentials)){
            logger.warn("Credenciais inválidas: {}", credentials);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = authService.sigIn(credentials);

        if(token == null) {
            logger.warn("Falha na autenticação para usuário: {}", credentials.getUserName());
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        logger.info("Login bem-sucedido para usuário: {}", credentials.getUserName());
        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                          @RequestHeader("Authorization") String refreshToken) {
        if(parametersAreInvalid(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = authService.refreshToken(username, refreshToken);

        if(token == null) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return ResponseEntity.ok().body(token);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getPassword()) ||
                StringUtils.isBlank(credentials.getUserName());
    }
}
