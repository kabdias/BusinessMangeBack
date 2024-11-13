package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.impl;

import com.tsconsulting.businessManage.application.domain.model.PurchaseProductDetail;
import com.tsconsulting.businessManage.application.domain.ports.out.PurchaseProductDetailRepository;

import java.util.Map;
import java.util.Optional;

public class PurchaseProductDetailRepositoryImpl implements PurchaseProductDetailRepository {
    @Override
    public Optional<PurchaseProductDetail> getProDtlByIds(String idBuying, String idProduct) {
        return Optional.empty();
    }

    @Override
    public void savePurchaseProductDetail(PurchaseProductDetail purchaseProductDetail) {

    }

    @Override
    public Map<String, PurchaseProductDetail> getPurchaseDetailsByIdBuying(String idBuying) {
        return null;
    }
}
