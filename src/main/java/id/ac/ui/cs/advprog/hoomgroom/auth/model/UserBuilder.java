package id.ac.ui.cs.advprog.hoomgroom.auth.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserBuilder {
    private final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final Pattern usernamePattern = Pattern.compile("^[a-z0-9._]+$");
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String sex;
    private String role;
    private LocalDate birthDate;

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

    public UserBuilder sex(String sex){
        if (sex.isEmpty() || !(sex.equals("M") || sex.equals("F"))) {
            throw new IllegalArgumentException();
        }
        this.sex = sex;
        return this;
    }

    public UserBuilder role(String role){
        if (!(role.equals("USER") || role.equals("ADMIN"))) {
            throw new IllegalArgumentException();
        }
        this.role = role;
        return this;
    }
    public UserBuilder birthDate(String birthDate){
        this.birthDate = LocalDate.parse(birthDate);
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(this.username);
        user.setFullName(this.fullName);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setSex(this.sex);
        user.setRole(this.role);
        user.setBirthDate(this.birthDate);
        return user;
    }
}
