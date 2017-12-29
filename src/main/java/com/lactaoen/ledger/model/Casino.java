package com.lactaoen.ledger.model;

public class Casino {

    private Integer casinoId;
    private String name;

    public Casino() {
    }

    public Integer getCasinoId() {
        return casinoId;
    }

    public void setCasinoId(Integer casinoId) {
        this.casinoId = casinoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Casino{" +
                "casinoId=" + casinoId +
                ", name='" + name + '\'' +
                '}';
    }
}
