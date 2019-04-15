package com.zrz.inventory.bean;

import java.io.Serializable;

public class Upload implements Serializable {

    /**
     * 签名数据
     */
    private String sign;
    /**
     * 登陆时返回的keyName
     */
    private String jhKey;
    /**
     * 时间戳，签名和参数的时间戳要一致
     */
    private Long timestamp;
    /**
     * RFID值，多个值之间用英文逗号分隔
     */
    private String rfidData;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        String stringSignTemp = "";
        this.sign = sign;
    }

    public String getJhKey() {
        return jhKey;
    }

    public void setJhKey(String jhKey) {
        this.jhKey = jhKey;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRfidData() {
        return rfidData;
    }

    public void setRfidData(String rfidData) {
        this.rfidData = rfidData;
    }
}
