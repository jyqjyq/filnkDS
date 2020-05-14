package com.aq.server;

import com.aq.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("test")
public class TestControl {

    @Autowired
    TestService testService;

    @RequestMapping("test")
    public String test(){
        return testService.test();
    }
}
