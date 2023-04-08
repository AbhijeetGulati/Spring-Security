package com.luv2code.springboot.cruddemo.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/hello")
    public String sayHello(Model theModel){
        theModel.addAttribute("todayDate",new java.util.Date());

        return "hello guys";
    }

}
