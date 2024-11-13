package com.tsconsulting.businessManage.application.domain.ports.out;

import com.tsconsulting.businessManage.application.domain.model.PurchaseProductDetail;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PurchaseProductDetailRepository {
    Optional<PurchaseProductDetail> getProDtlByIds(String idBuying, String idProduct);

    void savePurchaseProductDetail(PurchaseProductDetail purchaseProductDetail);

    Map<String, PurchaseProductDetail> getPurchaseDetailsByIdBuying(String idBuying);
}
