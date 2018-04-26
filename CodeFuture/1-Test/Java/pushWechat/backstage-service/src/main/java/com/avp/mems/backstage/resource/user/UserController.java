package com.avp.mems.backstage.resource.user;

import com.avp.mems.backstage.entity.push.PushInfoWechat;
import com.avp.mems.backstage.entity.user.*;
import com.avp.mems.backstage.repositories.push.PushInfoWechatRepository;
import com.avp.mems.backstage.repositories.user.UserProjectRepository;
import com.avp.mems.backstage.repositories.user.UserRepository;
import com.avp.mems.backstage.repositories.user.UserRoleRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amber Wang on 2017-05-29 下午 05:32.
 */
@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private PushInfoWechatRepository pushInfoWechatRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    @Qualifier("entityManagerFactoryUser")
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "users/search/findAssignEmployee", method = RequestMethod.GET)
    ResponseEntity findAssignEmployeeByProfessionId() {

        logger.debug("url:users/search/findAssignEmployee----------");

        ResponseBuilder builder = ResponseBuilder.createBuilder();

        List<UserProject> userProjects = userProjectRepository.findUserProjectByProjectId(6);
        List<String> userNames = new ArrayList<>();

        for (UserProject userProject : userProjects) {
            userNames.add(userProject.getUserProjectPK().getUserName());
        }

        List<User> users = userRepository.findByUsernameIn(userNames);

        List<User> users_res = new ArrayList<>();

        for (User user : users) {
            for (Role role : user.getRoleCollection()) {
                if (role.getName().equals("ROLE_ASSIGN")){
                    users_res.add(user);
                    break;
                }
            }
        }
//
//
//        List<String> usernames =
//        List<User> userRet = new ArrayList<>();
//        //根据专业获取用户
//        userRet = userRepository.findByRoleName("ROLE_ASSIGN");

        builder.setResultEntity(users_res, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    /**
     * 工单-查看微信是否绑定
     * 逻辑：用手机号和微信ID（并不是微信ID，只是微信返回的唯一标识，不同的设备不同）去数据库中查询数据校验。
     * 如果手机号存在，但是微信ID并不相同，则更新微信ID字段
     * 如果手机号不存在，则新建个账号，同时保存微信ID字段，并建立用户和项目关联关系
     *
     * @param weChatId
     * @param phoneNumber
     * @return
     */
    @RequestMapping(value = "userWechats/search/checkWeChat", method = RequestMethod.GET)
    ResponseEntity checkWeChat(@Param("weChatId") String weChatId, @Param("phoneNumber") String phoneNumber) {

        logger.debug("url:userWechats/search/checkWeChat----------,weChatId:{},phoneNumber:{}", weChatId, phoneNumber);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        boolean isUserExist = false;
        Map<String, Object> map = new HashMap<>();

        if (Validator.isNotNull(weChatId) && Validator.isNotNull(phoneNumber)) {

            User user = userRepository.findByPhoneNumber(phoneNumber);

            if (user == null) {
                isUserExist = false;
            } else if (Validator.equals(user.getWechatid(), weChatId)) {
                //用户和企业微信已经绑定了
                isUserExist = true;
            } else {
                //用户还未和企业微信绑定
                isUserExist = false;
            }
            map.put("isUserExist", isUserExist);
            builder.setResultEntity(map, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "userWechats/search/setProfessions", method = RequestMethod.GET)
    @Transactional
    ResponseEntity setProfessions(
            @RequestParam("phoneNumber") String phoneNumber,
//            @RequestParam("professionIds") List<Integer> professionIds,
            @RequestParam("userName") String userName,
            @RequestParam("weChatId") String weChatId,
//            @RequestParam("password") String password,
            @RequestParam("projectId") Integer projectId,
            @RequestParam("headimgurl") String headimgurl) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        logger.debug("url:userWechats/search/checkWeChat----------,weChatId:{},phoneNumber:{}", weChatId, phoneNumber);
        if (
                Validator.isNotNull(phoneNumber)
//                        && Validator.isNotNull(professionIds)
                        && Validator.isNotNull(weChatId)
//                        && Validator.isNotNull(password)
                        && Validator.isNotNull(projectId)
                ) {
            User user = userRepository.findByPhoneNumber(phoneNumber);

            String username;

            if (user == null) {
//                List<UserProfession> userProfessionList = new ArrayList<>();
                //1.用户不存在,新建用户绑定企业微信
                logger.debug("用户:{}不存在,新建用户绑定企业微信", userName);
                user = new User();
                user.setPassword(new BCryptPasswordEncoder().encode("123456"));
                user.setPhoneNumber(phoneNumber);
                user.setFirstName(" ");
//                user.setPassword(new BCryptPasswordEncoder().encode(password));

                user.setLastName(userName);
                user.setUsername(phoneNumber);
                user.setWechatid(weChatId);
                user.setEmail(phoneNumber + "@weChat.com");
                user.setHeadimgurl(headimgurl);
                username = phoneNumber;

                user = userRepository.save(user);

                UserRole userRole = new UserRole();
                UserRolePK userRolePK = new UserRolePK();

                userRolePK.setRoleName("ROLE_REPORT");
                userRolePK.setUserName(username);

                userRole.setUserRolePK(userRolePK);

                userRoleRepository.save(userRole);

                //4.关联用户和项目
                // 后续需要判断数据是否已经存在
                UserProjectPK userProjectPK = new UserProjectPK();
                userProjectPK.setUserName(username);
                userProjectPK.setProjectName(projectId);
                UserProject userProject = new UserProject();
                userProject.setUserProjectPK(userProjectPK);

                userProjectRepository.save(userProject);


                //2.关联用户和专业
//                for (Integer professionId : professionIds) {
//                    UserProfession userProfession = new UserProfession();
//                    userProfession.setUserName(phoneNumber);
//                    userProfession.setProfessionId(professionId);
//
//                    userProfessionList.add(userProfession);
//                }
//                userProfessionRepository.save(userProfessionList);

            } else {
                //更新用户的微信ID字段
                logger.debug("用户:{}已经存在,更新用户的微信ID字段", userName);
                username = user.getUsername();

//                user.setPassword(new BCryptPasswordEncoder().encode(password));
                user.setWechatid(weChatId);
                user = userRepository.save(user);

            }

            //3.PushInfoWechat更新推送表
            PushInfoWechat pushInfoWechat = pushInfoWechatRepository.findByUsername(username);
            if (pushInfoWechat == null) {
                pushInfoWechat = new PushInfoWechat();
                pushInfoWechat.setUsername(username);
            }
            pushInfoWechat.setWechatid(weChatId);

            pushInfoWechatRepository.save(pushInfoWechat);

            logger.debug("注册完成。。。。");

            builder.setResultEntity(user, ResponseCode.CREATE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }
}
