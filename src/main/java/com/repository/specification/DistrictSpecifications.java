package com.repository.specification;

import com.model.District;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DistrictSpecifications {
    public static Specification<District> byProvince(Integer id) {
        return new Specification<District>() {
            @Override
            public Predicate toPredicate(Root<District> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.join("province").get("id"), id);
            }
        };
    }
}
