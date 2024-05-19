package com.example.todoapp.configurations;

import com.example.todoapp.service.CustomSuccessHandler;
import com.example.todoapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration class for setting up Spring Security.
 * <p>
 * This class configures security settings for the application, including authentication, authorization, and password encoding.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    CustomSuccessHandler customSuccessHandler;
    CustomUserDetailsService customUserDetailsService;

    /**
     * Constructs a new SecurityConfig with the specified custom success handler and custom user details service.
     *
     * @param customSuccessHandler the custom success handler
     * @param customUserDetailsService the custom user details service
     */
    @Autowired
    public SecurityConfig(CustomSuccessHandler customSuccessHandler, CustomUserDetailsService customUserDetailsService) {
        this.customSuccessHandler = customSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Provides a {@link PasswordEncoder} bean that uses BCrypt for password encoding.
     *
     * @return a BCrypt password encoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain, defining which URLs are secured and which are publicly accessible.
     *
     * @param httpSecurity the {@link HttpSecurity} to modify
     * @return the security filter chain
     * @throws Exception if an error occurs while configuring the filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.csrf(c -> c.disable())
                    .authorizeHttpRequests(request -> request.requestMatchers( "/registration","/css/**").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login").loginProcessingUrl("/login")
                .successHandler(customSuccessHandler).permitAll()
        )
                .logout((logout) -> logout.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll());

        return httpSecurity.build();
    }

    /**
     * Configures the authentication manager with a custom user details service and password encoder.
     *
     * @param auth the {@link AuthenticationManagerBuilder} to configure
     * @throws Exception if an error occurs while configuring the authentication manager
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
