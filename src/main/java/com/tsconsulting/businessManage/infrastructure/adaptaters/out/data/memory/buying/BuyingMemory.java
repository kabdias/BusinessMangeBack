package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying;

import com.tsconsulting.businessManage.application.domain.model.Purchase;

import com.tsconsulting.businessManage.application.domain.ports.out.BuyingRepository;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

public class BuyingMemory implements BuyingRepository {

    private Map<String, Purchase> purchases = new HashMap<>();


    @Override
    public Optional<Purchase> getPurchaseById(String idBuying) {
        return Optional.ofNullable(purchases.get(idBuying));
    }

    @Override
    public void savePurchase(Purchase purchase) {
        purchases.put(purchase.getIdBuying(), purchase);
    }

    @Override
    public Map<String, Purchase> getAll() {
        return purchases;
    }


}
