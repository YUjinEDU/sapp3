package com.ll.sapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sapp")
    @ResponseBody
    public String index() {
        return "안녕하세요 스프링부트 페이지입니다.";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}