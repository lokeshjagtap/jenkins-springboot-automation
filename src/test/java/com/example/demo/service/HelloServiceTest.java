package com.example.demo.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloServiceTest {

    private final HelloService helloService = new HelloService();

    @Test
    void testGreet() {
        String result = helloService.greet("Lokesh");
        assertEquals("Hello Lokesh!", result);
    }
}
