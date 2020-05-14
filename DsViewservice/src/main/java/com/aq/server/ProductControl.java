package com.aq.server;

import com.aq.batch.analy.ProductAnaly;
import com.aq.service.ProductAnalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("productControl")
public class ProductControl {

    @Autowired
    private ProductAnalyService productAnalyService ;

    @RequestMapping(value = "searchproductanaly")
    public ProductAnaly searchproductanaly(long prdouctid, String datetime){
           return     productAnalyService.listProductAnaly(prdouctid,datetime);
    }

}
