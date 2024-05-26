package id.ac.ui.cs.advprog.hoomgroomauth.services;

import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.RegisterRequest;
import id.ac.ui.cs.advprog.hoomgroomauth.dto.AuthenticationResponse;

public interface AuthService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
