package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class UserBuilder {
    private final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final Pattern usernamePattern = Pattern.compile("^[a-z0-9._]+$");
    private String username;
    private String fullName;
    private String password;
    private String email;

    public UserBuilder username(String username) {
        if (username.isEmpty() || !usernamePattern.matcher(username).matches()) {
            throw new IllegalArgumentException();
        }
        this.username = username;
        return this;
    }

    public UserBuilder fullName(String fullName) {
        this.fullName = fullName.isEmpty() ? "Pengguna" : fullName;
        return this;
    }

    public UserBuilder password(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException();
        }
        this.email = email;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(this.username);
        user.setFullName(this.fullName);
        user.setPassword(this.password);
        user.setEmail(this.email);
        return user;
    }
}
