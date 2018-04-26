package com.avp.mem.njpb.resource.user;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.*;
import com.avp.mem.njpb.reponsitory.user.*;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.BuzUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysProjectRepository sysProjectRepository;

    @Autowired
    AssoUserProjectRepository assoUserProjectRepository;

    @Autowired
    VwUserRepository vwUserRepository;

    @Autowired
    SysUserRoleRepository sysUserRoleRepository;

    /**
     * 用户新建(账号、用户名、头衔都要确认)
     * (方法中，注入参数名称不一致会导致获取不到值；参数顺序不对会导致400错误)
     *
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST)
    @Transactional
    ResponseEntity<RestBody<SysUser>> buildUser(@RequestBody SysUser user,
                                                @RequestParam(value = "roleIds", required = false) List<Integer> roleIds) {

        logger.debug("buildUser, roleIds是({})", roleIds);
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        try {
            if (sysUserRepository.findOneByUserAccount(user.getUserAccount()) != null || sysUserRepository.findOneByUserName(user.getUserName()) != null) {

                logger.debug("根据账号或者用户名称查询用户,该userAccount({})或者userName({})用户名称对应的用户已经存在", user.getUserAccount(), user.getUserName());
                // 1 validation, check if duplicated user Account
                builder.setErrorCode(ResponseCode.ALREADY_EXIST);
            } else {
                // 2.1 encrypt user password in jpa saving event handler
                user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
                if (roleIds.contains(4)) {
                    List<VwUser> vwUsers = vwUserRepository.findByRoleIdsAndCorpId("4", user.getCorpId());
                    if (vwUsers != null) {
                        builder.setErrorCode(ResponseCode.ALREADY_EXIST, "仓库管理员只能有一个");
                        return builder.getResponseEntity();
                    }
                }

                // 2.2 save user
                SysUser sysUser = sysUserRepository.save(user);
                logger.debug("用户新建成功，新用户名称为({})", sysUser.getUserName());

                // 3 save role relationship
                buildUserRoles(sysUser.getId(), roleIds);

                // 4 save user project relationship
                buildUserProjects(sysUser.getId(),user.getCorpId());

                builder.setErrorCode(ResponseCode.CREATE_SUCCEED);

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("用户新建失败");
            builder.setErrorCode(ResponseCode.CREATE_FAILED);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    protected List<AssoUserProject> buildUserProjects(Integer userId,  Integer corpId) {
        List<AssoUserProject> result = new ArrayList<>();
        // save project relationship
        try {
            List<AssoUserProject> entities = new ArrayList<>();
            if(corpId == 0){
                Iterable<SysProject> sysProjects = sysProjectRepository.findAll();
                for(SysProject temp : sysProjects){
                    entities.add(new AssoUserProject(userId,temp.getId()));
                }
            }else{
                SysProject sysProject =sysProjectRepository.findOneByIdAndRemoveTimeIsNull(corpId);
                entities.add(new AssoUserProject(userId, sysProject.getId()));
            }
            result.addAll(BuzUtil.toList(assoUserProjectRepository.save(entities)));
        } catch (Exception e) {
            logger.error("buildUserProjects {}", e.getMessage());
            throw e;
        }

        return result;
    }

    protected List<SysUserRole> buildUserRoles(Integer userId, List<Integer> rids) {
        List<SysUserRole> result = new ArrayList<>();

        try {
            List<SysUserRole> entities = new ArrayList<>();
            for (int rid : rids) {
                entities.add(new SysUserRole(userId, rid));
            }

            // save user roles
            result.addAll(BuzUtil.toList(sysUserRoleRepository.save(entities)));
        } catch (Exception e) {
            logger.error("buildUserRoles {}", e.getMessage());
            throw e;
        }

        return result;
    }


    /**
     * 用户编辑
     *
     * @param id
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    @Transactional
    ResponseEntity<RestBody<SysUser>> editUser(@PathVariable("id") Integer id,
                                               @RequestBody SysUser user,
                                               @RequestParam(value = "roleIds", required = false) List<Integer> roleIds) {

        logger.debug("editUser, id是({}),roleIds是({})", id, roleIds);

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Boolean isExists = sysUserRepository.exists(id);
            if (isExists) {
                if (roleIds.contains(4)) {
                    List<VwUser> vwUsers = vwUserRepository.findByRoleIdsAndCorpId("4", user.getCorpId());
                    if (vwUsers != null) {
                        builder.setErrorCode(ResponseCode.ALREADY_EXIST, "仓库管理员只能有一个");
                        return builder.getResponseEntity();
                    }
                }
                SysUser sysUsers = sysUserRepository.findOneById(id);
                user.setId(id);
                user.setLastUpdateBy(SecurityUtils.getLoginUserId());
                if (sysUsers.getUserPassword().equals(user.getUserPassword())) {
                    user.setUserPassword(user.getUserPassword());
                } else {
                    user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
                }
                SysUser sysUser = sysUserRepository.save(user);

                logger.debug("根据id({})编辑用户成功，用户名称为({})", id, sysUser.getUserName());

                // edit user roles
                editUserRoles(user.getId(), roleIds);

                // edit user project
                editUserProjects(user.getId(),user.getCorpId());

                builder.setErrorCode(ResponseCode.UPDATE_SUCCEED);
            } else {
                builder.setErrorCode(ResponseCode.NOT_EXIST, "用户不存在");
                logger.debug("根据id({})查询用户，数据不存在", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
            logger.debug("id({})对应的用户编辑失败", id);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    protected List<SysUserRole> editUserRoles(Integer userId, List<Integer> rids) {
        List<SysUserRole> result = new ArrayList<>();

        try {
            List<SysUserRole> entities = sysUserRoleRepository.findByUserId(userId);
            for (SysUserRole r : entities) {
                r.setRemoveTime(new Date());
                r.setLastUpdateBy(SecurityUtils.getLoginUserId());
            }
            sysUserRoleRepository.save(entities);

            // save new
            result.addAll(buildUserRoles(userId, rids));
        } catch (Exception e) {
            logger.error("editUserRoles {}", e.getMessage());
            throw e;
        }

        return result;
    }

    protected List<AssoUserProject> editUserProjects(Integer userId, Integer cropId) {
        List<AssoUserProject> result = new ArrayList<>();

        try {
            List<AssoUserProject> entities = assoUserProjectRepository.findByUserIdOrderByLastUpdateTimeDesc(userId);
            for (AssoUserProject r : entities) {
                r.setRemoveTime(new Date());
                r.setLastUpdateBy(SecurityUtils.getLoginUserId());
            }
            assoUserProjectRepository.save(entities);

            // save new
            result.addAll(buildUserProjects(userId,cropId ));
        } catch (Exception e) {
            logger.error("editUserProjects {}", e.getMessage());
            throw e;
        }

        return result;
    }



    /**
     * 用户--批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "user/deleteMore", method = RequestMethod.DELETE)
    @Transactional
    ResponseEntity<RestBody<SysUser>> deleteUserMore(@RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        int uid = SecurityUtils.getLoginUserId();
        try {
            logger.debug("user/deleteMore：ids为({})", ids);

            if (ids.isEmpty()) {
                logger.debug("ids({})参数缺失", ids);
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT.getValue()) {
                List<SysUser> userList = sysUserRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                for (SysUser user : userList) {
                    user.setRemoveTime(new Date());
                    user.setLastUpdateBy(uid);
                }
                builder.setResultEntity(sysUserRepository.save(userList), ResponseCode.DELETE_SUCCEED);
                logger.debug("ids({})对应的用户批量删除成功", ids);
            } else {
                logger.debug("批量删除的数量必须在({})以内", BusinessRefData.BATCH_COUNT.getValue());

                builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("批量删除的数量必须在30条以内");
            builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询所有用户--web+app
     *
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> findAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        try {
            logger.debug("查询用户列表");
            List<SysUser> users = sysUserRepository.findByRemoveTimeIsNull();

            logger.debug("查询设备类型成功,数据量为:{}", users.size());
            builder.setResultEntity(users, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();

        } catch (Exception e) {
            logger.error("findAll {}", e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 用户查重
     *
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "user/findByUserAccount", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> findByUserAccount(@Param("userAccount") String userAccount) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("user/findByUserAccount, userAccount是({})", userAccount);

            SysUser user = sysUserRepository.findOneByUserAccount(userAccount);
            if (user != null) {
                logger.debug("根据userAccount：({})查询SysUsers,数据已存在 ", userAccount);
                builder.setResultEntity(user, ResponseCode.ALREADY_EXIST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 用户密码修改
     *
     * @param userPassword
     * @return
     */
    @RequestMapping(value = "user/editPassword", method = RequestMethod.PUT)
    ResponseEntity<RestBody<SysUser>> editPassword(@RequestParam(value = "userPassword", required = false) String userPassword) {

        logger.debug("users/editPassword,userPassword为({})", userPassword);

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int uid = SecurityUtils.getLoginUserId();
        try {
            if (userPassword == null) {
                logger.debug("参数缺失，请求失败");
                builder.setErrorCode(ResponseCode.FAILED);
            }

            SysUser user = sysUserRepository.findOneById(uid);
            if (user != null) {
                user.setUserPassword(new BCryptPasswordEncoder().encode(userPassword));
                user.setLastUpdateBy(uid);
            }

            SysUser sysUser = sysUserRepository.save(user);
            builder.setResultEntity(sysUser, ResponseCode.UPDATE_SUCCEED);

            logger.debug("根据id({}修改用户密码成功，用户名称为({})", uid, user.getUserName());

        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user/findByUserId", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> getByUserId(@Param("userId") Integer userId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            logger.debug("users/findByUserId, userId是({})", userId);

            if (userId == null) {
                logger.debug("参数缺失，请求失败");
                builder.setErrorCode(ResponseCode.FAILED);
            }

            SysUser user = sysUserRepository.findOneById(userId);
            if (user != null) {
                logger.debug("根据userId：({})查询SysUsers,数据已存在 ", userId);
                builder.setResultEntity(user, ResponseCode.ALREADY_EXIST);
            } else {
                logger.debug("根据userId：({})查询SysUsers,数据不存在 ", userId);
                builder.setErrorCode(ResponseCode.NOT_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

}
