package com.example.roleBased.Controller;


import com.example.roleBased.Dto.PasswordReset;
import com.example.roleBased.Services.EmailService;
import com.example.roleBased.Services.ForgotPasswordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/send-otp")
    public void sendOtp(@RequestBody String jsonInput) {

            ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String email = jsonNode.get("email").asText();
        System.out.println("Sending OTP to: " + email);  // Debug log
        forgotPasswordService.forgotPassword(email);



    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody PasswordReset passwordreset) {
        try {
            // Verify OTP and reset password
//            System.out.println(passwordreset.getEmail());

            System.out.println(passwordreset.getOtp() +"jijijjijij");
            System.out.println(passwordreset.getNewPassword());
            forgotPasswordService.verifyOtpResetPassword(passwordreset.getOtp(), passwordreset.getNewPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
