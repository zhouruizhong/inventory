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
    private Integer matched;
    /**
     * 总数量
     */
    private Integer count;

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

    public Integer getMatched() {
        return matched;
    }

    public void setMatched(Integer matched) {
        this.matched = matched;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
