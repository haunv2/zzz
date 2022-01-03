package com.service.impl;

import com.repository.AddressRepository;
import com.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.model._Address;

import java.util.List;

@Service
@CacheConfig(cacheNames = "addresses")
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.repo = addressRepository;
    }

    @Override
    @Cacheable(value = "address", key = "#id")
    public _Address findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "addresses", allEntries = true), @CacheEvict(value = "address", key = "#obj.id")}
            ,
            put = {@CachePut(value = "address", key = "#obj.id")}
    )
    public _Address save(_Address obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "addresses", allEntries = true, beforeInvocation = true), @CacheEvict(value = "address", key = "#obj.id")}
            ,
            put = {@CachePut(value = "address", key = "#obj.id")}
    )
    public _Address deleteById(Long id) {
        _Address obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Caching(
            cacheable = {@Cacheable(key = "#page")},
            put = {@CachePut(key = "#page")}
    )
    public List<_Address> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs)/30;
    }
}
