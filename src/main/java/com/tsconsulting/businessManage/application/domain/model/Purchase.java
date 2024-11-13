package com.tsconsulting.businessManage.application.domain.model;

import java.time.LocalDate;

public class Purchase {
    private String idBuying;
    private LocalDate buyingDate;
    private String providerName;
    private String providerAdress;
    private String paymentMode;

    public Purchase(String idBuying, LocalDate buyingDate, String providerName, String providerAdress, String paymentMode) {
        this.idBuying = idBuying;
        this.buyingDate = buyingDate;
        this.providerName = providerName;
        this.providerAdress = providerAdress;
        this.paymentMode = paymentMode;
    }

    public String getIdBuying() {
        return this.idBuying;
    }
}
