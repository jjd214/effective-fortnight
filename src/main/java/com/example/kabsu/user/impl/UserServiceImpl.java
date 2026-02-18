package com.example.kabsu.user.impl;

import com.example.kabsu.exception.BusinessException;
import com.example.kabsu.exception.ErrorCode;
import com.example.kabsu.user.UserMapper;
import com.example.kabsu.user.UserRepository;
import com.example.kabsu.user.UserService;
import com.example.kabsu.user.request.RegisterRequest;
import com.example.kabsu.user.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerUser(final RegisterRequest request) {
        checkEmailExists(request.email());
        var user = userMapper.toEntity(request);
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        var saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmailIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private void checkEmailExists(final String email) {
        boolean emailExists = userRepository.existsUserByEmailIgnoreCase(email);
        if (emailExists)
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
