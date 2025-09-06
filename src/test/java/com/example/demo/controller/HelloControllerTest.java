package com.example.demo.controller;

import com.example.demo.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloService helloService;

    @Test
    void testHelloEndpoint() throws Exception {
        when(helloService.greet("Lokesh")).thenReturn("Hello Lokesh!");

        mockMvc.perform(get("/api/hello").param("name", "Lokesh"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Lokesh!"));
    }
}
