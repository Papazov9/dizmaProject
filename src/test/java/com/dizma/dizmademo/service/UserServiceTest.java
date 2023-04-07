package com.dizma.dizmademo.service;

import com.dizma.dizmademo.exceptions.UserNotFoundException;
import com.dizma.dizmademo.model.binding.AddRoleBindingModel;
import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private DizmaUserDetails dizmaUserDetails;

    @Mock
    private ModelMapper modelMapper;

    private UserService serviceTest;
    private User admin;
    private User member;

    @BeforeEach
    public void setUp() {
        this.serviceTest = new UserServiceImpl(userRepository, roleService, modelMapper, passwordEncoder, userDetailsService);

        this.admin = new User();
        admin.setFirstName("Admin")
                .setLastName("Adminov")
                .setUsername("admin")
                .setAge(21)
                .setPassword("secret")
                .setPhoneNumber("+359888888")
                .setEmail("admin@admin.bg")
                .setId(1L);

        this.member = new User();
        member.setFirstName("Member")
                .setLastName("Memberov")
                .setEmail("member@member.bg")
                .setUsername("member")
                .setPassword("secretMember")
                .setAge(21)
                .setPhoneNumber("+35999999")
                .setId(2L);

        this.dizmaUserDetails = new DizmaUserDetails(1L,
                admin.getUsername(),
                admin.getPassword(),
                admin.getFirstName(),
                admin.getLastName(),
                Collections.emptyList());
    }

    @Test
    public void testMemberGetCorrectly() {

        when(this.userRepository.findByUsername(member.getUsername()))
                .thenReturn(Optional.of(member));

        Optional<User> memberToTest = this.serviceTest.findByUsername(member.getUsername());

        Assertions.assertTrue(memberToTest.isPresent());
        Assertions.assertEquals(member.getUsername(), memberToTest.get().getUsername());
    }

    @Test
    public void testGetAdminByIdReturnCorrect() {
        when(this.userRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        Optional<User> userToTest = this.serviceTest.getUserById(1L);
        Assertions.assertTrue(userToTest.isPresent());
        Assertions.assertEquals(admin.getUsername(), userToTest.get().getUsername());
        Assertions.assertEquals(admin.getEmail(), userToTest.get().getEmail());
    }

    @Test
    public void testAddRoleCorrect() {
        Role role = new Role();
        role.setRoleName(UserRoleEnum.MEMBER);
        role.setId(1L);

        AddRoleBindingModel roleBindingModel = new AddRoleBindingModel();
        roleBindingModel.setRole("MEMBER")
                        .setUsername(member.getUsername());

        when(this.roleService.findRoleByName(UserRoleEnum.MEMBER))
                .thenReturn(role);

        when(this.serviceTest.findByUsername(member.getUsername()))
                .thenReturn(Optional.of(member));

        this.serviceTest.addRole(member.getUsername(), roleBindingModel);

        Assertions.assertEquals(1, member.getUserRoles().size());
    }

    @Test
    public void testRemoveRoleIfOnlyOneRoleFail() {
        Role role = new Role();
        role.setRoleName(UserRoleEnum.ADMIN);
        role.setId(2L);

        AddRoleBindingModel roleBindingModel = new AddRoleBindingModel();
        roleBindingModel.setUsername(member.getUsername());
        roleBindingModel.setRole("ADMIN");

        when(this.roleService.findRoleByName(UserRoleEnum.ADMIN))
                .thenReturn(role);

        when(this.serviceTest.findByUsername(member.getUsername()))
                .thenReturn(Optional.of(member));

        this.serviceTest.addRole(member.getUsername(), roleBindingModel);

        this.serviceTest.removeRole(member.getUsername(), roleBindingModel);

        Assertions.assertNotEquals(0, member.getUserRoles().size());
    }

    @Test
    public void testRemoveRoleMoreThanOneRoleSuccess() {
        Role role1 = new Role();
        role1.setRoleName(UserRoleEnum.ADMIN);
        role1.setId(2L);

        Role role2= new Role();
        role2.setRoleName(UserRoleEnum.MEMBER);
        role2.setId(1L);

        AddRoleBindingModel roleBindingModel1 = new AddRoleBindingModel();
        roleBindingModel1.setUsername(member.getUsername());
        roleBindingModel1.setRole("ADMIN");

        AddRoleBindingModel roleBindingModel2 = new AddRoleBindingModel();
        roleBindingModel2.setUsername(member.getUsername());
        roleBindingModel2.setRole("MEMBER");

        when(this.roleService.findRoleByName(UserRoleEnum.ADMIN))
                .thenReturn(role1);
        when(this.roleService.findRoleByName(UserRoleEnum.MEMBER))
                .thenReturn(role2);

        when(this.serviceTest.findByUsername(member.getUsername()))
                .thenReturn(Optional.of(member));

        this.serviceTest.addRole(member.getUsername(), roleBindingModel1);
        this.serviceTest.addRole(member.getUsername(), roleBindingModel2);

        this.serviceTest.removeRole(member.getUsername(), roleBindingModel1);

        Assertions.assertEquals(1, member.getUserRoles().size());
    }

    @Test
    public void testAddRoleFailedWrongUsername(){
        AddRoleBindingModel roleBindingModel1 = new AddRoleBindingModel();
        roleBindingModel1.setUsername(member.getUsername());
        roleBindingModel1.setRole("ADMIN");

        String newUsername = "newUser";
        Assertions.assertThrows(UserNotFoundException.class, () -> this.serviceTest.addRole(newUsername, roleBindingModel1));
    }

    @Test
    public void deleteByUsername(){
        when(userRepository.findByUsername(member.getUsername()))
                .thenReturn(Optional.of(member));
        this.serviceTest.deleteByUsername(member.getUsername());
        verify(userRepository).delete(member);
    }
}
