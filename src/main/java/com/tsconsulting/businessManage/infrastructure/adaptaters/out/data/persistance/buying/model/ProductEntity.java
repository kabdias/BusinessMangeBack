package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.model;

import com.tsconsulting.businessManage.application.domain.model.Purchase;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProductEntity;
    private String productNameEntity;
    private String descriptionEntity;
    private double sellingPriceEntity;
    private String categoryProductEntity;
    private int stockQuantityEntity;



    public ProductEntity(Long idProductEntity, String productNameEntity, String descriptionEntity, double sellingPriceEntity, String categoryProductEntity, int stockQuantityEntity) {
        this.idProductEntity = idProductEntity;
        this.productNameEntity = productNameEntity;
        this.descriptionEntity = descriptionEntity;
        this.sellingPriceEntity = sellingPriceEntity;
        this.categoryProductEntity = categoryProductEntity;
        this.stockQuantityEntity = stockQuantityEntity;
    }

    public Long getIdProduct() {
        return this.idProductEntity;
    }

    public int getBuyingQuantity() {
        return this.stockQuantityEntity;
    }

    public double getCurrentSellingPrice() {
        return this.sellingPriceEntity;
    }

    public void updateCurrentSellingPrice(Double currentSellingPriceEntity) {
        if(currentSellingPriceEntity != null && currentSellingPriceEntity != 0)  this.sellingPriceEntity = currentSellingPriceEntity;
    }

    public void updateBuyingQuantity(int oldBuyingQuantity, int newBuyingQuantity ) {
        this.stockQuantityEntity = oldBuyingQuantity + newBuyingQuantity;
    }

    public int getStockQuantity() {
        return this.stockQuantityEntity;
    }


}
