package id.ac.ui.cs.advprog.hoomgroom.auth.services;

import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroom.auth.dto.AuthenticationResponse;

public interface AuthService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
