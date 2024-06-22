package com.c4c.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.repositories.RoleRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthorizationApplicationTests {
  @Autowired
  RoleRepository roleRepository;

  @Test
  void contextLoads() {
    List<Role> roleList = roleRepository.findAll();

    assertThat(roleList.size()).isEqualTo(3);
  }
}
