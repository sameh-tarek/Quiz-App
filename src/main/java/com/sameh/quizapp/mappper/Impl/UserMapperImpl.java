package com.sameh.quizapp.mappper.Impl;

import com.sameh.quizapp.entity.User;
import com.sameh.quizapp.entity.enums.Role;
import com.sameh.quizapp.mappper.UserMapper;
import com.sameh.quizapp.model.user.UserRequestDto;
import com.sameh.quizapp.model.user.UserResponseDto;
import org.springframework.stereotype.Component;


@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toEntity(UserRequestDto userRequestDTO) {
        User newUser = new User();
        newUser.setUserName(userRequestDTO.getUserName());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setRole(Role.USER);
        newUser.setEnabled(true);
        return newUser;
    }

    @Override
    public UserResponseDto toDTO(User user) {
        UserResponseDto newUserResponseDTO = new UserResponseDto();
        newUserResponseDTO.setId(user.getId());
        newUserResponseDTO.setUserName(user.getUserName());
        newUserResponseDTO.setEmail(user.getEmail());
        return newUserResponseDTO;
    }
}
