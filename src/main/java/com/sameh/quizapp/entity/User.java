package com.sameh.quizapp.entity;

import com.sameh.quizapp.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name = "`user`")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean isEnabled = false;

    public User(String userName, Role role, String email, String password, boolean isEnabled) {
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

}
