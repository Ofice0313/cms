package com.mcts.cms.services;

import com.mcts.cms.dto.security.AccountCredentialsDTO;
import com.mcts.cms.dto.security.TokenDTO;
import com.mcts.cms.repositories.UserRepository;
import com.mcts.cms.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity<TokenDTO> sigIn(AccountCredentialsDTO credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUserName(),
                        credentials.getPassword()
                )
        );
        var user = repository.findByUserName(credentials.getUserName());
        if(user == null) {
            throw new UsernameNotFoundException("User " +credentials.getUserName() + " not found!");
        }
        var token = tokenProvider.createAccessToken(
                credentials.getUserName(),
                user.getRoles()
        );
        return ResponseEntity.ok(token);
    }
}
