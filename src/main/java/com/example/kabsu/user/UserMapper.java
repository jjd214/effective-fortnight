package com.example.kabsu.user;

import com.example.kabsu.user.request.RegisterRequest;
import com.example.kabsu.user.response.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(final RegisterRequest request) {
        return User
                .builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public RegisterResponse toResponseDto(final User user) {
        return new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

}
