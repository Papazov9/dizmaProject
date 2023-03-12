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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lasTName) {
        this.lastName = lasTName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
