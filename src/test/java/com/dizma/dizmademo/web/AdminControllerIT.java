package com.dizma.dizmademo.web;

import com.dizma.dizmademo.model.binding.AddRoleBindingModel;
import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.repository.CategoryRepository;
import com.dizma.dizmademo.repository.RoleRepository;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.ProductService;
import com.dizma.dizmademo.service.RoleService;
import com.dizma.dizmademo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private RoleService roleService;

    private List<Role> rolesToRemove;

    private User member;
    private UserRegistrationBinding memberBinding;
    private UserRegistrationBinding adminBinding;
    private User admin;

    private AddRoleBindingModel removeRoleBinding;

    private UserViewModel userViewModel;


    @BeforeEach
    void setUp() {
        Role adminRole = new Role();
        adminRole.setRoleName(UserRoleEnum.ADMIN);
        adminRole.setId(1L);

        Role memberRole = new Role();
        memberRole.setRoleName(UserRoleEnum.MEMBER);
        memberRole.setId(2L);


        this.roleRepository.saveAndFlush(adminRole);
        this.roleRepository.saveAndFlush(memberRole);
        this.member = new User();
        this.member.setAge(32)
                .setPhoneNumber("+359878786")
                .setPassword("123")
                .setEmail("member@member.bg")
                .setUsername("Member1")
                .setFirstName("Member")
                .setLastName("Memberov")
                .setUserRoles(List.of(adminRole, memberRole));

        this.memberBinding = new UserRegistrationBinding();

        this.memberBinding.setAge(32)
                .setPhoneNumber("+359878786")
                .setPassword("123")
                .setEmail("member@member.bg")
                .setUsername("Member1")
                .setFirstName("Member")
                .setLastName("Memberov");


        this.userService.registerAndLogin(this.memberBinding);

        this.admin = new User();
        this.admin.setAge(21)
                .setPhoneNumber("+359878786453")
                .setPassword("123")
                .setEmail("admin@admin.bg")
                .setUsername("Admin1")
                .setFirstName("Admin")
                .setLastName("Adminov");
        this.adminBinding = new UserRegistrationBinding();

        this.adminBinding.setAge(32)
                .setPhoneNumber("+359878786453")
                .setPassword("123")
                .setEmail("admin@admin.bg")
                .setUsername("Admin1")
                .setFirstName("Admin")
                .setLastName("Adminov");


        this.userService.registerAndLogin(this.adminBinding);

        this.userViewModel = new UserViewModel();
        this.userViewModel.setAge(32)
                .setPhone("+359878786")
                .setEmail("member@member.bg")
                .setUsername("Member1")
                .setFirstName("Member")
                .setLastName("Memberov");

        this.removeRoleBinding = new AddRoleBindingModel();
        this.removeRoleBinding.setUsername(this.member.getUsername());

        when(this.userService.findByUsername("Admin1"))
                .thenReturn(Optional.of(this.admin));
    }

    @Test
    @WithMockUser(username = "Member1", roles = {"MEMBER"})
    void testAccessToAdminPageDenied() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testAdminPageForUsersSuccessful() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-users"));
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testAddProductAdminPageSuccessful() throws Exception {
        mockMvc.perform(get("/admin/add-product"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-product"));
    }

    @Test
    @WithMockUser(username = "Member1", roles = {"MEMBER"})
    void testAddProductAdminPageAccessDenied() throws Exception {
        mockMvc.perform(get("/admin/add-product"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testAddRoleSuccessful() throws Exception {

        this.rolesToRemove = this.member.getUserRoles();
        when(this.userService.findByUsername("Member1"))
                .thenReturn(Optional.of(this.member));

        when(this.modelMapper.map(this.member, AddRoleBindingModel.class))
                .thenReturn(this.removeRoleBinding);

        when(this.roleService.getRolesToRemove(this.member))
                .thenReturn(this.rolesToRemove);

        mockMvc.perform(get("/admin/{username}/addRole","Member1"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-role"));
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testAddRoleNotFound() throws Exception {

        when(this.userService.findByUsername("Member1"))
                .thenReturn(Optional.of(this.member));

        mockMvc.perform(get("/admin/{username}/addRole","Member12"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testRemoveRoleSuccessful() throws Exception {

        this.rolesToRemove = this.member.getUserRoles();
        when(this.userService.findByUsername("Member1"))
                .thenReturn(Optional.of(this.member));

        when(this.modelMapper.map(this.member, AddRoleBindingModel.class))
                .thenReturn(this.removeRoleBinding);

        when(this.roleService.getRolesToRemove(this.member))
                .thenReturn(this.rolesToRemove);

        mockMvc.perform(get("/admin/{username}/removeRole","Member1"))
                .andExpect(status().isOk())
                .andExpect(view().name("remove-role"));
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testRemoveRoleNotFound() throws Exception {

        when(this.userService.findByUsername("Member1"))
                .thenReturn(Optional.of(this.member));

        mockMvc.perform(get("/admin/{username}/removeRole","Member12"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testAddRolePostSuccessful() throws Exception{
        when(this.userRepository.findByUsername("Member1"))
                .thenReturn(Optional.of(this.member));

        mockMvc.perform(post("/admin/{username}/addRole","Member1")
                        .param("username", "Member1")
                        .param("role", "MODERATOR")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN"})
    void testDeleteUserSuccessful() throws Exception{

        when(this.userRepository.findByUsername(this.member.getUsername()))
                .thenReturn(Optional.of(this.member));

        Optional<User> byUsername = this.userRepository.findByUsername(this.member.getUsername());

        when(this.modelMapper.map(byUsername.get(), UserViewModel.class))
                .thenReturn(this.userViewModel);

        when(this.userService.deleteByUsername(this.member.getUsername()))
                .thenReturn(this.userViewModel);

        mockMvc.perform(delete("/admin/{username}/delete", this.member.getUsername()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }
}
