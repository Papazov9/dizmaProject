package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.repository.RoleRepository;
import com.dizma.dizmademo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {
        if (this.roleRepository.count() == 0)
        {
            Arrays.stream(UserRoleEnum.values()).forEach( r -> {
                Role currentRole = new Role();
                currentRole.setRoleName(r);
                this.roleRepository.saveAndFlush(currentRole);
            });
        }
    }

    @Override
    public Role findRoleByName(UserRoleEnum name) {
        return this.roleRepository.findByRoleName(name);
    }

    @Override
    public List<Role> getPossibleRolesToAdd(User user) {
        List<Role> userRoles = user.getUserRoles();
        List<Role> all = this.roleRepository.findAll();
        all.removeAll(userRoles);
        return all;
    }

    @Override
    public List<Role> getRolesToRemove(User user) {
        return user.getUserRoles();
    }
}
