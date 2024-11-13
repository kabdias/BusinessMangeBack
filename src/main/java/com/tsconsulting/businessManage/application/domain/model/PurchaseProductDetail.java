package com.tsconsulting.businessManage.application.domain.model;

import com.tsconsulting.businessManage.application.domain.exception.ControlPurchaseDetailException;
import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;

public class PurchaseProductDetail {
    private String idBuying;
    private String idProduct;
    private Integer buyingQuantity;
    private Double buyingPriceUnit;
    private String tvaUnit;

    private Double currentSellingPrice;


    public PurchaseProductDetail(String idBuying, String idProduct, Integer buyingQuantity, Double buyingPriceUnit, String tvaUnit, Double currentSellingPrice) throws PurchaseException {

        this.idBuying = idBuying;
        this.idProduct = idProduct;
        this.buyingQuantity = buyingQuantity;
        this.buyingPriceUnit = buyingPriceUnit;
        this.currentSellingPrice = currentSellingPrice;
        this.tvaUnit = tvaUnit;
        // Validez les paramètres avant de les assigner aux champs de l'objet
        isParametersValid(this.idBuying, this.idProduct, this.buyingQuantity, this.buyingPriceUnit, this.tvaUnit, this.currentSellingPrice);
    }

    public String getIdBuying() {
        return this.idBuying;
    }

    public String getIdProduct() {
        return this.idProduct;
    }

    public int getBuyingQuantity() {return buyingQuantity;}

    public Double getBuyingPriceUnit() {return buyingPriceUnit;}
    public Double getCurrentSellingPrice() {return currentSellingPrice;}
    public String getTvaUnit() {return tvaUnit;}

    private void isParametersValid(String idBuying, String idProduct, Integer buyingQuantity, Double buyingPriceUnit, String tvaUnit, Double currentSellingPrice) throws PurchaseException {
        checkIdProduct(idProduct);
        checkBuyingQuantity(buyingQuantity);
        checkBuyingPriceUnit(buyingPriceUnit);
        checkTva(tvaUnit);
        checkCurrentSellingPrice(currentSellingPrice);
    }

    private void checkCurrentSellingPrice(Double currentSellingPrice) throws ControlPurchaseDetailException {
        if (currentSellingPrice == null || currentSellingPrice <= this.buyingPriceUnit ) throw  new ControlPurchaseDetailException(ExceptionUtils.CURRENTSELLINGPRICE_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkBuyingPriceUnit(Double buyingPriceUnit) throws ControlPurchaseDetailException {
        if (buyingPriceUnit == null || buyingPriceUnit <= 0) throw  new ControlPurchaseDetailException(ExceptionUtils.BUYINGPRICEUNIT_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkBuyingQuantity(Integer buyingQuantity) throws ControlPurchaseDetailException {
        if (buyingQuantity == null || buyingQuantity <= 0) throw  new ControlPurchaseDetailException(ExceptionUtils.BUYINGQANTITY_IS_INVALIDE, ExceptionUtils.FUNCTIONAL_ERROR);
    }
    private void checkIdProduct(String idProduct) throws PurchaseException {
        if(idProduct == null || idProduct.isEmpty())
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
