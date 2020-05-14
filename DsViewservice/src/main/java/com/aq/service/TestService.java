package com.aq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@FeignClient(value = "DsInterfaceService")
public interface TestService {

    @RequestMapping("/interface/test")
    String test();
}
