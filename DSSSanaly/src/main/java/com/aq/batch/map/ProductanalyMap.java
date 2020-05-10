package com.aq.batch.map;

import com.alibaba.fastjson.JSONObject;
import com.aq.batch.analy.OrderInfo;
import com.aq.batch.analy.ProductAnaly;
import com.aq.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Date;

/**
 */
public class ProductanalyMap  implements FlatMapFunction<String, ProductAnaly> {
    @Override
    public void flatMap(String value, Collector<ProductAnaly> out) throws Exception {
        OrderInfo orderInfo = JSONObject.parseObject(value,OrderInfo.class);
        long productid = orderInfo.getProductid();
        Date date = orderInfo.getCreatetime();
        String timestring = DateUtil.getDateby(date.getTime(),"yyyyMM");
        Date paytime = orderInfo.getPaytime();
        long chengjiaocount =0l; //成交
        long weichegnjiao = 0;//未成交
        if(paytime != null){
            chengjiaocount = 1l;
        }else{
            weichegnjiao = 0l;
        }
        ProductAnaly productAnaly = new ProductAnaly();
        productAnaly.setProductid(productid);
        productAnaly.setDateString(timestring);
        productAnaly.setChengjiaocount(chengjiaocount);
        productAnaly.setWeichegnjiao(weichegnjiao);
        productAnaly.setGroupbyfield(timestring+productid);
        out.collect(productAnaly);
    }
}
