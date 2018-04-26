/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.user;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.system.SysResource;
import com.avp.mem.njpb.entity.view.VwAuthResource;
import com.avp.mem.njpb.entity.view.VwRoleResource;
import com.avp.mem.njpb.repository.sys.SysResourceRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.sys.VwAuthResourceRepository;
import com.avp.mem.njpb.repository.sys.VwRoleResourceRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
@RestController
public class ResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    SysResourceRepository sysResourceRepository;

    @Autowired
    VwRoleResourceRepository vwRoleResourceRepository;

    @Autowired
    VwAuthResourceRepository vwAuthResourceRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    /**
     * 资源信息查询
     *
     * @return
     */
    @RequestMapping(value = "resources", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysResource>> getResources() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<SysResource> resources = sysResourceRepository.findAll();
            LOGGER.debug("资源查询成功，数据量为：{}", resources.size());
            builder.setResultEntity(resources, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error("getResources", e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 用户APP资源获取(APP API)
     *
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "resources/findByUserAccount", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwAuthResource>> findByUserAccount(@Param("userAccount") String userAccount) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("resources/findByUserAccount, userAccount是({})", userAccount);

        List<VwAuthResource> authResourceList;
        try {
            authResourceList = vwAuthResourceRepository.findByUserAccountAndResourceTypeId(
                    userAccount, BusinessRefData.AUTH_RES_MOBILE);
            if (authResourceList.size() <= 0) {
                LOGGER.debug("根据账号查询用户资源，不存在, userAccount是({})", userAccount);
                builder.setResponseCode(ResponseCode.NOT_EXIST);
            } else {
                LOGGER.debug("根据账号查询用户资源成功, userAccount是({})", userAccount);
                builder.setResultEntity(authResourceList, ResponseCode.RETRIEVE_SUCCEED);
            }
            builder.setResultEntity(sysUserRepository.findOneById(SecurityUtils.getLoginUserId()), ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error("findByUserAccount", e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 角色资源获取
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "resources/findByRoleId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwRoleResource>> findByRoleId(@Param("roleId") Integer roleId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("resource/findByRoleId：roleId为({})", roleId);
            List<VwRoleResource> vwRoleResources = vwRoleResourceRepository.findByRoleId(roleId);
            builder.setResultEntity(vwRoleResources, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error("findByRoleId", e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 角色资源获取
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "resourceTreeDatas/findByRoleId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwRoleResource>> findTreeDataByRoleId(@Param("roleId") Integer roleId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwRoleResource> vwRoleResourceList = new ArrayList<>();
        try {
            LOGGER.debug("resourceTreeDatas/findByRoleId：roleId为({})", roleId);
            List<VwRoleResource> vwRoleResources = vwRoleResourceRepository.findByRoleId(roleId);

            List<SysResource> sysResourcesList = sysResourceRepository.findAll();

            for(VwRoleResource vwRoleResource : vwRoleResources){
                    //判断用户资源是否是最后一级节点
                    if (checkUserRolesCanUse(sysResourcesList, vwRoleResource)) {
                        vwRoleResourceList.add(vwRoleResource);
                    }
            }
            builder.setResultEntity(vwRoleResourceList, ResponseCode.RETRIEVE_SUCCEED);

        } catch (Exception e) {
            LOGGER.error("findByRoleId", e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);

        }
        return builder.getResponseEntity();
    }



    private boolean checkUserRolesCanUse(List<SysResource> sysResourcesList, VwRoleResource vwRoleResource) {
        for (SysResource sysResource : sysResourcesList) {

            if (vwRoleResource.getId().equals(sysResource.getParentId())) {
                return false;
            }
        }
        return true;
    }


}


