package com.dizma.dizmademo.service;

import com.dizma.dizmademo.exceptions.UserNotFoundException;
import com.dizma.dizmademo.model.binding.AddRoleBindingModel;
import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void initFirstUser();
    boolean isUserExists(UserRegistrationBinding userRegistrationBinding);


    List<UserViewModel> findAllUsers();

    void registerAndLogin(UserRegistrationBinding userRegistrationBinding);

    Optional<User> findByUsername(String username);

    void changeUsername(String newUsername) throws UserNotFoundException;

    UserViewModel deleteByUsername(String username) throws UserNotFoundException;

    void addRole(String username, AddRoleBindingModel userModel);

    boolean removeRole(String username, AddRoleBindingModel userModel);

    Optional<User> getUserById(long l);
}
