package com.aq.server;

import com.aq.batch.analy.ProductAnaly;
import com.aq.utils.HbaseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("product")
public class ProductControl {

    @RequestMapping("listProductAnaly")
    public ProductAnaly listProductAnaly(long productid, String timedate){
        ProductAnaly productAnaly = new ProductAnaly();
        try {
            String chengjiaocount = HbaseUtil.getdata("productinfo",productid+"=="+timedate,"info","chengjiaocount");
            String weichegnjiao = HbaseUtil.getdata("productinfo",productid+"=="+timedate,"info","weichegnjiao");

            productAnaly.setProductid(productid);
            productAnaly.setDateString(timedate);
            productAnaly.setWeichegnjiao(Long.valueOf(weichegnjiao));
            productAnaly.setChengjiaocount(Long.valueOf(chengjiaocount));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productAnaly;
    }

}
