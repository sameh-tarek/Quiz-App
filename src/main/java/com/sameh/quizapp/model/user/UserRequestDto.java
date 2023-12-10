package com.sameh.quizapp.model.user;

import com.sameh.quizapp.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String userName;

    private Role role;

    private String email;

    private String password;

    private boolean isEnabled = true;
}
