package com.tsconsulting.businessManage.application.domain.model;

public enum Rule {

    GESTIONNAIRE (1, "gestionnaire"),
    COLLABORATEUR (2, "collaborateur");

    private final int code;
    private final String name;

    private Rule(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public double getCode() {
        return code;
    }

    public static Rule getRule(String str){
        return GESTIONNAIRE.getName().equals(str) ? GESTIONNAIRE : COLLABORATEUR;
    }

}


