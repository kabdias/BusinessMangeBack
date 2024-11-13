package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.dao;

import com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.model.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingDataRepository extends JpaRepository<PurchaseEntity, Long> {
}
