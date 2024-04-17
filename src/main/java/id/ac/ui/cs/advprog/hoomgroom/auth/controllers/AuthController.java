package id.ac.ui.cs.advprog.hoomgroom.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class AuthController {
    @GetMapping("")
    public String homePage() {
        return "Hello, world!";
    }
}
