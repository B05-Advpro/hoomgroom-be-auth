package id.ac.ui.cs.advprog.hoomgroom.auth.service;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.enums.UserRole;
import id.ac.ui.cs.advprog.hoomgroom.auth.model.User;
import id.ac.ui.cs.advprog.hoomgroom.auth.repositories.UserRepository;
import id.ac.ui.cs.advprog.hoomgroom.auth.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    private AuthenticationRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        String username = "cbkadal";
        String fullName = "Cicak bin Kadal";
        String email = "cbkadal@kadal.co";
        String password = "kadalbesar123";

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(password);

        String encodedPassword = passwordEncoder.encode(password);

        registerRequest = new RegisterRequest();
        loginRequest = new AuthenticationRequest();

        registerRequest.setUsername(username);
        registerRequest.setFullName(fullName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(encodedPassword);
        registerRequest.setRole(UserRole.USER.getRole());
        registerRequest.setSex("M");

        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
    }

    @Test
    void testValidRegister() {
        String username = "cbkadal";
        String fullName = "Cicak bin Kadal";
        String email = "cbkadal@kadal.co";
        String password = "kadalbesar123";
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(password);
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .fullName(fullName)
                .email(email)
                .password(encodedPassword)
                .role("USER")
                .sex("M")
                .build();

        String token = "abcde.fghij.klmno";
        doReturn(token).when(jwtService).generateToken(new HashMap<>(), user);

        AuthenticationResponse registerResponse = authService.register(registerRequest);
        verify(userRepository, times(1)).save(user);
        assertEquals(registerResponse.getToken(), token);
    }
}
