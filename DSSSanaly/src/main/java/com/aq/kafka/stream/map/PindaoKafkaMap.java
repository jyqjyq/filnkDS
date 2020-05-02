package com.aq.kafka.stream.map;

import com.alibaba.fastjson.JSON;
import com.aq.analy.PindaoRD;
import com.aq.input.KafkaMessage;
import com.aq.log.UserscanLog;
import org.apache.flink.api.common.functions.RichMapFunction;

/**
 */
public class PindaoKafkaMap extends RichMapFunction<KafkaMessage, PindaoRD> {

    @Override
    public PindaoRD map(KafkaMessage value) throws Exception {
        String jsonstring = value.getJsonmessage();
        System.out.println("map进来的数据=="+jsonstring);
        UserscanLog userscanLog = JSON.parseObject(jsonstring, UserscanLog.class);
        long pingdaoid = userscanLog.getPingdaoid();
        PindaoRD pindaoRD = new PindaoRD();
        pindaoRD.setPingdaoid(pingdaoid);
        pindaoRD.setCount(Long.valueOf(value.getCount()+""));
        return pindaoRD;
    }
}
