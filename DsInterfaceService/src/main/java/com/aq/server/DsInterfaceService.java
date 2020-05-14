package com.aq.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("interface")
public class DsInterfaceService {


    @GetMapping("test")
    public String test(){
        return "test";
    }

}
