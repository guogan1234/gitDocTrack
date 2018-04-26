/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.stock.ObjStockRecord;
import com.avp.mem.njpb.entity.stock.ObjStockRecordPersonal;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrder;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderDetail;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderResource;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrder;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwStockWorkOrderResource;
import com.avp.mem.njpb.entity.view.VwUserRole;
import com.avp.mem.njpb.repository.stock.ObjStockRecordPersonalRepository;
import com.avp.mem.njpb.repository.stock.ObjStockRecordRepository;
import com.avp.mem.njpb.repository.stock.ObjStockWorkOrderDetailRepository;
import com.avp.mem.njpb.repository.stock.ObjStockWorkOrderRepository;
import com.avp.mem.njpb.repository.stock.ObjStockWorkOrderResourceRepository;
import com.avp.mem.njpb.repository.stock.VwStockWorkOrderDetailRepository;
import com.avp.mem.njpb.repository.stock.VwStockWorkOrderRepository;
import com.avp.mem.njpb.repository.stock.VwStockWorkOrderResourceRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.sys.VwUserRoleRepository;
import com.avp.mem.njpb.service.activiti.ActivitiService;
import com.avp.mem.njpb.service.push.PushClientService;
import com.avp.mem.njpb.service.stock.StockRecordPersonalHistory;
import com.avp.mem.njpb.service.stock.StockWorkOrderService;
import com.avp.mem.njpb.service.workorder.WorkOperateService;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.Validator;
import com.avp.mem.njpb.util.WorkOrderSerialNoUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by six on 2017/8/15.
 */
