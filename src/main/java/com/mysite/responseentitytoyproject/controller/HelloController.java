package com.mysite.responseentitytoyproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World");
    }
    @GetMapping("/hello2")
    public ResponseEntity<String> sayHello2() {
        return ResponseEntity
                .status(200)
                .body("Hello World2");
    }
}
