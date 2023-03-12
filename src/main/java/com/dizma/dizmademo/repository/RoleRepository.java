package com.dizma.dizmademo.repository;

import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(UserRoleEnum name);
}
