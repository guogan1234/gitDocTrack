/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.user;

import com.avp.mems.backstage.entity.user.UserProject;
import com.avp.mems.backstage.entity.user.UserProjectPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Amber
 */
public interface UserProjectRepository extends PagingAndSortingRepository<UserProject, UserProjectPK> {

    @Transactional
    @Modifying
    @Query("delete from UserProject up where up.userProjectPK.userName = :userName")
    void deleteUserProjectByUserName(@Param("userName") String userName);

    @Transactional
    @Modifying
    @Query("delete from UserProject up where up.userProjectPK.projectName = :projectName")
    void deleteUserProjectByProjectName(@Param("projectName") int projectName);

    @Query("select up from UserProject up where up.userProjectPK.projectName = :projectId")
    List findUserProjectByProjectId(@Param("projectId") int projectId);

    @Query("select up from UserProject up where up.userProjectPK.userName = :userName")
    List<UserProject> findByUserName(@Param("userName") String userName);
}
