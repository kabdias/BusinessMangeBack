package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying;

import com.tsconsulting.businessManage.application.domain.model.PurchaseProductDetail;
import com.tsconsulting.businessManage.application.domain.ports.out.PurchaseProductDetailRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PurchaseProductDetailMemory implements PurchaseProductDetailRepository {
    Map<String, Map<String, PurchaseProductDetail>> purchaseDetails = new HashMap<>();


    @Override
    public Optional<PurchaseProductDetail> getProDtlByIds(String idBuying, String idProduct) {
        Map<String, PurchaseProductDetail> productDetailMap = purchaseDetails.get(idBuying);
        return productDetailMap != null ? Optional.of(productDetailMap.get(idProduct)) : Optional.empty();
    }


    @Override
    public void savePurchaseProductDetail(PurchaseProductDetail purchaseProductDetail) {
        Map<String, PurchaseProductDetail> productDetailMap = purchaseDetails.get(purchaseProductDetail.getIdBuying());
        if(productDetailMap != null) {
            productDetailMap.put(purchaseProductDetail.getIdProduct(), purchaseProductDetail);
        } else {
            productDetailMap = new HashMap<>();
            productDetailMap.put(purchaseProductDetail.getIdProduct(), purchaseProductDetail);
            purchaseDetails.put(purchaseProductDetail.getIdBuying(), productDetailMap);
        }
    }

    @Override
    public Map<String, PurchaseProductDetail> getPurchaseDetailsByIdBuying(String idBuying) {
        return  purchaseDetails.get(idBuying);

    }

}
