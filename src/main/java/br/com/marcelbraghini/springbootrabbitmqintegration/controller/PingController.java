package br.com.marcelbraghini.springbootrabbitmqintegration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @RequestMapping("/pings")
    public String index(){
        return "Its ok!!!";
    }

}
