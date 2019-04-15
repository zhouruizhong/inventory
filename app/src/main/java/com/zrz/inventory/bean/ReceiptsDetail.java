package com.zrz.inventory.bean;

import java.io.Serializable;

public class ReceiptsDetail implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer receiptsId;
    /**
     * 包号
     */
    private String item1;
    /**
     * 面积
     */
    private String item2;
    /**
     * 状态
     */
    private String item3;
    /**
     * RFID
     */
    private String item4;

    public ReceiptsDetail(){
        this.item1 = "";
        this.item2 = "";
        this.item3 = "";
        this.item4 = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiptsId() {
        return receiptsId;
    }

    public void setReceiptsId(Integer receiptsId) {
        this.receiptsId = receiptsId;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }
}
