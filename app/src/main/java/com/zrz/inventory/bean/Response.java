package com.zrz.inventory.bean;

import java.io.Serializable;

public class Response implements Serializable {

    /**
     * 新的面积
     * 5436.58
     */
    private String new_area;
    /**
     * 成功
     * 200
     */
    private String code;
    /**
     * 盘盈面积
     */
    private String profit_area;
    /**
     * 包号
     */
    private String packet_num;
    /**
     * 盘亏面积
     */
    private String losses_area;
    /**
     * 柜号
     * 1-1
     */
    private String container_num;
    /**
     * 盘点的面积 5436.58
     */
    private String inventory_area;
    /**
     * 匹配状态 4
     */
    private String match_state;
    /**
     * 盘点状态1、未匹配，2、匹配不合格，3、新增，4、匹配符合
     */
    private String inventory_state;
    /**
     * 旧面积 5436.58
     */
    private String old_area;
    /**
     *
     * 2018-12-25 14:23:57
     */
    private String inventory_time;
    /**
     * RFID值 123456789900001
     */
    private String rfid_data;

    public String getNew_area() {
        return new_area;
    }

    public void setNew_area(String new_area) {
        this.new_area = new_area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProfit_area() {
        return profit_area;
    }

    public void setProfit_area(String profit_area) {
        this.profit_area = profit_area;
    }

    public String getPacket_num() {
        return packet_num;
    }

    public void setPacket_num(String packet_num) {
        this.packet_num = packet_num;
    }

    public String getLosses_area() {
        return losses_area;
    }

    public void setLosses_area(String losses_area) {
        this.losses_area = losses_area;
    }

    public String getContainer_num() {
        return container_num;
    }

    public void setContainer_num(String container_num) {
        this.container_num = container_num;
    }

    public String getInventory_area() {
        return inventory_area;
    }

    public void setInventory_area(String inventory_area) {
        this.inventory_area = inventory_area;
    }

    public String getMatch_state() {
        return match_state;
    }

    public void setMatch_state(String match_state) {
        this.match_state = match_state;
    }

    public String getInventory_state() {
        return inventory_state;
    }

    public void setInventory_state(String inventory_state) {
        this.inventory_state = inventory_state;
    }

    public String getOld_area() {
        return old_area;
    }

    public void setOld_area(String old_area) {
        this.old_area = old_area;
    }

    public String getInventory_time() {
        return inventory_time;
    }

    public void setInventory_time(String inventory_time) {
        this.inventory_time = inventory_time;
    }

    public String getRfid_data() {
        return rfid_data;
    }

    public void setRfid_data(String rfid_data) {
        this.rfid_data = rfid_data;
    }
}
