package com.avp.mem.njpb.resource.user;


import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.SysRole;
import com.avp.mem.njpb.entity.SysRoleResource;
import com.avp.mem.njpb.entity.SysUserRole;
import com.avp.mem.njpb.reponsitory.user.SysRoleRepository;
import com.avp.mem.njpb.reponsitory.user.SysRoleResourceRepository;
import com.avp.mem.njpb.reponsitory.user.SysUserRoleRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.BuzUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
@RestController
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysRoleResourceRepository sysRoleResourceRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;


    /**
     * 角色查询
     *
     * @return
     */
    @RequestMapping(value = "role", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysRole>> getRoles() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("角色查询");
            List<SysRole> roles = sysRoleRepository.findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();
            builder.setResultEntity(roles, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
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
            logger.error("buildRoleResources {}", e.getMessage());
            throw e;
        }
        return result;
    }

    // 角色新建
    @RequestMapping(value = "role", method = RequestMethod.POST)
    ResponseEntity<RestBody<SysRole>> buildRole(@RequestParam("resourceIds") List<Integer> resourceIds, @RequestBody SysRole role) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            // duplicated check
            if (sysRoleRepository.findOneByRoleName(role.getRoleName()) != null) {
                logger.warn("duplicated role ({})", role.getRoleName());
                builder.setErrorCode(ResponseCode.ALREADY_EXIST, "角色名称【" + role.getRoleName() + "】已经存在！");
            } else {
                // build role
                int uid = SecurityUtils.getLoginUserId();

                role.setCreateBy(uid);
                role.setLastUpdateBy(uid);
                role = sysRoleRepository.save(role);

                // build role resource relationship
                buildRoleResources(role.getId(), resourceIds);
                builder.setResultEntity(role, ResponseCode.CREATE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    protected List<SysRoleResource> editRoleResources(Integer roleId, List<Integer> resourceIds) {
        List<SysRoleResource> result = new ArrayList<>();

        try {
            // set delete flag for old data
            List<SysRoleResource> entities = sysRoleResourceRepository.findByRoleIdAndRemoveTimeIsNull(roleId);
            for (SysRoleResource r : entities) {
                r.setRemoveTime(new Date());
                r.setLastUpdateBy(SecurityUtils.getLoginUserId());
            }
            sysRoleResourceRepository.save(entities);

            // build new
            result.addAll(this.buildRoleResources(roleId, resourceIds));
        } catch (Exception e) {
            logger.error("editRoleResources {}", e.getMessage());
            throw e;
        }
        return result;
    }

    // 角色编辑
    @Transactional
    @RequestMapping(value = "role/{id}", method = RequestMethod.PUT)
    ResponseEntity<RestBody<SysRole>> editRole(@PathVariable("id") int id, @RequestParam("resourceIds") List<Integer> resourceIds, @RequestBody SysRole role) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Boolean isExists = sysRoleRepository.exists(id);
            if (isExists) {
                if(role.getRoleGrade() == 1){
                  logger.debug("这个角色是预制角色");
                    builder.setErrorCode(ResponseCode.FAILED,"预制角色,不可以修改");
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
                logger.debug("update--role----传入对象为空");
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 角色--批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "role/deleteMore", method = RequestMethod.DELETE)
    @Transactional
    ResponseEntity<RestBody<SysRole>> deleteRoleMore(@RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int uid = SecurityUtils.getLoginUserId();
        //角色
        try {
            logger.debug("roles/deleteMore：ids为({})", ids);

            if (ids.isEmpty()) {
                logger.debug("ids({})参数缺失", ids);
                logger.debug("ids({})参数缺失", ids);
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT.getValue()) {

                //验证用户角色中间表中得到的关联是否是脏数据
                SysUserRole userRole = sysUserRoleRepository.findTopByRoleIdIn(ids);
                if (userRole != null) {
                    SysRole role = sysRoleRepository.findOne(userRole.getRoleId());
                    logger.debug("角色({})已经与用户进行关联，请先解除关联", role.getRoleName());
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST,"角色【" + role.getRoleName() + "】已经与用户进行关联，请先解除关联");
                    return builder.getResponseEntity();
                }

                // update
                List<SysRole> roleList = sysRoleRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                for (SysRole r : roleList) {
                    if(r.getRoleGrade() == 1){
                        logger.debug("这个角色是预制角色");
                        builder.setErrorCode(ResponseCode.FAILED,"预制角色,不可以修改");
                        return builder.getResponseEntity();
                    }
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(uid);
                }
                builder.setResultEntity(sysRoleRepository.save(roleList), ResponseCode.DELETE_SUCCEED);
                logger.debug("ids({})对应的批次号批量删除成功", ids);
            } else {
                logger.debug("批量删除的数量必须在({})以内", BusinessRefData.BATCH_COUNT.getValue());
                builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            builder.setErrorCode(ResponseCode.DELETE_FAILED);
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
    @RequestMapping(value = "role/findByRoleName", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysRole>> findByTitle(@Param("roleName") String roleName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("role/findRoleName, roleName是({})", roleName);
            SysRole role = sysRoleRepository.findOneByRoleName(roleName);
            if (role != null) {
                logger.debug("根据roleName：({})查询SysRoles,数据已存在", roleName);
                builder.setResultEntity(role, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                logger.debug("根据title：({})查询SysRoles,数据不存在", roleName);
                builder.setErrorCode(ResponseCode.NOT_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }



}
