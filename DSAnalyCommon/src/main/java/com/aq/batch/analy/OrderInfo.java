package com.aq.batch.analy;

import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
public class OrderInfo {
    private long orderid;
    private long userid;
    private long mechartid;
    private double orderamount;
    private long paytype;
    private Date paytime;
    private long hbamount;
    private long djjamount;
    private long productid;
    private long huodongnumber;
    private Date createtime;


    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderid=" + orderid +
                ", userid=" + userid +
                ", mechartid=" + mechartid +
                ", orderamount=" + orderamount +
                ", paytype=" + paytype +
                ", paytime=" + paytime +
                ", hbamount=" + hbamount +
                ", djjamount=" + djjamount +
                ", productid=" + productid +
                ", huodongnumber=" + huodongnumber +
                ", createtime=" + createtime +
                '}';
    }
}
