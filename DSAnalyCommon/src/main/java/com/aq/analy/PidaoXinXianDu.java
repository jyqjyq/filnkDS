package com.aq.analy;

import lombok.Data;

/**
 */
@Data
public class PidaoXinXianDu {
    private long pingdaoid;
    private long newcount;
    private long oldcount;
    private long timestamp;
    private String timestring;
    private String groupbyfield;

    @Override
    public String toString() {
        return "PidaoXinXianDu{" +
                "pingdaoid=" + pingdaoid +
                ", newcount=" + newcount +
                ", oldcount=" + oldcount +
                ", timestamp=" + timestamp +
                ", timestring='" + timestring + '\'' +
                ", groupbyfield='" + groupbyfield + '\'' +
                '}';
    }

}
