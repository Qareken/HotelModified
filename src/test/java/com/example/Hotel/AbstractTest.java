package com.example.Hotel;

import com.example.Hotel.dto.userDto.UserRequestDto;
import com.example.Hotel.mapper.CommonMapper;
import com.example.Hotel.mapper.UserMapper;
import com.example.Hotel.repository.UserRepository;
import com.example.Hotel.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
public class AbstractTest {
    protected static PostgreSQLContainer postgreSQLContainer;
    static {
        DockerImageName postgres = DockerImageName.parse("postgres:16.0");
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer<>(postgres)
                .withReuse(true);
        postgreSQLContainer.start();
    }
    @DynamicPropertySource
    public static void  registerProperties(DynamicPropertyRegistry registry){
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", ()->jdbcUrl);
    }
    @Autowired
    protected UserServiceImpl userService;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        userService.save(UserRequestDto.builder()
                .name("Aziz")
                .email("aziz@gmail.com")
                .password("securePassword123")
                .roles(Set.of("admin", "user"))
                .build());
        userService.save(UserRequestDto.builder()
                .name("Gayrat")
                .email("gayrat@gmail.com")
                .password("securePassword123")
                .roles(Set.of( "user"))
                .build());
    }
    @AfterEach
    public void afterEach(){
        userRepository.deleteAll();
    }
}
