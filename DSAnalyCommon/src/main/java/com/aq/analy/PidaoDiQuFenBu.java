package com.aq.analy;

import lombok.Data;

/**
 */
@Data
public class PidaoDiQuFenBu {
    private long pingdaoid;
    private String area;//地区
    private long pv;
    private  long uv;
    private long newcount;
    private long oldcount;
    private long timestamp;
    private String timestring;
    private String groupbyfield;


    @Override
    public String toString() {
        return "PidaoDiQuFenBu{" +
                "pingdaoid=" + pingdaoid +
                ", area='" + area + '\'' +
                ", pv=" + pv +
                ", uv=" + uv +
                ", newcount=" + newcount +
                ", oldcount=" + oldcount +
                ", timestamp=" + timestamp +
                ", timestring='" + timestring + '\'' +
                ", groupbyfield='" + groupbyfield + '\'' +
                '}';
    }
}
