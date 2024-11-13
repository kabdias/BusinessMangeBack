package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.model;

import com.tsconsulting.businessManage.application.domain.exception.ControlPurchaseDetailException;
import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.Tva;
import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class PurchaseProductDetailEntity {
    @Id
    private Long idBuyingEntity;
    @Id
    private Long idProductEntity;
    private Integer buyingQuantityEntity;
    private Double buyingPriceUnitEntity;
    private String tvaUnitEntity;
    private Double currentSellingPriceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity productEntityList;

    public PurchaseProductDetailEntity(Long idBuyingEntity, Long idProductEntity, Integer buyingQuantityEntity, Double buyingPriceUnitEntity, String tvaUnitEntity, Double currentSellingPriceEntity) throws PurchaseException {

        this.idBuyingEntity = idBuyingEntity;
        this.idProductEntity = idProductEntity;
        this.buyingQuantityEntity = buyingQuantityEntity;
        this.buyingPriceUnitEntity = buyingPriceUnitEntity;
        this.currentSellingPriceEntity = currentSellingPriceEntity;
        this.tvaUnitEntity = tvaUnitEntity;
        // Validez les paramètres avant de les assigner aux champs de l'objet
        isParametersValid(this.idBuyingEntity, this.idProductEntity, this.buyingQuantityEntity, this.buyingPriceUnitEntity, this.tvaUnitEntity, this.currentSellingPriceEntity);
    }

    public Long getIdBuying() {
        return this.idBuyingEntity;
    }

    public Long getIdProduct() {
        return this.idProductEntity;
    }

    public int getBuyingQuantity() {return buyingQuantityEntity;}

    public Double getBuyingPriceUnit() {return buyingPriceUnitEntity;}
    public Double getCurrentSellingPrice() {return currentSellingPriceEntity;}
    public String getTvaUnit() {return tvaUnitEntity;}

    private void isParametersValid(Long idBuying, Long idProduct, Integer buyingQuantity, Double buyingPriceUnit, String tvaUnit, Double currentSellingPrice) throws PurchaseException {
        checkIdProduct(idProduct);
        checkBuyingQuantity(buyingQuantity);
        checkBuyingPriceUnit(buyingPriceUnit);
        checkTva(tvaUnit);
        checkCurrentSellingPrice(currentSellingPrice);
    }

    private void checkCurrentSellingPrice(Double currentSellingPrice) throws ControlPurchaseDetailException {
        if (currentSellingPrice == null || currentSellingPrice <= this.buyingPriceUnitEntity ) throw  new ControlPurchaseDetailException(ExceptionUtils.CURRENTSELLINGPRICE_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkBuyingPriceUnit(Double buyingPriceUnit) throws ControlPurchaseDetailException {
        if (buyingPriceUnit == null || buyingPriceUnit <= 0) throw  new ControlPurchaseDetailException(ExceptionUtils.BUYINGPRICEUNIT_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkBuyingQuantity(Integer buyingQuantity) throws ControlPurchaseDetailException {
        if (buyingQuantity == null || buyingQuantity <= 0) throw  new ControlPurchaseDetailException(ExceptionUtils.BUYINGQANTITY_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkIdProduct(Long idProduct) throws PurchaseException {
        if(idProduct == null )
            throw new ControlPurchaseDetailException(ExceptionUtils.PRODUCT_IS_NULL, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private Tva checkTva(String tvaUnit) throws PurchaseException {
        if (tvaUnit == null || tvaUnit.isEmpty() || Tva.TVA_0.getName().equals(tvaUnit)) return Tva.TVA_0;
        if (Tva.TVA_5_5.getName().equals(tvaUnit)) return Tva.TVA_5_5;
        if (Tva.TVA_10.getName().equals(tvaUnit)) return Tva.TVA_10;
        if (Tva.TVA_20.getName().equals(tvaUnit)) return Tva.TVA_20;
        throw new ControlPurchaseDetailException(ExceptionUtils.TVA_NOT_VALID, ExceptionUtils.FUNCTIONAL_ERROR);
    }

}

