package com.aq.analy;

import lombok.Data;

/**
 */
@Data
public class Usernetwork {
    private String network;
    private long count;
    private long newcount;
    private long oldcount;
    private long timestamp;
    private String timestring;


    @Override
    public String toString() {
        return "Usernetwork{" +
                "network='" + network + '\'' +
                ", count=" + count +
                ", newcount=" + newcount +
                ", oldcount=" + oldcount +
                ", timestamp=" + timestamp +
                ", timestring='" + timestring + '\'' +
                '}';
    }
}
