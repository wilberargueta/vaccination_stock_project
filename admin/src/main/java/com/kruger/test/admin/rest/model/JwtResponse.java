package com.kruger.test.admin.rest.model;

public class JwtResponse {

    private String token;
    private String token_type;
    private String expires;
    private String username;
    private Role role;
    private Long id;

    public JwtResponse() {
    }

    public JwtResponse(String token, String token_type, String expires, String username, Role role, Long id) {
        this.token = token;
        this.token_type = token_type;
        this.expires = expires;
        this.username = username;
        this.role = role;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JwtResponse [expires=" + expires + ", id=" + id + ", role=" + role + ", token=" + token
                + ", token_type=" + token_type + ", username=" + username + "]";
    }

}
