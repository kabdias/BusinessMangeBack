package com.tsconsulting.businessManage.application.domain.model;

public class Personel {

    private  Rule rule;
    private String idPersonel;
    private String firstNamePersonel;
    private String lastNamePersonel;



    public Personel(String idPersonel, String firstNamePersonel, String lastNamePersonel, Rule rule) {
        this.idPersonel = idPersonel;
        this.firstNamePersonel = firstNamePersonel;
        this.lastNamePersonel = lastNamePersonel;
        this.rule = rule;
    }

    public Personel(String firstNamePersonel, String lastNamePersonel, Rule rule) {
        this.firstNamePersonel = firstNamePersonel;
        this.lastNamePersonel = lastNamePersonel;
        this.rule = rule;
    }
    public String getIdPersonel() {
        return idPersonel;
    }

    public String getFirstNamePersonel() {
        return firstNamePersonel;
    }

    public String getLastNamePersonel() {
        return lastNamePersonel;
    }

    public Rule getRule() {return rule;}
}
