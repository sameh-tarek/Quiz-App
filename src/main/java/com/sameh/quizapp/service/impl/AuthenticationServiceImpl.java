package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.UserRepository;
import com.sameh.quizapp.dto.user.UserRequestDto;
import com.sameh.quizapp.exception.DuplicateRecordException;
import com.sameh.quizapp.mappper.UserMapper;
import com.sameh.quizapp.model.auth.AuthenticationRequest;
import com.sameh.quizapp.model.auth.AuthenticationResponse;
import com.sameh.quizapp.security.JWTService;
import com.sameh.quizapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(UserRequestDto request) {
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.warn("this user want to register: {}", user);

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateRecordException("this User already exist");
        }

        userRepository.save(user);
        return authenticate(new AuthenticationRequest(user.getEmail(), request.getPassword()));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("user wants to login with this credentials {}", request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(authentication);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .build();
    }

}
