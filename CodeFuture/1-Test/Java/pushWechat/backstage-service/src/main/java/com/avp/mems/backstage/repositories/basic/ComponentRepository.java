package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.Component;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/5/26.
 */
public interface ComponentRepository extends PagingAndSortingRepository<Component, Long> {
	List<Component> findByEquipmentId(@Param("equipmentId")Long equipmentId);

	@Query("select count(1) from Component where typeId = :typeId and equipmentId in (select id from Equipment where locationId in (select id from Location where parentId = :lineId) )")
	Integer countComponentByTypeIdAndLineId(@Param("typeId")Long typeId,@Param("lineId") Integer lineId);

	@Query("select count(1) from Component where equipmentId in (select id from Equipment where locationId in (select id from Location where parentId = :lineId) )")
	Integer countComponentByLineId(@Param("lineId") Integer lineId);
}
