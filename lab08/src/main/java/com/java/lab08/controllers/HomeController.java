package com.java.lab08.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Trả về view index.html
        return "index";
    }

    @GetMapping("/contact")
    public String showContactForm() {
        // Trả về view contact.html để hiển thị form nhập thông tin liên hệ
        return "contact";
    }


    @PostMapping("/contact")
    public String submitContactForm(@RequestParam String name, @RequestParam String email) {
        return "contact-info";
    }

    @GetMapping("/about")
    @ResponseBody
    public ResponseEntity<String> about() {
        return ResponseEntity.status(HttpStatus.OK).body("About this site");
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, value = "/{path:[^\\.]*}")
    @ResponseBody
    public ResponseEntity<String> handleUnsupportedMethods() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("About this site");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleExceptions(Exception e) {
        return "An error occurred: " + e.getMessage();
    }
}
