package model;

import exception.AuthenticationException;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role; 
    private boolean isActive;

    public User(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = true;
    }

    public boolean login(String email, String password) {
        if (!this.email.equals(email) || !this.password.equals(password)) {
            throw new AuthenticationException( "Login gagal: email atau password salah untuk akun " + email);
        }
        return true;
    }

    public int getUserId() { 
        return userId; 
    }
    public String getName() { 
        return name; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getRole() { 
        return role; 
    }
    public boolean isActive() { 
        return isActive; 
    }

    @Override
    public String toString() {
        return "[User " + userId + "] " + name + " (" + role + ")";
    }
}

