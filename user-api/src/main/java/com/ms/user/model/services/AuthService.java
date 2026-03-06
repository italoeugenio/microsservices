package com.ms.user.model.services;

import com.ms.user.enums.UserRole;
import com.ms.user.exception.auth.ExpiredCodeException;
import com.ms.user.exception.auth.InvalidCodeException;
import com.ms.user.exception.user.UserAlreadyExistException;
import com.ms.user.exception.user.UserAlreadyVerifiedException;
import com.ms.user.exception.user.UserNotFoundException;
import com.ms.user.infra.security.TokenService;
import com.ms.user.model.dtos.*;
import com.ms.user.model.entities.UserModel;
import com.ms.user.model.repository.UserRepository;
import com.ms.user.producer.UserProduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserProduce userProduce;

    private String generateCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO data) {
        var user = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(user);
        refreshTokenService.revokeAllUserTokensByEmail(data.email().toLowerCase());
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        String refreshToken = refreshTokenService.saveToken(new RefreshTokenRequest(data.email().toLowerCase()));
        return new LoginResponseDTO(data.email().toLowerCase(), token, refreshToken);
    }

    @Transactional
    public String register(RegisterRequestDTO data) {
        var user = userRepository.findByEmail(data.email().toLowerCase());
        if (user.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }

        UserModel newUser = new UserModel();
        UserRole role = UserRole.CLIENT;

        newUser.setFullName(data.fullName());
        newUser.setEmail(data.email().toLowerCase());
        newUser.setPassword(new BCryptPasswordEncoder().encode(data.password()));
        newUser.setRole(role);
        newUser.setVerificationCode(generateCode());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setVerificationTokenExpiresAt(LocalDateTime.now().plusMinutes(10));

        EmailDTO emailDTO = new EmailDTO(
                newUser.getEmail(),
                "Verification Code",
                "Your verification code is: " + newUser.getVerificationCode()
        );

        userProduce.publishEmailMessage(emailDTO);
        userRepository.save(newUser);

        return "message: Registration successful. Please check your email for verification code.\n" +
                "email: " + newUser.getEmail();
    }

    public String confirm(ConfirmeUserRequestDTO data){
        var user = userRepository.findByEmail(data.email());

        if(user.isEmpty()) throw new UserNotFoundException("User not found");
        if(user.get().isVerified()) throw new UserAlreadyVerifiedException("User already verified");
        if(!data.code().equals(user.get().getVerificationCode())) throw new InvalidCodeException("Code is invalid");
        if(LocalDateTime.now().isAfter(user.get().getVerificationTokenExpiresAt())) throw new ExpiredCodeException("Code expired");

        if(data.code().equals(user.get().getVerificationCode())) {
            user.get().setVerified(true);
            user.get().setVerificationCode(null);
            user.get().setVerificationTokenExpiresAt(null);
            user.get().setUpdatedAt(LocalDateTime.now());
            userRepository.save(user.get());
            return "User successfully verified";
        }

        return "Something went wrong";
    }


    public String resendCode(ResendCodeRequestDTO data){
        var user = userRepository.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        var now = LocalDateTime.now();
        var expiresAt = user.getVerificationTokenExpiresAt();

        if (now.isAfter(expiresAt)) {
            user.setVerificationCode(generateCode());
            user.setVerificationTokenExpiresAt(now.plusMinutes(10));
            EmailDTO emailDTO = new EmailDTO(
                    user.getEmail(),
                    "New Verification Code",
                    "Your new verification code is: " + user.getVerificationCode()
            );
            userProduce.publishEmailMessage(emailDTO);
            userRepository.save(user);
            return "Code resent successfully";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedTime = expiresAt.format(formatter);

        throw new RuntimeException("You can request a new code after: " + formattedTime);
    }

    public String changePassword(@AuthenticationPrincipal UserDetails user, ChangePasswordRequestDTO data){
        var userModel = userRepository.findByEmail(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!passwordEncoder.matches(data.currentPassword(), userModel.getPassword())){
            return "Current password is incorrect";
        } else {
            userModel.setPassword(passwordEncoder.encode(data.newPassword()));
            userRepository.save(userModel);
            return "Password changed successfully";
        }
    }

    @Transactional
    public String recoverPassword(RecoverPasswordRequestDTO data) {
        var user = userRepository.findByEmail(data.email().toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.isVerified()) {
            throw new RuntimeException("User is not verified");
        }

        String code = generateCode();
        user.setVerificationCode(code);
        user.setVerificationTokenExpiresAt(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        EmailDTO emailDTO = new EmailDTO(
                user.getEmail(),
                "Password Recovery",
                "Your password recovery code is: " + code
        );
        userProduce.publishEmailMessage(emailDTO);

        return "Password recovery code sent successfully. Please check your email.";
    }

    @Transactional
    public String resetPassword(ResetPasswordRequestDTO data) {
        var user = userRepository.findByEmail(data.email().toLowerCase()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.isVerified()) throw new UserNotFoundException("User is not verified");
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(data.code())) throw new InvalidCodeException("Invalid verification code");
        if (LocalDateTime.now().isAfter(user.getVerificationTokenExpiresAt())) throw new ExpiredCodeException("Verification code expired");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(data.password()));

        user.setVerificationCode(null);
        user.setVerificationTokenExpiresAt(null);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return "Password has been reset successfully";
    }

    @Transactional
    public TokenResponseDTO refreshToken(RefreshRequestDTO data){
        return refreshTokenService.generateTokenJwt(data);
    }


    @Transactional
    public void logout(RefreshRequestDTO data){
        refreshTokenService.revokeToken(data.refreshToken());
    }
}
