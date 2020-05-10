package com.aq.batch.analy;

import lombok.Data;

/**
 *
 */
@Data
public class OrderInfotable {
    public String orderid;
    public String userid;
    public String mechartid;
    public double orderamount;
    public String paytype;
    public String paytime;
    public String hbamount;
    public String djjamount;
    public String productid;
    public String huodongnumber;
    public String createtime;

    public OrderInfotable() {
    }

    public OrderInfotable(String orderid, String userid, String mechartid, double orderamount, String paytype, String paytime, String hbamount, String djjamount, String productid, String huodongnumber, String createtime) {
        this.orderid = orderid;
        this.userid = userid;
        this.mechartid = mechartid;
        this.orderamount = orderamount;
        this.paytype = paytype;
        this.paytime = paytime;
        this.hbamount = hbamount;
        this.djjamount = djjamount;
        this.productid = productid;
        this.huodongnumber = huodongnumber;
        this.createtime = createtime;
    }

}
