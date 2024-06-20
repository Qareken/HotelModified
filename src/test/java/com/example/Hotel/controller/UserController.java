package com.example.Hotel.controller;

import com.example.Hotel.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController extends AbstractTest {
    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "JohnDoe",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUserRequestMethodWithNameTestUser_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/hotels/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("User Called"));

    }
}
