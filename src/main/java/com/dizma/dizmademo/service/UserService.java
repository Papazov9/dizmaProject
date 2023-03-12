package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.User;

public interface UserService {

    void initFirstUser();
    boolean isUserExists(UserRegistrationBinding userRegistrationBinding);

    User register(UserRegistrationBinding userRegistrationBinding);
}
