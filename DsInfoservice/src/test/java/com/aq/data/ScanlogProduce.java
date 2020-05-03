package com.aq.data;

import com.alibaba.fastjson.JSONObject;
import com.aq.log.UserscanLog;
import com.aq.utils.UrlsendUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 */
public class ScanlogProduce {
    private static Long[] pindaoids = new Long[]{1l,2l,3l,4l,5l,6l,7l,8l};//频道id集合
    private static Long[] leibieids = new Long[]{1l,2l,3l,4l,5l,6l,7l,8l};//产品类别id集合
    private static Long[] chanpinids = new Long[]{1l,2l,3l,4l,5l,6l,7l,8l};//产品id集合
    private static Long[] yonghuids = new Long[]{1l,2l,3l,4l,5l,6l,7l,8l};//用户id集合

    /**
     * 地区
     */
    private static String[] contrys = new String[]{"America","china"};//地区-国家集合
    private static String[] provinces = new String[]{"America","china"};//地区-省集合
    private static String[] citys = new String[]{"America","china"};//地区-市集合

    /**
     *网络方式
     */
    private static String[] networks = new String[]{"电信","移动","联通"};

    /**
     * 来源方式
     */
    private static String[] sources = new String[]{"直接输入","百度跳转","360搜索跳转","必应跳转"};

    /**
     * 浏览器
     */
    private static String[] liulanqis = new String[]{"火狐","qq浏览器","360浏览器","谷歌浏览器"};

    /**
     * 打开时间 离开时间
     */
    private static List<Long[]> usetimelog = new ScanlogProduce().producetimes();

    public List<Long[]> producetimes(){
        List<Long[]> usetimelog = new ArrayList<Long[]>();
        for(int i=0;i<10;i++){
            Long [] timesarray = gettimes("2020-05-02 11:36:26:025");
            usetimelog.add(timesarray);
        }
        return usetimelog;
    }

    private Long [] gettimes(String time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            Date date = dateFormat.parse(time);
            long timetemp = date.getTime();
            Random random = new Random();
            int randomint = random.nextInt(10);
            long starttime = timetemp - randomint*3600*1000;
            long endtime = starttime + randomint*3600*1000;
            return new Long[]{starttime,endtime};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Long[]{0l,0l};
    }

    public static void main(String[] args) {
        Random random = new Random();
        for(int i=0;i<20;i++){
            //频道id 类别id 产品id 用户id 打开时间 离开时间 地区 网络方式 来源方式 浏览器
            UserscanLog userscanLog = new UserscanLog();
            userscanLog.setPingdaoid(pindaoids[random.nextInt(pindaoids.length)]);
//            userscanLog.setPingdaoid(1l);
            userscanLog.setLeibieid(leibieids[random.nextInt(leibieids.length)]);
            userscanLog.setChanpinid(chanpinids[random.nextInt(chanpinids.length)]);
            userscanLog.setUserid(yonghuids[random.nextInt(yonghuids.length)]);
            userscanLog.setContry(contrys[random.nextInt(contrys.length)]);
            userscanLog.setProvince(provinces[random.nextInt(provinces.length)]);
            userscanLog.setCity(citys[random.nextInt(citys.length)]);

            userscanLog.setNetwork(networks[random.nextInt(networks.length)]);
            userscanLog.setSources(sources[random.nextInt(sources.length)]);
            userscanLog.setLiulanqitype(liulanqis[random.nextInt(liulanqis.length)]);

            Long[] times = usetimelog.get(random.nextInt(usetimelog.size()));
            userscanLog.setStarttime(times[0]);
            userscanLog.setEndetime(times[1]);

            String jonstr = JSONObject.toJSONString(userscanLog);
            System.out.println(jonstr);
            UrlsendUtil.sendmessage("http://127.0.0.1:8080/DsInfoSjService/webInfoSJService",jonstr);
        }

    }


}
