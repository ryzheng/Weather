package com.feng.weather.entity;

/**
 * author 丰
 * date   2018/12/10
 * desc
 */
public class Province {
    private String id;
    private String provinceName;

    public Province() {
    }

    public Province(String id, String provinceName) {
        this.id = id;
        this.provinceName = provinceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return provinceName;
    }
}
