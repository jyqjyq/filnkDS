package com.aq.service;

import com.aq.batch.analy.ProductAnaly;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 */
@FeignClient(value = "DsInterfaceService")
public interface ProductAnalyService {

    @RequestMapping("/product/listProductAnaly")
    ProductAnaly listProductAnaly(@RequestParam(value = "productid") long productid, @RequestParam(value = "timedate") String timedate);
}
