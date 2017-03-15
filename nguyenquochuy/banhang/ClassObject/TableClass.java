package com.example.dell.banhang.ClassObject;

/**
 * Created by DELL on 8/8/2016.
 */
public class TableClass {
    public String NameT;
    public Integer IDTB;
    public Integer Empty;

    public Integer getIDTB() {
        return IDTB;
    }

    public void setIDTB(Integer IDTB) {
        this.IDTB = IDTB;
    }

    public Integer getEmpty() {
        return Empty;
    }

    public void setEmpty(Integer empty) {
        Empty = empty;
    }

    public TableClass(Integer idtb, String nameT, Integer empty) {
        NameT = nameT;
        IDTB = idtb;
        Empty = empty;
    }
    public String getNameT() {
        return NameT;
    }
    public void setNameT(String nameT) {
        NameT = nameT;
    }
}
