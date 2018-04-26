package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.Equipment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/5/26.
 */
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long> {

	Equipment findByBarCode(@Param("barCode") String barCode);

	List<Equipment> findByLocationId(@Param("locationId") Long locationId);

	Integer countByLocationId(@Param("locationId") Long locationId);
}
