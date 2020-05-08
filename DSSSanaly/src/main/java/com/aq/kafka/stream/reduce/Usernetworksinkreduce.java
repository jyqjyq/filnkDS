package com.aq.kafka.stream.reduce;

import com.alibaba.fastjson.JSONObject;
import com.aq.analy.Usernetwork;
import com.aq.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Usernetworksinkreduce implements SinkFunction<Usernetwork> {
    @Override
    public void invoke(Usernetwork value, Context context) throws Exception {
        System.out.println( "recuducesinkd --usernetwork=="+value);
        String timestring = value.getTimestring();
        long newcount = value.getNewcount();
        long counttemp = value.getCount();
        long oldcount = value.getOldcount();
        String network = value.getNetwork();


        String networkcount = HbaseUtil.getdata("userinfo",timestring,"info","networkcount");
        String networknewcount = HbaseUtil.getdata("userinfo",timestring,"info","networknewcount");
        String networkoldcount = HbaseUtil.getdata("userinfo",timestring,"info","networkoldcount");


        Map<String,String> datamap = new HashMap<String,String>();


        Map<String,Long> map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(networkcount)){

            map = JSONObject.parseObject(networkcount,Map.class);
            Long count =  map.get(network);
            if(count != null){
                counttemp += counttemp+count;
            }
        }

        map.put(network,counttemp);
        datamap.put("networkcount",JSONObject.toJSONString(map));



        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(networknewcount)){
            map = JSONObject.parseObject(networknewcount,Map.class);
            Long count =  map.get(network);
            if(count != null){
                newcount += newcount+count;
            }
        }
        map.put(network,newcount);
        datamap.put("networknewcount",JSONObject.toJSONString(map));


        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(networkoldcount)){
            map = JSONObject.parseObject(networkoldcount,Map.class);
            Long count =  map.get(network);
            if(count != null){
                oldcount += oldcount+count;
            }
        }
        map.put(network,oldcount);
        datamap.put("networkoldcount",JSONObject.toJSONString(map));
        System.out.println( "usernetwork---- HbaseUtil.put(usernetwork+"+","+timestring+",info"+datamap+")");
        HbaseUtil.put("userinfo",timestring,"info",datamap);

    }
}
