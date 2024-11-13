package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.model;

import com.tsconsulting.businessManage.application.domain.model.Purchase;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.List;

@Entity
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBuyingEntity;
    private LocalDate buyingDateEntity;
    private String providerNameEntity;
    private String providerAdressEntity;
    private String paymentModeEntity;

    @OneToMany(mappedBy="idBuyingEntity")
    private List<PurchaseProductDetailEntity> PurchaseProductDetailEntityList;
    public PurchaseEntity(Long idBuyingEntity, LocalDate buyingDateEntity, String providerNameEntity, String providerAdressEntity, String paymentModeEntity) {
        this.idBuyingEntity = idBuyingEntity;
        this.buyingDateEntity = buyingDateEntity;
        this.providerNameEntity = providerNameEntity;
        this.providerAdressEntity = providerAdressEntity;
        this.paymentModeEntity = paymentModeEntity;
    }

    public Long getIdBuying() {
        return this.idBuyingEntity;
    }

    public Purchase mappingToPurchase(){

        return new Purchase(String.valueOf(this.idBuyingEntity), this.buyingDateEntity, this.providerNameEntity, this.providerAdressEntity, this.paymentModeEntity);
    }
}

