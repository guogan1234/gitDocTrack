/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.FixApproach;
import com.avp.mems.backstage.entity.work.FixApproachPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author sky
 */
public interface FixApproachWorkRepository extends PagingAndSortingRepository<FixApproach, FixApproachPK> {
    
    @Query("select fa from FixApproach fa where fa.fixApproachPK.workOrderId = :workOrderId")
    List findByWorkOrderId(@Param("workOrderId") Integer workOrderId);
}
