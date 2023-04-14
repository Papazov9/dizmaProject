package com.dizma.dizmademo.web;

import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private User adminUser, memberUser;

    private UserRegistrationBinding adminBinding, memberBinding;

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

        this.memberUser = new User();
        this.memberUser.setUsername("Member")
                .setFirstName("Member")
                .setLastName("Member")
                .setPassword("123")
                .setAge(21)
                .setPhoneNumber("+35987664")
                .setEmail("member@member.bg");

        this.userRepository.save(this.adminUser);
        this.adminBinding = new UserRegistrationBinding();
        this.adminBinding.setUsername("Admin")
                .setFirstName("Administrator")
                .setLastName("Administrator")
                .setPassword("123")
                .setConfirmPassword("123")
                .setAge(21)
                .setPhoneNumber("+35987664")
                .setEmail("admin@admin1.bg");

        this.memberBinding = new UserRegistrationBinding();
        this.memberBinding.setUsername("Member")
                .setFirstName("Member")
                .setLastName("Member")
                .setPassword("123")
                .setConfirmPassword("123")
                .setAge(21)
                .setPhoneNumber("+35987664")
                .setEmail("member@member.bg");

//        this.userService.registerAndLogin(adminBinding);
//        this.userService.registerAndLogin(memberBinding);
    }

    @Test
    void testRegisterPageSuccessful() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testLoginSuccessful() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testRegisterPostSuccessful() throws Exception {
        mockMvc.perform(post("/user/register")
                .param("firstName", "vfvfvf")
                .param("lastName", "Papvfvfvazov")
                .param("username", "vvfvf")
                .param("email", "dsavfvfdd@gmail.bg")
                .param("password","123")
                .param("confirmPassword","123")
                .param("phoneNumber", "08897678")
                .param("age", "21")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testRegisterFailUserAlreadyExists() throws Exception {
        mockMvc.perform(post("/user/register")
                        .param("firstName", this.adminUser.getFirstName())
                        .param("lastName", this.adminUser.getLastName())
                        .param("username", this.adminUser.getUsername())
                        .param("email", this.adminUser.getEmail())
                        .param("password",this.adminUser.getPassword())
                        .param("confirmPassword",this.adminUser.getPassword())
                        .param("phoneNumber", this.adminUser.getPhoneNumber())
                        .param("age", "21")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testLoginFailed() throws Exception {
        mockMvc.perform(post("/user/login")
                        .param("username", "admin")
                        .param("password", "12345")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/user/login-error"));
    }

    @AfterEach
    void destroy() {
        userRepository.deleteAll();
    }
}
