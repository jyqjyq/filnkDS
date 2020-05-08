package com.aq.kafka.stream.reduce;

import com.alibaba.fastjson.JSONObject;
import com.aq.analy.PidaoDiQuFenBu;
import com.aq.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Pindaodiqufenbusinkreduce implements SinkFunction<PidaoDiQuFenBu> {
    @Override
    public void invoke(PidaoDiQuFenBu value, Context context) throws Exception {
        System.out.println( "recuducesinkd --pidaoPvUv=="+value);
        long pingdaoid = value.getPingdaoid();
        String area = value.getArea();
        long pvcount = value.getPv();
        long uvcount = value.getUv();
        String timestring = value.getTimestring();
        long newcount = value.getNewcount();
        long oldcount = value.getOldcount();

        String pv = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","areapv");
        String uv = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","areauv");

        String areanewcount = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","areanewcount");
        String areaoldcount = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","areaoldcount");

        Map<String,String> datamap = new HashMap<String,String>();


        Map<String,Long> map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(pv)){
            /**
             * {"diqu",suliang}
             */
            map = JSONObject.parseObject(pv,Map.class);
            Long count =  map.get(area);
            if(count != null){
                pvcount += pvcount+count;
            }
        }
        map.put(area,pvcount);
        datamap.put("areapv",JSONObject.toJSONString(map));



        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(uv)){
            /**
             * {"diqu",suliang}
             */
            map = JSONObject.parseObject(uv,Map.class);
            Long count =  map.get(area);
            if(count != null){
                uvcount += uvcount+count;
            }
        }
        map.put(area,uvcount);
        datamap.put("areauv",JSONObject.toJSONString(map));


        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(areanewcount)){
            /**
             * {"diqu",suliang}
             */
            map = JSONObject.parseObject(areanewcount,Map.class);
            Long count =  map.get(area);
            if(count != null){
                newcount += newcount+count;
            }
        }
        map.put(area,newcount);
        datamap.put("areanewcount",JSONObject.toJSONString(map));

        map = new HashMap<String,Long>();
        if(StringUtils.isNotBlank(areaoldcount)){
            /**
             * {"diqu",suliang}
             */
            map = JSONObject.parseObject(areaoldcount,Map.class);
            Long count =  map.get(area);
            if(count != null){
                oldcount += oldcount+count;
            }
        }
        map.put(area,oldcount);
        datamap.put("areaoldcount",JSONObject.toJSONString(map));

        System.out.println( "pidaodiqufenbu---- HbaseUtil.put(pindaoinfo+"+","+pingdaoid+timestring+",info"+datamap+")");
        HbaseUtil.put("pindaoinfo",pingdaoid+timestring,"info",datamap);

    }
}
