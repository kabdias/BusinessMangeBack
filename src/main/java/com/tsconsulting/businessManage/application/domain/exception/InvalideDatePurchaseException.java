package com.tsconsulting.businessManage.application.domain.exception;

public class InvalideDatePurchaseException extends PurchaseException{

    public InvalideDatePurchaseException(){
        super();
    }

    public InvalideDatePurchaseException(String meessage){
        super(meessage);
    }

    public InvalideDatePurchaseException(String meessage, int code){
        super(meessage, code);
    }

}
