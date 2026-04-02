package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.AuthRequestDto;
import com.example.Coffee.Shop.Management.dto.AuthResponseDto;
import com.example.Coffee.Shop.Management.entity.Token;
import com.example.Coffee.Shop.Management.entity.TokenType;
import com.example.Coffee.Shop.Management.model.user.User;
import com.example.Coffee.Shop.Management.repository.TokenRepository;
import com.example.Coffee.Shop.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Transactional
    public String register(AuthRequestDto request){

        var user = User.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .email(request.getEmail())
                .build();
        userRepository.save(user);

        return "User registered successfully!";
    }



    @Transactional
    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        revokeAllUserTokens(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    private void revokeAllUserTokens (User user){
        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
            if(!validTokens.isEmpty()){
                validTokens.forEach(token ->{
                        token.setExpired(true);
                        token.setRevoked(true);
                });
                tokenRepository.saveAll(validTokens);
            }
    }

    @Transactional
    private void saveUserToken(User user, String jwtToken){
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

}
