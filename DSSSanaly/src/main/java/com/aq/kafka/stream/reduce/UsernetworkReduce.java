package com.aq.kafka.stream.reduce;

import com.aq.analy.Usernetwork;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 */
public class UsernetworkReduce implements ReduceFunction<Usernetwork> {

    @Override
    public Usernetwork reduce(Usernetwork value1, Usernetwork value2) throws Exception {
        System.out.println( "value1=="+value1);
        System.out.println( "value2=="+value2);

        long timestampvalue = value1.getTimestamp();
        String timestring = value1.getTimestring();
        long countvalue1 = value1.getCount();
        long newcountvalue1 = value1.getNewcount();
        long oldcountvalue1 = value1.getOldcount();

        long countvalue2 = value2.getCount();
        long newcountvalue2 = value2.getNewcount();
        long oldcountvalue2 = value2.getOldcount();

        Usernetwork usernetwork = new Usernetwork();
        usernetwork.setTimestamp(timestampvalue);
        usernetwork.setTimestring(timestring);
        usernetwork.setOldcount(oldcountvalue1+oldcountvalue2);
        usernetwork.setNewcount(newcountvalue1+newcountvalue2);
        usernetwork.setCount(countvalue1+countvalue2);

        System.out.println( "recuduce --usernetwork=="+usernetwork);
        return  usernetwork;
    }
}
