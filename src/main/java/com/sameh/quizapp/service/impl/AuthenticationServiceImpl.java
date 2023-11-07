package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.UserRepository;
import com.sameh.quizapp.mappper.UserMapper;
import com.sameh.quizapp.model.auth.AuthenticationRequest;
import com.sameh.quizapp.model.auth.AuthenticationResponse;
import com.sameh.quizapp.model.user.UserRequestDto;
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
            log.info("user wants to register with this information {}", user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);


            var authenticationRequest = AuthenticationRequest.builder()
                    .email(request.getEmail())
                    .password(request.getPassword()).build();

            log.info("user wants to Authenticate with this email and password {}", authenticationRequest);

            AuthenticationResponse response = authenticate(authenticationRequest);
            return response;
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
