package com.GreetingApp.GreetingApp.Service;


import com.GreetingApp.GreetingApp.DTO.PasswordUpdateRequest;
import com.GreetingApp.GreetingApp.Model.AuthUser;
import com.GreetingApp.GreetingApp.Repository.AuthUserRepository;
import com.GreetingApp.GreetingApp.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired(required=true)
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String forgotPassword(String email, PasswordUpdateRequest request) {
        Optional<AuthUser> userOpt = Optional.ofNullable(authUserRepository.findByEmail(email));

        if (userOpt.isEmpty()) {
            return "Sorry! We cannot find the user email: " + email;
        }

        AuthUser user = userOpt.get();
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the new password
        authUserRepository.save(user);

        emailService.sendSimpleEmail(email, "Forgot Password Successful",
                "Your password has been successfully changed.\n\nRegards,\nBridgeLabz");

        return "Password has been changed successfully!";
    }

    public String resetPassword(String email, PasswordUpdateRequest request) {
        Optional<AuthUser> userOpt = Optional.ofNullable(authUserRepository.findByEmail(email));

        if (userOpt.isEmpty()) {
            return "User not found with email: " + email;
        }

        AuthUser user = userOpt.get();

        System.out.println(request.getCurrentPassword());
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return "Current password is incorrect!";
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authUserRepository.save(user);

        emailService.sendSimpleEmail(email, "Password Reset Successful",
                "Your password has been successfully changed.\n\nRegards,\nBridgeLabz");

        return "Password reset successfully!";
    }

    public String registerUser(AuthUser authUser) {
        if (authUserRepository.existsByEmail(authUser.getEmail())) {
            return "Email is already in use!";
        }
        System.out.println(authUser.getPassword());
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword())); // Encrypt password
        System.out.println(authUser.getPassword());
        authUserRepository.save(authUser);
        emailService.sendSimpleEmail(authUser.getEmail(), "Registration Status", "You are registered successfully! Regards, BridgeLabz");
        return "User registered successfully! A confirmation email has been sent from BridgeLabz.";
    }

    public String authenticateUser(String email, String password) {
        Optional<AuthUser> userOpt = Optional.ofNullable(authUserRepository.findByEmail(email));

        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        AuthUser user = userOpt.get();
        System.out.println(password);
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid email or password!";
        }

        return jwtUtil.generateToken(email);
    }
}