package com.rlx.command.center.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController{

  @RequestMapping("/")
  public String index(){
    return "Here my first service on springboot";
  }

}
