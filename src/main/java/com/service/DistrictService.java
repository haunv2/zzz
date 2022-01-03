package com.service;

import com.model.District;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DistrictService {
    District findById(Integer id);

    District save(District obj);

    District deleteById(Integer id);

    List<District> findAll(Specification specs, int page);

    Long count(Specification specs);
}
