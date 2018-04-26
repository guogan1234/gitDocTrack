package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.Assignment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/5/27.
 */
public interface AssignmentRepository extends PagingAndSortingRepository<Assignment,Long> {
	List<Assignment> findByWorkOrderIdOrderByAssignTimeAsc(@Param("workOrderId")Long workOrderId);
}
