/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.user;

import com.avp.mems.backstage.entity.user.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by pw on 2017/5/22.
 */
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    List findByProjOwner(@Param("projOwner") String projOwner);

    @Query(value = "select pl.location_id from project_location pl where pl.project_id =6 AND pl.project_id in(select up.project_name from user_project up where up.user_name=?1) group by pl.location_id",nativeQuery = true)
    List<BigInteger> findLineIdByUserName(String userName);

    @Query(value = "select pl.location_id from project_location pl where pl.project_id =:projectId",nativeQuery = true)
    List<BigInteger> findLineIds(@Param("projectId") Long projectId);
}
