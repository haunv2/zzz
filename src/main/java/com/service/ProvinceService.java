package com.service;

import com.model.Province;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProvinceService {

    Province findById(Integer id);

    Province save(Province obj);

    Province deleteById(Integer id);

    List<Province> findAll(Specification specs, int page);

    Long count(Specification specs);
}
