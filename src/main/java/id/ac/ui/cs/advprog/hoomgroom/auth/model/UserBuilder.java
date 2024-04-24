package id.ac.ui.cs.advprog.hoomgroom.auth.model;

public class UserBuilder {
    private String username;
    private String fullName;
    private String password;
    private String email;

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserBuilder password(String username) {
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(this.username);
        user.setFullName(this.fullName);
        user.setPassword(this.password);
        user.setEmail(this.password);
        return user;
    }
}
