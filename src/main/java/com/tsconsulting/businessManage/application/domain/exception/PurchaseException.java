package com.tsconsulting.businessManage.application.domain.exception;

import com.tsconsulting.businessManage.application.domain.utils.ExceptionUtils;

public class PurchaseException extends Exception{

    private String message;
    private int code = ExceptionUtils.UNKNOWN_ERROR;

    public PurchaseException(){
        super();
        this.message = ExceptionUtils.UNKNOWN_ERROR_PURCHASE;
    }

    public PurchaseException(String message, int code){
        this.message = message;
        this.code = code;
    }

    public PurchaseException(String message){
        this.message = message;
    }

    public int getCode(){
        return this.code;
    }

    public String  getMessage(){
        return this.message;
    }


}
