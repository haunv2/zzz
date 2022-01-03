package com.service.impl;

import com.model.Category;
import com.repository.CategoryRepository;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "categoties")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.repo = categoryRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(allEntries = true)},
            put = {@CachePut(key = "#obj.id")}
    )
    public Category save(Category obj) {
        return repo.saveAndFlush(obj);
    }


    @Override
    @CacheEvict(allEntries = true)
    public Category deleteById(Long id) {
        Category obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    @Cacheable(key = "#page")
    public List<Category> findAll(Specification specs, int page) {
        return repo.findAll(specs, PageRequest.of(page, 30)).toList();
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs)/30;
    }
}
