package com.aq.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DsClientTest {

    public static void main(String[] args) {
        String message = "kafkatest";
        String address = "http://127.0.0.1:6097/DsInfoSjService/webInfoSJservice";
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(true);
            conn.setUseCaches(false);
            conn.setReadTimeout(6*1000);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            conn.setRequestProperty("Content-Type","application/json");
            conn.connect();
            OutputStream outputStream = conn.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            out.write(message.getBytes());
            out.flush();
            String temp = "";
            InputStream in = conn.getInputStream();
            byte[] tempbytes = new byte[1024];
            while(in.read(tempbytes,0,1024) != -1){
                temp+=new String(tempbytes);
            }
            System.out.println(conn.getResponseCode());
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
