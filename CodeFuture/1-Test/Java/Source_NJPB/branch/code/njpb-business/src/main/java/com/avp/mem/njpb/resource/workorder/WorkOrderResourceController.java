/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.workorder;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjFile;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.view.VwScoreMonth;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder_;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponent;
import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponentFaultTypeSum;
import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponentFaultTypeSum_;
import com.avp.mem.njpb.entity.view.VwWorkOrderFaultType;
import com.avp.mem.njpb.entity.view.VwWorkOrderFaultType_;
import com.avp.mem.njpb.entity.view.VwWorkOrderHistory;
import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount;
import com.avp.mem.njpb.entity.view.VwWorkOrderResource;
import com.avp.mem.njpb.entity.view.VwWorkOrder_;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.repository.basic.ObjFileRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderBadComponentRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderResourceRepository;
import com.avp.mem.njpb.repository.workorder.VwScoreMonthRepository;
import com.avp.mem.njpb.repository.workorder.VwUserWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderBadComponentFaultTypeSumRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderBadComponentRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderFaultTypeRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderHistoryRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderModuleBadComponentCountRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderResourceRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.DateUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by six on 2017/7/28.
 */
@RestController
public class WorkOrderResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderResourceController.class);

    @Autowired
    VwUserWorkOrderRepository vwUserWorkOrderRepository;

    @Autowired
    ObjWorkOrderBadComponentRepository objWorkOrderBadComponentRepository;

    @Autowired
    ObjWorkOrderResourceRepository objWorkOrderResourceRepository;

    @Autowired
    ObjFileRepository objFileRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    ObjWorkOrderRepository objWorkOrderRepository;

    @Autowired
    VwScoreMonthRepository vwScoreMonthRepository;

    @Autowired
    VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    VwWorkOrderHistoryRepository vwWorkOrderHistoryRepository;

    @Autowired
    VwWorkOrderResourceRepository vwWorkOrderResourceRepository;

    @Autowired
    VwWorkOrderBadComponentRepository vwWorkOrderBadComponentRepository;

    @Autowired
    VwWorkOrderFaultTypeRepository vwWorkOrderFaultTypeRepository;

    @Autowired
    VwWorkOrderModuleBadComponentCountRepository vwWorkOrderModuleBadComponentCountRepository;

    @Autowired
    VwWorkOrderBadComponentFaultTypeSumRepository vwWorkOrderBadComponentFaultTypeSumRepository;

    /**
     * 查询工单数据-web
     *
     * @param corpId
     * @param stationId
     * @param workOrderTypeId
     * @param workOrderStatusId
     * @param estateTypeId
     * @param beginTime
     * @param endTime
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByUidAndConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserWorkOrder>> searchWorkOrdersByCondition(Integer corpId, Integer stationId, Integer workOrderTypeId, Integer workOrderStatusId, Integer estateTypeId, Integer reportWay, Integer assignEmployee, Date beginTime, Date endTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            Page<VwUserWorkOrder> list = vwUserWorkOrderRepository.findAll(where(byConditions(corpId, stationId, workOrderTypeId, workOrderStatusId, estateTypeId, reportWay, assignEmployee, beginTime, endTime, uid)), page);
            LOGGER.debug("查询工单详细数据成功，数据量为:({})", list.getTotalElements());

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public static Specification<VwUserWorkOrder> byConditions(Integer corpId, Integer stationId, Integer workOrderTypeId, Integer workOrderStatusId, Integer estateTypeId, Integer reportWay, Integer assignEmployee, Date beginTime, Date endTime, Integer uid) {
        return new Specification<VwUserWorkOrder>() {
            public Predicate toPredicate(Root<VwUserWorkOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserWorkOrder_.projectId), corpId));
                }
                if (stationId != null) {
                    predicate.getExpressions()
                            .add(builder.equal(root.get(VwUserWorkOrder_.stationId), stationId));
                }
                if (workOrderTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserWorkOrder_.typeId), workOrderTypeId));
                }
                if (workOrderStatusId != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserWorkOrder_.statusId), workOrderStatusId));
                }
                if (estateTypeId != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserWorkOrder_.estateTypeId), estateTypeId));
                }
                if (reportWay != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserWorkOrder_.reportWay), reportWay));
                }

                if (assignEmployee != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserWorkOrder_.assignEmployee), assignEmployee));
                }
                if (beginTime != null) {
                    predicate.getExpressions().add(
                            builder.greaterThan(root.get(VwUserWorkOrder_.createTime), beginTime));
                }

                if (endTime != null) {
                    predicate.getExpressions().add(
                            builder.lessThan(root.get(VwUserWorkOrder_.lastUpdateTime), endTime));
                }

                predicate.getExpressions().add(builder.equal(root.get(VwUserWorkOrder_.uid), uid));

                return predicate;
            }
        };
    }

    // Dynamic End


    /**
     * @param corpId
     * @param workOrderTypeId
     * @param workOrderStatusId
     * @param repairEmployee
     * @param category
     * @param beginTime
     * @param endTime
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findWorkOrdersCondition(Integer corpId, Integer workOrderTypeId, Integer workOrderStatusId, Integer repairEmployee, Integer category, Date beginTime, Date endTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        try {
            Page<VwWorkOrder> list = vwWorkOrderRepository.findAll(where(byConditionsT(corpId, workOrderTypeId, workOrderStatusId, repairEmployee, category, beginTime, endTime)), page);
            LOGGER.debug("查询工单详细数据成功，数据量为:({})", list.getTotalElements());

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public static Specification<VwWorkOrder> byConditionsT(Integer corpId, Integer workOrderTypeId, Integer workOrderStatusId, Integer repairEmployee, Integer category, Date beginTime, Date endTime) {
        return new Specification<VwWorkOrder>() {
            public Predicate toPredicate(Root<VwWorkOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.projectId), corpId));
                }
                if (workOrderTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.estateTypeId), workOrderTypeId));
                }
                if (workOrderStatusId != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwWorkOrder_.statusId), workOrderStatusId));
                }
                if (repairEmployee != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwWorkOrder_.repairEmployee), repairEmployee));
                }
                if (category != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwWorkOrder_.category), category));
                }

                if (beginTime != null) {
                    predicate.getExpressions().add(
                            builder.greaterThan(root.get(VwWorkOrder_.lastUpdateTime), beginTime));
                }

                if (endTime != null) {
                    predicate.getExpressions().add(
                            builder.lessThan(root.get(VwWorkOrder_.workOrderScore), MagicNumber.ZEROD));

                }

                return predicate;
            }
        };
    }

    // Dynamic End


    /**
     * 工单数据查询
     *
     * @return
     */
    @RequestMapping(value = "workOrders/findByWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstate>> findByWorkOrderId(@Param("workOrderId") Integer workOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (workOrderId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("工单查询({}),参数缺失，请求失败", workOrderId);
                return builder.getResponseEntity();
            }

            VwWorkOrder workOrder = vwWorkOrderRepository.findOne(workOrderId);

            LOGGER.debug("查询工单成功,数据为:{}", workOrder.toString());
            builder.setResultEntity(workOrder, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 更换备件查询&坏件查询
     *
     * @return
     */
    @RequestMapping(value = "workOrderBadComponents/findByWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrderBadComponent>> findReplaceEstates(@Param("workOrderId") Integer workOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (workOrderId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("工单坏件记录查询({}),参数缺失，请求失败", workOrderId);
                return builder.getResponseEntity();
            }

            List<VwWorkOrderBadComponent> objWorkOrderBadComponents = vwWorkOrderBadComponentRepository.findByWorkOrderId(workOrderId);

            LOGGER.debug("查询坏件记录成功,数据量为:{}", objWorkOrderBadComponents.size());
            builder.setResultEntity(objWorkOrderBadComponents, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 工单资源查询
     *
     * @return
     * @Param workOrderId
     */
    @RequestMapping(value = "workOrderResources/findByWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjFile>> findWorkOrderResource(@Param("workOrderId") Integer workOrderId, @Param("resourceType") Integer resourceType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwWorkOrderResource> workOrderResources = new ArrayList<>();
        try {
            if (workOrderId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("工单资源查询({}),参数缺失，请求失败", workOrderId);
                return builder.getResponseEntity();
            }

            if (Validator.isNull(resourceType)) {
                workOrderResources = vwWorkOrderResourceRepository.findByWorkOrderId(workOrderId);
            } else {
                workOrderResources = vwWorkOrderResourceRepository.findByWorkOrderIdAndCategory(workOrderId, resourceType);
            }

            LOGGER.debug("查询工单资源成功,数据量为:{}", workOrderResources.size());

            builder.setResultEntity(workOrderResources, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询工单历史记录
     *
     * @param workOrderId
     * @return
     */
    @RequestMapping(value = "workOrderHistories/findByWorkOrderId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrderHistory>> findWorkOrderHistory(Integer workOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwWorkOrderHistory> workOrderHistoryList = new ArrayList<>();
        try {
            if (workOrderId == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("historyWorkOrders({}),参数缺失，请求失败", workOrderId);
                return builder.getResponseEntity();
            }

            workOrderHistoryList = vwWorkOrderHistoryRepository.findByWorkOrderIdOrderByCreateTimeDesc(workOrderId);

            LOGGER.debug("查询工单历史记录成功，数据量为：({})", workOrderHistoryList.size());

            builder.setResultEntity(workOrderHistoryList, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的报修记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndReportEmployeeAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndReportEmployeeAndOperationTimeLessThan(Date firstOperationTime, Integer reportWay, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的报修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的报修记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            Page<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询我的报修记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的报修记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询我的报修记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndReportEmployeeAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndReportEmployeeAndOperationTimeGreaterThan(Date firstOperationTime, Integer reportWay) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的报修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的报修记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            List<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询我的报修记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的报修记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的维修记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndRepairEmployeeAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndRepairEmployeeAndOperationTimeLessThan(Date firstOperationTime, Integer reportWay, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的维修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的维修记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            Page<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndRepairEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询我的维修记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的维修记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询我的维修记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndRepairEmployeeAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndRepairEmployeeAndOperationTimeGreaterThan(Date firstOperationTime, Integer reportWay) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的报修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的维修记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            List<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndRepairEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询我的维修记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的维修记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的自修保养记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndOperationTimeLessThan(Date firstOperationTime, Integer reportWay, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的自修保养, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的自修保养记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            Page<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_SELF_REPAIR, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询我的自修保养记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的自修保养记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询自修保养记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndOperationTimeGreaterThan(Date firstOperationTime, Integer reportWay) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的自修保养, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的自修保养记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            List<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_SELF_REPAIR, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询我的自修保养记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的自修保养记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * (上拉加载更多)-查询我的派单记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @param page
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndAssignEmployeeAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndAssignEmployeeAndOperationTimeLessThan(Date firstOperationTime, Integer reportWay, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的维修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的派单记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            Page<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndAssignEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询我的派单记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的派单记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询我的派单记录-APP
     *
     * @param firstOperationTime
     * @param reportWay
     * @return
     */
    @RequestMapping(value = "workOrders/findByReportWayAndAssignEmployeeAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> findByReportWayAndAssignEmployeeAndOperationTimeGreaterThan(Date firstOperationTime, Integer reportWay) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("我的报修, firstOperationTime为：{},报修类别：{}，p为：{}", firstOperationTime, reportWay);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(uid)) {
                LOGGER.debug("当前用户不存在");
                builder.setResponseCode(ResponseCode.FAILED, "当前用户不存在");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(firstOperationTime) || Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("我的派单记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);

            List<VwWorkOrder> list = vwWorkOrderRepository.findByReportWayAndAssignEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(reportWay, uid, BusinessRefData.WORK_ORDER_TYPE_REPAIR, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询我的派单记录成功，结果为空");
            } else {
                LOGGER.debug("查询我的派单记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询上一次保养信息
     *
     * @param estateId
     * @return
     */
    @RequestMapping(value = "workOrders/findByEstateId")
    ResponseEntity<RestBody<VwWorkOrder>> findByEstateId(Integer estateId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("设备id：{}", estateId);
            List<Integer> statusIds = new ArrayList<>();
            statusIds.add(BusinessRefData.WOSTATUS_RPR_COMPLATED);
            statusIds.add(BusinessRefData.WOSTATUS_WO_COMPLATED);
            VwWorkOrder vwWorkOrder = vwWorkOrderRepository.findTopByTypeIdAndEstateIdAndStatusIdInAndFixedOrderByReportTimeDesc(BusinessRefData.WORK_ORDER_TYPE_SELF_REPAIR, estateId, statusIds, true);
            if (Validator.isNull(vwWorkOrder)) {
                LOGGER.debug("沒有上次保养记录");
                builder.setResponseCode(ResponseCode.FAILED, "沒有上次保养记录");
            } else {
                builder.setResultEntity(vwWorkOrder, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询上一次维修信息
     *
     * @param estateId
     * @return
     */
    @RequestMapping(value = "workOrders/findByEstateIdAndRepair")
    ResponseEntity<RestBody<VwWorkOrder>> findByEstateIdAndRepair(Integer estateId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("设备id：{}", estateId);
            List<Integer> statusIds = new ArrayList<>();
            statusIds.add(BusinessRefData.WOSTATUS_RPR_COMPLATED);
            statusIds.add(BusinessRefData.WOSTATUS_WO_COMPLATED);
            VwWorkOrder vwWorkOrder = vwWorkOrderRepository.findTopByTypeIdAndEstateIdAndStatusIdInAndFixedOrderByReportTimeDesc(BusinessRefData.WORK_ORDER_TYPE_REPAIR, estateId, statusIds, true);
            if (Validator.isNull(vwWorkOrder)) {
                LOGGER.debug("沒有上次维修记录");
                builder.setResponseCode(ResponseCode.FAILED, "沒有上次维修记录");
            } else {
                builder.setResultEntity(vwWorkOrder, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询总积分--APP
     *
     * @return
     */
    @RequestMapping(value = "workOrders/findByUidAll", method = RequestMethod.GET)
    ResponseEntity findByUidAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            List<VwWorkOrder> vwWorkOrders = vwWorkOrderRepository.findByRepairEmployee(uid);

            if (Validator.isNull(vwWorkOrders)) {
                LOGGER.debug("没有维修记录");
                builder.setResultEntity(MagicNumber.ZERO, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                Double sumAll = 0.0;
                for (int i = 0; i < vwWorkOrders.size(); i++) {
                    sumAll += (vwWorkOrders.get(i).getWorkOrderScore() - vwWorkOrders.get(i).getWorkOrderScoreDeduct());
                }
                builder.setResultEntity(sumAll, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询积分--月份--APP
     *
     * @return
     */
    @RequestMapping(value = "workOrders/findByUidAndMonth", method = RequestMethod.GET)
    ResponseEntity findByUidAndMonth(Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            Page<VwScoreMonth> vwScoreMonths = vwScoreMonthRepository.findByUid(uid, page);
            if (Validator.isNull(vwScoreMonths)) {
                LOGGER.debug("没有工分记录");
                builder.setResultEntity(MagicNumber.ZERO, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setResultEntity(vwScoreMonths, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询积分--月份详情--APP
     *
     * @return
     */
    @RequestMapping(value = "workOrdersScoreDetails/findByMonth", method = RequestMethod.GET)
    ResponseEntity findByMonth(@RequestParam("beginTime") String beginTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String dateBegin = beginTime + "-01 00:00:00";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date beginDate = sdf.parse(dateBegin);
            Date endDate = DateUtil.getonthOfDate(beginDate);

            Integer uid = SecurityUtils.getLoginUserId();
            List<VwWorkOrder> vwWorkOrders = vwWorkOrderRepository.findByRepairEmployeeAndStatusIdAndLastUpdateTimeBetweenOrderByLastUpdateTimeDesc(uid, BusinessRefData.WOSTATUS_WO_COMPLATED, beginDate, endDate);

            if (Validator.isNull(vwWorkOrders)) {
                LOGGER.debug("没有工分记录");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED, "没有工分");
            } else {
                builder.setResultEntity(vwWorkOrders, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    @RequestMapping(value = "workOrders/close", method = RequestMethod.PUT)
    ResponseEntity closeWorkOrder(@RequestParam("workOrderId") Integer workOrderId, @RequestParam("closeRemark") String closeRemark) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjWorkOrder objWorkOrder = objWorkOrderRepository.findOne(workOrderId);


            if (Validator.isNotNull(objWorkOrder)) {
                objWorkOrder.setProcessInstanceId("");
                objWorkOrder.setCloseRemark(closeRemark);
                objWorkOrderRepository.save(objWorkOrder);
                builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);
            } else {
                LOGGER.debug("工单不存在");
                builder.setResponseCode(ResponseCode.UPDATE_FAILED, "工单不存在");

            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    @RequestMapping(value = "workOrders/list", method = RequestMethod.GET)
    ResponseEntity listWorkOrder(Integer projectId, Integer stationId,
                                 Integer estateTypeId, Integer faultTypeId,
                                 Integer statusId, Integer repairEmployee,
                                 Integer reportEmployee, Integer assignEmployee,
                                 Integer checkEmployee, Date endDate, Integer typeId,
                                 Date startDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwWorkOrderFaultType> vwWorkOrderFaultTypes = vwWorkOrderFaultTypeRepository.findAll(where(queryConditions(projectId, stationId, estateTypeId, faultTypeId, statusId, repairEmployee,
                    reportEmployee, checkEmployee, assignEmployee, startDate, endDate, typeId)));

            for (int i = 0; i < vwWorkOrderFaultTypes.size(); i++) {
                VwWorkOrderFaultType vwWorkOrderFaultType = vwWorkOrderFaultTypes.get(i);
                String replaceEstates = "";
                Integer id = vwWorkOrderFaultType.getId();
                List<VwWorkOrderModuleBadComponentCount> vwWorkOrderModuleBadComponentCounts = vwWorkOrderModuleBadComponentCountRepository.findByWorkOrderId(id);
                for (int j = 0; j < vwWorkOrderModuleBadComponentCounts.size(); j++) {
                    if (j < vwWorkOrderModuleBadComponentCounts.size() - 1) {
                        replaceEstates += vwWorkOrderModuleBadComponentCounts.get(j).getName() + ";";
                    } else {
                        replaceEstates += vwWorkOrderModuleBadComponentCounts.get(j).getName();
                    }
                }
                vwWorkOrderFaultType.setReplaceEstates(replaceEstates);
            }


            builder.setResultEntity(vwWorkOrderFaultTypes, ResponseCode.OK);

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    public Specification<VwWorkOrderFaultType> queryConditions(Integer projectId, Integer stationId, Integer estateTypeId, Integer faultTypeId, Integer statusId, Integer repairEmployee,
                                                               Integer reportEmployee, Integer checkEmployee, Integer assignEmployee, Date startDate, Date endDate, Integer typeId) {
        return new Specification<VwWorkOrderFaultType>() {
            public Predicate toPredicate(Root<VwWorkOrderFaultType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("queryConditions/findByConditions请求的参数corpId值为:{}", projectId);
                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.projectId), projectId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数stationId值为:{}", stationId);
                if (stationId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.stationId), stationId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数faultTypeId值为:{}", faultTypeId);
                if (faultTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.faultId), faultTypeId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数statusId值为:{}", statusId);
                if (statusId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.statusId), statusId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数repairEmployee值为:{}", repairEmployee);
                if (repairEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.repairEmployee), repairEmployee));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数reportEmployee值为:{}", reportEmployee);
                if (reportEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.reportEmployee), reportEmployee));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数assignEmployee值为:{}", assignEmployee);
                if (assignEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.assignEmployee), assignEmployee));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数checkEmployee值为:{}", checkEmployee);
                if (checkEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.checkEmployee), estateTypeId));
                }


                LOGGER.debug("queryConditions/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwWorkOrderFaultType_.reportTime), startDate));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwWorkOrderFaultType_.reportTime), endDate));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数typeId值为:{}", typeId);
                if (typeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.typeId), typeId));
                }
                return predicate;
            }
        };
    }


    @RequestMapping(value = "workOrders/bikeList", method = RequestMethod.GET)
    ResponseEntity bikeListWorkOrder(Integer projectId, Integer stationId,
                                     Integer estateTypeId, Integer faultTypeId,
                                     Integer statusId, Integer repairEmployee,
                                     Integer bikeFrameNo, Integer typeId,
                                     Integer reportEmployee, Integer assignEmployee,
                                     Integer checkEmployee, Date endDate,
                                     Date startDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwWorkOrderBadComponentFaultTypeSum> vwWorkOrderBadComponentFaultTypeSums = vwWorkOrderBadComponentFaultTypeSumRepository.findAll(where(queryConditionsTwice(projectId, stationId, estateTypeId, faultTypeId, statusId, repairEmployee,
                    reportEmployee, checkEmployee, assignEmployee, bikeFrameNo, startDate, endDate, typeId)));

            for (int i = 0; i < vwWorkOrderBadComponentFaultTypeSums.size(); i++) {
                VwWorkOrderBadComponentFaultTypeSum vwWorkOrderBadComponentFaultTypeSum = vwWorkOrderBadComponentFaultTypeSums.get(i);
                String replaceEstates = "";
                Integer id = vwWorkOrderBadComponentFaultTypeSum.getId();
                List<VwWorkOrderModuleBadComponentCount> vwWorkOrderModuleBadComponentCounts = vwWorkOrderModuleBadComponentCountRepository.findByWorkOrderId(id);
                for (int j = 0; j < vwWorkOrderModuleBadComponentCounts.size(); j++) {
                    if (j < vwWorkOrderModuleBadComponentCounts.size() - 1) {
                        replaceEstates += vwWorkOrderModuleBadComponentCounts.get(j).getName() + ";";
                    } else {
                        replaceEstates += vwWorkOrderModuleBadComponentCounts.get(j).getName();
                    }
                }
                vwWorkOrderBadComponentFaultTypeSum.setReplaceEstates(replaceEstates);
            }


            builder.setResultEntity(vwWorkOrderBadComponentFaultTypeSums, ResponseCode.OK);

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    public Specification<VwWorkOrderBadComponentFaultTypeSum> queryConditionsTwice(Integer projectId, Integer stationId, Integer estateTypeId, Integer faultTypeId, Integer statusId, Integer repairEmployee,
                                                                                   Integer reportEmployee, Integer checkEmployee, Integer assignEmployee, Integer bikeFrameNo, Date startDate, Date endDate, Integer typeId) {
        return new Specification<VwWorkOrderBadComponentFaultTypeSum>() {
            public Predicate toPredicate(Root<VwWorkOrderBadComponentFaultTypeSum> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("queryConditions/findByConditions请求的参数corpId值为:{}", projectId);
                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.projectId), projectId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数stationId值为:{}", stationId);
                if (stationId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.stationId), stationId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数faultTypeId值为:{}", faultTypeId);
                if (faultTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.faultTypeId), faultTypeId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数statusId值为:{}", statusId);
                if (statusId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.statusId), statusId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数repairEmployee值为:{}", repairEmployee);
                if (repairEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.repairEmployee), repairEmployee));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数reportEmployee值为:{}", reportEmployee);
                if (reportEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.reportEmployee), reportEmployee));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数assignEmployee值为:{}", assignEmployee);
                if (assignEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.assignEmployee), statusId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数checkEmployee值为:{}", checkEmployee);
                if (checkEmployee != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.checkEmployee), checkEmployee));
                }


                LOGGER.debug("queryConditions/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwWorkOrderBadComponentFaultTypeSum_.reportTime), startDate));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwWorkOrderBadComponentFaultTypeSum_.reportTime), endDate));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数bikeFrameNo值为:{}", bikeFrameNo);
                if (bikeFrameNo != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.bikeFrameNo), bikeFrameNo));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数typeId值为:{}", typeId);
                if (typeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderBadComponentFaultTypeSum_.typeId), typeId));
                }

                return predicate;
            }
        };
    }
    // Dynamic End


}
