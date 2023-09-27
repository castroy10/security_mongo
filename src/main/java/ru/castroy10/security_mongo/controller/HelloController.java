package ru.castroy10.security_mongo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hellopage";
    }

    @GetMapping("/data")
    public String data() {
        return "datapage";
    }

    @GetMapping("/secret")
    public String secret() {
        return "secretpage";
    }
}
