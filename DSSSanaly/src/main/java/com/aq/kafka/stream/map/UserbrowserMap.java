package com.aq.kafka.stream.map;

import com.alibaba.fastjson.JSON;
import com.aq.analy.UserState;
import com.aq.analy.Userbrowser;
import com.aq.dao.PdvisterDao;
import com.aq.input.KafkaMessage;
import com.aq.log.UserscanLog;
import com.aq.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;


/**
 * Created by Administrator on 2018/10/27 0027.
 */
public class UserbrowserMap implements FlatMapFunction<KafkaMessage, Userbrowser> {

    @Override
    public void flatMap(KafkaMessage value, Collector<Userbrowser> out) throws Exception {
        String jsonstring = value.getJsonmessage();
        long timestamp = value.getTimestamp();


        String hourtimestamp = DateUtil.getDateby(timestamp,"yyyyMMddhh");//小时
        String daytimestamp = DateUtil.getDateby(timestamp,"yyyyMMdd");//天
        String monthtimestamp = DateUtil.getDateby(timestamp,"yyyyMM");//月

        UserscanLog userscanLog = JSON.parseObject(jsonstring, UserscanLog.class);
        long userid = userscanLog.getUserid();
        String browser = userscanLog.getLiulanqitype();
        UserState userState = PdvisterDao.getUserSatebyvistertime(userid+"",timestamp);
        boolean isnew = userState.isIsnew();
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();

        Userbrowser userbrowser = new Userbrowser();
        userbrowser.setBrower(browser);
        userbrowser.setTimestamp(timestamp);
        userbrowser.setCount(1l);
        long newuser= 0l;
        if(isnew){
            newuser= 1l;
        }
        userbrowser.setNewcount(newuser);

        //小时
        long oldcount = 0l;
        if(isFirsthour){
            oldcount = 1l;
        }
        userbrowser.setOldcount(oldcount);
        userbrowser.setTimestring(hourtimestamp);
        out.collect(userbrowser);
        System.out.println("小时=="+userbrowser);

        //天
        oldcount = 0l;
        if(isFisrtday){
            oldcount = 1l;
        }
        userbrowser.setOldcount(oldcount);
        userbrowser.setTimestring(daytimestamp);
        System.out.println("天=="+userbrowser);
        out.collect(userbrowser);
        //月
        oldcount = 0l;
        if(isFisrtmonth){
            oldcount = 1l;
        }
        userbrowser.setOldcount(oldcount);
        userbrowser.setTimestring(monthtimestamp);
        System.out.println("月=="+userbrowser);
        out.collect(userbrowser);
    }
}
