package com.aq.kafka.stream.map;

import com.alibaba.fastjson.JSON;
import com.aq.analy.PidaoDiQuFenBu;
import com.aq.analy.UserState;
import com.aq.dao.PdvisterDao;
import com.aq.input.KafkaMessage;
import com.aq.log.UserscanLog;
import com.aq.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;


/**
 */
public class PindaoDiQuFenbuMap implements FlatMapFunction<KafkaMessage,PidaoDiQuFenBu> {

    @Override
    public void flatMap(KafkaMessage value, Collector<PidaoDiQuFenBu> out) throws Exception {
        String jsonstring = value.getJsonmessage();
        long timestamp = value.getTimestamp();


        String hourtimestamp = DateUtil.getDateby(timestamp,"yyyyMMddhh");//小时
        String daytimestamp = DateUtil.getDateby(timestamp,"yyyyMMdd");//天
        String monthtimestamp = DateUtil.getDateby(timestamp,"yyyyMM");//月

        UserscanLog userscanLog = JSON.parseObject(jsonstring, UserscanLog.class);
        long pingdaoid = userscanLog.getPingdaoid();
        long userid = userscanLog.getUserid();
        String city = userscanLog.getCity();//城市
        UserState userState = PdvisterDao.getUserSatebyvistertime(userid+"",timestamp);
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();

        PidaoDiQuFenBu pidaoDiQuFenBu = new PidaoDiQuFenBu();
        pidaoDiQuFenBu.setPingdaoid(pingdaoid);
        pidaoDiQuFenBu.setArea(city);


        pidaoDiQuFenBu.setPv(1l);
        long newcount = 0l;
        if(userState.isIsnew()){
            newcount = 1l;
        }
        pidaoDiQuFenBu.setNewcount(newcount);

        //小时
        long uvcount= 0l;
        long oldcount = 0l;
        if(isFirsthour){
            uvcount = 1l;
            oldcount = 1l;
        }
        pidaoDiQuFenBu.setUv(uvcount);
        pidaoDiQuFenBu.setOldcount(oldcount);
        pidaoDiQuFenBu.setTimestamp(timestamp);
        pidaoDiQuFenBu.setTimestring(hourtimestamp);
        pidaoDiQuFenBu.setGroupbyfield(pingdaoid+hourtimestamp);
        System.out.println("小时=="+pidaoDiQuFenBu);

        //天
        uvcount= 0l;
        oldcount = 0l;
        if(isFisrtday){
            uvcount = 1l;
            oldcount = 1l;
        }
        pidaoDiQuFenBu.setUv(uvcount);
        pidaoDiQuFenBu.setOldcount(oldcount);
        pidaoDiQuFenBu.setTimestamp(timestamp);
        pidaoDiQuFenBu.setTimestring(daytimestamp);
        pidaoDiQuFenBu.setGroupbyfield(pingdaoid+daytimestamp);
        System.out.println("天=="+pidaoDiQuFenBu);

        //月
        uvcount= 0l;
        oldcount = 0l;
        if(isFisrtmonth){
            uvcount = 1l;
            oldcount = 1l;
        }
        pidaoDiQuFenBu.setUv(uvcount);
        pidaoDiQuFenBu.setOldcount(oldcount);
        pidaoDiQuFenBu.setTimestamp(timestamp);
        pidaoDiQuFenBu.setTimestring(monthtimestamp);
        pidaoDiQuFenBu.setGroupbyfield(pingdaoid+monthtimestamp);
        System.out.println("月=="+pidaoDiQuFenBu);

    }
}
