package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObject(userController,"userRepository", userRepository);
        TestUtils.injectObject(userController,"cartRepository", cartRepository);
        TestUtils.injectObject(userController,"bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception{
        when(encoder.encode("saksaksak")).thenReturn("ThisIsHAshed");
        CreateUserRequest cr = new CreateUserRequest();
        cr.setPassword("saksaksak");
        cr.setConfirmPassword("saksaksak");
        cr.setUsername("saku");

        final ResponseEntity res = userController.createUser(cr);
        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        User u = (User) res.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("saku", u.getUsername());
        assertEquals("ThisIsHAshed", u.getPassword());

    }

}
