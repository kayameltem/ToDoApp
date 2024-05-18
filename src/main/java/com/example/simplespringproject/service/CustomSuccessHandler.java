package com.example.simplespringproject.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Custom authentication success handler that redirects the user upon successful authentication.
 * <p>
 * This handler redirects authenticated users to the to-do list page after successfully logged in.
 * </p>
 */
@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Handles a successful authentication event.
     * Redirects the user to the to-do list page upon successful login.
     *
     * @param request the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @param authentication the {@link Authentication} object
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if a servlet exception occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.sendRedirect("/to-do-list");

    }
}
