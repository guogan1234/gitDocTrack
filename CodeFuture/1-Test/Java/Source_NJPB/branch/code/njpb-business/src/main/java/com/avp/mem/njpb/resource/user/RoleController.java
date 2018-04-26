/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.user;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.system.SysRole;
import com.avp.mem.njpb.entity.system.SysRoleResource;
import com.avp.mem.njpb.entity.system.SysUserRole;
import com.avp.mem.njpb.repository.sys.SysRoleRepository;
import com.avp.mem.njpb.repository.sys.SysRoleResourceRepository;
import com.avp.mem.njpb.repository.sys.SysUserRoleRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.BuzUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
@RestController
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysRoleResourceRepository sysRoleResourceRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    protected IdentityService identityService;

    /**
     * 角色查询
     *
     * @return
     */
    @RequestMapping(value = "roles", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysRole>> getRoles() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<SysRole> roles = sysRoleRepository.findAll();
            LOGGER.debug("角色查询成功，数据量为：{}", roles.size());
            builder.setResultEntity(roles, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * Activiti角色增加
     *
     * @return
     */
//    @PostMapping("activitiRoles")
    ResponseEntity<RestBody<SysRole>> saveActivitiRoles() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("查询是预制角色的角色");
            List<SysRole> roles = sysRoleRepository.findByRoleGrade(1);

            for (SysRole sysRole : roles) {
                String groupId = sysRole.getRoleName();

                Group group = identityService.newGroup(groupId);
                group.setName(sysRole.getRoleName());
                group.setType("预制角色");

                identityService.saveGroup(group);
            }

            builder.setResultEntity(ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    protected List<SysRoleResource> buildRoleResources(Integer roleId, List<Integer> resourceIds) {
        List<SysRoleResource> result = new ArrayList<>();

        try {
            List<SysRoleResource> entities = new ArrayList<>(resourceIds.size());
            for (Integer resId : resourceIds) {
                SysRoleResource rr = new SysRoleResource();
                rr.setResourceId(resId);
                rr.setRoleId(roleId);
                rr.setCreateBy(SecurityUtils.getLoginUserId());
                rr.setLastUpdateBy(SecurityUtils.getLoginUserId());
                entities.add(rr);
            }

            result.addAll(BuzUtil.toList(sysRoleResourceRepository.save(entities)));
        } catch (Exception e) {
            LOGGER.error("buildRoleResources {}", e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * 角色添加
     *
     * @param resourceIds
     * @param role
     * @return
     */
    @RequestMapping(value = "roles", method = RequestMethod.POST)
    ResponseEntity<RestBody<SysRole>> buildRole(@RequestParam("resourceIds") List<Integer> resourceIds, @RequestBody SysRole role) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            // duplicated check
            if (sysRoleRepository.findOneByRoleName(role.getRoleName()) != null) {
                LOGGER.debug("添加角色名字： ({})", role.getRoleName());
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "角色名称【" + role.getRoleName() + "】已经存在！");
            } else {
                // build role
                Integer uid = SecurityUtils.getLoginUserId();
                role.setCreateBy(uid);
                role.setLastUpdateBy(uid);
                role = sysRoleRepository.save(role);

                // build role resource relationship
                buildRoleResources(role.getId(), resourceIds);
                builder.setResultEntity(role, ResponseCode.CREATE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    protected List<SysRoleResource> editRoleResources(Integer roleId, List<Integer> resourceIds) {
        List<SysRoleResource> result = new ArrayList<>();

        try {
            // set delete flag for old data
            List<SysRoleResource> entities = sysRoleResourceRepository.findByRoleId(roleId);
            for (SysRoleResource r : entities) {
                r.setRemoveTime(new Date());
                r.setLastUpdateBy(SecurityUtils.getLoginUserId());
            }
            sysRoleResourceRepository.save(entities);
         resourceIds.remove(resourceIds.size()-1);
            // build new
            result.addAll(this.buildRoleResources(roleId, resourceIds));
        } catch (Exception e) {
            LOGGER.error("editRoleResources {}", e.getMessage());
            throw e;
        }
        return result;
    }

    // 角色编辑
    @Transactional
    @RequestMapping(value = "roles/{id}", method = RequestMethod.PUT)
    ResponseEntity<RestBody<SysRole>> editRole(@PathVariable("id") int id, @RequestParam("resourceIds") List<Integer> resourceIds, @RequestBody SysRole role) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Boolean isExists = sysRoleRepository.exists(id);
            SysRole sysRole = sysRoleRepository.findOne(id);
            if (isExists) {
                if (sysRole.getRoleGrade() == BusinessRefData.ROLE_GRADE_PREFABRICATE) {
                    LOGGER.debug("这个角色是预制角色,不可以修改");
                    builder.setResponseCode(ResponseCode.FAILED, "预制角色,不可以修改");
                    return builder.getResponseEntity();
                }
                if (role.getRoleGrade() == BusinessRefData.ROLE_GRADE_PREFABRICATE) {
                    LOGGER.debug("您没有权限制定预制角色");
                    builder.setResponseCode(ResponseCode.FAILED, "不可以添加预制角色");
                    return builder.getResponseEntity();
                }
                // build role
                role.setId(id);
                role.setLastUpdateBy(SecurityUtils.getLoginUserId());
                role = sysRoleRepository.save(role);

                // edit role resource
                editRoleResources(role.getId(), resourceIds);
                builder.setResultEntity(role, ResponseCode.UPDATE_SUCCEED);
            } else {
                LOGGER.debug("update--role----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 角色--批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "roles/deleteMore", method = RequestMethod.DELETE)
    @Transactional
    ResponseEntity<RestBody<SysRole>> deleteRoleMore(@RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int uid = SecurityUtils.getLoginUserId();
        //角色
        try {
            LOGGER.debug("role/deleteMore：ids为({})", ids);

            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT) {

                //验证用户角色中间表中得到的关联是否是脏数据
                SysUserRole userRole = sysUserRoleRepository.findTopByRoleIdIn(ids);
                if (userRole != null) {
                    SysRole role = sysRoleRepository.findOne(userRole.getRoleId());
                    LOGGER.debug("角色({})已经与用户进行关联，请先解除关联", role.getRoleName());
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "角色【" + role.getRoleName() + "】已经与用户进行关联，请先解除关联");
                    return builder.getResponseEntity();
                }

                // update
                List<SysRole> roleList = sysRoleRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                for (SysRole r : roleList) {
                    if (r.getRoleGrade() == BusinessRefData.ROLE_GRADE_PREFABRICATE) {
                        LOGGER.debug("这个角色是预制角色");
                        builder.setResponseCode(ResponseCode.FAILED, "预制角色,不可以修改");
                        return builder.getResponseEntity();
                    }
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(uid);
                }
                builder.setResultEntity(sysRoleRepository.save(roleList), ResponseCode.DELETE_SUCCEED);
                LOGGER.debug("ids({})对应的批次号批量删除成功", ids);
            } else {
                LOGGER.debug("批量删除的数量必须在({})以内", BusinessRefData.BATCH_COUNT);
                builder.setResponseCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            builder.setResponseCode(ResponseCode.DELETE_FAILED);
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 角色查重
     *
     * @param roleName
     * @return
     */
    @RequestMapping(value = "roles/findByRoleName", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysRole>> findByTitle(@Param("roleName") String roleName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("roles/findRoleName, roleName是({})", roleName);
            SysRole role = sysRoleRepository.findOneByRoleName(roleName);
            if (role != null) {
                LOGGER.debug("根据roleName：({})查询SysRoles,数据已存在", roleName);
                builder.setResultEntity(role, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据title：({})查询SysRoles,数据不存在", roleName);
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
