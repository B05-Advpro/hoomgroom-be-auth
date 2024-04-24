package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    UserBuilder userBuilder;
    @BeforeEach
    void setUp() {
        this.userBuilder = new User.builder();
    }
    @Test
    void testCreateUserValidUsername() {
        User user = userBuilder.username("cbkadal").build();
        assertEquals("cbkadal", user.getUsername());
    }

    @Test
    void testCreateUserInvalidUsername() {
        assertThrows(IllegalArgumentException.class, userBuilder.username("").build());
        assertThrows(IllegalArgumentException.class, userBuilder.username("~@#$!@%@#$%!$^!#$%").build());
    }

    @Test
    void testCreateUserValidPassword() {
        User user = userBuilder.password("adprosukses123").build();
        assertEquals("{bcrypt}$2a$12$qwDzccZRqXQ6Dm3auq5Sx.nXegoc0vfuO84DoLVLE.sTgYGjM/kw2",
                user.getPassword());
    }

    @Test
    void testCreateUserInvalidPassword() {
        assertThrows(IllegalArgumentException.class, userBuilder.password("").build());
    }

    @Test
    void testCreateUserFullName() {
        User user = userBuilder.fullName("Baba Fitt").build();
        assertEquals("Baba Fitt",
                user.getFullName());
    }

    @Test
    void testCreateUserDefaultFullName() {
        User user = userBuilder.fullName("").build();
        assertEquals("Pengguna", user.getFullName());
    }

    @Test
    void testCreateUserValidEmail() {
        User user = userBuilder.email("baba@gmail.com").build();
        assertEquals("baba@gmail.com", user.getEmail());
    }

    @Test
    void testCreateUserInvalidEmail() {
        assertThrows(userBuilder.email("Gak Punya Email 😭").build());
    }
}
