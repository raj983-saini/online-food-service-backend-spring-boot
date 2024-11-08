package com.example.roleBased.Services;


import com.example.roleBased.Entity.Otp;
import com.example.roleBased.Repository.OtpTokenRepository;
import com.example.roleBased.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private EmailService emailService;

    public  String emailCache;

    // Generate a random OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Generate and send OTP to user email
    public void forgotPassword(String email) {
        // Check if user exists with this email
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found with email: " + email);
        }
        otpTokenRepository.deleteByEmail(email);
        System.out.println( " dfhjgkjdhfkjgdhfjkghkj            hello ");
        String otp = generateOtp();
        Otp otpToken = new Otp();
        otpToken.setEmail(email);
        otpToken.setOtp(otp);
        otpToken.setCreatedAt(System.currentTimeMillis());
        otpTokenRepository.save(otpToken);
        emailCache = email;
        System.out.println(email);
        System.out.println(otp);
        // Send the OTP via email
        emailService.sendOtpEmail(email, "Password Reset OTP", otp);
    }

    //     Verify the OTP and reset the password
// Verify the OTP and reset the password
    public void verifyOtpResetPassword(String otp, String newPassword) {
        String email = emailCache;
        System.out.println("Verify " + email);
        System.out.println("verify " + otp);
        System.out.println(newPassword);

        // Fetch all OTPs associated with the email
        List<Otp> otpTokens = otpTokenRepository.findByEmail(email);
        System.out.println("find " + email);

        if (otpTokens.isEmpty()) {
            throw new RuntimeException("Invalid OTP or email");
        }

        // Get the most recent OTP token (assuming the list is ordered by creation time)
        Otp otpToken = otpTokens.get(otpTokens.size() - 1);
        long currentTime = System.currentTimeMillis();

        // Check if OTP is correct and hasn't expired (e.g., 5 minutes)
        if (!otpToken.getOtp().equals(otp) || (currentTime - otpToken.getCreatedAt()) > 5 * 60 * 1000) {
            throw new RuntimeException("OTP is invalid or expired");
        }

        // Reset the password
        userRepository.updatePasswordByEmail(passwordEncoder.encode(newPassword), email);

        // Remove the OTP after successful password reset
        otpTokenRepository.delete(otpToken);
    }
}