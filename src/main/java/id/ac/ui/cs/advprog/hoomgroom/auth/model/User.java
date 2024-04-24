package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;

    private String username;

    private String fullName;

    private String password;

    private String email;

    private LocalDate birthDate;

    private char sex;

    private String role;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

}
