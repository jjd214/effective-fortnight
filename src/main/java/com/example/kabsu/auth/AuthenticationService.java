package com.example.kabsu.auth;

import com.example.kabsu.auth.request.AuthenticationRequest;
import com.example.kabsu.auth.response.AuthenticationResponse;
import com.example.kabsu.user.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    AuthenticationResponse login(AuthenticationRequest request);
    void register(RegisterRequest request);
}
