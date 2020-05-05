package com.aq.util;

public class main {
    public static void main(String[] args) {
//        System.setProperty("hadoop.home.dir","E:\\soft\\hadoop-2.6.0-cdh5.5.1\\hadoop-2.6.0-cdh5.5.1");
        System.setProperty("hadoop.home.dir","D:\\soft\\hadoop-2.6.0-cdh5.5.1\\hadoop_dll2.6.0");
        String string = null;
        try {
            String s = string = HbaseUtil.getdata("baseuserscaninfo", "1", "time", "fisrtvisittime");
//            String s = string = HbaseUtil.getdata("pindaoinfo", "1", "info", "fisrtvisittime");
            System.out.println(s+"-----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(string);
    }
}
