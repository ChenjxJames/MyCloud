package com.chenxiaoyu.cloud.vo;

/**
 * @author ：
 * @date ：Created in 2020/6/14 2:51
 * @description：Response data object
 */
public class Result {
    private Integer state;
    private String info;
    private Object data;

    public Result() {

    }

    public Result(Integer state, String info) {
        this.state = state;
        this.info = info;
    }

    public Result(Integer state, String info, Object data) {
        this(state, info);
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String resultStr = "{\"state\":" + state + ",\"info\":\"" + info + "\"}";
        if (this.data != null) {
            resultStr = "{\"state\":" + state + ",\"info\":\"" + info + "\",\"data\":" + data + '}';
        }
        return resultStr;
    }
}
