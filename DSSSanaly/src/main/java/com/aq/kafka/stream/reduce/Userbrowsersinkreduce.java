package com.aq.kafka.stream.reduce;

import com.alibaba.fastjson.JSONObject;
import com.aq.analy.Userbrowser;
import com.aq.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Userbrowsersinkreduce implements SinkFunction<Userbrowser> {
    @Override
    public void invoke(Userbrowser value, Context context) throws Exception {
        System.out.println( "recuducesinkd --userbrowser=="+value);
        String timestring = value.getTimestring();
        long newcount = value.getNewcount();
        long counttemp = value.getCount();
        long oldcount = value.getOldcount();
        String browser = value.getBrower();


        String browsercount = HbaseUtil.getdata("userinfo",timestring,"info","browsercount");
        String browsernewcount = HbaseUtil.getdata("userinfo",timestring,"info","browsernewcount");
        String browseroldcount = HbaseUtil.getdata("userinfo",timestring,"info","browseroldcount");


        Map<String,String> datamap = new HashMap<String,String>();


        Map<String,Long> map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(browsercount)){

            map = JSONObject.parseObject(browsercount,Map.class);
            Long count =  map.get(browser);
            if(count != null){
                counttemp += counttemp+count;
            }
        }

        map.put(browser,counttemp);
        datamap.put("browsercount",JSONObject.toJSONString(map));



        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(browsernewcount)){
            map = JSONObject.parseObject(browsernewcount,Map.class);
            Long count =  map.get(browser);
            if(count != null){
                newcount += newcount+count;
            }
        }
        map.put(browser,newcount);
        datamap.put("browsernewcount",JSONObject.toJSONString(map));


        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(browseroldcount)){
            map = JSONObject.parseObject(browseroldcount,Map.class);
            Long count =  map.get(browser);
            if(count != null){
                oldcount += oldcount+count;
            }
        }
        map.put(browser,oldcount);
        datamap.put("browseroldcount",JSONObject.toJSONString(map));
        System.out.println( "userbrowser---- HbaseUtil.put(userbrowser+"+","+timestring+",info"+datamap+")");
        HbaseUtil.put("userinfo",timestring,"info",datamap);

    }
}
