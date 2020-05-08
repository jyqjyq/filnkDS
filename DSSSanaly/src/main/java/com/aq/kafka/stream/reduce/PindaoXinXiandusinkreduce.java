package com.aq.kafka.stream.reduce;

import com.aq.analy.PidaoXinXianDu;
import com.aq.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class PindaoXinXiandusinkreduce implements SinkFunction<PidaoXinXianDu> {
    @Override
    public void invoke(PidaoXinXianDu value, Context context) throws Exception {
        System.out.println( "recuducesinkd --pidaoPvUv=="+value);
        long pingdaoid = value.getPingdaoid();
        long newcount = value.getNewcount();
        long oldcount = value.getOldcount();
        String timestring = value.getTimestring();
        String newcountstring = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","xinxiandunewcount");
        System.out.println("newcountstring:"+newcountstring);
        String oldcountstring = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","xinxianduoldcount");
        if(StringUtils.isNotBlank(newcountstring)){
            newcount += newcount + Long.valueOf(newcountstring);
        }
        if(StringUtils.isNotBlank(oldcountstring)) {
            oldcount += oldcount + Long.valueOf(oldcountstring);
        }

        Map<String,String> datamap = new HashMap<String,String>();
        datamap.put("xinxiandunewcount",newcount+"");
        datamap.put("xinxianduoldcount",oldcount+"");
        System.out.println( "xinxiandu---- HbaseUtil.put(pindaoinfo+"+","+pingdaoid+timestring+",info"+datamap+")");
        HbaseUtil.put("pindaoinfo",pingdaoid+timestring,"info",datamap);
    }
}
