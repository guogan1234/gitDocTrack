/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.report;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.entityBO.RepairFaultStatisticsBO;
import com.avp.mem.njpb.entity.entityBO.StationEstateFaultStatisticsBo;
import com.avp.mem.njpb.entity.entityBO.StationRepairFaultStatisticsBo;
import com.avp.mem.njpb.entity.entityBO.WorkOrderFaultTypeBO;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.view.VwInventoryCheckRecord;
import com.avp.mem.njpb.entity.view.VwInventoryCheckRecord_;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount;
import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount_;
import com.avp.mem.njpb.entity.view.VwWorkOrder_;
import com.avp.mem.njpb.repository.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.repository.report.VwInventoryCheckRecordRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderModuleBadComponentCountRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Amber Wang on 2017-09-18 下午 02:30.
 */
@RestController
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    VwInventoryCheckRecordRepository vwInventoryCheckRecordRepository;

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    VwWorkOrderModuleBadComponentCountRepository vwWorkOrderModuleBadComponentCountRepository;

    /**
     * 站点设备故障情况统计--主表数据
     *
     * @return
     */
    @RequestMapping(value = "estateFaultStatistics/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> estateFaultStatistics(Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<StationEstateFaultStatisticsBo> stationEstateFaultStatisticsBos = new ArrayList<>();
        //String sql = "select project_id,estate_type_id,corp_name,estate_type_name,count(id) from business.vw_work_order where 1=1 ";
        try {
            if (Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            Integer categoryo = 0;
            if (Validator.isNotNull(estateTypeId)) {
                ObjEstateType objEstateType = objEstateTypeRepository.findOne(estateTypeId);
                categoryo = objEstateType.getCategory();
            }
            if (Validator.isNull(estateTypeId) || categoryo == MagicNumber.ONE) {

                String sql = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(estate_type_name, ' ') as estate_type_name,count(id) from business.vw_work_order where 1=1  and remove_time is null ";
                if (Validator.isNotNull(corpId)) {
                    if (corpId == 1) {
                        sql += " and project_id in (:corpId) ";
                    } else {
                        sql += " and project_id = :corpId ";
                    }

                }
                if (Validator.isNotNull(estateTypeId)) {
                    sql += " and estate_type_id = :estateTypeId ";
                }
                if (Validator.isNotNull(startDate)) {
                    sql += " and report_time > :startDate ";
                }
                if (Validator.isNotNull(endDate)) {
                    sql += " and report_time < :endDate ";
                }

                sql += " and report_way = :reportWay group by estate_type_id,project_id,corp_name,estate_type_name ";

                LOGGER.debug("sql:{}", sql);
                //执行原生SQL
                Query nativeQuery = entityManager.createNativeQuery(sql);

                List<Integer> t = new ArrayList<>();
                t.add(MagicNumber.ONE);
                t.add(MagicNumber.TWO);
                t.add(MagicNumber.THREE);
                t.add(MagicNumber.FOUR);
                t.add(MagicNumber.FIVE);
                t.add(MagicNumber.SIX);
                t.add(MagicNumber.SEVEN);
                t.add(MagicNumber.EIGHT);
                t.add(MagicNumber.NINE);
                t.add(MagicNumber.TEN);
                t.add(MagicNumber.ELEVEN);

                if (Validator.isNotNull(corpId)) {
                    if (corpId == 1) {
                        nativeQuery.setParameter("corpId", t);
                    } else {
                        nativeQuery.setParameter("corpId", corpId);
                    }

                }
                if (Validator.isNotNull(estateTypeId)) {
                    nativeQuery.setParameter("estateTypeId", estateTypeId);
                }
                if (Validator.isNotNull(startDate)) {
                    nativeQuery.setParameter("startDate", startDate);
                }
                if (Validator.isNotNull(endDate)) {
                    nativeQuery.setParameter("endDate", endDate);
                }
                nativeQuery.setParameter("reportWay", reportWay);

                List<Object> resultList = nativeQuery.getResultList();
                LOGGER.debug("sql:{}", resultList.size());


                for (int i = 0; i < resultList.size(); i++) {
                    Object[] obj = (Object[]) resultList.get(i);
                    if ((Integer) obj[MagicNumber.ONE] == 0) {
                        resultList.remove(i);
                    }
                }

                resultList.forEach(result -> {
                    StationEstateFaultStatisticsBo stationEstateFaultStatisticsBo = new StationEstateFaultStatisticsBo();
                    Object[] obj = (Object[]) result;

                    stationEstateFaultStatisticsBo.setProjectId((Integer) obj[MagicNumber.ZERO]);

                    stationEstateFaultStatisticsBo.setEstateTypeId((Integer) obj[MagicNumber.ONE]);
                    stationEstateFaultStatisticsBo.setCorpName(obj[MagicNumber.TWO].toString());
                    stationEstateFaultStatisticsBo.setEstateTypeName(obj[MagicNumber.THREE].toString());
                    stationEstateFaultStatisticsBo.setCount((BigInteger) obj[MagicNumber.FOUR]);

                    stationEstateFaultStatisticsBos.add(stationEstateFaultStatisticsBo);
                });
                LOGGER.debug("sql:{}", stationEstateFaultStatisticsBos.size());


            }
            if (Validator.isNull(estateTypeId) || categoryo == MagicNumber.TWO) {
                String sql2 = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(estate_type_name, ' ') as estate_type_name,count(id) from business.vw_work_order_module_bad_component_count where 1=1 and category = 2 and remove_time is null";

                if (Validator.isNull(reportWay)) {
                    builder.setResponseCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }

                if (Validator.isNotNull(corpId)) {
                    sql2 += " and project_id = :corpId ";
                }
                if (Validator.isNotNull(estateTypeId)) {
                    sql2 += " and estate_type_id = :estateTypeId ";
                }
                if (Validator.isNotNull(startDate)) {
                    sql2 += " and report_time > :startDate ";
                }
                if (Validator.isNotNull(endDate)) {
                    sql2 += " and report_time < :endDate ";
                }

                sql2 += " and report_way = :reportWay group by estate_type_id,project_id,corp_name,estate_type_name ";

                LOGGER.debug("sql2:{}", sql2);
                //执行原生SQL
                Query nativeQuery2 = entityManager.createNativeQuery(sql2);

                if (Validator.isNotNull(corpId)) {
                    nativeQuery2.setParameter("corpId", corpId);
                }
                if (Validator.isNotNull(estateTypeId)) {
                    nativeQuery2.setParameter("estateTypeId", estateTypeId);
                }
                if (Validator.isNotNull(startDate)) {
                    nativeQuery2.setParameter("startDate", startDate);
                }
                if (Validator.isNotNull(endDate)) {
                    nativeQuery2.setParameter("endDate", endDate);
                }
                nativeQuery2.setParameter("reportWay", reportWay);

                List<Object> resultList2 = nativeQuery2.getResultList();
                LOGGER.debug("sq2:{}", resultList2.size());

                for (int i = 0; i < resultList2.size(); i++) {
                    Object[] obj = (Object[]) resultList2.get(i);
                    if ((Integer) obj[MagicNumber.ONE] == 0) {
                        resultList2.remove(i);
                    }
                }

                resultList2.forEach(result -> {
                    StationEstateFaultStatisticsBo stationEstateFaultStatisticsBo2 = new StationEstateFaultStatisticsBo();
                    Object[] obj = (Object[]) result;

                    stationEstateFaultStatisticsBo2.setProjectId((Integer) obj[MagicNumber.ZERO]);
                    stationEstateFaultStatisticsBo2.setEstateTypeId((Integer) obj[MagicNumber.ONE]);
                    stationEstateFaultStatisticsBo2.setCorpName(obj[MagicNumber.TWO].toString());
                    stationEstateFaultStatisticsBo2.setEstateTypeName(obj[MagicNumber.THREE].toString());
                    stationEstateFaultStatisticsBo2.setCount((BigInteger) obj[MagicNumber.FOUR]);

                    stationEstateFaultStatisticsBos.add(stationEstateFaultStatisticsBo2);
                });
                LOGGER.debug("sq2:{}", stationEstateFaultStatisticsBos.size());
            }

            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", stationEstateFaultStatisticsBos.size());
            builder.setResultEntity(stationEstateFaultStatisticsBos, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点设备故障情况统计--子表数据
     *
     * @return
     */
    @RequestMapping(value = "estateFaultStatisticDetails/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwWorkOrder>> estateFaultStatisticDetails(Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<Integer> workOrderIds = new ArrayList<>();
            List<VwWorkOrder> resultList = new ArrayList<>();
            Integer categoryo = 0;
            if (Validator.isNotNull(estateTypeId)) {
                ObjEstateType objEstateType = objEstateTypeRepository.findOne(estateTypeId);
                categoryo = objEstateType.getCategory();
            }
            if (categoryo == MagicNumber.ONE) {
                resultList = vwWorkOrderRepository.findAll(where(queryEstateFaultStatisticDetailsEstateByConditions(reportWay, corpId, estateTypeId, startDate, endDate)));
            } else {
                List<VwWorkOrderModuleBadComponentCount> resultList1 = vwWorkOrderModuleBadComponentCountRepository.findAll(where(queryEstateFaultStatisticDetailsByConditions(reportWay, corpId, estateTypeId, startDate, endDate)));
                if (resultList1.size() > 0) {
                    for (int i = 0; i < resultList1.size(); i++) {
                        workOrderIds.add(resultList1.get(i).getWorkOrderId());
                    }
                }
                resultList = vwWorkOrderRepository.findByIdIn(workOrderIds);
            }

            LOGGER.debug("查询站点设备故障情况统计数据子表成功，数据量为:({})", resultList.size());
            builder.setResultEntity(resultList, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwWorkOrderModuleBadComponentCount> queryEstateFaultStatisticDetailsByConditions(Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate) {
        return new Specification<VwWorkOrderModuleBadComponentCount>() {
            public Predicate toPredicate(Root<VwWorkOrderModuleBadComponentCount> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderModuleBadComponentCount_.projectId), corpId));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数reportWay值为:{}", reportWay);
                if (reportWay != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderModuleBadComponentCount_.reportWay), reportWay));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderModuleBadComponentCount_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwWorkOrderModuleBadComponentCount_.reportTime), startDate));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwWorkOrderModuleBadComponentCount_.reportTime), endDate));
                }

                return predicate;
            }
        };
    }
    // Dynamic End

    // Dynamic Query Utils
    public Specification<VwWorkOrder> queryEstateFaultStatisticDetailsEstateByConditions(Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate) {
        return new Specification<VwWorkOrder>() {
            public Predicate toPredicate(Root<VwWorkOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.projectId), corpId));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数reportWay值为:{}", reportWay);
                if (reportWay != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.reportWay), reportWay));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwWorkOrder_.reportTime), startDate));
                }

                LOGGER.debug("estateFaultStatisticDetails/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwWorkOrder_.reportTime), endDate));
                }

                return predicate;
            }
        };
    }
    // Dynamic End

    /**
     * 盘点记录查询
     *
     * @return
     */
    @RequestMapping(value = "vwInventoryCheckRecord/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryCheckRecord>> findVwInventoryCheckRecordsByConditions(Integer userId, Integer stationId, Integer corpId, Date startDate, Date endDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwInventoryCheckRecord> resultList = vwInventoryCheckRecordRepository.findAll(where(queryVwInventoryCheckRecordsByConditions(userId, corpId, stationId, startDate, endDate)));

            LOGGER.debug("查询盘点记录成功，数据量为:({})", resultList.size());
            builder.setResultEntity(resultList, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    // Dynamic Query Utils
    public Specification<VwInventoryCheckRecord> queryVwInventoryCheckRecordsByConditions(Integer userId, Integer corpId, Integer stationId, Date startDate, Date endDate) {
        return new Specification<VwInventoryCheckRecord>() {
            public Predicate toPredicate(Root<VwInventoryCheckRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("evwInventoryCheckRecord/findByConditions请求的参数userId值为:{}", userId);
                if (userId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwInventoryCheckRecord_.checkUserId), userId));
                }

                LOGGER.debug("evwInventoryCheckRecord/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwInventoryCheckRecord_.corpId), corpId));
                }

                LOGGER.debug("evwInventoryCheckRecord/findByConditions请求的参数stationId值为:{}", stationId);
                if (stationId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwInventoryCheckRecord_.stationId), stationId));
                }

                LOGGER.debug("evwInventoryCheckRecord/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwInventoryCheckRecord_.checkTime), startDate));
                }

                LOGGER.debug("evwInventoryCheckRecord/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwInventoryCheckRecord_.checkTime), endDate));
                }

                return predicate;
            }
        };
    }
    // Dynamic End


    /**
     * 设备/模块二维码条件查询被使用的二维码
     *
     * @return
     */
    @RequestMapping(value = "report/printOut", method = RequestMethod.GET)
    ResponseEntity printOut() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        PrintUtil.printOut();
        builder.setResponseCode(ResponseCode.OK);
        return builder.getResponseEntity();
    }


    /**
     * 站点维修汇总
     *
     * @return
     */
    @RequestMapping(value = "stationFaultStatistics/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> stationFaultStatistics(Integer faultTypeId, Integer corpId, Integer estateTypeId, Date startDate, Date endDate, Integer reportWay, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<StationRepairFaultStatisticsBo> stationRepairFaultStatisticsBos = new ArrayList<>();
        try {
            if (Validator.isNull(corpId)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (Validator.isNull(reportWay)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            String sql2 = "";
            if (reportWay == 1) {

                sql2 = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(fault_type_id, 0) as fault_type_id,COALESCE(estate_type_name, ' ') as estate_type_name,COALESCE(fault_type_name, ' ') as fault_type_name,count(id) from business.vw_work_order_fault_type_count where 1=1 and remove_time is null";

            } else if (reportWay == 2) {

                sql2 = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(fault_type_id, 0) as fault_type_id,COALESCE(estate_type_name, ' ') as estate_type_name,COALESCE(fault_type_name, ' ') as fault_type_name,count(id) from business.vw_work_order_bad_component_fault_type where 1=1 and remove_time is null";

            }
            if (Validator.isNotNull(corpId)) {
                if (corpId == 1) {
                    sql2 += " and project_id in (:corpId) ";
                } else {
                    sql2 += " and project_id = :corpId ";
                }
            }
            if (Validator.isNotNull(faultTypeId)) {
                sql2 += " and fault_type_id = :faultTypeId ";
            }
            if (Validator.isNotNull(estateTypeId)) {
                sql2 += " and estate_type_id = :estateTypeId ";
            }
            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id = :typeId ";
            }


            if (Validator.isNotNull(startDate)) {
                sql2 += " and report_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {
                sql2 += " and report_time < :endDate ";
            }

            sql2 += " and report_way = :reportWay group by estate_type_id,project_id,corp_name,estate_type_name,fault_type_id,fault_type_name,type_id ";

            LOGGER.debug("sql2:{}", sql2);
            //执行原生SQL
            Query nativeQuery2 = entityManager.createNativeQuery(sql2);

            List<Integer> t = new ArrayList<>();
            t.add(MagicNumber.ONE);
            t.add(MagicNumber.TWO);
            t.add(MagicNumber.THREE);
            t.add(MagicNumber.FOUR);
            t.add(MagicNumber.FIVE);
            t.add(MagicNumber.SIX);
            t.add(MagicNumber.SEVEN);
            t.add(MagicNumber.EIGHT);
            t.add(MagicNumber.NINE);
            t.add(MagicNumber.TEN);
            t.add(MagicNumber.ELEVEN);

            if (Validator.isNotNull(corpId)) {
                if (corpId == 1) {
                    nativeQuery2.setParameter("corpId", t);
                } else {
                    nativeQuery2.setParameter("corpId", corpId);
                }

            }
            if (Validator.isNotNull(faultTypeId)) {
                nativeQuery2.setParameter("faultTypeId", faultTypeId);
            }

            if (Validator.isNotNull(estateTypeId)) {
                nativeQuery2.setParameter("estateTypeId", estateTypeId);
            }

            if (Validator.isNotNull(startDate)) {
                nativeQuery2.setParameter("startDate", startDate);
            }
            if (Validator.isNotNull(endDate)) {
                nativeQuery2.setParameter("endDate", endDate);
            }

            if (Validator.isNotNull(typeId)) {
                nativeQuery2.setParameter("typeId", typeId);
            }

            nativeQuery2.setParameter("reportWay", reportWay);

            List<Object> resultList2 = nativeQuery2.getResultList();
            LOGGER.debug("sq2:{}", resultList2.size());

            for (int i = 0; i < resultList2.size(); i++) {
                Object[] obj = (Object[]) resultList2.get(i);
                if ((Integer) obj[MagicNumber.ONE] == 0) {
                    resultList2.remove(i);
                }
            }

            LOGGER.debug("sq2:{}", resultList2.size());
            resultList2.forEach(result -> {
                StationRepairFaultStatisticsBo stationRepairFaultStatisticsBo = new StationRepairFaultStatisticsBo();
                Object[] obj = (Object[]) result;

                stationRepairFaultStatisticsBo.setProjectId((Integer) obj[MagicNumber.ZERO]);
                stationRepairFaultStatisticsBo.setEstateTypeId((Integer) obj[MagicNumber.ONE]);
                stationRepairFaultStatisticsBo.setCorpName(obj[MagicNumber.TWO].toString());
                stationRepairFaultStatisticsBo.setEstateTypeName(obj[MagicNumber.FOUR].toString());
                stationRepairFaultStatisticsBo.setCount((BigInteger) obj[MagicNumber.SIX]);
                stationRepairFaultStatisticsBo.setFaultTypeId((Integer) obj[MagicNumber.THREE]);
                stationRepairFaultStatisticsBo.setFaultTypeName(obj[MagicNumber.FIVE].toString());
                stationRepairFaultStatisticsBos.add(stationRepairFaultStatisticsBo);
            });
            LOGGER.debug("sq2:{}", stationRepairFaultStatisticsBos.size());


            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", stationRepairFaultStatisticsBos.size());
            builder.setResultEntity(stationRepairFaultStatisticsBos, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 维修汇总
     *
     * @return
     */
    @RequestMapping(value = "repairFaultStatistics/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> repairFaultStatistics(Integer corpId, Date startDate, Date endDate, Integer reportWay, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            String sql2 = "select project_id,corp_name,count(id),report_way,type_id from business.vw_user_work_order where 1=1 and remove_time is null and uid =" + uid;

            if (Validator.isNotNull(corpId)) {
                if (corpId == 1) {
                    sql2 += " and project_id in (:corpId) ";
                } else {
                    sql2 += " and project_id = :corpId ";
                }
            }

            if (Validator.isNotNull(reportWay)) {
                sql2 += " and report_way > :reportWay ";

            }
            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id > :typeId ";

            }
            if (Validator.isNotNull(startDate)) {

                sql2 += " and report_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {

                sql2 += " and report_time < :endDate ";
            }

            sql2 += "group by report_way,project_id,corp_name,type_id";

            LOGGER.debug("sql2:{}", sql2);
            //执行原生SQL
            Query nativeQuery2 = entityManager.createNativeQuery(sql2);

            List<Integer> t = new ArrayList<>();
            t.add(MagicNumber.ONE);
            t.add(MagicNumber.TWO);
            t.add(MagicNumber.THREE);
            t.add(MagicNumber.FOUR);
            t.add(MagicNumber.FIVE);
            t.add(MagicNumber.SIX);
            t.add(MagicNumber.SEVEN);
            t.add(MagicNumber.EIGHT);
            t.add(MagicNumber.NINE);
            t.add(MagicNumber.TEN);
            t.add(MagicNumber.ELEVEN);

            if (Validator.isNotNull(corpId)) {
                if (corpId == 1) {
                    nativeQuery2.setParameter("corpId", t);
                } else {
                    nativeQuery2.setParameter("corpId", corpId);
                }

            }
            if (Validator.isNotNull(typeId)) {
                nativeQuery2.setParameter("typeId", typeId);
            }
            if (Validator.isNotNull(reportWay)) {
                nativeQuery2.setParameter("reportWay", reportWay);
            }

            if (Validator.isNotNull(startDate)) {
                nativeQuery2.setParameter("startDate", startDate);
            }
            if (Validator.isNotNull(endDate)) {
                nativeQuery2.setParameter("endDate", endDate);
            }

            List<Object> resultList2 = nativeQuery2.getResultList();
            LOGGER.debug("sq2:{}", resultList2.size());

//            for (int i = 0; i < resultList2.size(); i++) {
//                Object[] obj = (Object[]) resultList2.get(i);
//                if ((Integer) obj[MagicNumber.ONE] == 0) {
//                    resultList2.remove(i);
//                }
//            }

            LOGGER.debug("sq2:{}", resultList2.size());
            resultList2.forEach(result -> {
                RepairFaultStatisticsBO repairFaultStatisticsBO = new RepairFaultStatisticsBO();
                Object[] obj = (Object[]) result;

                repairFaultStatisticsBO.setProjectId((Integer) obj[MagicNumber.ZERO]);
                repairFaultStatisticsBO.setReportWay((Integer) obj[MagicNumber.THREE]);
                repairFaultStatisticsBO.setCorpName(obj[MagicNumber.ONE].toString());
                repairFaultStatisticsBO.setTypeId((Integer) obj[MagicNumber.FOUR]);
                repairFaultStatisticsBO.setCount((BigInteger) obj[MagicNumber.TWO]);
//                stationRepairFaultStatisticsBo.setFaultTypeId((Integer) obj[MagicNumber.THREE]);
                repairFaultStatisticsBOS.add(repairFaultStatisticsBO);
            });
            LOGGER.debug("sq2:{}", repairFaultStatisticsBOS.size());


            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", repairFaultStatisticsBOS.size());
            builder.setResultEntity(repairFaultStatisticsBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点维修列表
     *
     * @return
     */
    @RequestMapping(value = "stationRepairList/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> stationRepairConditions(Integer corpId, Date startDate, Date endDate, Integer stationId, Integer estateTypeId, Integer faultTypeId, Integer statusId, Integer reportEmployee,
                                                                                     Integer assignEmployee, Integer repairEmployee, Integer checkEmployee, Integer typeId, Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<WorkOrderFaultTypeBO> workOrderFaultTypeBOS = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            String sql2 = "select project_id,corp_name,count(id),station_id,station_name,station_no,estate_type_id,estate_type_name,fault_type_id, fault_type_name,status_id,work_order_status_name_cn, repair_employee,repair_employee_user_name from business.vw_work_order_fault_type where 1=1 and remove_time is null and parts_type=" + partsType;

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    sql2 += " and project_id in (:corpId) ";
                } else {
                    sql2 += " and project_id = :corpId ";
                }
            }

            if (Validator.isNotNull(stationId) && stationId != -1) {

                sql2 += " and station_id = :stationId ";

            }
            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                sql2 += " and estate_type_id = :estateTypeId ";

            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {
                sql2 += " and fault_type_id = :faultTypeId ";

            }
            if (Validator.isNotNull(statusId) && statusId != -1) {
                sql2 += " and status_id = :statusId ";
            }


            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                sql2 += " and report_employee = :reportEmployee ";

            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                sql2 += " and assign_employee = :assignEmployee ";

            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                sql2 += " and repair_employee = :repairEmployee ";

            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                sql2 += " and check_employee = :checkEmployee ";
            }
            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id = :typeId ";
            }

            if (Validator.isNotNull(startDate)) {

                sql2 += " and report_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {

                sql2 += " and report_time < :endDate ";
            }

            sql2 += "group by project_id,corp_name,station_id,station_name,station_no," +
                    "estate_type_id,estate_type_name,fault_type_id,fault_type_name,status_id,work_order_status_name_cn," +
                    "repair_employee,repair_employee_user_name,report_employee,assign_employee,check_employee";

            LOGGER.debug("sql2:{}", sql2);
            //执行原生SQL
            Query nativeQuery2 = entityManager.createNativeQuery(sql2);

            List<Integer> t = new ArrayList<>();
            t.add(MagicNumber.ONE);
            t.add(MagicNumber.TWO);
            t.add(MagicNumber.THREE);
            t.add(MagicNumber.FOUR);
            t.add(MagicNumber.FIVE);
            t.add(MagicNumber.SIX);
            t.add(MagicNumber.SEVEN);
            t.add(MagicNumber.EIGHT);
            t.add(MagicNumber.NINE);
            t.add(MagicNumber.TEN);
            t.add(MagicNumber.ELEVEN);

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    nativeQuery2.setParameter("corpId", t);
                } else {
                    nativeQuery2.setParameter("corpId", corpId);
                }

            }
            if (Validator.isNotNull(stationId) && stationId != -1) {

                nativeQuery2.setParameter("stationId", stationId);

            }
            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                nativeQuery2.setParameter("estateTypeId", estateTypeId);
            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {

                nativeQuery2.setParameter("faultTypeId", faultTypeId);
            }
            if (Validator.isNotNull(statusId) && statusId != -1) {

                nativeQuery2.setParameter("statusId", statusId);
            }
            if (Validator.isNotNull(typeId)) {

                nativeQuery2.setParameter("typeId", typeId);
            }

            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                nativeQuery2.setParameter("reportEmployee", reportEmployee);
            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                nativeQuery2.setParameter("assignEmployee", assignEmployee);
            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                nativeQuery2.setParameter("repairEmployee", repairEmployee);
            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                nativeQuery2.setParameter("checkEmployee", checkEmployee);
            }


            if (Validator.isNotNull(startDate)) {
                nativeQuery2.setParameter("startDate", startDate);
            }
            if (Validator.isNotNull(endDate)) {
                nativeQuery2.setParameter("endDate", endDate);
            }

            List<Object> resultList2 = nativeQuery2.getResultList();
            LOGGER.debug("sq2:{}", resultList2.size());

            LOGGER.debug("sq2:{}", resultList2.size());
            resultList2.forEach(result -> {
                WorkOrderFaultTypeBO workOrderFaultTypeBO = new WorkOrderFaultTypeBO();
                Object[] obj = (Object[]) result;

                if (obj[MagicNumber.ZERO] != null) {
                    workOrderFaultTypeBO.setProjectId((Integer) obj[MagicNumber.ZERO]);
                    workOrderFaultTypeBO.setCorpName(obj[MagicNumber.ONE].toString());
                }

                workOrderFaultTypeBO.setCount((BigInteger) obj[MagicNumber.TWO]);
                if (obj[MagicNumber.THREE] != null) {
                    workOrderFaultTypeBO.setStationId((Integer) obj[MagicNumber.THREE]);
                    workOrderFaultTypeBO.setStationName(obj[MagicNumber.FOUR].toString());
                    workOrderFaultTypeBO.setStationNo(obj[MagicNumber.FIVE].toString());
                }


                if (obj[MagicNumber.SIX] != null) {
                    workOrderFaultTypeBO.setEstateTypeId((Integer) obj[MagicNumber.SIX]);
                    workOrderFaultTypeBO.setEstateTypeName(obj[MagicNumber.SEVEN].toString());
                }


                if (obj[MagicNumber.EIGHT] != null) {
                    workOrderFaultTypeBO.setFaultTypeId((Integer) obj[MagicNumber.EIGHT]);
                    workOrderFaultTypeBO.setFaultTypeName(obj[MagicNumber.NINE].toString());
                }


                if (obj[MagicNumber.TEN] != null) {
                    workOrderFaultTypeBO.setStatusId((Integer) obj[MagicNumber.TEN]);
                    workOrderFaultTypeBO.setWorkOrderStatusNameCn(obj[MagicNumber.ELEVEN].toString());
                }

                if (obj[MagicNumber.ONE_TWO] != null) {
                    workOrderFaultTypeBO.setRepairEmployee((Integer) obj[MagicNumber.ONE_TWO]);
                    workOrderFaultTypeBO.setRepairEmployeeUserName(obj[MagicNumber.ONE_THREE].toString());
                }


                workOrderFaultTypeBOS.add(workOrderFaultTypeBO);
            });
            LOGGER.debug("sq2:{}", workOrderFaultTypeBOS.size());


            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", workOrderFaultTypeBOS.size());
            builder.setResultEntity(workOrderFaultTypeBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 自行车维修列表
     *
     * @return
     */
    @RequestMapping(value = "bikeRepairList/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> bikeFindByConditions(Integer corpId, Date startDate, Date endDate, Integer stationId, Integer bikeFrameNo, Integer estateTypeId, Integer faultTypeId, Integer statusId, Integer reportEmployee,
                                                                                  Integer assignEmployee, Integer repairEmployee, Integer checkEmployee, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<WorkOrderFaultTypeBO> workOrderFaultTypeBOS = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            String sql2 = "select project_id,corp_name,count(id),station_id,station_name,station_no,estate_type_id,estate_type_name,fault_type_id, fault_type_name,status_id,work_order_status_name_cn,repair_employee,repair_employee_user_name ,bike_frame_no from business.vw_work_order_bad_component_fault_type_sum where 1=1 and remove_time is null ";

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    sql2 += " and project_id in (:corpId) ";
                } else {
                    sql2 += " and project_id = :corpId ";
                }
            }

            if (Validator.isNotNull(stationId) && stationId != -1) {

                sql2 += " and station_id = :stationId ";

            }
            if (Validator.isNotNull(bikeFrameNo)) {

                sql2 += " and bike_frame_no = :bikeFrameNo ";

            }

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                sql2 += " and estate_type_id = :estateTypeId ";

            }

            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id = :typeId ";

            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {
                sql2 += " and fault_type_id = :faultTypeId ";

            }
            if (Validator.isNotNull(statusId) && statusId != -1) {
                sql2 += " and status_id = :statusId ";
            }


            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                sql2 += " and report_employee = :reportEmployee ";

            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                sql2 += " and assign_employee = :assignEmployee ";

            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                sql2 += " and repair_employee = :repairEmployee ";

            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                sql2 += " and check_employee = :checkEmployee ";
            }


            if (Validator.isNotNull(startDate)) {

                sql2 += " and report_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {

                sql2 += " and report_time < :endDate ";
            }

            sql2 += "group by project_id,corp_name,station_id,station_name,station_no," +
                    "bike_frame_no,estate_type_id,estate_type_name,fault_type_id,fault_type_name,status_id,work_order_status_name_cn," +
                    "repair_employee,repair_employee_user_name,report_employee,assign_employee,check_employee";

            LOGGER.debug("sql2:{}", sql2);
            //执行原生SQL
            Query nativeQuery2 = entityManager.createNativeQuery(sql2);

            List<Integer> t = new ArrayList<>();
            t.add(MagicNumber.ONE);
            t.add(MagicNumber.TWO);
            t.add(MagicNumber.THREE);
            t.add(MagicNumber.FOUR);
            t.add(MagicNumber.FIVE);
            t.add(MagicNumber.SIX);
            t.add(MagicNumber.SEVEN);
            t.add(MagicNumber.EIGHT);
            t.add(MagicNumber.NINE);
            t.add(MagicNumber.TEN);
            t.add(MagicNumber.ELEVEN);

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    nativeQuery2.setParameter("corpId", t);
                } else {
                    nativeQuery2.setParameter("corpId", corpId);
                }

            }
            if (Validator.isNotNull(stationId) && stationId != -1) {

                nativeQuery2.setParameter("stationId", stationId);

            }

            if (Validator.isNotNull(bikeFrameNo)) {
                nativeQuery2.setParameter("bikeFrameNo", bikeFrameNo);

            }

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                nativeQuery2.setParameter("estateTypeId", estateTypeId);
            }
            if (Validator.isNotNull(typeId)) {
                nativeQuery2.setParameter("typeId", typeId);
            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {

                nativeQuery2.setParameter("faultTypeId", faultTypeId);
            }
            if (Validator.isNotNull(statusId) && statusId != -1) {

                nativeQuery2.setParameter("statusId", statusId);
            }


            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                nativeQuery2.setParameter("reportEmployee", reportEmployee);
            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                nativeQuery2.setParameter("assignEmployee", assignEmployee);
            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                nativeQuery2.setParameter("repairEmployee", repairEmployee);
            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                nativeQuery2.setParameter("checkEmployee", checkEmployee);
            }


            if (Validator.isNotNull(startDate)) {
                nativeQuery2.setParameter("startDate", startDate);
            }
            if (Validator.isNotNull(endDate)) {
                nativeQuery2.setParameter("endDate", endDate);
            }

            List<Object> resultList2 = nativeQuery2.getResultList();
            LOGGER.debug("sq2:{}", resultList2.size());

