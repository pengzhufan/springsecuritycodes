package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public String hello(){
        System.out.println("hello");
        return "Hello World";
    }
}
