package com.dizma.dizmademo.config;

import com.dizma.dizmademo.model.enums.UserRoleEnum;
import com.dizma.dizmademo.repository.UserRepository;
import com.dizma.dizmademo.service.impl.DizmaUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/home", "/user/login", "/user/register", "/user/login-error", "/about", "/maintenance").permitAll()
                .antMatchers("/static/**", "/js/**", "/css/**", "/img/**", "/videos/**").permitAll()
                .antMatchers("/admin/**", "/api/users", "/products/edit/**").hasRole(UserRoleEnum.ADMIN.name())
                .antMatchers("/moderator/**").hasRole(UserRoleEnum.MODERATOR.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/user/login-error")
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }

   @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new DizmaUserDetailsService(userRepository);
   }
}
