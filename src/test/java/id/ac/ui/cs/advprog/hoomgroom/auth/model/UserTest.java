package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    UserBuilder userBuilder;
    @BeforeEach
    void setUp() {
        this.userBuilder = User.builder();
    }
    @Test
    void testCreateUserValidUsername() {
        User user = userBuilder.username("cbkadal").build();
        assertEquals("cbkadal", user.getUsername());
    }

    @Test
    void testCreateUserInvalidUsername() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username("").build());
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username("~@#$!@%@#$%!$^!#$%").build());
    }

    @Test
    void testCreateUserValidPassword() {
        User user = userBuilder.password("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").build();
        assertEquals("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO",
                user.getPassword());
    }

    @Test
    void testCreateUserInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.password("").build());
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
        assertThrows(IllegalArgumentException.class, () -> userBuilder.email("Gak Punya Email 😭").build());
    }

    @Test
    void testCreateUserValidDate() {
        User user = userBuilder.birthDate("2004-01-01").build();
        assertEquals("2004-01-01", user.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    void testCreateUserInvalidDate() {
        assertThrows(DateTimeParseException.class, () -> userBuilder.birthDate("37826957812").build());
    }

    @Test
    void testCreateUserValidSex() {
        User userM = userBuilder.sex("M").build();
        User userF = userBuilder.sex("F").build();
        assertEquals("M", userM.getSex());
        assertEquals("F", userF.getSex());
    }

    @Test
    void testCreateUserInvalidSex() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.sex("N").build());
    }

    @Test
    void testCreateUserValidRoles() {
        User user1 = userBuilder.role("USER").build();
        User user2 = userBuilder.role("ADMIN").build();

        assertEquals("USER", user1.getRole());
        assertEquals("ADMIN", user2.getRole());
    }

    @Test
    void testCreateUserNonexistentRole() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.role("HACKER").build());
    }
}
