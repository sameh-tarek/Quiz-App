package com.sameh.quizapp.model.user;

import com.sameh.quizapp.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

        String userName;
        String email;
        String password;
        Role role;
}
