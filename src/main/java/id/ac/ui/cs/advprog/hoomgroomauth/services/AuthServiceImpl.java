package id.ac.ui.cs.advprog.hoomgroomauth.services;

import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.model.User;
import id.ac.ui.cs.advprog.hoomgroomauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }
        else if (request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .sex(request.getSex())
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .status("success")
                .message("User registered successfully.")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(extraClaims, user);
        Map<String, String> data = new HashMap<String, String>();
        data.put("token", jwtToken);
        return AuthenticationResponse.builder()
                .status("success")
                .message("Authentication successful.")
                .data(data).build();
    }
}
