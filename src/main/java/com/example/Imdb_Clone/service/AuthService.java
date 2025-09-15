package com.example.Imdb_Clone.service;

import com.example.Imdb_Clone.dto.AuthResponseDto;
import com.example.Imdb_Clone.dto.LoginRequestDto;
import com.example.Imdb_Clone.dto.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
