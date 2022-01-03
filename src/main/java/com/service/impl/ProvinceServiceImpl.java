package com.service.impl;

import com.model.Province;
import com.repository.ProvinceRepository;
import com.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "provinces")
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository repo;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.repo = provinceRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Province findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(allEntries = true)},
            put = {@CachePut(key = "#obj.id")}
    )
    public Province save(Province obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Province deleteById(Integer id) {
        Province obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Cacheable(key = "#page")
    public List<Province> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs)/30;
    }
}
