package com.aq.log;

import lombok.Data;

/**
 * 用户浏览记录
 */
@Data
public class UserscanLog {

    private Long pingdaoid;//频道id;
    private Long leibieid;//产品类别id
    private Long chanpinid;//产品id
    private String contry;//国家
    private String province;//省份
    private String city;//城市
    private String network;//网络方式
    private String sources;//来源方式
    private String liulanqitype;//浏览器类型
    private Long starttime;//打开时间
    private Long endetime;//离开时间
    private Long userid;//用户id
}
