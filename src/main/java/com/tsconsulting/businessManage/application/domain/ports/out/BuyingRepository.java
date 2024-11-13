package com.tsconsulting.businessManage.application.domain.ports.out;

import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.Purchase;

import java.util.Map;

import java.util.Collection;
import java.util.Optional;


public interface BuyingRepository {
    Optional<Purchase> getPurchaseById(String idBuying) throws PurchaseException;

    void savePurchase(Purchase purchase);
    Map<String, Purchase> getAll();
}
