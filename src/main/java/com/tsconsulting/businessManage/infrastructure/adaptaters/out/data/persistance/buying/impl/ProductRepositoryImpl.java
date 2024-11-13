package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.persistance.buying.impl;

import com.tsconsulting.businessManage.application.domain.model.Product;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public void add(Product product) {

    }

    @Override
    public LinkedHashSet<Product> getAll() {
        return null;
    }

    @Override
    public Optional<Product> getProductById(String idProduct) {
        return Optional.empty();
    }
}
