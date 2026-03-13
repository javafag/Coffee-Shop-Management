package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.AuthRequestDto;
import com.example.Coffee.Shop.Management.dto.AuthResponseDto;
import com.example.Coffee.Shop.Management.model.user.User;
import com.example.Coffee.Shop.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(AuthRequestDto request){

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);

        return "User registered successfully!";
    }



    public AuthResponseDto authenticate(AuthRequestDto request) {
        // 1. Проверяем логин и пароль (если неверно — вылетит исключение)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);


        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
