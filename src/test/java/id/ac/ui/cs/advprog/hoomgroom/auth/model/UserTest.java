package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    User valid_user;
    @BeforeEach
    void setUp() {
        this.valid_user = new User();
        this.valid_user.setUsername("cbkadal");
        this.valid_user.setPassword("{bcrypt}$2a$12$1kU3dPF2KhSWpLyG7CrWhObGaKiEjdhZ9H3M.TdIimKyaiRM9N80O");
    }

    @Test
    void testCreateUserValidUsername(){
        assertEquals("cbkadal", this.valid_user.getUsername());
    }
}
