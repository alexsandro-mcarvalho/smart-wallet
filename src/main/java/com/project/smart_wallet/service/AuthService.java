package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.request.LoginRequest;
import com.project.smart_wallet.dto.request.RegisterRequest;
import com.project.smart_wallet.dto.response.RegisterResponse;
import com.project.smart_wallet.dto.response.TokenResponse;
import com.project.smart_wallet.exception.ConflictException;
import com.project.smart_wallet.repository.UserRepository;
import com.project.smart_wallet.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.smart_wallet.mapper.RegisterMapper.toResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.email(),
                request.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        if (auth.getPrincipal() == null) {
            throw new RuntimeException();
        }

        return tokenService.generateToken((UserDetails) auth.getPrincipal());
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflictException("Usuário já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());

        User user = new User(request.username(), request.email(), encryptedPassword);
        userRepository.save(user);

        return toResponse(user);
    }
}
