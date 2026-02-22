package com.example.kabsu.auth;

import com.example.kabsu.auth.request.AuthenticationRequest;
import com.example.kabsu.auth.response.AuthenticationResponse;
import com.example.kabsu.common.response.ApiResponse;
import com.example.kabsu.user.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login (@Valid @RequestBody final AuthenticationRequest request) {
        var response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody final RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
