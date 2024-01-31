package com.jalizadeh.todocial.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {

    public static Collection<? extends GrantedAuthority> createMockAuthorities(String... roles){
        return Arrays.asList(roles).stream()
                .map( role -> createMockAuthority(role))
                .collect(Collectors.toList());
    }

    private static GrantedAuthority createMockAuthority(String role) {
        GrantedAuthority authority = mock(GrantedAuthority.class);
        when(authority.getAuthority()).thenReturn(role);
        return authority;
    }


}
