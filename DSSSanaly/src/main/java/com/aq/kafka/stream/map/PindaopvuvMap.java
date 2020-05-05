package com.aq.kafka.stream.map;

import com.alibaba.fastjson.JSON;
import com.aq.analy.PidaoPvUv;
import com.aq.analy.UserState;
import com.aq.dao.PdvisterDao;
import com.aq.input.KafkaMessage;
import com.aq.log.UserscanLog;
import com.aq.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;


/**
 */
public class PindaopvuvMap implements FlatMapFunction<KafkaMessage, PidaoPvUv> {

    @Override
    public void flatMap(KafkaMessage value, Collector<PidaoPvUv> out) throws Exception {
        String jsonstring = value.getJsonmessage();
        long timestamp = value.getTimestamp();

        String hourtimestamp = DateUtil.getDateby(timestamp,"yyyyMMddhh");//小时
        String daytimestamp = DateUtil.getDateby(timestamp,"yyyyMMdd");//天
        String monthtimestamp = DateUtil.getDateby(timestamp,"yyyyMM");//月

        System.out.println(hourtimestamp);
        UserscanLog userscanLog = JSON.parseObject(jsonstring, UserscanLog.class);
        long pingdaoid = userscanLog.getPingdaoid();
        long userid = userscanLog.getUserid();

        UserState userState = PdvisterDao.getUserSatebyvistertime(userid+"",timestamp);
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();

        PidaoPvUv pidaoPvUv = new PidaoPvUv();
        pidaoPvUv.setPingdaoid(pingdaoid);
        pidaoPvUv.setUserid(userid);
        pidaoPvUv.setPvcount(Long.valueOf(value.getCount()+""));
        pidaoPvUv.setUvcount(isFirsthour==true?1l:0l);
        pidaoPvUv.setTimestamp(timestamp);
        pidaoPvUv.setTimestring(hourtimestamp);
        pidaoPvUv.setGroupbyfield(hourtimestamp+pingdaoid);
        out.collect(pidaoPvUv);
        System.out.println("小时=="+pidaoPvUv);

        //天
        pidaoPvUv.setUvcount(isFisrtday==true?1l:0l);
        pidaoPvUv.setGroupbyfield(daytimestamp+pingdaoid);
        pidaoPvUv.setTimestring(daytimestamp);
        out.collect(pidaoPvUv);
        System.out.println("天=="+pidaoPvUv);
        //月
        pidaoPvUv.setUvcount(isFisrtmonth==true?1l:0l);
        pidaoPvUv.setGroupbyfield(monthtimestamp+pingdaoid);
        pidaoPvUv.setTimestring(monthtimestamp);
        out.collect(pidaoPvUv);
        System.out.println("月=="+pidaoPvUv);
    }
}
