package com.service.impl;

import com.model.District;
import com.repository.DistrictRepository;
import com.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "districts")
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repo;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.repo = districtRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public District findById(Integer id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(allEntries = true)},
            put = {@CachePut(key = "#obj.id")}
    )
    public District save(District obj) {
        return repo.saveAndFlush(obj);
    }


    @Override
    @CacheEvict(allEntries = true)
    public District deleteById(Integer id) {
        District obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Cacheable(key = "#page")
    public List<District> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs)/30;
    }
}
