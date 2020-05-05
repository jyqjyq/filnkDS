package com.aq.dao;

import com.aq.analy.UserState;
import com.aq.util.DateUtil;
import com.aq.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class PdvisterDao {


    /**
     * 查询本次用户的访问状态
     * @param userid
     * @param timestamp
     * @return
     */
    public static UserState getUserSatebyvistertime(String userid, long timestamp){
        UserState userState = new UserState();
        try {
           String result =  HbaseUtil.getdata("baseuserscaninfo",userid,"time","fisrtvisittime");
            if(result == null){//第一次访问
                Map<String,String> datamap = new HashMap<String,String>();
                datamap.put("fisrtvisittime",timestamp+"");
                datamap.put("lastvisittime",timestamp+"");
                HbaseUtil.put("baseuserscaninfo",userid,"time",datamap);
                userState.setIsnew(true);
                userState.setFisrtday(true);
                userState.setFisrthour(true);
                userState.setFisrtmonth(true);
            }else{
                String lastvisittimestring = HbaseUtil.getdata("baseuserscaninfo",  userid, "time","lastvisittime");
                if(StringUtils.isNotBlank(lastvisittimestring)){
                    long lastvisittime = Long.valueOf(lastvisittimestring);
                    //小时
                    long timstamp = DateUtil.getDatebyConditon(timestamp,"yyyyMMddhh");
                    if(lastvisittime < timestamp){
                        userState.setFisrthour(true);
                    }
                    //天
                    timstamp = DateUtil.getDatebyConditon(timestamp,"yyyyMMdd");
                    if(lastvisittime < timestamp){
                        userState.setFisrtday(true);
                    }
                    //月
                    timstamp = DateUtil.getDatebyConditon(timestamp,"yyyyMM");
                    if(lastvisittime < timestamp){
                        userState.setFisrtmonth(true);
                    }
                }
                HbaseUtil.putdata("baseuserscaninfo", userid, "time","lastvisittime",timestamp+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userState;
    }
}
