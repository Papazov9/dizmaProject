package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.RoleService;
import com.dizma.dizmademo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initFirstUser() {
        if (this.userRepository.count() == 0 ) {
            User adminUser = new User();
            Role role = this.roleService.findRoleByName(UserRoleEnum.ADMIN);

            adminUser
                    .setUsername("admin")
                    .setFirstName("Administrator")
                    .setLastName("Administrator")
                    .setEmail("admin@admin.bg")
                    .setPassword(this.passwordEncoder.encode("123"))
                    .setPhoneNumber("+359 777 777 777")
                    .setAge(21)
                    .setRole(role);
            this.userRepository.saveAndFlush(adminUser);
        }
    }

    @Override
    public boolean isUserExists(UserRegistrationBinding userRegistrationBinding) {
        Optional<User> user = this.userRepository.findByUsername(userRegistrationBinding.getUsername());
        return user.isPresent();
    }

    @Override
    public User register(UserRegistrationBinding userRegistrationBinding) {
        User user = this.modelMapper.map(userRegistrationBinding, User.class);
        user.setPassword(this.passwordEncoder.encode(userRegistrationBinding.getPassword()));
        user.setRole(this.roleService.findRoleByName(UserRoleEnum.MEMBER));
        return this.userRepository.saveAndFlush(user);
    }
}
