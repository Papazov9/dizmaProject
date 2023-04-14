package com.dizma.dizmademo.model.binding;

import javax.validation.constraints.*;

public class UserRegistrationBinding {
    @NotNull
    @Size(min = 2, message = "The length of the first name must be at least 2 symbols.")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "The length of the last name must be at least 2 symbols.")
    private String lastName;

    @NotNull
    @Size(min = 2, max = 30,message = "The length of the username must be between 2 and 30 symbols.")
    private String username;

    @NotNull
    @Pattern(regexp = "[A-z].+[@][A-z]+[.][A-z+]+", message = "The email must contains @!")
    private String email;

    @NotNull
    @Size(min = 3, message = "The length of the password must be at least 3 symbols.")
    private String password;

    @NotBlank
    @Size(min = 3, message = "The length of the confirm password must be at least 3 symbols.")
    private String confirmPassword;

    @NotBlank
    private String phoneNumber;
    @Min(value = 0)
    @Max(value = 150, message = "The age must be between 0 and 150.")
    private Integer age;

    public UserRegistrationBinding(){
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationBinding setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationBinding setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationBinding setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationBinding setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationBinding setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBinding setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationBinding setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserRegistrationBinding setAge(Integer age) {
        this.age = age;
        return this;
    }
}
