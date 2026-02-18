package com.example.kabsu.user;

import com.example.kabsu.user.request.RegisterRequest;
import com.example.kabsu.user.response.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    RegisterResponse registerUser(RegisterRequest request);

}
