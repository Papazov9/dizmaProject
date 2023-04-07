package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.exceptions.UserNotFoundException;
import com.dizma.dizmademo.model.binding.AddRoleBindingModel;
import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.RoleService;
import com.dizma.dizmademo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
                    .setUserRoles(List.of(role));
            this.userRepository.saveAndFlush(adminUser);
        }
    }

    @Override
    public boolean isUserExists(UserRegistrationBinding userRegistrationBinding) {
        Optional<User> user = this.userRepository.findByUsername(userRegistrationBinding.getUsername());
        return user.isPresent();
    }

    @Override
    public void registerAndLogin(UserRegistrationBinding userRegistrationBinding) {
        Role userRole = this.roleService.findRoleByName(UserRoleEnum.MEMBER);

        User user = this.modelMapper.map(userRegistrationBinding, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationBinding.getPassword()))
                .addRole(userRole);
        this.userRepository.saveAndFlush(user);
        this.login(user.getUsername());
    }

    private void login(String username) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void changeUsername(String newUsername) throws UserNotFoundException {
        DizmaUserDetails userDetails = (DizmaUserDetails) this.userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = findByUsername(userDetails.getUsername()).orElseThrow(() -> new UserNotFoundException("User with this username was not found!"));

        userDetails.setUsername(newUsername);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        user.setUsername(newUsername);
        this.userRepository.save(user);

    }

    @Override
    public UserViewModel deleteByUsername(String username) throws UserNotFoundException {
        User userEntity = this.userRepository.
                findByUsername(username)
                .orElseThrow
                        (() -> new UserNotFoundException(String.format("User with username: %s was not found!", username)));
        this.userRepository.delete(userEntity);
        return  this.modelMapper.map(userEntity, UserViewModel.class);
    }

    @Override
    public void addRole(String username, AddRoleBindingModel userModel) {
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow
                        (() -> new UserNotFoundException(String.format("User with username: %s was not found!", username)));
        List<Role> userRoles = user.getUserRoles();
        Role roleToAdd = this.roleService.findRoleByName(UserRoleEnum.valueOf(userModel.getRole()));
        userRoles.add(roleToAdd);
        user.setUserRoles(userRoles);
        this.userRepository.save(user);

    }

    @Override
    public boolean removeRole(String username, AddRoleBindingModel userModel) {
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow
                        (() -> new UserNotFoundException(String.format("User with username: %s was not found!", username)));

        if (user.getUserRoles().size() <= 1) {
            return false;
        }
        List<Role> userRoles = user.getUserRoles();
        Role roleToRemove = this.roleService.findRoleByName(UserRoleEnum.valueOf(userModel.getRole()));
        userRoles.remove(userRoles
                .stream()
                .filter(r -> r.getRoleName()
                        .name()
                        .equals(roleToRemove.getRoleName().name())).findFirst().get());
        user.setUserRoles(userRoles);
        this.userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> getUserById(long l) {
        return this.userRepository.findById(l);
    }

    @Override
    public List<UserViewModel> findAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());
    }
}
