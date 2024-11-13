package com.tsconsulting.businessManage.application.domain.exception;

public class InvalidePaymentPurchaseException extends PurchaseException{

    public InvalidePaymentPurchaseException(){
        super();
    }

    public InvalidePaymentPurchaseException(String meessage){
        super(meessage);
    }

    public InvalidePaymentPurchaseException(String meessage, int code){
        super(meessage, code);
    }
}
