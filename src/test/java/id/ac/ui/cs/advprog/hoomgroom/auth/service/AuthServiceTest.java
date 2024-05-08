package id.ac.ui.cs.advprog.hoomgroom.auth.service;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.enums.UserRole;
import id.ac.ui.cs.advprog.hoomgroom.auth.model.User;
import id.ac.ui.cs.advprog.hoomgroom.auth.repositories.UserRepository;
import id.ac.ui.cs.advprog.hoomgroom.auth.services.AuthServiceImpl;
import id.ac.ui.cs.advprog.hoomgroom.auth.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

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

    /*
    Register tests
     */
    @Test
    void testValidRegister() {
        String token = "abcde.fghij.klmno";
        doReturn(token).when(jwtService).generateToken(any(User.class));

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());

        AuthenticationResponse registerResponse = authService.register(registerRequest);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(registerResponse.getToken(), token);
    }

    @Test
    void testValidRegisterEmptyFullName() {
        registerRequest.setFullName("");
        String token = "abcde.fghij.klmno";
        doReturn(token).when(jwtService).generateToken(any(User.class));

        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());

        AuthenticationResponse registerResponse = authService.register(registerRequest);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(registerResponse.getToken(), token);
    }

    @Test
    void testInvalidRegisterEmptyUsername() {
        registerRequest.setUsername("");
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyPassword() {
        registerRequest.setPassword("");
        doReturn("")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyEmail() {
        registerRequest.setEmail("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptyRole() {
        registerRequest.setRole("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));
    }

    @Test
    void testInvalidRegisterEmptySex() {
        registerRequest.setRole("");
        doReturn("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO")
                .when(passwordEncoder)
                .encode(registerRequest.getPassword());
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));
    }

    /*
    Login tests
     */
    @Test
    void testValidLogin() {
        String token = "abcde.fghij.klmno";
        User user = User.builder().username(loginRequest.getUsername()).password(loginRequest.getPassword()).build();
        doReturn(token).when(jwtService).generateToken(user);
        doReturn(user).when(userRepository).findByUsername(loginRequest.getUsername());
        doReturn(null).when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        AuthenticationResponse loginResponse = authService.authenticate(loginRequest);
        verify(userRepository, times(1)).findByUsername("cbkadal");
        assertEquals(loginResponse.getToken(), token);
    }

    @Test
    void testInvalidLogin() {
        loginRequest.setPassword("passwordSalah");
        assertThrows(NoSuchElementException.class, () -> authService.authenticate(loginRequest));
    }
}
