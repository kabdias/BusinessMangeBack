package com.tsconsulting.businessManage.application.domain.model;

public enum Tva {
    TVA_0 (0.0, "0%"),
    TVA_5_5 (0.055, "5.5%"),
    TVA_10 (0.1, "10%"),
    TVA_20 (0.2, "20%");

    private final double value;
    private final String name;

    Tva(double value, String name) {
        this.value = value;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }

}
