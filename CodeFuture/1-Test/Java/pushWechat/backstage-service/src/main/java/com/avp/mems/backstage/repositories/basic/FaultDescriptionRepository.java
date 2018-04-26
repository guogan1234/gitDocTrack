package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.FaultDescription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/5/26.
 */
public interface FaultDescriptionRepository extends PagingAndSortingRepository<FaultDescription, Long> {
	List<FaultDescription> findByComponentTypeId(@Param("componentTypeId")Long componentTypeId);
}
