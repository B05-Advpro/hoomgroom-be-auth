package id.ac.ui.cs.advprog.hoomgroomauth.service;

import id.ac.ui.cs.advprog.hoomgroomauth.enums.UserRole;
import id.ac.ui.cs.advprog.hoomgroomauth.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", "aaaabbbbccccddddeeeeffff1111222233334444555566667777888899990000");
    }

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImNia2FkYWwiLCJpYXQiOjE3MTU3NzQ0NjYsImV4cCI6MTc0ODI1NDU3Mn0.fasejYhXgOV-yjwIjha-UkhwrmH6bhnefVDluuuuMVc";

    @Test
    void testGenerateJwtToken() {
        when(userDetails.getUsername()).thenReturn("cbkadal");

        String newToken = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(newToken);
        assertEquals("cbkadal", username);
    }

    @Test
    void testGenerateJwtTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("extra1", "abc");
        extraClaims.put("extra2", "def");
        when(userDetails.getUsername()).thenReturn("cbkadal");

        String newToken = jwtService.generateToken(extraClaims, userDetails);
        String extra1 = jwtService.extractExtraClaim(newToken, "extra1");
        String extra2 = jwtService.extractExtraClaim(newToken, "extra2");
        assertEquals("abc", extra1);
        assertEquals("def", extra2);
    }

    @Test
    void testExtractUsername() {
        String username = jwtService.extractUsername(token);
        assertEquals("cbkadal", username);
    }

    @Test
    void testExtractRole() {
        String role = jwtService.extractRole(token);
        assertEquals(UserRole.USER.getRole(), role);
    }

    @Test
    void testTokenIsExpired() {
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }

}
