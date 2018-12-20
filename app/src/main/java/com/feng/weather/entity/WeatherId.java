package com.feng.weather.entity;

/**
 * author 丰
 * date   2018/12/6
 * desc
 */
public class WeatherId {
    private String fa;
    private String fb;

    public WeatherId() {
    }

    public WeatherId(String fa, String fb) {
        this.fa = fa;
        this.fb = fb;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    @Override
    public String toString() {
        return "WeatherId{" +
                "fa='" + fa + '\'' +
                ", fb='" + fb + '\'' +
                '}';
    }
}
