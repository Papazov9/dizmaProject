package com.dizma.dizmademo.model.entity;

import com.dizma.dizmademo.model.enums.UserRoleEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum roleName;

    public Role(){

    }

    public UserRoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(UserRoleEnum roleName) {
        this.roleName = roleName;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
}
