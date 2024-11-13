package com.tsconsulting.businessManage.application.domain.ports.out;

import com.tsconsulting.businessManage.application.domain.model.Product;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface ProductRepository {
    void add(Product product);

    LinkedHashSet<Product> getAll();

    Optional<Product> getProductById(String idProduct);
}
