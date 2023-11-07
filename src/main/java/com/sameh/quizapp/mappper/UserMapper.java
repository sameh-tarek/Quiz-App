package com.sameh.quizapp.mappper;

import com.sameh.quizapp.entity.User;
import com.sameh.quizapp.model.user.UserRequestDto;
import com.sameh.quizapp.model.user.UserResponseDto;

public interface UserMapper {

    public User toEntity(UserRequestDto userRequestDTO);

    public UserResponseDto toDTO(User user);
}
