package com.aq.analy;

import lombok.Data;


@Data
public class PidaoPvUv {
    private long pingdaoid;
    private long userid;
    private long pvcount;
    private long uvcount;
    private long timestamp;
    private String timestring;
    private String groupbyfield;


    @Override
    public String toString() {
        return "PidaoPvUv{" +
                "pingdaoid=" + pingdaoid +
                ", userid=" + userid +
                ", pvcount=" + pvcount +
                ", uvcount=" + uvcount +
                ", timestamp=" + timestamp +
                ", timestring='" + timestring + '\'' +
                ", groupbyfield='" + groupbyfield + '\'' +
                '}';
    }
}
