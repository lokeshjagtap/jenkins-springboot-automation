package com.example.demo.controller;

import com.example.demo.service.HelloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World") String name) {
    	System.out.println("sayHello method");
        return helloService.greet(name);
    }
}
