package com.example.dell.banhang.ClassObject;

/**
 * Created by DELL on 9/27/2016.
 */

public class BillClass {
    public Integer getBillID() {
        return BillID;
    }



    public String getDate() {
        return Date;
    }

    public void setBillID(Integer billID) {
        this.BillID = billID;
    }



    public void setDate(String date) {
        Date = date;
    }

    public Integer BillID;
    public String Date;

    public Integer getIdtb() {
        return Idtb;
    }

    public void setIdtb(Integer idtb) {
        Idtb = idtb;
    }

    public Integer Idtb;

    public Integer getPay() {
        return Pay;
    }

    public void setPay(Integer pay) {
        Pay = pay;
    }

    public Integer Pay;
    public BillClass (Integer billID, Integer idtb,String date, Integer pay)
    {
        this.BillID= billID;
        Date= date;
        Idtb=idtb;
        Pay = pay;
    }
}
