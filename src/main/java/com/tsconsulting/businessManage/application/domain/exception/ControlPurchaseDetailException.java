package com.tsconsulting.businessManage.application.domain.exception;

public class ControlPurchaseDetailException extends PurchaseException{

    public ControlPurchaseDetailException(){
        super();
    }

    public ControlPurchaseDetailException(String meessage){
        super(meessage);
    }

    public ControlPurchaseDetailException(String meessage, int code){
        super(meessage, code);
    }
}
