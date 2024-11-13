package com.tsconsulting.businessManage.application.domain.model;

public class Product {

    private String idProduct;
    private String productName;
    private String description;
    private double sellingPrice;
    private String categoryProduct;
    private int stockQuantity;

    public Product(String idProduct, String productName, String description, double sellingPrice, String categoryProduct, int stockQuantity) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.categoryProduct = categoryProduct;
        this.stockQuantity = stockQuantity;
    }

    public String getIdProduct() {
        return this.idProduct;
    }

    public int getBuyingQuantity() {
       return this.stockQuantity;
    }

    public double getCurrentSellingPrice() {
        return this.sellingPrice;
    }

    public void updateCurrentSellingPrice(Double currentSellingPrice) {
        if(currentSellingPrice != null && currentSellingPrice != 0)  this.sellingPrice = currentSellingPrice;
    }

    public void updateBuyingQuantity(int oldBuyingQuantity, int newBuyingQuantity ) {
        this.stockQuantity = oldBuyingQuantity + newBuyingQuantity;
    }

    public int getStockQuantity() {
        return this.stockQuantity;
    }
}
