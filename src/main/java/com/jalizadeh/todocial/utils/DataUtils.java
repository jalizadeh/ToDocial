package com.jalizadeh.todocial.utils;

import com.jalizadeh.todocial.api.controllers.dto.UserDto;
import com.jalizadeh.todocial.model.User;
import org.owasp.encoder.Encode;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataUtils {

    public static String sanitizeQuery(String query) {
        // Use OWASP Java Encoder to HTML encode the user input
        return Encode.forHtml(query);
    }

    public static UserDto mapUserToDTO(User u) {
        if(u == null) return null;

        return new UserDto(
                u.getId(), u.getFirstname(), u.getLastname(),
                u.getUsername() ,u.getEmail(), u.isEnabled(), u.getPhoto(),
                u.getFollowers() != null ? u.getFollowers().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>(),
                u.getFollowings() != null ? u.getFollowings().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>()
        );
    }
}
