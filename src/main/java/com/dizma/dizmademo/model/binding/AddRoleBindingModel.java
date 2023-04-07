package com.dizma.dizmademo.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddRoleBindingModel {

    private String username;


    @NotEmpty
    private String role;

    public AddRoleBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public AddRoleBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRole() {
        return role;
    }

    public AddRoleBindingModel setRole(String role) {
        this.role = role;
        return this;
    }
}
