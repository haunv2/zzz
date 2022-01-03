package com.service;

import com.model.Product;
import com.repository.specification.model.ProductFilter;
import org.eclipse.sisu.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {
    Product findById(Long id);

    Product save(Product obj);

    Product deleteById(Long id);

    List<Product> findAll(Specification specs, int page);

    List<Product> filter(Specification specs, int page);

    Long count(Specification specs);

}
