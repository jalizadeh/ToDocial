package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.utils.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class LogoutHandlerImpl implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Log.info("User '" + auth.getName() + "' signed out successfully");
            new SecurityContextLogoutHandler().logout(request, response, auth);
            SecurityContextHolder.clearContext();
        }


        try {
            response.sendRedirect(request.getContextPath() + "/login?logout=true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