@RestController
public class StockWorkOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockWorkOrderController.class);

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    ObjStockWorkOrderRepository objStockWorkOrderRepository;

    @Autowired
    VwStockWorkOrderRepository vwStockWorkOrderRepository;

    @Autowired
    ObjStockWorkOrderDetailRepository objStockWorkOrderDetailRepository;

    @Autowired
    VwStockWorkOrderDetailRepository vwStockWorkOrderDetailRepository;

    @Autowired
    VwStockWorkOrderResourceRepository vwStockWorkOrderResourceRepository;

    @Autowired
    ObjStockRecordRepository objStockRecordRepository;

    @Autowired
    ObjStockRecordPersonalRepository objStockRecordPersonalRepository;

    @Autowired
    StockRecordPersonalHistory stockRecordPersonalHistory;

    @Autowired
    WorkOperateService workOperateService;

    @Autowired
    PushClientService pushClientService;

    @Autowired
    StockWorkOrderService stockWorkOrderService;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    VwUserRoleRepository vwUserRoleRepository;

    @Autowired
    ObjStockWorkOrderResourceRepository objStockWorkOrderResourceRepository;

    /**
     * 申请领料/归还/报废
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity saveObjStockWorkOrders(@RequestBody List<ObjStockRecord> objStockRecords,
                                                 @RequestParam Integer stockWorkOrderTypeId, String applyRemark,
                                                 @RequestParam(value = "fileIds", required = false) List<Integer> fileIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(objStockRecords) || Validator.isNull(stockWorkOrderTypeId)) {
                LOGGER.debug("传入参数对象为空");
                builder.setResponseCode(ResponseCode.FAILED, "传入参数对象为空");
                throw new RuntimeException("传入参数对象为空");
            }

            Integer uid = SecurityUtils.getLoginUserId();

            LOGGER.debug("当前申请领料/归还/报废用户为：{}", uid);
            SysUser sysUser = sysUserRepository.findOne(uid);
            Integer corpId = sysUser.getCorpId();
            List<VwUserRole> vwUserRoles = vwUserRoleRepository.findByRoleIdAndCorpId(BusinessRefData.ROLE_STOCK_KEEPER, corpId);

            if (Validator.isNull(vwUserRoles) || vwUserRoles.size() != 1) {
                LOGGER.debug("ID：{}公司仓库管理员不存在，或者仓库管理员人数不为1", corpId);
                builder.setResponseCode(ResponseCode.FAILED, "当前公司仓库管理员不存在或者仓库管理员人数不为1");
                return builder.getResponseEntity();
            }

            //库存工单创建
            ObjStockWorkOrder objStockWorkOrder = new ObjStockWorkOrder();
            objStockWorkOrder.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            objStockWorkOrder.setStockWorkOrderTypeId(stockWorkOrderTypeId);
            //领料/归还
            objStockWorkOrder.setApplyUserId(uid);
            objStockWorkOrder.setApplyTime(new Date());
            objStockWorkOrder.setApplyRemark(applyRemark);
            objStockWorkOrder.setProcessUserId(vwUserRoles.get(0).getUserId());
            objStockWorkOrder.setStockWorkOrderStatusId(BusinessRefData.STOCK_WORK_ORDER_STATUS_APPLY);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            LOGGER.debug("库存工单添加成功，id是：{}，申请人是：{}", objStockWorkOrder.getId(), uid);
            workOperateService.saveStockWorkOrderOperateHistory(objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_APPLY, uid);
            LOGGER.debug("库存工单操作历史添加成功，库存工单id是：{}，操作状态：{}，申请人是：{}", objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_APPLY, uid);

            //库存工单详细创建
            List<ObjStockWorkOrderDetail> objStockWorkOrderDetails = new ArrayList<>();
            for (ObjStockRecord temp : objStockRecords) {
                LOGGER.debug("申请设备类型：{}，申请数量：{}", temp.getEstateTypeId(), temp.getCount());
                ObjStockWorkOrderDetail objStockWorkOrderDetail = new ObjStockWorkOrderDetail();
                objStockWorkOrderDetail.setStockWorkOrderId(objStockWorkOrder.getId());
                objStockWorkOrderDetail.setEstateTypeId(temp.getEstateTypeId());
                objStockWorkOrderDetail.setCount(temp.getCount());
                objStockWorkOrderDetails.add(objStockWorkOrderDetail);
            }
            objStockWorkOrderDetails = objStockWorkOrderDetailRepository.save(objStockWorkOrderDetails);
            LOGGER.debug("本次申请设备类型种类数量为：{}", objStockWorkOrderDetails.size());

            if (Validator.isNotNull(fileIds)) {
                List<ObjStockWorkOrderResource> objStockWorkOrderResources = new ArrayList<>();
                for (Integer temp : fileIds) {
                    LOGGER.debug("文件id ：{}", temp);
                    ObjStockWorkOrderResource objStockWorkOrderResource = new ObjStockWorkOrderResource();
                    objStockWorkOrderResource.setStockWorkOrderId(objStockWorkOrder.getId());
                    objStockWorkOrderResource.setFileId(temp);
                    objStockWorkOrderResources.add(objStockWorkOrderResource);
                }
                objStockWorkOrderResources = objStockWorkOrderResourceRepository.save(objStockWorkOrderResources);
                LOGGER.debug("本次报废图片数量为：{}", objStockWorkOrderResources.size());
            }

            builder.setResponseCode(ResponseCode.OK, "申请成功，请等待仓库管理员确认");

            String processInstanceId = activitiService.startStockWorkflow(objStockWorkOrder);
            objStockWorkOrder.setStockProcessInstanceId(processInstanceId);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            LOGGER.debug("库存工单工作流添加成功，库存工单流id是：{}", objStockWorkOrder.getStockProcessInstanceId());

            //消息推送
            LOGGER.debug("库存工单工作流添加成功，库存");
            String content = "库存工单【" + objStockWorkOrder.getSerialNo() + "】已创建，请注意处理";
            List<Integer> userIds = new ArrayList<>();
            userIds.add(vwUserRoles.get(0).getUserId());


            pushClientService.pushCommonMsg(content, userIds);
            LOGGER.debug("库存工单推送成功");


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            if (builder.getResponseEntity().getStatusCode() == null) {
                builder.setResponseCode(ResponseCode.FAILED, "申请失败，请重新申请");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 领料/归还审核详情查询（根据库存工单ID）
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrderDetail>> findByStockWorkOrderId(@RequestParam Integer stockWorkOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("传入库存工单id是：{}", stockWorkOrderId);
            List<VwStockWorkOrderDetail> vwStockWorkOrderDetails = vwStockWorkOrderDetailRepository.findByStockWorkOrderId(stockWorkOrderId);
            if (Validator.isNull(vwStockWorkOrderDetails)) {
                LOGGER.debug("没有此库存工单，id:{}", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.FAILED, "没有此库存工单");
            } else {
                LOGGER.debug("当前库存工单对应详细记录有：{}", vwStockWorkOrderDetails.size());
                builder.setResultEntity(vwStockWorkOrderDetails, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询库存工单下一步操作
     *
     * @param stockWorkOrderId
     * @return ResponseEntity<RestBody<List<String>>>
     */
    @GetMapping("stockWorkOrders/findTaskNextOperation")
    public ResponseEntity<RestBody<List<String>>> findTaskNextOperation(Integer stockWorkOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<String> processInstanceIds = new ArrayList<>();
        List<String> nextOperation = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            ObjStockWorkOrder objStockWorkOrder = objStockWorkOrderRepository.findOne(stockWorkOrderId);

            if (Validator.isNull(objStockWorkOrder)) {
                LOGGER.debug("库存工单数据为空，查询失败");
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "库存工单数据为空，请求失败");
                return builder.getResponseEntity();
            }

            String stockProcessInstanceId = objStockWorkOrder.getStockProcessInstanceId();
            if (Validator.isNull(stockProcessInstanceId)) {
                LOGGER.debug("库存工单：{}数据中的流程为空，脏数据", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "库存工单数据有误，请校验数据");
                return builder.getResponseEntity();
            }

            List<Task> tasks = taskService.createTaskQuery().taskAssignee(uid.toString()).list();

            LOGGER.debug("根据当前用户查询待办事项task，数量为：{}", tasks.size());

            tasks.forEach(task -> {
                String processInstanceId = task.getProcessInstanceId();
                //为工单表中存放的id

                processInstanceIds.add(processInstanceId);
                LOGGER.debug("工单中的流程id为：{}", processInstanceId);
            });

            if (processInstanceIds.contains(stockProcessInstanceId)) {
                LOGGER.debug("该工单是用户的待办工单之一，可以获取下一步操作。。。");
                String processInstanceId = objStockWorkOrder.getStockProcessInstanceId();
                nextOperation = activitiService.getTaskOperation(processInstanceId);
            }

            builder.setResultEntity(nextOperation, ResponseCode.RETRIEVE_SUCCEED);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("查询库存工单下一步操作失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }


    /**
     * 物料审核-库存工单审核通过
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/checkAgreeByStockWorkOrderId", method = RequestMethod.PUT)
    ResponseEntity checkAgreeByStockWorkOrderId(@RequestParam Integer stockWorkOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("用户不存在：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "用户不存在");
                return builder.getResponseEntity();
            }

            LOGGER.debug("传入库存工单id是：{}", stockWorkOrderId);
            ObjStockWorkOrder objStockWorkOrder = objStockWorkOrderRepository.findOne(stockWorkOrderId);
            if (Validator.isNull(objStockWorkOrder)) {
                LOGGER.debug("此库存工单不存在，id:{}", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.FAILED, "此库存工单不存在");
                return builder.getResponseEntity();
            }
            List<VwStockWorkOrderDetail> vwStockWorkOrderDetails = vwStockWorkOrderDetailRepository.findByStockWorkOrderId(stockWorkOrderId);
            if (Validator.isNull(vwStockWorkOrderDetails)) {
                LOGGER.debug("此库存工单不存在，id:{}", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.FAILED, "此库存工单不存在");
                return builder.getResponseEntity();
            }

            for (VwStockWorkOrderDetail temp : vwStockWorkOrderDetails) {
                Integer corpId = temp.getCorpId();
                Integer pickOrBackCount = temp.getCount();
                Integer pickOrBackEstateTypeId = temp.getEstateTypeId();
                ObjStockRecord objStockRecord = objStockRecordRepository.findByEstateTypeIdAndCorpId(pickOrBackEstateTypeId, corpId);
                ObjStockRecordPersonal objStockRecordPersonal = objStockRecordPersonalRepository.findByUserIdAndEstateTypeId(temp.getApplyUserId(), pickOrBackEstateTypeId);
                if (objStockWorkOrder.getStockWorkOrderTypeId() == BusinessRefData.STOCK_WORK_ORDER_TYPE_PICKING) {
                    if (Validator.isNull(objStockRecord)) {
                        LOGGER.debug("仓库中没有此设备类型，typeId:{}，corpId:{}", pickOrBackEstateTypeId, corpId);
                        builder.setResponseCode(ResponseCode.FAILED, "仓库中没有此设备类型,不可以领取");
                        return builder.getResponseEntity();
                    }
                    Integer objStockRecordCount = objStockRecord.getCount();
                    if (objStockRecordCount < pickOrBackCount) {
                        LOGGER.debug("仓库中此设备剩余数量为：{}，申请领料数量为：{},设备类型：{}，公司：{}", objStockRecordCount, pickOrBackCount, pickOrBackEstateTypeId, corpId);
                        builder.setResponseCode(ResponseCode.FAILED, "仓库中数量不足，无法领取");
                        return builder.getResponseEntity();
                    }

                    //减少公司库存
                    objStockRecord.setCount(objStockRecordCount - pickOrBackCount);
                    objStockRecord = objStockRecordRepository.save(objStockRecord);
                    LOGGER.debug("库存数量减少量为：{}", pickOrBackCount);

                    //增加个人库存
                    // ObjStockRecordPersonal objStockRecordPersonal = objStockRecordPersonalRepository.findByUserIdAndEstateTypeId(temp.getApplyUserId(), pickEstateTypeId);
                    if (Validator.isNull(objStockRecordPersonal)) {
                        LOGGER.debug("个人仓库中不存在此种设备，id:{},添加设备", pickOrBackEstateTypeId);
                        ObjStockRecordPersonal stockRecordPersonal = new ObjStockRecordPersonal();
                        stockRecordPersonal.setUserId(temp.getApplyUserId());
                        stockRecordPersonal.setCount(pickOrBackCount);
                        stockRecordPersonal.setEstateTypeId(pickOrBackEstateTypeId);
                        stockRecordPersonal = objStockRecordPersonalRepository.save(stockRecordPersonal);
                        LOGGER.debug("个人仓库新类型添加成功，数量为：{}，类型：{},id:{}", pickOrBackCount, pickOrBackEstateTypeId, stockRecordPersonal.getId());
                        stockRecordPersonalHistory.saveStockRecordPersonalHistory(pickOrBackEstateTypeId, pickOrBackCount, BusinessRefData.STOCK_WORK_ORDER_TYPE_PICKING, new Date(), temp.getApplyUserId());
                    } else {
                        Integer count = objStockRecordPersonal.getCount();
                        objStockRecordPersonal.setCount(count + pickOrBackCount);
                        objStockRecordPersonal = objStockRecordPersonalRepository.save(objStockRecordPersonal);
                        LOGGER.debug("个人仓库新类型添加成功，数量为：{}，类型：{},id:{}", count + pickOrBackCount, pickOrBackEstateTypeId, objStockRecordPersonal.getId());
                        stockRecordPersonalHistory.saveStockRecordPersonalHistory(pickOrBackEstateTypeId, pickOrBackCount, BusinessRefData.STOCK_WORK_ORDER_TYPE_PICKING, new Date(), temp.getApplyUserId());
                    }
                } else if (objStockWorkOrder.getStockWorkOrderTypeId() == BusinessRefData.STOCK_WORK_ORDER_TYPE_BACK) {
                    if (Validator.isNull(objStockRecordPersonal)) {
                        LOGGER.debug("个人仓库中不存在此种设备，id:{},归还设备", pickOrBackEstateTypeId);
                        builder.setResponseCode(ResponseCode.FAILED, "个人仓库中不存在此种设备,无法归还");
                        throw new RuntimeException("个人仓库中不存在此种设备,无法归还");
                    } else {
                        Integer personalCount = objStockRecordPersonal.getCount();
                        if (objStockRecordPersonal.getCount() < pickOrBackCount) {
                            LOGGER.debug("个人仓库中存在此种设备，id:{},个人仓库剩余数量：{}，归还数量为：{}", pickOrBackEstateTypeId, personalCount, pickOrBackCount);
                            builder.setResponseCode(ResponseCode.FAILED, "个人仓库中不存在此种设备,无法归还");
                            throw new RuntimeException("个人仓库中不存在此种设备,无法归还");
                        }
                        objStockRecordPersonal.setCount(personalCount - pickOrBackCount);
                        objStockRecordPersonal = objStockRecordPersonalRepository.save(objStockRecordPersonal);
                        Integer objStockRecordCount = objStockRecord.getCount();
                        objStockRecord.setCount(objStockRecordCount + pickOrBackCount);
                        objStockRecord = objStockRecordRepository.save(objStockRecord);
                        LOGGER.debug("个人仓库类型归还成功，数量为：{}，类型：{},id:{}", personalCount - pickOrBackCount, pickOrBackEstateTypeId, objStockRecordPersonal.getId());
                        stockRecordPersonalHistory.saveStockRecordPersonalHistory(pickOrBackEstateTypeId, pickOrBackCount, BusinessRefData.STOCK_WORK_ORDER_TYPE_PICKING, new Date(), temp.getApplyUserId());
                    }

                }else if (objStockWorkOrder.getStockWorkOrderTypeId() == BusinessRefData.STOCK_WORK_ORDER_TYPE_SCRAP){
                    if (Validator.isNull(objStockRecord)) {
                        LOGGER.debug("仓库中没有此设备类型，typeId:{}，corpId:{}", pickOrBackEstateTypeId, corpId);
                        builder.setResponseCode(ResponseCode.FAILED, "仓库中没有此设备类型,不可以报废");
                        return builder.getResponseEntity();
                    }
                    if (Validator.isNull(objStockRecordPersonal)) {
                        LOGGER.debug("个人仓库中不存在此种设备，id:{},报废设备", pickOrBackEstateTypeId);
                        builder.setResponseCode(ResponseCode.FAILED, "个人仓库中不存在此种设备,无法报废");
                        throw new RuntimeException("个人仓库中不存在此种设备,无法报废");
                    } else {
                        Integer personalCount = objStockRecordPersonal.getCount();
                        if (personalCount < pickOrBackCount) {
                            LOGGER.debug("个人仓库中存在此种设备，id:{},个人仓库剩余数量：{}，报废数量为：{}", pickOrBackEstateTypeId, personalCount, pickOrBackCount);
                            builder.setResponseCode(ResponseCode.FAILED, "个人仓库中此设备还有"+ personalCount+"个,但是报废数量为："+pickOrBackCount+",无法报废");
                            throw new RuntimeException("个人仓库中此设备还有"+ personalCount+"个,但是报废数量为："+pickOrBackCount+",无法报废");
                        }
                        objStockRecordPersonal.setCount(personalCount - pickOrBackCount);
                        objStockRecordPersonal = objStockRecordPersonalRepository.save(objStockRecordPersonal);

                        Integer objStockRecordCount = objStockRecord.getCount();
                        objStockRecord.setCount(objStockRecordCount - pickOrBackCount);
                        objStockRecord = objStockRecordRepository.save(objStockRecord);
                        LOGGER.debug("个人仓库类型报废成功，数量为：{}，类型：{},id:{}", personalCount - pickOrBackCount, pickOrBackEstateTypeId, objStockRecordPersonal.getId());
                        stockRecordPersonalHistory.saveStockRecordPersonalHistory(pickOrBackEstateTypeId, pickOrBackCount, BusinessRefData.STOCK_WORK_ORDER_TYPE_PICKING, new Date(), temp.getApplyUserId());
                    }
                }
            }
            objStockWorkOrder.setProcessUserId(uid);
            //审核人
            objStockWorkOrder.setStockWorkOrderStatusId(BusinessRefData.STOCK_WORK_ORDER_STATUS_AFFIRM);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            workOperateService.saveStockWorkOrderOperateHistory(objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_AFFIRM, uid);
            LOGGER.debug("库存工单操作历史添加成功，库存工单id是：{}，操作状态：{}，申请人是：{}", objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_AFFIRM, uid);

            LOGGER.debug("库存工单已经确认，领料/归还/报废成功");
            builder.setResponseCode(ResponseCode.OK, "领料/归还/报废成功");

            String processInstanceId = objStockWorkOrder.getStockProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String activityId = processInstance.getActivityId();
            Map<String, Object> variables = new HashMap<>();
            variables.put("stockKeeperAgree", true);

            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().singleResult();
            LOGGER.debug("task:{}", task.getId());

            taskService.complete(task.getId(), variables);
            LOGGER.debug("activityId:{}", activityId);

            //消息推送
            String content = "库存工单【" + objStockWorkOrder.getSerialNo() + "】审核同意，请注意查看";
            List<Integer> userIds = new ArrayList<>();
            userIds.add(objStockWorkOrder.getApplyUserId());
            pushClientService.pushCommonMsg(content, userIds);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (builder.getResponseEntity().getStatusCode() == null) {
                builder.setResponseCode(ResponseCode.FAILED, "申请失败，请重新申请");
            }
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 物料审核-库存工单审核驳回
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/checkRejectByStockWorkOrderId", method = RequestMethod.PUT)
    ResponseEntity checkRejectByStockWorkOrderId(@RequestParam Integer stockWorkOrderId, @RequestParam String rejectRemark) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("用户不存在：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "用户不存在");
                return builder.getResponseEntity();
            }

            LOGGER.debug("传入库存工单id是：{}", stockWorkOrderId);
            ObjStockWorkOrder objStockWorkOrder = objStockWorkOrderRepository.findOne(stockWorkOrderId);
            if (Validator.isNull(objStockWorkOrder)) {
                LOGGER.debug("此库存工单不存在，id:{}", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.FAILED, "此库存工单不存在");
                return builder.getResponseEntity();
            }
            objStockWorkOrder.setRejectRemark(rejectRemark);
            objStockWorkOrder.setProcessUserId(uid);
            //审核人
            objStockWorkOrder.setStockWorkOrderStatusId(BusinessRefData.STOCK_WORK_ORDER_STATUS_REJECT);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            LOGGER.debug("申请领料/归还/报废被驳回，无法领取/归还/报废");
            workOperateService.saveStockWorkOrderOperateHistory(objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_REJECT, uid);
            LOGGER.debug("库存工单操作历史添加成功，库存工单id是：{}，操作状态：{}，申请人是：{}", objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_REJECT, uid);

            builder.setResponseCode(ResponseCode.OK, "申请领料/归还/报废被驳回，无法领取");


            String processInstanceId = objStockWorkOrder.getStockProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String activityId = processInstance.getActivityId();
            Map<String, Object> variables = new HashMap<>();
            variables.put("stockKeeperAgree", false);

            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().singleResult();
            LOGGER.debug("task:{}", task.getId());

            taskService.complete(task.getId(), variables);
            LOGGER.debug("activityId:{}", activityId);

            //消息推送
            String content = "库存工单【" + objStockWorkOrder.getSerialNo() + "】审核驳回，请注意查看";
            List<Integer> userIds = new ArrayList<>();
            userIds.add(objStockWorkOrder.getApplyUserId());
            pushClientService.pushCommonMsg(content, userIds);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 驳回库存工单-修改后提交-重新领料/归还
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/reapply", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity reapplyStockWorkOrders(@RequestBody List<ObjStockRecord> objStockRecords, @RequestParam Integer stockWorkOrderTypeId, @RequestParam Integer stockWorkOrderId,
    @RequestParam(value = "fileIds", required = false) List<Integer> fileIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("操作状态：{}，库存工单id:{}", stockWorkOrderTypeId, stockWorkOrderId);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            LOGGER.debug("当前申请领料/归还/报废用户为：{}", uid);

            if (Validator.isNull(objStockRecords) || Validator.isNull(stockWorkOrderTypeId)) {
                LOGGER.debug("传入参数对象为空");
                builder.setResponseCode(ResponseCode.FAILED, "传入参数对象为空");
                throw new RuntimeException("传入参数对象为空");
            }

            ObjStockWorkOrder objStockWorkOrder = objStockWorkOrderRepository.findOne(stockWorkOrderId);
            if (Validator.isNull(objStockWorkOrder)) {
                LOGGER.debug("此库存工单不存在,无法重新提交");
                builder.setResponseCode(ResponseCode.FAILED, "此库存工单不存在,无法重新提交");
                throw new RuntimeException("此库存工单不存在,无法重新提交");
            }

            //更新库存工单
            objStockWorkOrder.setApplyTime(new Date());
            objStockWorkOrder.setStockWorkOrderStatusId(BusinessRefData.STOCK_WORK_ORDER_STATUS_UPDATE);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            Integer objStockWorkOrderId = objStockWorkOrder.getId();
            LOGGER.debug("库存工单添加成功，id是：{}，申请人是：{}", objStockWorkOrderId, uid);
            workOperateService.saveStockWorkOrderOperateHistory(objStockWorkOrder.getId(), BusinessRefData.STOCK_WORK_ORDER_STATUS_UPDATE, uid);
            LOGGER.debug("库存工单操作历史添加成功，库存工单id是：{}，操作状态：{}，申请人是：{}", objStockWorkOrderId, BusinessRefData.STOCK_WORK_ORDER_STATUS_UPDATE, uid);

            List<ObjStockWorkOrderDetail> objStockWorkOrderDetails = objStockWorkOrderDetailRepository.findByStockWorkOrderId(stockWorkOrderId);
            List<ObjStockWorkOrderDetail> stockWorkOrderDetails;
            if (Validator.isNull(objStockWorkOrderDetails)) {
                LOGGER.debug("此库存工单详情不存在,无需修改");
                //库存工单详细创建
                stockWorkOrderDetails = stockWorkOrderService.saveStockWorkOrderDetail(objStockRecords, objStockWorkOrderId);
            } else {
                for (ObjStockWorkOrderDetail temp : objStockWorkOrderDetails) {//删除原始记录
                    temp.setRemoveTime(new Date());
                }
                objStockWorkOrderDetailRepository.save(objStockWorkOrderDetails);
                stockWorkOrderDetails = stockWorkOrderService.saveStockWorkOrderDetail(objStockRecords, objStockWorkOrderId);
            }
            objStockWorkOrderDetails = objStockWorkOrderDetailRepository.save(stockWorkOrderDetails);

            List<ObjStockWorkOrderResource> objStockWorkOrderResources = objStockWorkOrderResourceRepository.findByStockWorkOrderId(stockWorkOrderId);
            List<ObjStockWorkOrderResource> stockWorkOrderResources;
            if (Validator.isNull(objStockWorkOrderResources)) {
                LOGGER.debug("此库存工单详情不存在,无需修改");
                //库存工单详细创建
                stockWorkOrderResources = stockWorkOrderService.saveStockWorkOrderResources(objStockWorkOrderResources, objStockWorkOrderId);
            } else {
                for (ObjStockWorkOrderResource temp : objStockWorkOrderResources) {//删除原始记录
                    temp.setRemoveTime(new Date());
                }
                objStockWorkOrderResourceRepository.save(objStockWorkOrderResources);
                stockWorkOrderResources = stockWorkOrderService.saveStockWorkOrderResources(objStockWorkOrderResources, objStockWorkOrderId);
            }

            objStockWorkOrderResources = objStockWorkOrderResourceRepository.save(stockWorkOrderResources);



            LOGGER.debug("本次申请/归还设备类型种类数量为：{}", objStockWorkOrderDetails.size());
            builder.setResponseCode(ResponseCode.OK, "申请成功，请等待仓库管理员确认");


            String processInstanceId = objStockWorkOrder.getStockProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String activityId = processInstance.getActivityId();

            Map<String, Object> variables = new HashMap<>();
            variables.put("applierAgree", true);

            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().singleResult();
            LOGGER.debug("task:{}", task.getId());

            taskService.complete(task.getId(), variables);
            LOGGER.debug("activityId:{}", activityId);

            //消息推送
            String content = "库存工单【" + objStockWorkOrder.getSerialNo() + "】重新提交，请注意查看";
            List<Integer> userIds = new ArrayList<>();
            userIds.add(objStockWorkOrder.getProcessUserId());
            pushClientService.pushCommonMsg(content, userIds);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            if (builder.getResponseEntity().getStatusCode() == null) {
                builder.setResponseCode(ResponseCode.FAILED, "申请失败，请重新申请");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 库存工单审核驳回后-取消申请
     *
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/cancel", method = RequestMethod.PUT)
    ResponseEntity cancelStockWorkOrders(@RequestParam Integer stockWorkOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("用户不存在：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "用户不存在");
                return builder.getResponseEntity();
            }

            LOGGER.debug("库存工单id是：{}", stockWorkOrderId);
            ObjStockWorkOrder objStockWorkOrder = objStockWorkOrderRepository.findOne(stockWorkOrderId);
            if (Validator.isNull(objStockWorkOrder)) {
                LOGGER.debug("此库存工单不存在，id:{}", stockWorkOrderId);
                builder.setResponseCode(ResponseCode.FAILED, "此库存工单不存在");
                return builder.getResponseEntity();
            }
            objStockWorkOrder.setStockWorkOrderStatusId(BusinessRefData.STOCK_WORK_ORDER_STATUS_CANCEL);
            objStockWorkOrder = objStockWorkOrderRepository.save(objStockWorkOrder);
            LOGGER.debug("申请领料/归还驳回后，取消领取/归还成功");
            workOperateService.saveStockWorkOrderOperateHistory(stockWorkOrderId, BusinessRefData.STOCK_WORK_ORDER_STATUS_CANCEL, uid);
            LOGGER.debug("库存工单操作历史添加成功，库存工单id是：{}，操作状态：{}，申请人是：{}", stockWorkOrderId, BusinessRefData.STOCK_WORK_ORDER_STATUS_CANCEL, uid);

            builder.setResponseCode(ResponseCode.OK, "申请领料/归还驳回后，取消领取/归还成功");


            String processInstanceId = objStockWorkOrder.getStockProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String activityId = processInstance.getActivityId();
            Map<String, Object> variables = new HashMap<>();
            variables.put("applierAgree", false);

            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().singleResult();
            LOGGER.debug("task:{}", task.getId());

            taskService.complete(task.getId(), variables);
            LOGGER.debug("activityId:{}", activityId);


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询领料/归还审核记录-APP
     *
     * @param stockWorkOrderTypeId
     * @param firstOperationTime
     * @param page
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndOperationTimeLessThan(Integer stockWorkOrderTypeId, Date firstOperationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("物料审核类型：{}, firstOperationTime为:{}", stockWorkOrderTypeId, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null || stockWorkOrderTypeId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            List<Integer> stockWorkOrderStatusIds = new ArrayList<>();
            stockWorkOrderStatusIds.add(BusinessRefData.STOCK_WORK_ORDER_STATUS_APPLY);
            stockWorkOrderStatusIds.add(BusinessRefData.STOCK_WORK_ORDER_STATUS_UPDATE);
            LOGGER.debug("领料/归还审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByStockWorkOrderTypeIdAndStockWorkOrderStatusIdInAndCorpIdAndLastUpdateTimeLessThan(stockWorkOrderTypeId, stockWorkOrderStatusIds, sysUser.getCorpId(), firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询领料/归还审核成功，结果为空");
            } else {
                LOGGER.debug("查询领料/归还审核成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询领料/归还审核记录-APP
     *
     * @param stockWorkOrderTypeId
     * @param firstOperationTime
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndOperationTimeGreaterThan(Integer stockWorkOrderTypeId, Date firstOperationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("物料审核类型：{}，时间为： firstOperationTime为({})", stockWorkOrderTypeId, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null || stockWorkOrderTypeId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            List<Integer> stockWorkOrderStatusIds = new ArrayList<>();
            stockWorkOrderStatusIds.add(BusinessRefData.STOCK_WORK_ORDER_STATUS_APPLY);
            stockWorkOrderStatusIds.add(BusinessRefData.STOCK_WORK_ORDER_STATUS_UPDATE);
            LOGGER.debug("领料/归还审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByStockWorkOrderTypeIdAndStockWorkOrderStatusIdInAndCorpIdAndLastUpdateTimeGreaterThan(stockWorkOrderTypeId, stockWorkOrderStatusIds, sysUser.getCorpId(), firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询出/入库记录成功，结果为空");
            } else {
                LOGGER.debug("查询出/入库记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询个人领料/归还记录-APP
     *
     * @param stockWorkOrderTypeId
     * @param operationResult
     * @param firstOperationTime
     * @param page
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeLessThan(Integer stockWorkOrderTypeId, Integer operationResult, Date firstOperationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("领料/归还的类型：{}，操作结果类型：{}，时间为： firstOperationTime为({})", stockWorkOrderTypeId, operationResult, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null || stockWorkOrderTypeId == null || operationResult == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("领料/归还审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByApplyUserIdAndStockWorkOrderTypeIdAndOperationResultAndLastUpdateTimeLessThan(uid, stockWorkOrderTypeId, operationResult, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询领料/归还审核成功，结果为空");
            } else {
                LOGGER.debug("查询领料/归还审核成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (下拉刷新)-查询领料/归还记录-APP
     *
     * @param stockWorkOrderTypeId
     * @param firstOperationTime
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeGreaterThan(Integer stockWorkOrderTypeId, Integer operationResult, Date firstOperationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("领料/归还的类型：{}，操作结果类型：{}，时间为： firstOperationTime为({})", stockWorkOrderTypeId, operationResult, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }

            if (firstOperationTime == null || stockWorkOrderTypeId == null || operationResult == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }

            LOGGER.debug("领料/归还审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByApplyUserIdAndStockWorkOrderTypeIdAndOperationResultAndLastUpdateTimeGreaterThan(uid, stockWorkOrderTypeId, operationResult, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询出/入库记录成功，结果为空");
            } else {
                LOGGER.debug("查询出/入库记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的审核记录
     *
     * @param stockWorkOrderTypeId
     * @param firstOperationTime
     * @param page
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeLessThan(Integer stockWorkOrderTypeId, Date firstOperationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("物料审核类型：{}，时间为： firstOperationTime为({})", stockWorkOrderTypeId, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null || stockWorkOrderTypeId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }

            LOGGER.debug("我的审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByStockWorkOrderTypeIdAndProcessUserIdInAndLastUpdateTimeLessThan(stockWorkOrderTypeId, uid, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("我的审核查询成功，结果为空");
            } else {
                LOGGER.debug("我的审核查询成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (下拉刷新)-查询我的审核记录
     *
     * @param stockWorkOrderTypeId
     * @param firstOperationTime
     * @return
     */
    @RequestMapping(value = "stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrder>> findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeGreaterThan(Integer stockWorkOrderTypeId, Date firstOperationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("物料审核类型：{}，时间为： firstOperationTime为({})", stockWorkOrderTypeId, firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            if (Validator.isNull(sysUser)) {
                LOGGER.debug("此用户不存在，ID：{}", uid);
                builder.setResponseCode(ResponseCode.FAILED, "此用户不存在");
                return builder.getResponseEntity();
            }
            if (firstOperationTime == null || stockWorkOrderTypeId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }

            LOGGER.debug("我的审核上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwStockWorkOrder> list = vwStockWorkOrderRepository.findByStockWorkOrderTypeIdAndProcessUserIdInAndLastUpdateTimeGreaterThan(stockWorkOrderTypeId, uid, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("我的审核查询成功，结果为空");
            } else {
                LOGGER.debug("我的审核查询成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 库存工单资源查询
     *
     * @return
     * @Param stockWorkOrderId
     */
    @RequestMapping(value = "stockWorkOrderResources/findByStockWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrderResource>> findStockWorkOrderResource(@Param("stockWorkOrderId") Integer stockWorkOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwStockWorkOrderResource> stockWorkOrderResources = new ArrayList<>();
        try {
            if (stockWorkOrderId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("库存工单资源查询({}),参数缺失，请求失败", stockWorkOrderId);
                return builder.getResponseEntity();
            }
            stockWorkOrderResources = vwStockWorkOrderResourceRepository.findByStockWorkOrderId(stockWorkOrderId);
            LOGGER.debug("查询工单资源成功,数据量为:{}", stockWorkOrderResources.size());

            builder.setResultEntity(stockWorkOrderResources, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
