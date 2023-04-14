package com.dizma.dizmademo.web;

import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.service.UserService;
import com.dizma.dizmademo.service.impl.DizmaUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RedirectAttributes redirectAttributes;

    @MockBean
    private UserDetailsService userDetailsService;
    private User adminUser;

    private DizmaUserDetails dizmaUserDetails;

    private UserViewModel adminViewModel;

    @BeforeEach
    void setUp() {
        this.adminUser = new User();
        this.adminUser.setUsername("Admin")
                .setFirstName("Administrator")
                .setLastName("Administrator")
                .setPassword("123")
                .setAge(21)
                .setPhoneNumber("+35987664")
                .setEmail("admin@admin1.bg");

        this.adminViewModel = new UserViewModel();
        this.adminViewModel.setUsername("Admin")
                .setFirstName("Administrator")
                .setLastName("Administrator")
                .setAge(21)
                .setPhone("+35987664")
                .setEmail("admin@admin1.bg");

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        this.dizmaUserDetails = new DizmaUserDetails(null,
                this.adminUser.getUsername(),
                this.adminUser.getPassword(),
                this.adminUser.getFirstName(),
                this.adminUser.getLastName(),
                authorityList);
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "MEMBER"})
    void testProfilePageSuccessful() throws Exception{
        when(this.userService.findByUsername("Admin"))
                .thenReturn(Optional.of(this.adminUser));

        when(this.modelMapper.map(this.adminUser, UserViewModel.class))
                .thenReturn(this.adminViewModel);

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"));

    }

    @Test
    @WithMockUser(username = "Member", roles = {"MEMBER"})
    void testChangeUsernameSuccessful() throws Exception{
        when(this.userService.findByUsername("newUsername"))
                .thenReturn(Optional.empty());

        when(this.userService.findByUsername(this.adminUser.getUsername()))
                .thenReturn(Optional.of(this.adminUser));

        when(this.userDetailsService.loadUserByUsername(this.adminUser.getUsername()))
                .thenReturn(this.dizmaUserDetails);

        mockMvc.perform(post("/user/changeUsername/{newUsername}", "newUsername").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    @WithMockUser(username = "Member", roles = {"MEMBER"})
    void testChangeUsernameAlreadyExists() throws Exception{
        when(this.userService.findByUsername(this.adminUser.getUsername()))
                .thenReturn(Optional.of(this.adminUser));

        when(this.userService.findByUsername(this.adminUser.getUsername()))
                .thenReturn(Optional.of(this.adminUser));

        when(this.userDetailsService.loadUserByUsername(this.adminUser.getUsername()))
                .thenReturn(this.dizmaUserDetails);

        mockMvc.perform(post("/user/changeUsername/{newUsername}", this.adminUser.getUsername()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }
}
