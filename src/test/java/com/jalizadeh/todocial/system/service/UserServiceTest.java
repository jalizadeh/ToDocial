package com.jalizadeh.todocial.system.service;

import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.web.exception.EmailExistsException;
import com.jalizadeh.todocial.web.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.repository.RoleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("User & Todo Services Tests")
@Tag("unit_test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private User user;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SessionRegistry sessionRegistry;

    @InjectMocks
    private UserService service;


    @Test
    void loadUserByUsername() {
        //given

        //when
        when(userRepository.findByUsername("test")).thenReturn(user);
        User foundUser = service.findByUsername("test");

        //action
        verify(userRepository, times(1)).findByUsername(any());
        assertEquals(foundUser, user);
    }

    @Test
    void findByUsername() {
        //given

        //when
        when(userRepository.findByUsername("test")).thenReturn(user);
        User foundUser = service.findByUsername("test");

        //action
        verify(userRepository, times(1)).findByUsername(ArgumentMatchers.any());
        assertEquals(foundUser, user);
    }

    @Test
    void findByEmail() {
        //given

        //when
        when(userRepository.findByEmail("test@test.com")).thenReturn(user);
        User foundUser = service.findByEmail("test@test.com");

        //action
        verify(userRepository, times(1)).findByEmail(any());
        assertEquals(foundUser, user);
    }

    @Test
    void registerNewUserAccount() throws UserAlreadyExistException, EmailExistsException {
        //given

        //when
        //when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername("test")).thenReturn(user);
        //userRepository.save(user);

        User savedUser = service.registerNewUserAccount(user);
        verify(userRepository).save(savedUser);
        //assertEquals(service.findByUsername("test"), savedUser);

    }

    @Test
    void changePassword() {
    }
}