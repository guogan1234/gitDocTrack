package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Amber on 2017/5/26.
 */
public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {

	@Query("select l from Location l where l.id in :id or l.parentId in :id")
	List<Location> findByIdOrParentId(@Param("id") Integer id);

	List<Location> findByIdInAndLevel(@Param("ids") List<Integer> ids,@Param("level") Short level);

	List<Location> findByIdInAndLevelOrderByIdAsc(@Param("ids") List<Integer> ids,@Param("level") Short level);

	List<Location> findByParentId(@Param("parentId") Integer parentId);

}
