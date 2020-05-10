package com.aq.batch.analy;

import lombok.Data;

/**
 */
@Data
public class ProductAnaly {
    private long productid;
    private String dateString;
    private long chengjiaocount;//成交
    private long weichegnjiao;//未成交
    private String groupbyfield;//分组key

}
