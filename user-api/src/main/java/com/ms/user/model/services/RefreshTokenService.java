package com.ms.user.model.services;

import com.ms.user.exception.user.UserNotFoundException;
import com.ms.user.infra.security.TokenService;
import com.ms.user.model.dtos.RefreshRequestDTO;
import com.ms.user.model.dtos.RefreshTokenRequest;
import com.ms.user.model.dtos.TokenResponseDTO;
import com.ms.user.model.entities.RefreshTokenModel;
import com.ms.user.model.entities.UserModel;
import com.ms.user.model.repository.RefreshTokenRepository;
import com.ms.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private String generateRefreshToken(){
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Transactional
    public String saveToken(RefreshTokenRequest data){
        UserModel user = userRepository.findByEmail(data.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = this.generateRefreshToken();

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setToken(token);
        refreshTokenModel.setUser(user);
        refreshTokenModel.setCreatedAt(LocalDateTime.now());
        refreshTokenModel.setExpiresAt(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshTokenModel);
        return token;
    }

    @Transactional
    protected String createRefreshToken(UserModel user){
        String token = this.generateRefreshToken();

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setToken(token);
        refreshTokenModel.setUser(user);
        refreshTokenModel.setCreatedAt(LocalDateTime.now());
        refreshTokenModel.setExpiresAt(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshTokenModel);
        return token;
    }

    @Transactional
    public TokenResponseDTO generateTokenJwt(RefreshRequestDTO data){
        RefreshTokenModel refreshToken = refreshTokenRepository.findByToken(data.refreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if(refreshToken.isRevoked()){
            revokeAllUserTokensById(refreshToken.getUser().getId());
            throw new RuntimeException("Token reuse detected! All sessions have been terminated for security. Please log in again.");
        }

        if(LocalDateTime.now().isAfter(refreshToken.getExpiresAt())){
            throw new RuntimeException("Refresh token expired, please log in again.");
        }

        String newAccessToken = tokenService.generateToken(refreshToken.getUser());

        refreshToken.setRevoked(true);
        refreshToken.setRevokeAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);

        String newRefreshToken = createRefreshToken(refreshToken.getUser());

        return new TokenResponseDTO(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void revokeToken(String token) {
        RefreshTokenModel refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshToken.setRevokeAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeAllUserTokensById(UUID userId) {
        refreshTokenRepository.revokeAllByUserId(userId, LocalDateTime.now());
    }

    @Transactional
    public void revokeAllUserTokensByEmail(String email) {
        refreshTokenRepository.revokeAllByUserEmail(email, LocalDateTime.now());
    }
}