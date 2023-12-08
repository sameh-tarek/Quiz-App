package com.sameh.quizapp.service;

import com.sameh.quizapp.dto.UserRequestDto;
import com.sameh.quizapp.model.auth.AuthenticationRequest;
import com.sameh.quizapp.model.auth.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(UserRequestDto request);
}
