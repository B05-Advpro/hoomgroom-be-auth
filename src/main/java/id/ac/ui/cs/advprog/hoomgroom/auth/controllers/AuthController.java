package id.ac.ui.cs.advprog.hoomgroom.auth.controllers;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;
    @GetMapping(value={"", "/"})
    public ResponseEntity<Object> getHomePage() {
        return new ResponseEntity<>("Invalid endpoint. Try /login or /register.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value={"", "/"})
    public ResponseEntity<Object> postHomePage() {
        return new ResponseEntity<>("Invalid endpoint. Try /login or /register.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<>(authService.register(request), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response = new ResponseEntity<>(AuthenticationResponse.builder()
                    .status("failed")
                    .message("User already exists.")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value={"/register/", "/register"})
    public ResponseEntity<Object> getRegister() {
        return new ResponseEntity<>("Invalid method.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        ResponseEntity<Object> response;
        try {
            response = new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(AuthenticationResponse.builder()
                    .status("failed")
                    .message("Login failed.")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value={"/login/", "/login"})
    public ResponseEntity<Object> getLogin() {
        return new ResponseEntity<>("Invalid method.", HttpStatus.BAD_REQUEST);
    }
}
