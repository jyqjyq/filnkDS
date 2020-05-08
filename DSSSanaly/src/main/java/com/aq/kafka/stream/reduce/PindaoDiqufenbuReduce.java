package com.aq.kafka.stream.reduce;

import com.aq.analy.PidaoDiQuFenBu;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 */
public class PindaoDiqufenbuReduce implements ReduceFunction<PidaoDiQuFenBu> {

    @Override
    public PidaoDiQuFenBu reduce(PidaoDiQuFenBu value1,PidaoDiQuFenBu value2) throws Exception {
        System.out.println( "value1=="+value1);
        System.out.println( "value2=="+value2);
        long pingdaoid = value1.getPingdaoid();
        String area = value1.getArea();
        long timestampvalue = value1.getTimestamp();
        String timestring = value1.getTimestring();
        long newcount1 = value1.getNewcount();
        long oldcount1 =value1.getOldcount();
        long pv = value1.getPv();
        long uv = value1.getUv();

        long newcount2 = value2.getNewcount();
        long oldcount2 =value2.getOldcount();
        long pv2 = value2.getPv();
        long uv2 = value2.getUv();
        PidaoDiQuFenBu pidaoDiQuFenBu = new PidaoDiQuFenBu();
        pidaoDiQuFenBu.setTimestamp(timestampvalue);
        pidaoDiQuFenBu.setTimestring(timestring);
        pidaoDiQuFenBu.setPingdaoid(pingdaoid);
        pidaoDiQuFenBu.setArea(area);
        pidaoDiQuFenBu.setPv(pv+pv2);
        pidaoDiQuFenBu.setUv(uv + uv2);
        pidaoDiQuFenBu.setNewcount(newcount1 + newcount2);
        pidaoDiQuFenBu.setOldcount(oldcount1 + oldcount2);
        System.out.println( "recuduce --pidaoDiQuFenBu=="+pidaoDiQuFenBu);
        return  pidaoDiQuFenBu;
    }
}
