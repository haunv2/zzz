package com.service;

import com.model._Address;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AddressService {
    _Address findById(Long id);

    _Address save(_Address obj);


    _Address deleteById(Long id);

    List<_Address> findAll(Specification specs, int page);

    Long count(Specification specs);
}
