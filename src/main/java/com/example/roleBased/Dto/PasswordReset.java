package com.example.roleBased.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordReset {
    private  String email;
    private String otp;
    private String newPassword;
}
