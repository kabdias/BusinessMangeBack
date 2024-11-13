package com.tsconsulting.businessManage.infrastructure.adaptaters.out.data.memory.buying;

import com.tsconsulting.businessManage.application.domain.model.Product;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;

import java.util.LinkedHashSet;
import java.util.Optional;

public class ProductMemory implements ProductRepository {

    final LinkedHashSet<Product> products = new LinkedHashSet<>();

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public LinkedHashSet<Product> getAll() {
        return products;
    }

    @Override
    public Optional<Product> getProductById(String idProduct) {
        return products.stream().filter(produc -> {
            return produc.getIdProduct().equals(idProduct);
        }).findFirst();
    }
}
