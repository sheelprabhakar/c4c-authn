package com.c4c.auth.rest.controllers;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.services.api.RoleService;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class RegisterUserTest {

  @Mock
  UserService userService;

  @Mock
  RoleService roleService;

  @InjectMocks
  AuthController authController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(authController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  public void testFailToRegisterUserCauseInvalidData() throws Exception {
    Role role = mock(Role.class);
    User user = mock(User.class);

    when(roleService.findByName(anyString())).thenReturn(role);
    when(userService.save(any(CreateUserDto.class))).thenReturn(user);
    MvcResult result = mockMvc.perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        )
        //.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        //.andExpect(content().json("{ \"n\": \"n\" }", true))
        // .andExpect(status().isUnprocessableEntity())
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data", hasKey("errors")))
        .andExpect(jsonPath("$.data.errors", hasKey("firstName")))
        .andExpect(jsonPath("$.data.errors", hasKey("lastName")))
        .andExpect(jsonPath("$.data.errors", hasKey("timezone")))
        .andExpect(jsonPath("$.data.errors", hasKey("confirmPassword")))
        .andExpect(jsonPath("$.data.errors", hasKey("email")))
        .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }
}
