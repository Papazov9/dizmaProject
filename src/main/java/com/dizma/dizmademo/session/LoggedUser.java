package com.dizma.dizmademo.session;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {

    private long id;
    private String username;

    private Role role;

    public LoggedUser() {
    }

    public void login(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public void logout(){
        this.id = 0;
        this.username = "";
    }

    public boolean isLogged(){
        return this.id > 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
