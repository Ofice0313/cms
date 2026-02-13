package com.mcts.cms.services;

import com.mcts.cms.dto.security.AccountCredentialsDTO;
import com.mcts.cms.dto.security.RecoverPasswordDTO;
import com.mcts.cms.dto.security.TokenDTO;
import com.mcts.cms.entities.User;
import com.mcts.cms.repositories.UserRepository;
import com.mcts.cms.security.jwt.JwtTokenProvider;
import com.mcts.cms.services.exceptions.BusinessException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public ResponseEntity<TokenDTO> sigIn(AccountCredentialsDTO credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword()
                )
        );
        var user = repository.findByEmail(credentials.getEmail());
        if(user == null) {
            throw new UsernameNotFoundException("User " +credentials.getEmail() + " not found!");
        }
        var token = tokenProvider.createAccessToken(
                credentials.getEmail(),
                user.getRoles()
        );
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken(String email, String refreshToken) {
        var user = repository.findByEmail(email);
        TokenDTO token;
        if(user != null) {
            token = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Email " + email+ " not found!");
        }
        return ResponseEntity.ok(token);

    }

    public AccountCredentialsDTO create(AccountCredentialsDTO user) {

        if(user == null) throw new ResourceNotFoundException("The user is null!");

        logger.info("Creating a new user!");

        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setEmail(user.getEmail());
        entity.setPhoneNumber(user.getPhoneNumber());
        if(user.getPassword().equals(user.getConfirmationPassword())) {
            entity.setPassword(generateHashedPassword(user.getPassword()));
        }
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        var dto = repository.save(entity);
        return new AccountCredentialsDTO(
            dto.getFullName(),
            dto.getEmail(),
            dto.getPhoneNumber(),
            dto.getPassword(),
            user.getConfirmationPassword()
        );
    }

    public void recoverPassword(RecoverPasswordDTO dto) {
        if (dto == null || dto.getEmail() == null) {
            throw new BusinessException("Email is required");
        }
        if (dto.getNewPassword() == null || dto.getConfirmationPassword() == null) {
            throw new BusinessException("New password and confirmation are required");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmationPassword())) {
            throw new BusinessException("Password confirmation does not match");
        }

        User user = repository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        user.setPassword(generateHashedPassword(dto.getNewPassword()));
        repository.save(user);
    }

    private String generateHashedPassword(String password) {

        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }
}
