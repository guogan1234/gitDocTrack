/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.view.VwUserEstateBarCode;
import com.avp.mem.njpb.entity.view.VwUserEstateBarCode_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Amber Wang on 2017-08-09 上午 10:34.
 */
public class VwUserEstateBarCodeRepositorySpecification {
    public static Specification<VwUserEstateBarCode> byConditions(Integer projectId, Integer relation, Integer stationId, Integer category, Integer estateTypeId, Integer uid) {
        return new Specification<VwUserEstateBarCode>() {
            public Predicate toPredicate(Root<VwUserEstateBarCode> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.projectId), projectId));
                }

                if (relation != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.relation), relation));
                }

                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.category), category));
                }

                if (stationId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.stationId), stationId));
                }

                if (estateTypeId != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserEstateBarCode_.estateTypeId), estateTypeId));
                }

                predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.uid), uid));
                return predicate;
            }
        };
    }
}
