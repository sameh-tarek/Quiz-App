package com.sameh.quizapp.service.impl;

import com.sameh.quizapp.Repository.UserRepository;
import com.sameh.quizapp.entity.User;
import com.sameh.quizapp.entity.enums.Role;
import com.sameh.quizapp.mappper.UserMapper;
import com.sameh.quizapp.model.auth.AuthenticationRequest;
import com.sameh.quizapp.model.auth.AuthenticationResponse;
import com.sameh.quizapp.security.JWTService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @DisplayName("Authentication")
    @Test
    void shouldAuthenticateUser(){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user@example.com", "password123");
        User user = new User("john_doe",  Role.USER,"user@example.com", "encodedPassword", true);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(authentication)).thenReturn("jwtToken");

        // Call the method
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getUserName(), response.getUserName());

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(jwtService, times(1)).generateToken(authentication);
    }


}