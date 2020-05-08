package com.aq.kafka.stream.reduce;

import com.aq.analy.PidaoXinXianDu;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 */
public class PindaoXinxianduReduce implements ReduceFunction<PidaoXinXianDu> {

    @Override
    public PidaoXinXianDu reduce(PidaoXinXianDu value1, PidaoXinXianDu value2) throws Exception {
        System.out.println( "value1=="+value1);
        System.out.println( "value2=="+value2);
        long pingdaoid = value1.getPingdaoid();
        long timestampvalue = value1.getTimestamp();
        String timestring = value1.getTimestring();
        long newcountvalue1 = value1.getNewcount();
        long oldcountvalue1 = value1.getOldcount();

        long newcountvalue2 = value2.getNewcount();
        long oldcountvalue2 = value2.getOldcount();

        PidaoXinXianDu pidaoXinXianDu = new PidaoXinXianDu();
        pidaoXinXianDu.setPingdaoid(pingdaoid);
        pidaoXinXianDu.setTimestamp(timestampvalue);
        pidaoXinXianDu.setTimestring(timestring);
        pidaoXinXianDu.setNewcount(newcountvalue1+newcountvalue2);
        pidaoXinXianDu.setOldcount(oldcountvalue1+oldcountvalue2);
        System.out.println( "recuduce --pidaoXinXianDu=="+pidaoXinXianDu);
        return  pidaoXinXianDu;
    }
}
