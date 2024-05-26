package id.ac.ui.cs.advprog.hoomgroomauth.controllers;

import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
                    .message(e.getMessage())
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
