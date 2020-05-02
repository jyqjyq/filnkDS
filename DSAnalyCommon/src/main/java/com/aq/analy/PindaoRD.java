package com.aq.analy;

import lombok.Data;

/**
 * 频道热点
 */
@Data
public class PindaoRD {

    private Long pingdaoid;
    private Long count;

    @Override
    public String toString() {
        return "PindaoRD{" +
                "pingdaoid=" + pingdaoid +
                ", count=" + count +
                '}';
    }
}
