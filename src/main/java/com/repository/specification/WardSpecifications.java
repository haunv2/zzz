package com.repository.specification;

import com.model.District;
import com.model.Ward;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class WardSpecifications {
    public static Specification<Ward> byDistrict(Integer id){
        return  new Specification<Ward>() {
            @Override
            public Predicate toPredicate(Root<Ward> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Join<Ward, District> j = root.join("district");
                return criteriaBuilder.equal(j.get("id"), id);
            }
        };
    }
}
