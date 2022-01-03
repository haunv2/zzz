package com.service;

import com.model.Authority;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AuthorityService {
    Authority findById(Long id);

    Authority save(Authority obj);

    Authority deleteById(Long id);

    List<Authority> findAll(Specification specs, int page);

    Long count(Specification specs);
}
