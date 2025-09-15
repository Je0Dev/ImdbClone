package com.example.Imdb_Clone.service.impl;

import com.example.Imdb_Clone.dto.AuthResponseDto;
import com.example.Imdb_Clone.dto.LoginRequestDto;
import com.example.Imdb_Clone.dto.RegisterRequestDto;
import com.example.Imdb_Clone.exception.UserAlreadyExistsException;
import com.example.Imdb_Clone.model.Role;
import com.example.Imdb_Clone.model.User;
import com.example.Imdb_Clone.repository.RoleRepository;
import com.example.Imdb_Clone.repository.UserRepository;
import com.example.Imdb_Clone.service.AuthService;
import com.example.Imdb_Clone.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign a default "USER" role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        // If the above line doesn't throw an exception, the user is authenticated
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }

}
