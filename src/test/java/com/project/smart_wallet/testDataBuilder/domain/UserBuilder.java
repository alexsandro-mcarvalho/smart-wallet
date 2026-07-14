package com.project.smart_wallet.testDataBuilder.domain;

import com.project.smart_wallet.domain.User;

public class UserBuilder {

    private String username = "john_doe";
    private String email = "john.doe@example.com";
    private String password = "SecurePassword123!";

    private UserBuilder() {}

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(username, email, password);
    }
}
