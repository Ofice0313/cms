package com.mcts.cms.controllers;

import com.mcts.cms.controllers.docs.AuthControllerDocs;
import com.mcts.cms.dto.security.AccountCredentialsDTO;
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
public class AuthController implements AuthControllerDocs {
    
    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    @Override
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
        logger.info("Tentativa de login para usuário: {}", credentials != null ? credentials.getEmail() : "null");
        if(credentialsIsInvalid(credentials)){
            logger.warn("Credenciais inválidas: {}", credentials);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = authService.sigIn(credentials);

        if(token == null) {
            logger.warn("Falha na autenticação para usuário: {}", credentials.getEmail());
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        logger.info("Login bem-sucedido para usuário: {}", credentials.getEmail());
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{email}")
    @Override
    public ResponseEntity<?> refreshToken(@PathVariable("email") String email,
                                          @RequestHeader("Authorization") String refreshToken) {
        if(parametersAreInvalid(email, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = authService.refreshToken(email, refreshToken);

        if(token == null) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return ResponseEntity.ok().body(token);
    }

    private boolean parametersAreInvalid(String email, String refreshToken) {
        return StringUtils.isBlank(email) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getPassword()) ||
                StringUtils.isBlank(credentials.getEmail());
    }

    @PostMapping(value = "/createUser")
    @Override
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials) {
        return authService.create(credentials);
    }
}
