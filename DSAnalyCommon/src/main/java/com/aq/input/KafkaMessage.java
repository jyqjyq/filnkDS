package com.aq.input;

import lombok.Data;

/**
 */
@Data
public class KafkaMessage {
    private String jsonmessage;//json格式的消息内容
    private int count;//消息的次数
    private Long timestamp;//消息的时间

}
