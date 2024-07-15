package com.ll.sapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/sapp")
    public void index() {
        System.out.println("index");
    }
}