//            for (int i = 0; i < resultList2.size(); i++) {
//                Object[] obj = (Object[]) resultList2.get(i);
//                if ((Integer) obj[MagicNumber.ONE] == 0) {
//                    resultList2.remove(i);
//                }
//            }

            LOGGER.debug("sq2:{}", resultList2.size());
            resultList2.forEach(result -> {
                WorkOrderFaultTypeBO workOrderFaultTypeBO = new WorkOrderFaultTypeBO();
                Object[] obj = (Object[]) result;

                if (obj[MagicNumber.ZERO] != null) {
                    workOrderFaultTypeBO.setProjectId((Integer) obj[MagicNumber.ZERO]);
                    workOrderFaultTypeBO.setCorpName(obj[MagicNumber.ONE].toString());
                }

                workOrderFaultTypeBO.setCount((BigInteger) obj[MagicNumber.TWO]);
                if (obj[MagicNumber.THREE] != null) {
                    workOrderFaultTypeBO.setStationId((Integer) obj[MagicNumber.THREE]);
                    workOrderFaultTypeBO.setStationName(obj[MagicNumber.FOUR].toString());
                    workOrderFaultTypeBO.setStationNo(obj[MagicNumber.FIVE].toString());
                }


                if (obj[MagicNumber.SIX] != null) {
                    workOrderFaultTypeBO.setEstateTypeId((Integer) obj[MagicNumber.SIX]);
                    workOrderFaultTypeBO.setEstateTypeName(obj[MagicNumber.SEVEN].toString());
                }


                if (obj[MagicNumber.EIGHT] != null) {
                    workOrderFaultTypeBO.setFaultTypeId((Integer) obj[MagicNumber.EIGHT]);
                    workOrderFaultTypeBO.setFaultTypeName(obj[MagicNumber.NINE].toString());
                }


                if (obj[MagicNumber.TEN] != null) {
                    workOrderFaultTypeBO.setStatusId((Integer) obj[MagicNumber.TEN]);
                    workOrderFaultTypeBO.setWorkOrderStatusNameCn(obj[MagicNumber.ELEVEN].toString());
                }

                if (obj[MagicNumber.ONE_TWO] != null) {
                    workOrderFaultTypeBO.setRepairEmployee((Integer) obj[MagicNumber.ONE_TWO]);
                    workOrderFaultTypeBO.setRepairEmployeeUserName(obj[MagicNumber.ONE_THREE].toString());
                }
                if (obj[MagicNumber.ONE_FOUR] != null) {

                    workOrderFaultTypeBO.setBikeFrameNo((Integer) obj[MagicNumber.ONE_FOUR]);
                }


                workOrderFaultTypeBOS.add(workOrderFaultTypeBO);
            });
            LOGGER.debug("sq2:{}", workOrderFaultTypeBOS.size());


            LOGGER.debug("查询自行车设备故障情况统计数据成功，数据量为:({})", workOrderFaultTypeBOS.size());
            builder.setResultEntity(workOrderFaultTypeBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点设备出入库管理
     *
     * @return
     */
    @RequestMapping(value = "stationEstateInOutStock/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> stationEstateInOutStock(Integer corpId, Date startDate, Date endDate, Integer stationId, Integer bikeFrameNo, Integer estateTypeId, Integer faultTypeId, Integer statusId, Integer reportEmployee,
                                                                                     Integer assignEmployee, Integer repairEmployee, Integer checkEmployee, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<WorkOrderFaultTypeBO> workOrderFaultTypeBOS = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            String sql2 = "select project_id,corp_name,count(id),station_id,station_name,station_no,estate_type_id,estate_type_name,fault_type_id, fault_type_name,status_id,work_order_status_name_cn,repair_employee,repair_employee_user_name ,bike_frame_no from business.vw_work_order_bad_component_fault_type_sum where 1=1 and remove_time is null ";

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    sql2 += " and project_id in (:corpId) ";
                } else {
                    sql2 += " and project_id = :corpId ";
                }
            }

            if (Validator.isNotNull(stationId) && stationId != -1) {

                sql2 += " and station_id = :stationId ";

            }
            if (Validator.isNotNull(bikeFrameNo)) {

                sql2 += " and bike_frame_no = :bikeFrameNo ";

            }

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                sql2 += " and estate_type_id = :estateTypeId ";

            }

            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id = :typeId ";

            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {
                sql2 += " and fault_type_id = :faultTypeId ";

            }
            if (Validator.isNotNull(statusId) && statusId != -1) {
                sql2 += " and status_id = :statusId ";
            }


            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                sql2 += " and report_employee = :reportEmployee ";

            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                sql2 += " and assign_employee = :assignEmployee ";

            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                sql2 += " and repair_employee = :repairEmployee ";

            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                sql2 += " and check_employee = :checkEmployee ";
            }


            if (Validator.isNotNull(startDate)) {

                sql2 += " and report_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {

                sql2 += " and report_time < :endDate ";
            }

            sql2 += "group by project_id,corp_name,station_id,station_name,station_no," +
                    "bike_frame_no,estate_type_id,estate_type_name,fault_type_id,fault_type_name,status_id,work_order_status_name_cn," +
                    "repair_employee,repair_employee_user_name,report_employee,assign_employee,check_employee";

            LOGGER.debug("sql2:{}", sql2);
            //执行原生SQL
            Query nativeQuery2 = entityManager.createNativeQuery(sql2);

            List<Integer> t = new ArrayList<>();
            t.add(MagicNumber.ONE);
            t.add(MagicNumber.TWO);
            t.add(MagicNumber.THREE);
            t.add(MagicNumber.FOUR);
            t.add(MagicNumber.FIVE);
            t.add(MagicNumber.SIX);
            t.add(MagicNumber.SEVEN);
            t.add(MagicNumber.EIGHT);
            t.add(MagicNumber.NINE);
            t.add(MagicNumber.TEN);
            t.add(MagicNumber.ELEVEN);

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    nativeQuery2.setParameter("corpId", t);
                } else {
                    nativeQuery2.setParameter("corpId", corpId);
                }

            }
            if (Validator.isNotNull(stationId) && stationId != -1) {

                nativeQuery2.setParameter("stationId", stationId);

            }

            if (Validator.isNotNull(bikeFrameNo)) {
                nativeQuery2.setParameter("bikeFrameNo", bikeFrameNo);

            }

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                nativeQuery2.setParameter("estateTypeId", estateTypeId);
            }
            if (Validator.isNotNull(typeId)) {
                nativeQuery2.setParameter("typeId", typeId);
            }
            if (Validator.isNotNull(faultTypeId) && faultTypeId != -1) {

                nativeQuery2.setParameter("faultTypeId", faultTypeId);
            }
            if (Validator.isNotNull(statusId) && statusId != -1) {

                nativeQuery2.setParameter("statusId", statusId);
            }


            if (Validator.isNotNull(reportEmployee) && reportEmployee != -1) {
                nativeQuery2.setParameter("reportEmployee", reportEmployee);
            }
            if (Validator.isNotNull(assignEmployee) && assignEmployee != -1) {
                nativeQuery2.setParameter("assignEmployee", assignEmployee);
            }
            if (Validator.isNotNull(repairEmployee) && repairEmployee != -1) {
                nativeQuery2.setParameter("repairEmployee", repairEmployee);
            }
            if (Validator.isNotNull(checkEmployee) && checkEmployee != -1) {
                nativeQuery2.setParameter("checkEmployee", checkEmployee);
            }


            if (Validator.isNotNull(startDate)) {
                nativeQuery2.setParameter("startDate", startDate);
            }
            if (Validator.isNotNull(endDate)) {
                nativeQuery2.setParameter("endDate", endDate);
            }

            List<Object> resultList2 = nativeQuery2.getResultList();
            LOGGER.debug("sq2:{}", resultList2.size());

