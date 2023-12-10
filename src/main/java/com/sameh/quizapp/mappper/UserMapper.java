package com.sameh.quizapp.mappper;

import com.sameh.quizapp.model.user.UserRequestDto;
import com.sameh.quizapp.model.user.UserResponseDto;
import com.sameh.quizapp.entity.User;

public interface UserMapper {
    public User toEntity(UserRequestDto userRequestDto);

    public UserResponseDto toDto(User user);
}
