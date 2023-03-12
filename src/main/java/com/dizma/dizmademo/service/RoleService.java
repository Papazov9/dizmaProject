package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.enums.UserRoleEnum;

public interface RoleService {
    void initRoles();

    Role findRoleByName(UserRoleEnum member);
}
