package com.example.dell.banhang.ClassObject;

/**
 * Created by DELL on 8/7/2016.
 */
public class MenuClass {
    public Integer getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Integer getPrice() {
        return Price;
    }

    public byte[] getPic() {
        return Pic;
    }

    public Integer ID;

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public void setPic(byte[] pic) {
        Pic = pic;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String Name;
    public Integer Price;
    public byte[]Pic;




    public MenuClass(int id, String name, Integer price,byte[]pic ) {

        ID = id;
        Name = name;
        Price = price;
        Pic = pic;


    }
}
