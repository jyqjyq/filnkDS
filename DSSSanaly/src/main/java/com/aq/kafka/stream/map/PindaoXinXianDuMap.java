package com.aq.kafka.stream.map;

import com.alibaba.fastjson.JSON;
import com.aq.analy.PidaoXinXianDu;
import com.aq.analy.UserState;
import com.aq.dao.PdvisterDao;
import com.aq.input.KafkaMessage;
import com.aq.log.UserscanLog;
import com.aq.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;


/**
 */
public class PindaoXinXianDuMap implements FlatMapFunction<KafkaMessage,PidaoXinXianDu> {

    @Override
    public void flatMap(KafkaMessage value, Collector<PidaoXinXianDu> out) throws Exception {
        String jsonstring = value.getJsonmessage();
        long timestamp = value.getTimestamp();


        String hourtimestamp = DateUtil.getDateby(timestamp,"yyyyMMddhh");//小时
        String daytimestamp = DateUtil.getDateby(timestamp,"yyyyMMdd");//天
        String monthtimestamp = DateUtil.getDateby(timestamp,"yyyyMM");//月

        UserscanLog userscanLog = JSON.parseObject(jsonstring, UserscanLog.class);
        long pingdaoid = userscanLog.getPingdaoid();
        long userid = userscanLog.getUserid();

        UserState userState = PdvisterDao.getUserSatebyvistertime(userid+"",timestamp);
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();

        PidaoXinXianDu pidaoXinXianDu = new PidaoXinXianDu();
        pidaoXinXianDu.setPingdaoid(pingdaoid);
        pidaoXinXianDu.setTimestamp(timestamp);
        /**
         * 新增用户
         */
        long newuser = 0l;
        if(userState.isIsnew()){
            newuser = 1l;
        }
        pidaoXinXianDu.setNewcount(newuser);

        /**
         * 小时
         */
        long olduser = 0l;
        if(!userState.isIsnew()&&isFirsthour){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldcount(olduser);
        pidaoXinXianDu.setTimestring(hourtimestamp);
        pidaoXinXianDu.setGroupbyfield(hourtimestamp+pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);
        /**
         * 天
         */
        olduser = 0l;
        if(!userState.isIsnew()&&isFisrtday){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldcount(olduser);
        pidaoXinXianDu.setTimestring(daytimestamp);
        pidaoXinXianDu.setGroupbyfield(daytimestamp+pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);
        /**
         * 月
         */
        olduser = 0l;
        if(!userState.isIsnew()&&isFisrtmonth){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldcount(olduser);
        pidaoXinXianDu.setTimestring(monthtimestamp);
        pidaoXinXianDu.setGroupbyfield(monthtimestamp+pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);
    }
}
