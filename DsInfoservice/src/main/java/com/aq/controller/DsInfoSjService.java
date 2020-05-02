package com.aq.controller;

import com.alibaba.fastjson.JSON;
import com.aq.input.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 */
@Controller
@RequestMapping("DsInfoSjService")
public class DsInfoSjService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostMapping("webInfoSJService")
    public void webInfoSJservice(@RequestBody String jsonStr, HttpServletRequest request , HttpServletResponse response){
        System.out.println("hello 进来了==未转换kafkamessage之前的=="+jsonStr);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setJsonmessage(jsonStr);
        kafkaMessage.setCount(1);
        kafkaMessage.setTimestamp(new Date().getTime());
        jsonStr = JSON.toJSONString(kafkaMessage);
        System.out.println("hello 进来了==转换kafkamessage之后的=="+jsonStr);
        //业务开始
        kafkaTemplate.send("test3","key",jsonStr);
        //业务结束
        PrintWriter printWriter = getWriter(response);
        response.setStatus(HttpStatus.OK.value());
        printWriter.write("success");
        closeprintwriter(printWriter);
    }

    private PrintWriter getWriter(HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        OutputStream out = null;
        PrintWriter printWriter = null;
        try {
            out = response.getOutputStream();
            printWriter = new PrintWriter(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return printWriter;
    }
    private void closeprintwriter(PrintWriter printWriter){
        printWriter.flush();
        printWriter.close();
    }
}
