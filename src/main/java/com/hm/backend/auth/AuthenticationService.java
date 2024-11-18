package com.hm.backend.auth;

import com.hm.backend.config.JwtService;
import com.hm.backend.constants.ErrorMessages;
import com.hm.backend.dto.LoginRequest;
import com.hm.backend.dto.VerifyTokenResponse;
import com.hm.backend.exception.BadCredentialsException;
import com.hm.backend.exception.UserNotFoundException;
import com.hm.backend.user.Role;
import com.hm.backend.user.User;
import com.hm.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder   passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    //    Sign up
//    @PostMapping("/sign-up")
//    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest authRequest ){
//        var methodName = "AuthController:signup";
//
//        authRequest.isValid();
//        var accessToken = authService.signUp(
////                AuthRequestMapper.INSTANCE.mapToSignUpRequest(authRequest);
////                testMappers.mapToSignUpRequest(authRequest)
//                authRequest
//        );
//
//
//        return  ResponseEntity.status(HttpStatus.CREATED).body(
//                AuthResponse.builder().accessToken(accessToken).build()
//        );
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public VerifyTokenResponse verifyToken(String  accessToken) {
        //      Extract username (email) from access token
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
        }
        System.out.println("after Token "+ accessToken);
        var username =  jwtService.extractUsername(accessToken);
//        var user = repository.findByEmail(request.getEmail())
//                .orElseThrow();

        //      Find User by Email
        var appUser =  repository.findByEmail(username)
                .orElseThrow(()->{
                    return new UserNotFoundException(
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

//        Return User Id
        var userId = appUser.getId();

        return VerifyTokenResponse.builder()
                .userId(userId)
                .build();
    }

    public String login(LoginRequest loginRequest) {
//      Find User by Email
        var appUser =  repository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->{
                    return new UserNotFoundException(
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage(),
                            ErrorMessages.USER_NOT_FOUND.getErrorCode()
                    );
                });

//        Check Password
        if(!passwordEncoder.matches(loginRequest.getPassword(),appUser.getPassword())){
            throw new BadCredentialsException(
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorMessage(),
                    ErrorMessages.PASSWORD_NOT_MATCHED.getErrorCode()
            );
        }
        var user = repository.findByEmail(loginRequest.getEmail());

//        Generate Access Token
        var accessToken = jwtService.generateToken(user.get());

        //        Return Access Token
        return accessToken;
    }

}
