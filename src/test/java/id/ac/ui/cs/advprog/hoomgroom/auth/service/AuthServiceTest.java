package id.ac.ui.cs.advprog.hoomgroom.auth.service;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.enums.UserRole;
import id.ac.ui.cs.advprog.hoomgroom.auth.model.User;
import id.ac.ui.cs.advprog.hoomgroom.auth.repositories.UserRepository;
import id.ac.ui.cs.advprog.hoomgroom.auth.services.AuthServiceImpl;
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

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthenticationRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        String username = "cbkadal";
        String fullName = "Cicak bin Kadal";
        String email = "cbkadal@kadal.co";
        String password = "kadalbesar123";
        String birthDate = "2004-04-04";

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(password);

        String encodedPassword = passwordEncoder.encode(password);

        registerRequest = new RegisterRequest();
        loginRequest = new AuthenticationRequest();

        registerRequest.setUsername(username);
        registerRequest.setFullName(fullName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(encodedPassword);
        registerRequest.setRole(UserRole.USER.getRole());
        registerRequest.setBirthDate(birthDate);
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
        String birthDate = "2004-04-04";
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(password);
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .fullName(fullName)
                .email(email)
                .birthDate(birthDate)
                .password(encodedPassword)
                .role("USER")
                .sex("M")
                .build();

        String token = "abcde.fghij.klmno";
        doReturn(token).when(jwtService).generateToken(any(User.class));

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").when(passwordEncoder).encode(registerRequest.getPassword());

        AuthenticationResponse registerResponse = authService.register(registerRequest);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(registerResponse.getToken(), token);
    }
}
