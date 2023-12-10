package com.sameh.quizapp.service;

import com.sameh.quizapp.model.user.UserRequestDto;
import com.sameh.quizapp.model.auth.AuthenticationRequest;
import com.sameh.quizapp.model.auth.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(UserRequestDto request);
}
