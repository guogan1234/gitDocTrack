package com.avp.mem.njpb.resource.user;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.SysResource;
import com.avp.mem.njpb.entity.VwAuthResource;
import com.avp.mem.njpb.entity.VwRoleResource;
import com.avp.mem.njpb.reponsitory.user.SysResourceRepository;
import com.avp.mem.njpb.reponsitory.user.SysUserRepository;
import com.avp.mem.njpb.reponsitory.user.VwAuthResourceRepository;
import com.avp.mem.njpb.reponsitory.user.VwRoleResourceRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
@RestController
public class ResourceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysResourceRepository sysResourceRepository;

    @Autowired
    private VwRoleResourceRepository vwRoleResourceRepository;

    @Autowired
    VwAuthResourceRepository vwAuthResourceRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    /**
     * 资源信息查询
     *
     * @return
     */
    @RequestMapping(value = "resource", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysResource>> getResources() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("资源查询");
            List<SysResource> resources = sysResourceRepository.findByRemoveTimeIsNullOrderByCreateTimeAsc();
            builder.setResultEntity(resources, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error("getResources", e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 用户APP资源获取(APP API)
     *
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "resource/findByUserAccount", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwAuthResource>> findByUserAccount(@Param("userAccount") String userAccount) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        logger.debug("resource/findByUserAccount, userAccount是({})", userAccount);

        List<VwAuthResource> authResourceList ;
        try {
            authResourceList = vwAuthResourceRepository.findByUserAccountAndResourceTypeId(
                    userAccount, BusinessRefData.AUTH_RES_MOBILE.getValue());
            if (authResourceList.size() <= 0) {
                logger.debug("根据账号查询用户资源，不存在, userAccount是({})", userAccount);
                builder.setErrorCode(ResponseCode.NOT_EXIST);
            } else {
                logger.debug("根据账号查询用户资源成功, userAccount是({})", userAccount);
                builder.setResultEntity(authResourceList, ResponseCode.RETRIEVE_SUCCEED);
            }
            builder.setResultEntity(sysUserRepository.findOneById(SecurityUtils.getLoginUserId()), ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error("findByUserAccount", e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 角色资源获取
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "resource/findByRoleId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwRoleResource>> findByRoleId(@Param("roleId") Integer roleId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("resource/findByRoleId：roleId为({})", roleId);
            builder.setResultEntity(vwRoleResourceRepository.findByRoleId(roleId), ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error("findByRoleId", e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
