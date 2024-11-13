package com.tsconsulting.businessManage.application.domain.exception;

public class InvalidProductPurchaseException extends PurchaseException{

    public InvalidProductPurchaseException(){
        super();
    }

    public InvalidProductPurchaseException(String meessage){
        super(meessage);
    }

    public InvalidProductPurchaseException(String meessage, int code){
        super(meessage, code);
    }
}
