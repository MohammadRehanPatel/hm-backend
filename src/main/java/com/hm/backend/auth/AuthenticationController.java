package com.hm.backend.auth;

import com.hm.backend.dto.LoginRequest;
import com.hm.backend.dto.VerifyTokenRequest;
import com.hm.backend.dto.VerifyTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(AuthenticationResponse.builder().token(service.login(request)).build());
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest ){
        var methodName = "AuthController:verifyToken";

        var userId = service.verifyToken(verifyTokenRequest.getAccessToken());


        return  ResponseEntity.status(HttpStatus.OK).body(
                VerifyTokenResponse.builder().userId(userId.getUserId()).build()
        );
    }


}
