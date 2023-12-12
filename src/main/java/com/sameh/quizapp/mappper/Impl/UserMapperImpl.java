package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.dto.user.UserRequestDto;
import com.sameh.quizapp.dto.user.UserResponseDto;
import com.sameh.quizapp.entity.User;
import com.sameh.quizapp.mappper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());
        user.setEnabled(true);
        return user;
    }

    @Override
    public UserResponseDto toDto(User user) {
        UserResponseDto newUserResponseDTO = new UserResponseDto();
        newUserResponseDTO.setId(user.getId());
        newUserResponseDTO.setUserName(user.getUserName());
        newUserResponseDTO.setEmail(user.getEmail());
        return newUserResponseDTO;
    }
}
