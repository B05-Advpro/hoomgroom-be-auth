package id.ac.ui.cs.advprog.hoomgroomauth.model;

import id.ac.ui.cs.advprog.hoomgroomauth.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

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
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username(""));
        assertThrows(IllegalArgumentException.class, () -> userBuilder.username("~@#$!@%@#$%!$^!#$%"));
    }

    @Test
    void testCreateUserValidPassword() {
        User user = userBuilder.password("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO").build();
        assertEquals("$2a$12$tuUIz/Suy/iFj5b6UFWmROzMiqYMyPokavtlnVhwEHhF0CeCddokO",
                user.getPassword());
    }

    @Test
    void testCreateUserInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.password(""));
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
        assertThrows(IllegalArgumentException.class, () -> userBuilder.email("Gak Punya Email 😭"));
    }

    @Test
    void testCreateUserValidDate() {
        User user = userBuilder.birthDate("2004-01-01").build();
        assertEquals("2004-01-01", user.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    void testCreateUserInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.birthDate("37826957812"));
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
        assertThrows(IllegalArgumentException.class, () -> userBuilder.sex("N"));
    }

    @Test
    void testCreateUserValidRoles() {
        User user1 = userBuilder.role(UserRole.USER.getRole()).build();
        User user2 = userBuilder.role(UserRole.ADMIN.getRole()).build();

        assertEquals(UserRole.USER, user1.getRole());
        assertEquals(UserRole.ADMIN, user2.getRole());
    }

    @Test
    void testCreateUserNonexistentRole() {
        assertThrows(IllegalArgumentException.class, () -> userBuilder.role("HACKER"));
    }
}
