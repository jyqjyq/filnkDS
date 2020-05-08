package com.aq.analy;

import lombok.Data;

/**
 */
@Data
public class Userbrowser {

    private String brower;
    private long count;
    private long newcount;
    private long oldcount;
    private long timestamp;
    private String timestring;

    @Override
    public String toString() {
        return "Userbrowser{" +
                "brower='" + brower + '\'' +
                ", count=" + count +
                ", newcount=" + newcount +
                ", oldcount=" + oldcount +
                ", timestamp=" + timestamp +
                ", timestring='" + timestring + '\'' +
                '}';
    }

}
