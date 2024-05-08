package id.ac.ui.cs.advprog.hoomgroom.auth.services;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.enums.UserRole;
import id.ac.ui.cs.advprog.hoomgroom.auth.model.User;
import id.ac.ui.cs.advprog.hoomgroom.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .sex(request.getSex())
                .role(request.getRole())
                .build();
        if (userRepository.findByUsername(request.getUsername()).isEmpty()) {
            userRepository.save(user);
        }
        else {
            throw new IllegalArgumentException("User already exists");
        }
        String jwtToken = jwtService.generateToken(user);
        Map<String, String> data = new HashMap<String, String>();
        data.put("token", jwtToken);
        return AuthenticationResponse.builder()
                .status("success")
                .message("User registered successfully.")
                .data(data).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