//            for (int i = 0; i < resultList2.size(); i++) {
//                Object[] obj = (Object[]) resultList2.get(i);
//                if ((Integer) obj[MagicNumber.ONE] == 0) {
//                    resultList2.remove(i);
//                }
//            }

            LOGGER.debug("sq2:{}", resultList2.size());
            resultList2.forEach(result -> {
                WorkOrderFaultTypeBO workOrderFaultTypeBO = new WorkOrderFaultTypeBO();
                Object[] obj = (Object[]) result;

                if (obj[MagicNumber.ZERO] != null) {
                    workOrderFaultTypeBO.setProjectId((Integer) obj[MagicNumber.ZERO]);
                    workOrderFaultTypeBO.setCorpName(obj[MagicNumber.ONE].toString());
                }

                workOrderFaultTypeBO.setCount((BigInteger) obj[MagicNumber.TWO]);
                if (obj[MagicNumber.THREE] != null) {
                    workOrderFaultTypeBO.setStationId((Integer) obj[MagicNumber.THREE]);
                    workOrderFaultTypeBO.setStationName(obj[MagicNumber.FOUR].toString());
                    workOrderFaultTypeBO.setStationNo(obj[MagicNumber.FIVE].toString());
                }


                if (obj[MagicNumber.SIX] != null) {
                    workOrderFaultTypeBO.setEstateTypeId((Integer) obj[MagicNumber.SIX]);
                    workOrderFaultTypeBO.setEstateTypeName(obj[MagicNumber.SEVEN].toString());
                }


                if (obj[MagicNumber.EIGHT] != null) {
                    workOrderFaultTypeBO.setFaultTypeId((Integer) obj[MagicNumber.EIGHT]);
                    workOrderFaultTypeBO.setFaultTypeName(obj[MagicNumber.NINE].toString());
                }


                if (obj[MagicNumber.TEN] != null) {
                    workOrderFaultTypeBO.setStatusId((Integer) obj[MagicNumber.TEN]);
                    workOrderFaultTypeBO.setWorkOrderStatusNameCn(obj[MagicNumber.ELEVEN].toString());
                }

                if (obj[MagicNumber.ONE_TWO] != null) {
                    workOrderFaultTypeBO.setRepairEmployee((Integer) obj[MagicNumber.ONE_TWO]);
                    workOrderFaultTypeBO.setRepairEmployeeUserName(obj[MagicNumber.ONE_THREE].toString());
                }
                if (obj[MagicNumber.ONE_FOUR] != null) {

                    workOrderFaultTypeBO.setBikeFrameNo((Integer) obj[MagicNumber.ONE_FOUR]);
                }


                workOrderFaultTypeBOS.add(workOrderFaultTypeBO);
            });
            LOGGER.debug("sq2:{}", workOrderFaultTypeBOS.size());


            LOGGER.debug("查询自行车设备故障情况统计数据成功，数据量为:({})", workOrderFaultTypeBOS.size());
            builder.setResultEntity(workOrderFaultTypeBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
