package com.zrz.inventory.bean;

/**
 * @author 周瑞忠
 * 单据
 * @date 2019/3/31 17:29
 */
public class Receipts {

    /**
     *
     */
    private Integer id;
    /**
     *  编号
     */
    private String number;
    /**
     *  已匹配
     */
    private String matched;
    /**
     * 总数量
     */
    private String count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMatched() {
        return matched;
    }

    public void setMatched(String matched) {
        this.matched = matched;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
