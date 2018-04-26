/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.user;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.AssoUserProject;
import com.avp.mem.njpb.entity.system.SysProject;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.system.SysUserRole;
import com.avp.mem.njpb.entity.view.VwUser;
import com.avp.mem.njpb.entity.view.VwUserRole;
import com.avp.mem.njpb.entity.view.VwUserRoleResource;
import com.avp.mem.njpb.repository.basic.ObjCorporationRepository;
import com.avp.mem.njpb.repository.sys.AssoUserProjectRepository;
import com.avp.mem.njpb.repository.sys.SysProjectRepository;
import com.avp.mem.njpb.repository.sys.SysRoleRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.sys.SysUserRoleRepository;
import com.avp.mem.njpb.repository.sys.VwRoleResourceRepository;
import com.avp.mem.njpb.repository.sys.VwUserRepository;
import com.avp.mem.njpb.repository.sys.VwUserRoleRepository;
import com.avp.mem.njpb.repository.sys.VwUserRoleResourceRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.BuzUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

    @Autowired
    SysRoleRepository sysRoleRepository;

    @Autowired
    IdentityService identityService;

    @Autowired
    VwUserRoleRepository vwUserRoleRepository;

    @Autowired
    VwRoleResourceRepository vwRoleResourceRepository;

    @Autowired
    VwUserRoleResourceRepository vwUserRoleResourceRepository;

    @Autowired
    ObjCorporationRepository objCorporationRepository;

    /**
     * 用户新建(账号、用户名、头衔都要确认)
     *
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "users", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<RestBody<SysUser>> buildUser(@RequestBody SysUser user,
                                                       @RequestParam(value = "roleIds", required = false) List<Integer> roleIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String userName = user.getUserName();
            String userAccount = user.getUserAccount();
            Integer corpId = user.getCorpId();

            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(corpId)) {
                corpId = sysUser.getCorpId();
            }
            LOGGER.debug("userName:{},userAccount:{}", userName, userAccount);

            if (Validator.isNull(userName) || Validator.isNull(userAccount)) {
                LOGGER.debug("用户名、账号均不能为空");
                builder.setResponseCode(ResponseCode.FAILED, "用户名、账号不能为空");
                return builder.getResponseEntity();
            }

//            VwUser sysUser = vwUserRepository.findOne(uid);
//            if(sysUser.getCorpId()!= BusinessRefData.CROP_HEAD.getValue()&&sysUser.getCorpId()!= sys.getCorpId()){
//                LOGGER.debug("你没有权限添加其他公司的成员");
//                builder.setResponseCode(ResponseCode.FAILED,"你没有权限添加其他公司的成员");
//                return builder.getResponseEntity();
//            }

            if (roleIds.contains(BusinessRefData.ROLE_STOCK_KEEPER)) {
                LOGGER.debug("前端传入的角色列表有仓库管理员这个角色");
                List<VwUserRole> vwUserRoles = vwUserRoleRepository.findByRoleIdAndCorpId(BusinessRefData.ROLE_STOCK_KEEPER, corpId);
                if (vwUserRoles.size() > 0) {
                    LOGGER.debug("当前公司：{}下已经存在仓库管理员", corpId);
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "当前公司下已经有仓库管理员了!");
                    return builder.getResponseEntity();
                }
            }

            SysUser sysUserAccountDb = sysUserRepository.findOneByUserAccount(userAccount);
            if (Validator.isNotNull((sysUserAccountDb))) {
                LOGGER.debug("当前账号：{}数据库中已经存在", userAccount);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "账号:" + userAccount + "已经存在!");
                return builder.getResponseEntity();
            }
            LOGGER.debug("前端传入的用户账号:{}在数据库不存在", userAccount);

            SysUser sysUserNameDb = sysUserRepository.findOneByUserName(userName);
            if (Validator.isNotNull((sysUserNameDb))) {
                LOGGER.debug("当前用户名称：{}数据库中已经存在", userName);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "用户名称:" + userName + "已经存在!");
                return builder.getResponseEntity();
            }
            LOGGER.debug("前端传入的用户名称:{}在数据库不存在", userName);

            // 2.1 encrypt sys password in jpa saving event handler
            user.setUserPassword(new BCryptPasswordEncoder().encode(BusinessRefData.DEFAULT_PASSWORD));

            // 2.2 save sys
            SysUser sysUsers = sysUserRepository.save(user);
            LOGGER.debug("用户新建成功，新用户名称为({})", sysUsers.getUserName());

            // 3 save role relationship
            buildUserRoles(sysUsers.getId(), roleIds);

            // 4 save sys project relationship
            buildUserProjects(sysUsers.getId(), corpId);

            builder.setResultEntity(sysUsers, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("用户新建失败");
            //builder.setResponseCode(ResponseCode.CREATE_FAILED);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    protected void buildUserProjects(Integer userId, Integer corpId) {
        List<AssoUserProject> result = new ArrayList<>();
        // save project relationship
        List<AssoUserProject> assoUserProjects = new ArrayList<>();

        LOGGER.debug("判断前端传入的公司是否是总公司");
        if (MagicNumber.ONE.equals(corpId)) {
            LOGGER.debug("选择的公司为总公司,为当前用户赋值所有公司项目");
            sysProjectRepository.findAll().forEach(sysProject -> {
                assoUserProjects.add(new AssoUserProject(userId, sysProject.getId()));
            });
        } else {
            LOGGER.debug("当前用户公司id为：{}", corpId);
            SysProject sysProject = sysProjectRepository.findOneById(corpId);
            assoUserProjects.add(new AssoUserProject(userId, sysProject.getId()));
        }
        assoUserProjectRepository.save(assoUserProjects);
    }

    protected List<SysUserRole> buildUserRoles(Integer userId, List<Integer> rids) {
        List<SysUserRole> result = new ArrayList<>();

        try {
            List<SysUserRole> entities = new ArrayList<>();
            for (int rid : rids) {
                entities.add(new SysUserRole(userId, rid));
            }
            // save sys roles
            result.addAll(BuzUtil.toList(sysUserRoleRepository.save(entities)));
        } catch (Exception e) {
            LOGGER.error("buildUserRoles {}", e.getMessage());
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
    @RequestMapping(value = "users/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<RestBody<SysUser>> editUser(@PathVariable("id") Integer id,
                                                      @RequestBody SysUser user,
                                                      @RequestParam(value = "roleIds", required = false) List<Integer> roleIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String userName = user.getUserName();
            String userAccount = user.getUserAccount();
            Integer corpId = user.getCorpId();

            LOGGER.debug("userName:{},userAccount:{}", userName, userAccount);

            if (Validator.isNull(userName) || Validator.isNull(userAccount)) {
                LOGGER.debug("用户名、账号均不能为空");
                builder.setResponseCode(ResponseCode.FAILED, "用户名、账号不能为空");
                return builder.getResponseEntity();
            }

            LOGGER.debug("editUser, id是({}),roleIds是({})", id, roleIds);
            SysUser sysUser = sysUserRepository.findOne(id);
            if (sysUser != null) {
                if (roleIds.contains(BusinessRefData.ROLE_STOCK_KEEPER)) {
                    List<VwUserRole> vwUserRoles = vwUserRoleRepository.findByRoleIdAndCorpIdAndUserIdNot(BusinessRefData.ROLE_STOCK_KEEPER, corpId, id);

                    LOGGER.debug("当前公司下的仓库管理员人数为:{}", vwUserRoles.size());
                    if (vwUserRoles.size() > 0) {
                        LOGGER.debug("当前公司：{}下已经存在仓库管理员", sysUser.getCorpId());
                        builder.setResponseCode(ResponseCode.ALREADY_EXIST, "仓库管理员只能有一个");
                        return builder.getResponseEntity();
                    }
                }

                user.setId(id);
                user.setUserPassword(sysUser.getUserPassword());

                SysUser sysUserAccountDb = sysUserRepository.findOneByUserAccountAndIdNot(userAccount, id);
                if (Validator.isNotNull((sysUserAccountDb))) {
                    LOGGER.debug("当前账号：{}数据库中已经存在", userAccount);
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "账号:" + userAccount + "已经存在!");
                    return builder.getResponseEntity();
                }
                LOGGER.debug("前端传入的用户账号:{}在数据库不存在", userAccount);

                SysUser sysUserNameDb = sysUserRepository.findOneByUserNameAndIdNot(userName, id);
                if (Validator.isNotNull((sysUserNameDb))) {
                    LOGGER.debug("当前用户名称：{}数据库中已经存在", userName);
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "用户名称:" + userName + "已经存在!");
                    return builder.getResponseEntity();
                }
                LOGGER.debug("前端传入的用户名称:{}在数据库不存在", userName);

                sysUser = sysUserRepository.save(user);

                LOGGER.debug("根据id({})编辑用户成功，用户名称为({})", id, sysUser.getUserName());

                // edit sys roles
                editUserRoles(id, roleIds);

                // edit sys project
                editUserProjects(id, user.getCorpId());

                builder.setResultEntity(sysUser, ResponseCode.UPDATE_SUCCEED);
            } else {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "用户不存在");
                LOGGER.debug("根据id({})查询用户，数据不存在", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
            LOGGER.debug("id({})对应的用户编辑失败", id);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    protected List<SysUserRole> editUserRoles(Integer userId, List<Integer> rids) {
        List<SysUserRole> result = new ArrayList<>();

        try {
            List<SysUserRole> entities = sysUserRoleRepository.findByUserId(userId);

            LOGGER.debug("editUserRoles,用户id :{},rids 的数量为:{},当前账号角色有{}种", userId, rids.size(), entities.size());
            for (SysUserRole r : entities) {
                r.setRemoveTime(new Date());
                r.setLastUpdateBy(SecurityUtils.getLoginUserId());
            }
            sysUserRoleRepository.save(entities);

            // save new
            result.addAll(buildUserRoles(userId, rids));
        } catch (Exception e) {
            LOGGER.error("editUserRoles {}", e.getMessage());
            throw e;
        }

        return result;
    }

    protected void editUserProjects(Integer userId, Integer cropId) {
        List<AssoUserProject> entities = assoUserProjectRepository.findByUserIdOrderByLastUpdateTimeDesc(userId);

        LOGGER.debug("当前用户：{}的项目数量为：{}", userId, entities.size());
        for (AssoUserProject r : entities) {
            r.setRemoveTime(new Date());
        }
        assoUserProjectRepository.save(entities);

        // save new
        buildUserProjects(userId, cropId);
    }


    /**
     * 用户--批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "users/deleteMore", method = RequestMethod.DELETE)
    @Transactional
    ResponseEntity<RestBody<SysUser>> deleteUserMore(@RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            LOGGER.debug("sys/deleteMore：ids为({})", ids);

            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT) {
                List<SysUser> userList = sysUserRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                LOGGER.debug("批量删除数据量为：", userList.size());
                for (SysUser user : userList) {
                    user.setRemoveTime(new Date());
                    user.setLastUpdateBy(uid);
                }
                sysUserRepository.save(userList);
                builder.setResponseCode(ResponseCode.DELETE_SUCCEED);
                LOGGER.debug("ids({})对应的用户批量删除成功", ids);
            } else {
                LOGGER.debug("批量删除的数量必须在({})以内", BusinessRefData.BATCH_COUNT);

                builder.setResponseCode(ResponseCode.FAILED, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("批量删除的数量必须在30条以内");
            builder.setResponseCode(ResponseCode.FAILED, "批量删除的数量必须在30条以内");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 用户账号查重
     *
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "users/findByUserAccount", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> findByUserAccount(@Param("userAccount") String userAccount) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("sys/findByUserAccount, userAccount是({})", userAccount);
            SysUser user = sysUserRepository.findOneByUserAccount(userAccount);
            if (user != null) {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据已存在 ", userAccount);
                builder.setResultEntity(user, ResponseCode.ALREADY_EXIST);
            } else {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据不存在 ", userAccount);
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 用户名称查重
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "users/findByUserName", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> findByUserName(@Param("userName") String userName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("sys/findByUserAccount, userName({})", userName);
            SysUser user = sysUserRepository.findOneByUserName(userName);
            if (user != null) {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据已存在 ", userName);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            } else {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据不存在 ", userName);
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 用户名称校验-web端编辑
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "users/{userName}/verify/{uid}", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysUser>> verifyUserName(@PathVariable("userName") String userName, @PathVariable("uid") Integer uid) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("sys/findByUserAccount, userName({}),id：（{}）", userName);

            SysUser user = sysUserRepository.findOneByUserNameAndIdNot(userName, uid);
            if (user != null) {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据已存在 ", userName);
                builder.setResultEntity(user, ResponseCode.ALREADY_EXIST);
            } else {
                LOGGER.debug("根据userAccount：({})查询SysUsers,数据不存在 ", userName);
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 用户密码重置-web端接口
     *
     * @return
     */
    @RequestMapping(value = "users/reSetPassword", method = RequestMethod.PUT)
    ResponseEntity<RestBody<SysUser>> rootPassword(@RequestParam(value = "ids") List<Integer> ids) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //Integer uid = SecurityUtils.getLoginUserId();

        try {
            LOGGER.debug("users/reSetPassword：ids为({})", ids);

            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT) {
                List<SysUser> userList = sysUserRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                LOGGER.debug("重置密码数据量为：", userList.size());
                for (SysUser user : userList) {
                    user.setUserPassword(new BCryptPasswordEncoder().encode(BusinessRefData.DEFAULT_PASSWORD));

                }
                sysUserRepository.save(userList);
                builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);
                LOGGER.debug("ids({})对应的用户重置密码成功", ids);
            } else {
                LOGGER.debug("重置密码的数量必须在({})以内", BusinessRefData.BATCH_COUNT);

                builder.setResponseCode(ResponseCode.FAILED, "重置密码的数量必须在30条以内");
            }


