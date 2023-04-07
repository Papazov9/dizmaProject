package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;

import java.util.List;

public interface RoleService {
    void initRoles();

    Role findRoleByName(UserRoleEnum member);

    List<Role> getPossibleRolesToAdd(User user);

    List<Role> getRolesToRemove(User user);
}
