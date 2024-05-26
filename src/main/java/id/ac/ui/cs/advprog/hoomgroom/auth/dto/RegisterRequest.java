package id.ac.ui.cs.advprog.hoomgroom.auth.dto;

import id.ac.ui.cs.advprog.hoomgroom.auth.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String role;
    private String birthDate;
    private String sex;
}
