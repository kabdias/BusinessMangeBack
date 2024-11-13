package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.impl;

import com.tsconsulting.businessManage.application.domain.exception.PurchaseException;
import com.tsconsulting.businessManage.application.domain.model.Purchase;
import com.tsconsulting.businessManage.application.domain.ports.out.BuyingRepository;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.dao.BuyingDataRepository;
import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.model.PurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class BuyingRepositoryImpl implements BuyingRepository {

    private final BuyingDataRepository buyingDataRepository;

    @Autowired
    public BuyingRepositoryImpl ( BuyingDataRepository buyingDataRepository){
        this.buyingDataRepository = buyingDataRepository;
    }
    @Override
    public Optional<Purchase> getPurchaseById(String idBuying) throws PurchaseException {
        if (idBuying.isEmpty()) return Optional.empty();
        Optional<PurchaseEntity> purchaseEntity = buyingDataRepository.findById(Long.valueOf(idBuying));
        return Optional.ofNullable(purchaseEntity.get().mappingToPurchase());
    }

    @Override
    public void savePurchase(Purchase purchase) {

    }

    @Override
    public Map<String, Purchase> getAll() {
        return null;
    }
}
