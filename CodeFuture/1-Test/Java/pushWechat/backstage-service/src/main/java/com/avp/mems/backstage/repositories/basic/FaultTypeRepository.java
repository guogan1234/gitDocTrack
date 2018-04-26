package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.FaultType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zhoujs on 2017/6/26.
 */
public interface FaultTypeRepository extends PagingAndSortingRepository<FaultType, Long> {

}
