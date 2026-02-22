package com.example.kabsu.auth.impl;

import com.example.kabsu.auth.AuthenticationService;
import com.example.kabsu.auth.request.AuthenticationRequest;
import com.example.kabsu.auth.response.AuthenticationResponse;
import com.example.kabsu.exception.BusinessException;
import com.example.kabsu.exception.ErrorCode;
import com.example.kabsu.security.JwtService;
import com.example.kabsu.user.User;
import com.example.kabsu.user.UserMapper;
import com.example.kabsu.user.UserRepository;
import com.example.kabsu.user.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponse login(final AuthenticationRequest request) {
        final Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        final User user = (User) auth.getPrincipal();
        assert user != null;
        final String token = jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse
                .builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(final RegisterRequest request) {
        checkEmailExists(request.email());
        var user = userMapper.toEntity(request);
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public @NullMarked UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmailIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private void checkEmailExists(final String email) {
        boolean emailExists = userRepository.existsUserByEmailIgnoreCase(email);
        if (emailExists)
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