//            SysUser user = sysUserRepository.findOneById(uid);
//            if (user != null) {
//                user.setUserPassword(new BCryptPasswordEncoder().encode(BusinessRefData.DEFAULT_PASSWORD));
//                SysUser sysUser = sysUserRepository.save(user);
//                builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);
//                LOGGER.debug("根据id({}修改用户密码成功，用户名称为({}),初始化密码为：{}", uid, user.getUserName(), BusinessRefData.DEFAULT_PASSWORD);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据公司id查询用户
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "users/findByCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUser>> getByCorpId(Integer corpId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwUser> users = new ArrayList<>();
        try {
            LOGGER.debug("users/findByCorpId, corpId是({})", corpId);

            if (Validator.isNotNull(corpId)) {
                users = vwUserRepository.findByCorpId(corpId);
                //users = sysUserRepository.findByCorpId(corpId);
            } else {
                users = vwUserRepository.findAll();
            }

            LOGGER.debug("公司{}，人数{}", corpId, users.size());
            builder.setResultEntity(users, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 校验仓库管理员密码
     *
     * @param stockPassword
     * @return
     */
    @RequestMapping(value = "checkStorekeeperPassword/findByStockPassword", method = RequestMethod.GET)
    ResponseEntity checkStorekeeperPassword(@RequestParam("stockPassword") String stockPassword) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer uid = SecurityUtils.getLoginUserId();
        SysUser sysUser = sysUserRepository.findOne(uid);
        try {
            if (stockPassword == null) {
                LOGGER.debug("参数为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            List<VwUserRole> vwUserRoles = vwUserRoleRepository.findByRoleIdAndCorpId(BusinessRefData.ROLE_STOCK_KEEPER, sysUser.getCorpId());
            if (new BCryptPasswordEncoder().encode(stockPassword).equals(vwUserRoles.get(0).getUserPassword())) {
                LOGGER.debug("仓库管理员密码校验成功");
                builder.setResponseCode(ResponseCode.OK, "true");
            } else {
                LOGGER.debug("仓库管理员密码校验失败");
                builder.setResponseCode(ResponseCode.FAILED, "仓库管理员密码校验失败");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "users/findByUid", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUser>> findUser(@RequestParam("uid") Integer uid) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //Integer uid = SecurityUtils.getLoginUserId();
        try {
            LOGGER.debug("uid:{}", uid);
            VwUser sysUser = vwUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("用户不存在");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "用户不存在");
            } else {
                builder.setResultEntity(sysUser, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @RequestMapping(value = "users/findCurrentUserInfo", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUser>> findUserInfo() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer uid = SecurityUtils.getLoginUserId();
        try {
            VwUser sysUser = vwUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("用户不存在");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "用户不存在");
            } else {
                builder.setResultEntity(sysUser, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 修改密码-APP端接口
     *
     * @return
     */
    @RequestMapping(value = "users/changePassword", method = RequestMethod.PUT)
    ResponseEntity changePassword(@RequestParam String newPassword) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer uid = SecurityUtils.getLoginUserId();
        try {
            if (Validator.isNull(newPassword)) {
                LOGGER.debug("新密码不能为空");
                builder.setResponseCode(ResponseCode.FAILED, "新密码不能为空");
                return builder.getResponseEntity();
            }

            SysUser user = sysUserRepository.findOneById(uid);

            if (Validator.isNull(user)) {
                LOGGER.debug("用户不存在,id:{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "用户不存在");
                return builder.getResponseEntity();
            }

            LOGGER.debug("新密码：{}", newPassword);
            user.setUserPassword(new BCryptPasswordEncoder().encode(newPassword));
            user = sysUserRepository.save(user);
            builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);
            LOGGER.debug("根据id({}修改用户密码成功，用户名称为({}),新密码为：{}", uid, user.getUserName(), newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 根据公司id和角色id查询维修人
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "users/findByRoleIdAndCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserRole>> findByRoleIdAndCorpId(@RequestParam(required = false) Integer roleId, @RequestParam(required = false) Integer corpId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwUserRole> vwUserRoles = new ArrayList<>();
        try {
            LOGGER.debug("users/findByCorpIdAndRoleId, corpId是({}),roleId是（{}）", corpId, roleId);

            if (roleId == null) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (Validator.isNotNull(corpId)) {
                if (corpId == 1) {
                    vwUserRoles = vwUserRoleRepository.findByRoleId(roleId);
                } else {
                    vwUserRoles = vwUserRoleRepository.findByRoleIdAndCorpId(roleId, corpId);
                }
            } else {
                vwUserRoles = vwUserRoleRepository.findByRoleId(roleId);
            }
            LOGGER.debug("根据公司：{}和角色：{}查询用户成功，数据量为：{}", corpId, roleId, vwUserRoles.size());
            builder.setResultEntity(vwUserRoles, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询用户权限
     *
     * @return
     */
    @RequestMapping(value = "users/findByUserId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserRoleResource>> findByUserId() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (uid == null) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.FAILED);
                return builder.getResponseEntity();
            }

            List<VwUserRoleResource> vwUserRoleResources = vwUserRoleResourceRepository.findByUserIdAndResourceType(uid, BusinessRefData.RESOURCE_TYPE);
            if (Validator.isNull(vwUserRoleResources)) {
                LOGGER.debug("此用户没有资源权限");
                builder.setResponseCode(ResponseCode.FAILED, "此用户没有资源权限");
                return builder.getResponseEntity();
            }
            builder.setResultEntity(vwUserRoleResources, ResponseCode.OK);

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
