package com.service.impl;

import com.model.Ward;
import com.repository.WardRepository;
import com.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "wards")
public class WardServiceImpl implements WardService {

    private final WardRepository repo;

    @Autowired
    public WardServiceImpl(WardRepository wardRepository) {
        this.repo = wardRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Ward findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(allEntries = true)},
            put = {@CachePut(key = "#obj.id")}
    )
    public Ward save(Ward obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Ward deleteById(Integer id) {
        Ward obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Cacheable(key = "#page")
    public List<Ward> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        System.out.println(repo.count(specs) / 30);
        return repo.count(specs);
    }
}
