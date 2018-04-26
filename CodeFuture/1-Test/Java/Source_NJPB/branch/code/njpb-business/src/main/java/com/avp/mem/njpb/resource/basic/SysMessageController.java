/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjFile;
import com.avp.mem.njpb.entity.basic.SysMessageSendDetail;
import com.avp.mem.njpb.entity.basic.VwMessageSendDetail;
import com.avp.mem.njpb.entity.system.SysMessage;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.repository.basic.ObjFileRepository;
import com.avp.mem.njpb.repository.basic.SysMessageRepository;
import com.avp.mem.njpb.repository.basic.SysMessageSendDetailRepository;
import com.avp.mem.njpb.repository.basic.SysParamRepository;
import com.avp.mem.njpb.repository.basic.VwMessageSendDetailRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.service.push.PushClientService;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
@RestController
public class SysMessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysMessageController.class);

    @Autowired
    SysMessageRepository sysMessageRepository;

    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    PushClientService pushClientService;

    @Autowired
    ObjFileRepository objFileRepository;

    @Autowired
    VwMessageSendDetailRepository vwMessageSendDetailRepository;

    @Autowired
    SysMessageSendDetailRepository sysMessageSendDetailRepository;

    /**
     * 消息查询
     *
     * @return
     */
    @RequestMapping(value = "sysMessages", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysMessage>> findAll() {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<SysMessage> sysMessages = sysMessageRepository.findAll();
            LOGGER.debug("查询系统消息成功,数据量为：{}", sysMessages.size());
            builder.setResultEntity(sysMessages, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 消息查询 --APP
     *
     * @return
     */
    @RequestMapping(value = "sysMessages/findByUid", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysMessage>> findByUid() {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();


            List<SysMessage> sysMessages = sysMessageRepository.findAll();
            LOGGER.debug("查询系统消息成功,数据量为：{}", sysMessages.size());
            builder.setResultEntity(sysMessages, ResponseCode.RETRIEVE_SUCCEED);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息-新建-保存
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/saveMessage", method = RequestMethod.POST)
    ResponseEntity saveObjEstateType(@RequestBody SysMessage sysMessage, @RequestParam(value = "receiveUserIds", required = false) List<Integer> receiveUserIds) {
        LOGGER.debug("saveSysMessages----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);

            if (Validator.isNotNull(sysMessage)) {
                sysMessage.setMessageAuthor(sysUser.getUserName());
                sysMessage.setStatus(BusinessRefData.MESSAGE_SAVE);
                sysMessage = sysMessageRepository.save(sysMessage);
                builder.setResultEntity(sysMessage, ResponseCode.OK);
            } else {
                LOGGER.debug("SysMessage----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息-编辑-保存
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/saveMessageEdit", method = RequestMethod.PUT)
    ResponseEntity saveMessageEdit(@RequestBody SysMessage sysMessage, @RequestParam(value = "receiveUserIds", required = false) List<Integer> receiveUserIds) {
        LOGGER.debug("saveSysMessages----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            SysMessage sysMessage1 = sysMessageRepository.findOne(sysMessage.getId());
            if (Validator.isNotNull(sysMessage1)) {
                sysMessage1.setStatus(BusinessRefData.MESSAGE_SAVE);
                sysMessage1.setMessageText(sysMessage.getMessageText());
                sysMessage1.setMessageTitle(sysMessage.getMessageTitle());
                sysMessage1.setMessageAuthor(sysUser.getUserName());
                if (Validator.isNotNull(sysMessage.getMessageFile1Url())) {
                    sysMessage1.setMessageFile1Url(sysMessage.getMessageFile1Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile2Url())) {
                    sysMessage1.setMessageFile2Url(sysMessage.getMessageFile2Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile3Url())) {
                    sysMessage1.setMessageFile3Url(sysMessage.getMessageFile3Url());
                }
                sysMessage = sysMessageRepository.save(sysMessage1);

                builder.setResultEntity(sysMessage, ResponseCode.OK);
            } else {
                LOGGER.debug("SysMessage----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息-新建-下发
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/sendMessage", method = RequestMethod.POST)
    ResponseEntity sendObjEstateType(@RequestBody SysMessage sysMessage, @RequestParam(value = "receiveUserIds", required = false) List<Integer> receiveUserIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("消息发送，接收者人数：{}", receiveUserIds.size());
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            sysMessage.setMessageAuthor(sysUser.getUserName());
            sysMessage.setStatus(BusinessRefData.MESSAGE_FINISH);
            sysMessage = sysMessageRepository.save(sysMessage);
            LOGGER.debug("消息准备下发，id：{}", sysMessage.getId());
            List<SysMessageSendDetail> sysMessageSendDetails = new ArrayList<>();
            for (Integer temp : receiveUserIds) {
                SysMessageSendDetail sysMessageSendDetail = new SysMessageSendDetail();
                sysMessageSendDetail.setSysMessageId(sysMessage.getId());
                sysMessageSendDetail.setSendUserId(uid);
                sysMessageSendDetail.setReceiveUserId(temp);
                sysMessageSendDetails.add(sysMessageSendDetail);
            }
            sysMessageSendDetails = sysMessageSendDetailRepository.save(sysMessageSendDetails);


            LOGGER.debug("消息发送人员数量：{}", sysMessageSendDetails.size());
            builder.setResponseCode(ResponseCode.OK, "消息发送成功");

            String content = "公司最新通告";
            pushClientService.pushCommonMsg(content, receiveUserIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息-编辑-下发
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/sendMessageEdit", method = RequestMethod.PUT)
    ResponseEntity sendMessageEdit(@RequestBody SysMessage sysMessage, @RequestParam(value = "receiveUserIds", required = false) List<Integer> receiveUserIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("消息发送，接收者人数：{}", receiveUserIds.size());
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            SysMessage sysMessage1 = sysMessageRepository.findOne(sysMessage.getId());
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            if (Validator.isNotNull(sysMessage1)) {
                sysMessage1.setStatus(BusinessRefData.MESSAGE_FINISH);
                sysMessage1.setMessageText(sysMessage.getMessageText());
                sysMessage1.setMessageTitle(sysMessage.getMessageTitle());
                sysMessage1.setMessageAuthor(sysUser.getUserName());
                if (Validator.isNotNull(sysMessage.getMessageFile1Url())) {
                    sysMessage1.setMessageFile1Url(sysMessage.getMessageFile1Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile2Url())) {
                    sysMessage1.setMessageFile2Url(sysMessage.getMessageFile2Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile3Url())) {
                    sysMessage1.setMessageFile3Url(sysMessage.getMessageFile3Url());
                }
                sysMessage = sysMessageRepository.save(sysMessage1);
            }
            LOGGER.debug("消息准备下发，id：{}", sysMessage.getId());
            List<SysMessageSendDetail> sysMessageSendDetails = new ArrayList<>();
            for (Integer temp : receiveUserIds) {
                SysMessageSendDetail sysMessageSendDetail = new SysMessageSendDetail();
                sysMessageSendDetail.setSysMessageId(sysMessage.getId());
                sysMessageSendDetail.setSendUserId(uid);
                sysMessageSendDetail.setReceiveUserId(temp);
                sysMessageSendDetails.add(sysMessageSendDetail);
            }
            sysMessageSendDetails = sysMessageSendDetailRepository.save(sysMessageSendDetails);


            LOGGER.debug("消息发送人员数量：{}", sysMessageSendDetails.size());
            builder.setResponseCode(ResponseCode.OK, "消息发送成功");

            String content = "公司最新通告";
            pushClientService.pushCommonMsg(content, receiveUserIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的消息记录-APP
     *
     * @param firstOperationTime
     * @param page
     * @return
     */
    @RequestMapping(value = "sysMessages/findByOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwMessageSendDetail>> findByOperationTimeLessThan(Date firstOperationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //firstOperationTime = new Date();
        LOGGER.debug("消息记录查询, firstOperationTime为({}),p为({})", firstOperationTime, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("消息记录查询上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwMessageSendDetail> list = vwMessageSendDetailRepository.findByReceiveUserIdAndLastUpdateTimeLessThan(uid, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询消息记录成功，结果为空");
            } else {
                LOGGER.debug("查询消息记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询我的消息记录-APP
     *
     * @param firstOperationTime
     * @return
     */
    @RequestMapping(value = "sysMessages/findByOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwMessageSendDetail>> findByOperationTimeGreaterThan(Date firstOperationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("消息记录查询, firstOperationTime为({}),p为({})", firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("消息记录查询下拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwMessageSendDetail> list = vwMessageSendDetailRepository.findByReceiveUserIdAndLastUpdateTimeGreaterThan(uid, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询消息记录成功，结果为空");
            } else {
                LOGGER.debug("查询消息记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据消息id查询消息--APP
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "sysMessages/findMessageByMessageId", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysMessage>> findMessageByMessageId(Integer messageId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (messageId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            SysMessage sysMessage = sysMessageRepository.findOne(messageId);
            if (Validator.isNull(sysMessage)) {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "消息记录不存在");
            } else {
                LOGGER.debug("查询消息");
                builder.setResultEntity(sysMessage, ResponseCode.RETRIEVE_SUCCEED);
            }

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 根据消息id查询用户
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "sysMessages/findUsersByMessageId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwMessageSendDetail>> findUsersByMessageId(Integer messageId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (messageId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            List<VwMessageSendDetail> list = vwMessageSendDetailRepository.findBySysMessageId(messageId);
            LOGGER.debug("查询消息已经发送的用户成功，数据量为:({})", list.size());
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据消息id查询图片
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "sysMessages/findByMessageId", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjFile>> findByMessageId(Integer messageId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (messageId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            SysMessage sysMessage = sysMessageRepository.findOne(messageId);
            if (Validator.isNull(sysMessage)) {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "消息不存在");
            }
            List<String> fileIds = new ArrayList<>();
            if (Validator.isNotNull(sysMessage.getMessageFile1Url())) {
                String[] temp = sysMessage.getMessageFile1Url().split("/");
                String t = temp[temp.length - 1];
                String temp2 = t.substring(0, t.length() - MagicNumber.FOUR);
                fileIds.add(temp2);
            }
            if (Validator.isNotNull(sysMessage.getMessageFile2Url())) {
                String[] temp = sysMessage.getMessageFile2Url().split("/");
                String t = temp[temp.length - 1];
                String temp2 = t.substring(0, t.length() - MagicNumber.FOUR);
                fileIds.add(temp2);
            }
            if (Validator.isNotNull(sysMessage.getMessageFile3Url())) {
                String[] temp = sysMessage.getMessageFile3Url().split("/");
                String t = temp[temp.length - 1];
                String temp2 = t.substring(0, t.length() - MagicNumber.FOUR);
                fileIds.add(temp2);
            }

            List<ObjFile> objFiles = objFileRepository.findByFileIdIn(fileIds);
            if (Validator.isNotNull(objFiles)) {
                LOGGER.debug("查询图片消息");
                builder.setResultEntity(objFiles, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "没有图片");
            }

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息--批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "messages/deleteMore", method = RequestMethod.DELETE)
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

                List<SysMessage> sysMessages = sysMessageRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                LOGGER.debug("批量删除数据量为：", sysMessages.size());
                for (SysMessage sysMessage : sysMessages) {

                    List<SysMessageSendDetail> sysMessageSendDetails = sysMessageSendDetailRepository.findBySysMessageId(sysMessage.getId());
                    if (Validator.isNotNull(sysMessageSendDetails)) {
                        for (SysMessageSendDetail sysMessageSendDetail : sysMessageSendDetails) {
                            sysMessageSendDetail.setRemoveTime(new Date());
                            sysMessageSendDetail.setLastUpdateBy(uid);
                            sysMessageSendDetailRepository.save(sysMessageSendDetail);
                        }
                    }
                    sysMessage.setRemoveTime(new Date());
                    sysMessage.setLastUpdateBy(uid);
                }

                sysMessageRepository.save(sysMessages);
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
     * 消息-新建-全部下发
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/sendMessageAll", method = RequestMethod.POST)
    ResponseEntity sendObjEstateType(@RequestBody SysMessage sysMessage, @RequestParam(value = "corpId", required = false) Integer corpId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            List<SysUser> sysUsers;
            if (Validator.isNotNull(corpId)) {
                sysUsers = sysUserRepository.findByCorpId(sysUser.getCorpId());
            } else {
                sysUsers = sysUserRepository.findByCorpId(corpId);
            }
//            List<SysUser> sysUsers = sysUserRepository.findByCorpId(sysUser.getCorpId());
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            sysMessage.setMessageAuthor(sysUser.getUserName());
            sysMessage.setStatus(BusinessRefData.MESSAGE_FINISH);
            sysMessage = sysMessageRepository.save(sysMessage);
            LOGGER.debug("消息准备下发，id：{}", sysMessage.getId());
            List<SysMessageSendDetail> sysMessageSendDetails = new ArrayList<>();
            List<Integer> receiveUserIds = new ArrayList<>();
            for (SysUser temp : sysUsers) {
                receiveUserIds.add(temp.getId());
                SysMessageSendDetail sysMessageSendDetail = new SysMessageSendDetail();
                sysMessageSendDetail.setSysMessageId(sysMessage.getId());
                sysMessageSendDetail.setSendUserId(uid);
                sysMessageSendDetail.setReceiveUserId(temp.getId());
                sysMessageSendDetails.add(sysMessageSendDetail);
            }
            sysMessageSendDetails = sysMessageSendDetailRepository.save(sysMessageSendDetails);


            LOGGER.debug("消息发送人员数量：{}", sysMessageSendDetails.size());
            builder.setResponseCode(ResponseCode.OK, "消息发送成功");

            String content = "公司最新通告";
            pushClientService.pushCommonMsg(content, receiveUserIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 消息-编辑-下发
     *
     * @param sysMessage
     * @return
     */
    @RequestMapping(value = "sysMessages/sendMessageEditAll", method = RequestMethod.PUT)
    ResponseEntity sendMessageEdit(@RequestBody SysMessage sysMessage, @RequestParam(value = "corpId", required = false) Integer corpId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
//            LOGGER.debug("消息发送，接收者人数：{}", receiveUserIds.size());
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);

            List<SysUser> sysUsers;
            if (Validator.isNotNull(corpId)) {
                sysUsers = sysUserRepository.findByCorpId(sysUser.getCorpId());
            } else {
                sysUsers = sysUserRepository.findByCorpId(corpId);
            }
            SysMessage sysMessage1 = sysMessageRepository.findOne(sysMessage.getId());
            if (uid == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            if (Validator.isNotNull(sysMessage1)) {
                sysMessage1.setStatus(BusinessRefData.MESSAGE_FINISH);
                sysMessage1.setMessageText(sysMessage.getMessageText());
                sysMessage1.setMessageTitle(sysMessage.getMessageTitle());
                sysMessage1.setMessageAuthor(sysUser.getUserName());
                if (Validator.isNotNull(sysMessage.getMessageFile1Url())) {
                    sysMessage1.setMessageFile1Url(sysMessage.getMessageFile1Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile2Url())) {
                    sysMessage1.setMessageFile2Url(sysMessage.getMessageFile2Url());
                }
                if (Validator.isNotNull(sysMessage.getMessageFile3Url())) {
                    sysMessage1.setMessageFile3Url(sysMessage.getMessageFile3Url());
                }
                sysMessage = sysMessageRepository.save(sysMessage1);
            }
            LOGGER.debug("消息准备下发，id：{}", sysMessage.getId());

            List<Integer> receiveUserIds = new ArrayList<>();
            List<SysMessageSendDetail> sysMessageSendDetails = new ArrayList<>();
            for (SysUser temp : sysUsers) {
                receiveUserIds.add(temp.getId());
                SysMessageSendDetail sysMessageSendDetail = new SysMessageSendDetail();
                sysMessageSendDetail.setSysMessageId(sysMessage.getId());
                sysMessageSendDetail.setSendUserId(uid);
                sysMessageSendDetail.setReceiveUserId(temp.getId());
                sysMessageSendDetails.add(sysMessageSendDetail);
            }
            sysMessageSendDetails = sysMessageSendDetailRepository.save(sysMessageSendDetails);


            LOGGER.debug("消息发送人员数量：{}", sysMessageSendDetails.size());
            builder.setResponseCode(ResponseCode.OK, "消息发送成功");

            String content = "公司最新通告";
            pushClientService.pushCommonMsg(content, receiveUserIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
