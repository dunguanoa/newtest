package com.example.dell.banhang.ClassObject;

/**
 * Created by DELL on 10/19/2016.
 */

public class DebillClass {


    public Integer getNum() {
        return Num;
    }

    public String getName() {
        return Name;
    }



    public Integer getBillID() {
        return BillID;
    }

    public Integer BillID;



    public void setName(String name) {
        Name = name;
    }

    public void setNum(Integer num) {
        Num = num;
    }





    public void setBillID(Integer billID) {
        BillID = billID;
    }

    public String Name;
    public Integer Num;

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer total) {
        Total = total;
    }

    public Integer Total;

    public DebillClass (Integer billID, String name, Integer num, Integer total)
    {
        BillID= billID;
        Name = name;
        Num = num;
        Total =total;
    }


}
