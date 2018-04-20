package com.intive.shopme.offer;

import com.intive.shopme.model.db.DbOffer;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
class OfferSpecification implements Specification<DbOffer> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<DbOffer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Predicate result = null;
        switch (criteria.getOperation()) {
            case "≥":
                result = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                break;
            case "≤":
                result = builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                break;
            case ":":
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    result = builder.like(builder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue() + "%");
                } else {
                    result = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
                break;
        }

        return result;
    }

}