/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.exportExcel;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjCorporation;
import com.avp.mem.njpb.entity.entityBO.RepairFaultStatisticsBO;
import com.avp.mem.njpb.entity.entityBO.StationEstateFaultStatisticsBo;
import com.avp.mem.njpb.entity.entityBO.StationRepairFaultStatisticsBo;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwInventoryCheckRecord;
import com.avp.mem.njpb.entity.view.VwInventoryCheckRecord_;
import com.avp.mem.njpb.entity.view.VwStockRecord;
import com.avp.mem.njpb.entity.view.VwStockRecord_;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder_;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponentFaultTypeSum;
import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponentFaultTypeSum_;
import com.avp.mem.njpb.entity.view.VwWorkOrderFaultType;
import com.avp.mem.njpb.entity.view.VwWorkOrderFaultType_;
import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount;
import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount_;
import com.avp.mem.njpb.entity.view.VwWorkOrder_;
import com.avp.mem.njpb.repository.basic.ObjCorporationRepository;
import com.avp.mem.njpb.repository.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.repository.report.VwInventoryCheckRecordRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.workorder.VwUserWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderBadComponentFaultTypeSumRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderFaultTypeRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderModuleBadComponentCountRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.ExcelUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;


/**
 * Created by len on 2017/10/16.
 */
@RestController
public class ReportExportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportExportController.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    VwInventoryCheckRecordRepository vwInventoryCheckRecordRepository;

    @Autowired
    VwUserWorkOrderRepository vwUserWorkOrderRepository;

    @Autowired
    VwWorkOrderModuleBadComponentCountRepository vwWorkOrderModuleBadComponentCountRepository;

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    VwStockRecordRepository vwStockRecordRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    ObjCorporationRepository objCorporationRepository;


    @Autowired
    VwWorkOrderFaultTypeRepository vwWorkOrderFaultTypeRepository;

    @Autowired
    VwWorkOrderBadComponentFaultTypeSumRepository vwWorkOrderBadComponentFaultTypeSumRepository;

    /**
     * 工分统计导出
     *
     * @param response
     * @param projectId
     * @param repairEmployee
     * @param beginTime
     * @param endTime
     */
    @RequestMapping(value = "workOrderScore/exportWorkOrder", method = RequestMethod.POST)
    public void exportWorkOrder(HttpServletResponse response, @RequestParam(value = "projectId", required = false) Integer projectId,
                                @RequestParam(value = "repairEmployee", required = false) Integer repairEmployee,
                                @RequestParam(value = "beginTime", required = false) Date beginTime,
                                @RequestParam(value = "endTime", required = false) Date endTime) {

        //查询list
        List<VwWorkOrder> vwWorkOrders = new ArrayList<>();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "工分统计";
            //sheet名


//            String[] title = new String[]{"工分统计"};
            String[] title = new String[]{"工单编号", "类别", "设备名称", "工分", "所属公司", "姓名", "操作时间"};
            Sort sort = new Sort(Sort.Direction.DESC, "lastUpdateTime");
            vwWorkOrders = vwWorkOrderRepository.findAll(where(byConditionsWorkOrder(projectId, repairEmployee, beginTime, endTime)), sort);

            LOGGER.debug("数量为({})", vwWorkOrders.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[vwWorkOrders.size()][];

            for (int i = 0; i < vwWorkOrders.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwWorkOrder obj = vwWorkOrders.get(i);
                values[i][MagicNumber.ZERO] = obj.getSerialNo();
                String category = "";
                if (obj.getCategory() == MagicNumber.ZERO) {
                    category = "站点";
                } else {
                    category = "车辆";
                }
                values[i][MagicNumber.ONE] = category;
                values[i][MagicNumber.TWO] = obj.getEstateName();
                values[i][MagicNumber.THREE] = obj.getWorkOrderScore() + "";
                values[i][MagicNumber.FOUR] = obj.getCorpName();
                values[i][MagicNumber.FIVE] = obj.getRepairEmployeeUserName();
                Date d = obj.getLastUpdateTime();
                String lastUpdateTime = sdf.format(d);
                values[i][MagicNumber.SIX] = lastUpdateTime;
//                barcodeSns.add(obj.getBarCodeSn());
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出错， {}", e.getMessage());
        }
    }

    public static Specification<VwWorkOrder> byConditionsWorkOrder(Integer projectId, Integer repairEmployee, Date beginTime, Date endTime) {
        return new Specification<VwWorkOrder>() {
            public Predicate toPredicate(Root<VwWorkOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.projectId), projectId));
                }
                if (repairEmployee != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwWorkOrder_.repairEmployee), repairEmployee));
                }

                if (beginTime != null) {
                    predicate.getExpressions().add(
                            builder.greaterThan(root.get(VwWorkOrder_.lastUpdateTime), beginTime));
                }

                if (endTime != null) {
                    predicate.getExpressions().add(
                            builder.lessThan(root.get(VwWorkOrder_.lastUpdateTime), endTime));
                }
//                Order order = builder.desc(root.get(VwWorkOrder_.lastUpdateTime));
//                query.orderBy(order);
//                predicate.getExpressions().add(query.getRestriction());
                return predicate;
            }
        };
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设备故障统计
     *
     * @param response
     * @param reportWay
     * @param corpId
     * @param estateTypeId
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "estateFault/exportFaultStatistics", method = RequestMethod.POST)
    public void exportFaultStatistics(HttpServletResponse response, Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate) {

        List<StationEstateFaultStatisticsBo> stationEstateFaultStatisticsBos = new ArrayList<>();
        //String sql = "select project_id,estate_type_id,corp_name,estate_type_name,count(id) from business.vw_work_order where 1=1 ";
        try {

            Integer categoryo = 0;
            if (Validator.isNotNull(estateTypeId)) {
                ObjEstateType objEstateType = objEstateTypeRepository.findOne(estateTypeId);
                categoryo = objEstateType.getCategory();
            }
            if (Validator.isNull(estateTypeId) || categoryo == MagicNumber.ONE) {


                String sql = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(estate_type_name, ' '),count(id) from business.vw_work_order where 1=1 and remove_time is null";
                if (Validator.isNotNull(corpId)) {
                    sql += " and project_id = :corpId ";
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

                if (Validator.isNotNull(corpId)) {
                    nativeQuery.setParameter("corpId", corpId);
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


            }
            if (Validator.isNull(estateTypeId) || categoryo == MagicNumber.TWO) {
                String sql2 = "select project_id,COALESCE(estate_type_id, 0) as estate_type_id,corp_name,COALESCE(estate_type_name, ' '),count(id) from business.vw_work_order_module_bad_component_count where 1=1 and category = 2 and remove_time is null ";


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
            }


            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            if (reportWay == MagicNumber.ONE) {
                sheetName = "站点设备故障统计";
                //标题
                title = new String[]{"设备类型", "故障数"};
            } else {
                sheetName = "车辆故障统计";
                //标题
                title = new String[]{"配件类型", "故障数"};
            }
//            String sheetName = "站点设备故障统计";
//            //sheet名

            //标题

//            String[] title = new String[]{"设备类型", "故障数"};


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[stationEstateFaultStatisticsBos.size()][];
            for (int i = 0; i < stationEstateFaultStatisticsBos.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                StationEstateFaultStatisticsBo obj = stationEstateFaultStatisticsBos.get(i);
                values[i][MagicNumber.ZERO] = obj.getEstateTypeName();
                values[i][MagicNumber.ONE] = obj.getCount() + "";


//                barcodeSns.add(obj.getBarCodeSn());
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出出错， {}", e.getMessage());
        }
    }


    /**
     * 盘点记录查询
     *
     * @return
     */
    @RequestMapping(value = "bikeCheckRecord/exportCheckStatistics", method = RequestMethod.POST)
    public void exportCheckStatistics(HttpServletResponse response, Integer corpId, Integer stationId, Integer userId, Date startDate, Date endDate) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "lastUpdateTime");
            List<VwInventoryCheckRecord> resultList = vwInventoryCheckRecordRepository.findAll(where(queryVwInventoryCheckRecordsByConditions(userId, corpId, stationId, startDate, endDate)), sort);

            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            //sheet名
            String sheetName = "车辆盘点统计";

//            String[] title = new String[]{"工分统计"};
            String[] title = new String[]{"操作人", "公司", "站点", "数量", "提交时间"};
            LOGGER.debug("数量为({})", resultList.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[resultList.size()][];

            for (int i = 0; i < resultList.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwInventoryCheckRecord obj = resultList.get(i);
                values[i][MagicNumber.ZERO] = obj.getCheckUserName();
                values[i][MagicNumber.ONE] = obj.getCorpName();
                values[i][MagicNumber.TWO] = obj.getStationNoName();
                values[i][MagicNumber.THREE] = obj.getCount() + "";
                Date d = obj.getCheckTime();
                String lastUpdateTime = sdf.format(d);
                values[i][MagicNumber.FOUR] = lastUpdateTime;

            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

            LOGGER.debug("查询盘点记录成功，数据量为:({})", resultList.size());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
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
     * 工单导出
     *
     * @param response
     * @param corpId
     * @param stationId
     * @param workOrderTypeId
     * @param workOrderStatusId
     * @param estateTypeId
     * @param reportWay
     * @param assignEmployee
     * @param beginTime
     * @param endTime
     */
    @RequestMapping(value = "workOrder/exportWorkOrder", method = RequestMethod.POST)
    public void exportWorkOrder(HttpServletResponse response, Integer corpId, Integer stationId, Integer workOrderTypeId, Integer workOrderStatusId, Integer estateTypeId, Integer reportWay, Integer assignEmployee, Date beginTime, Date endTime) {

        Integer uid = SecurityUtils.getLoginUserId();
        //查询list
        List<VwUserWorkOrder> vwUserWorkOrders = new ArrayList<>();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "工单详情";
            //sheet名

//            String[] title = new String[]{"工分统计"};
            String[] title = new String[]{"工单编号", "工单类型", "报修方式", "所属公司", "所属站点", "设备类型", "设备名称", "报修人", "派单人", "报修时间", "故障描述"};
            Sort sort = new Sort(Sort.Direction.DESC, "reportTime");

            vwUserWorkOrders = vwUserWorkOrderRepository.findAll(where(byConditionsWorkOrderExport(corpId, stationId, workOrderTypeId, workOrderStatusId, estateTypeId, reportWay, assignEmployee, beginTime, endTime, uid)), sort);
            LOGGER.debug("数量为({})", vwUserWorkOrders.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[vwUserWorkOrders.size()][];

            for (int i = 0; i < vwUserWorkOrders.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwUserWorkOrder obj = vwUserWorkOrders.get(i);


                values[i][MagicNumber.ZERO] = obj.getSerialNo();
                String category = "";
//                if (obj.getCategory() == MagicNumber.ZERO) {
//                    category = "站点";
//                } else {
//                    category = "车辆";
//                }
                values[i][MagicNumber.ONE] = obj.getWorkOrderTypeNameCn();
                String reportWay2 = "";
                if (obj.getReportWay() == 1) {
                    reportWay2 = "站点报修";
                } else {
                    reportWay2 = "车辆报修";
                }

                values[i][MagicNumber.TWO] = reportWay2;

                values[i][MagicNumber.THREE] = obj.getCorpName();
                values[i][MagicNumber.FOUR] = obj.getStationName();
                values[i][MagicNumber.FIVE] = obj.getEstateTypeName();
//                Date d = obj.getLastUpdateTime();
//                String lastUpdateTime = sdf.format(d);
                values[i][MagicNumber.SIX] = obj.getEstateName();

                values[i][MagicNumber.SEVEN] = obj.getReportEmployeeUserName();

                values[i][MagicNumber.EIGHT] = obj.getAssignEmployeeUserName();
                Date d = obj.getReportTime();
                String reportTime = sdf.format(d);
                values[i][MagicNumber.NINE] = reportTime;
                values[i][MagicNumber.TEN] = obj.getFaultDescription();
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出错， {}", e.getMessage());
        }
    }

    // Dynamic Query Utils
    public static Specification<VwUserWorkOrder> byConditionsWorkOrderExport(Integer corpId, Integer stationId, Integer workOrderTypeId, Integer workOrderStatusId, Integer estateTypeId, Integer reportWay, Integer assignEmployee, Date beginTime, Date endTime, Integer uid) {
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
     * 入库数据导出
     *
     * @param response
     * @param corpId
     * @param category
     * @param estateTypeId
     */
    @RequestMapping(value = "stock/exportStock", method = RequestMethod.POST)
    public void exportWorkOrder(HttpServletResponse response, Integer corpId, Integer category, Integer estateTypeId) {

        Integer uid = SecurityUtils.getLoginUserId();
        //查询list
        List<VwStockRecord> vwStockRecords = new ArrayList<>();
        try {
            String fileName = System.currentTimeMillis() + ".xls";
            String sheetName = "入库数据";

            String[] title = new String[]{"类别", "配件类型", "数量", "所属公司", "操作人", "操作时间"};
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            vwStockRecords = vwStockRecordRepository.findAll(where(queryStockRecordsByConditions(corpId, category, estateTypeId)));
            LOGGER.debug("数量为({})", vwStockRecords.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[vwStockRecords.size()][];

            for (int i = 0; i < vwStockRecords.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwStockRecord obj = vwStockRecords.get(i);
                String category1 = "";
                if (obj.getCategory() == MagicNumber.ONE) {
                    category1 = "站点";
                } else {
                    category1 = "车辆";
                }
                values[i][MagicNumber.ZERO] = category1;

                values[i][MagicNumber.ONE] = obj.getEstateTypeName();

                values[i][MagicNumber.TWO] = obj.getCount() + "";

                values[i][MagicNumber.THREE] = obj.getCorpName();
                values[i][MagicNumber.FOUR] = obj.getCreateName();

                Date d = obj.getLastUpdateTime();
                String reportTime = sdf.format(d);
                values[i][MagicNumber.FIVE] = reportTime;

            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出错， {}", e.getMessage());
        }
    }


    // Dynamic Query Utils
    public Specification<VwStockRecord> queryStockRecordsByConditions(Integer corpId, Integer category, Integer estateTypeId) {
        return new Specification<VwStockRecord>() {
            public Predicate toPredicate(Root<VwStockRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("stockRecords/findAll请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.corpId), corpId));
                }
                LOGGER.debug("stockRecords/findAll请求的参数category值为:{}", category);
                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.category), category));
                }

                LOGGER.debug("stockRecords/findAll请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.estateTypeId), estateTypeId));
                }
                predicate.getExpressions().add(builder.isNull(root.get(VwStockRecord_.removeTime)));

                return predicate;
            }
        };
    }
    // Dynamic End


    @RequestMapping(value = "estateFaultStatisticDetailsExport/findByConditions", method = RequestMethod.POST)
    public void findByConditions(HttpServletResponse response, Integer reportWay, Integer corpId, Integer estateTypeId, Date startDate, Date endDate, String estateTypeName) {

        Integer uid = SecurityUtils.getLoginUserId();
        //查询list
//        List<VwUserWorkOrder> vwUserWorkOrders = new ArrayList<>();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "站点故障明细";
            //sheet名

//            String[] title = new String[]{"工分统计"};
            String[] title = new String[]{"工单编号", "设备条码", "设备名称", "坏件类型", "所属站点", "所属公司", "故障现象", "处理措施", "工单状态", "操作人", "报修时间"};
            Sort sort = new Sort(Sort.Direction.DESC, "reportTime");
            List<Integer> workOrderIds = new ArrayList<>();
            List<VwWorkOrder> resultList = new ArrayList<>();
            Integer categoryo = 0;
            if (Validator.isNotNull(estateTypeId)) {
                ObjEstateType objEstateType = objEstateTypeRepository.findOne(estateTypeId);
                categoryo = objEstateType.getCategory();
            }
            if (categoryo == MagicNumber.ONE) {
                resultList = vwWorkOrderRepository.findAll(where(queryEstateFaultStatisticDetailsEstateByConditions(reportWay, corpId, estateTypeId, startDate, endDate)), sort);
            } else {
                List<VwWorkOrderModuleBadComponentCount> resultList1 = vwWorkOrderModuleBadComponentCountRepository.findAll(where(queryEstateFaultStatisticDetailsByConditions(reportWay, corpId, estateTypeId, startDate, endDate)), sort);
                if (resultList1.size() > 0) {
                    for (int i = 0; i < resultList1.size(); i++) {
                        workOrderIds.add(resultList1.get(i).getWorkOrderId());
                    }
                }
                resultList = vwWorkOrderRepository.findByIdIn(workOrderIds);
            }
//            vwUserWorkOrders = vwUserWorkOrderRepository.findAll(where(byConditionsWorkOrderExport(corpId, stationId, workOrderTypeId, workOrderStatusId, estateTypeId, reportWay, assignEmployee, beginTime, endTime, uid)));
            LOGGER.debug("数量为({})", resultList.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[resultList.size()][];

            for (int i = 0; i < resultList.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwWorkOrder obj = resultList.get(i);
                values[i][MagicNumber.ZERO] = obj.getSerialNo();
                String estateSn = "";
                if (obj.getEstateTypeId() == MagicNumber.ONE) {
                    estateSn = obj.getBicycleStakeBarCode();
                } else {
                    estateSn = obj.getEstateSn();
                }
                values[i][MagicNumber.ONE] = estateSn;
//                String reportWay2 = "";
//                if (obj.getReportWay() == 1) {
//                    reportWay2 = "站点报修";
//                } else {
//                    reportWay2 = "车辆报修";
//                }
                values[i][MagicNumber.TWO] = obj.getEstateName();
                values[i][MagicNumber.THREE] = estateTypeName;
                values[i][MagicNumber.FOUR] = obj.getStationName();

                values[i][MagicNumber.FIVE] = obj.getCorpName();
//                Date d = obj.getLastUpdateTime();
//                String lastUpdateTime = sdf.format(d);
                values[i][MagicNumber.SIX] = obj.getFaultDescription();

                values[i][MagicNumber.SEVEN] = obj.getRepairRemark();

                values[i][MagicNumber.EIGHT] = obj.getWorkOrderStatusNameCn();
                values[i][MagicNumber.NINE] = obj.getReportEmployeeUserName();
                Date d = obj.getReportTime();
                String reportTime = sdf.format(d);
                values[i][MagicNumber.TEN] = reportTime;
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出错， {}", e.getMessage());
        }
    }


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


    /**
     * 维修汇总导出
     *
     * @return
     */
    @RequestMapping(value = "stationFaultStatisticsExport", method = RequestMethod.POST)
    public void stationFaultStatistics(HttpServletResponse response, Integer faultTypeId, Integer corpId, Integer estateTypeId, Date startDate, Date endDate, Integer reportWay, Integer typeId) {
        List<StationRepairFaultStatisticsBo> stationRepairFaultStatisticsBos = new ArrayList<>();
        try {

            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);

            ObjCorporation objCorporation = objCorporationRepository.findOne(corpId);

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

            if (Validator.isNotNull(typeId)) {
                sql2 += " and type_id = :typeId ";
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

            sql2 += " and report_way = :reportWay group by estate_type_id,project_id,corp_name,estate_type_name,fault_type_id,fault_type_name ";

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


            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名

            if (reportWay == MagicNumber.ONE) {
                sheetName = "站点维修汇总";
                //标题
                title = new String[]{"公司", "设备类型", "故障类型", "故障总数"};
            } else {
                sheetName = "车辆维修汇总";
                //标题
                title = new String[]{"公司", "设备类型", "故障类型", "故障总数"};
            }
            HSSFSheet sheet = wb.createSheet(sheetName);

            // 1.生成字体对象
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) (int) MagicNumber.THREE_ZERO);
            font.setFontName("新宋体");
//            font.setColor(HSSFColor.BLUE.index);
            font.setBoldweight((short) (int) MagicNumber.ONE);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) (int) MagicNumber.ONE_FIVE);
            font1.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font1.setBoldweight((short) (int) MagicNumber.ONE);
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) (int) MagicNumber.ONE_FOUR);
            font2.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font2.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font4 = wb.createFont();
            font4.setFontHeightInPoints((short) (int) MagicNumber.NINE);
            font4.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font4.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font3 = wb.createFont();
            font3.setFontHeightInPoints((short) (int) MagicNumber.TWO_ZERO);
            font3.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font3.setBoldweight((short) (int) MagicNumber.ONE);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);

            sheet.setDefaultColumnWidth(MagicNumber.TWO_FIVE);

            //列的宽度是15个字符宽度
            row.setHeightInPoints(MagicNumber.THREE_FIVE);


            //设置行的高度是20个点

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setFont(font); // 调用字体样式对象

            //垂直居中
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.THREE));

            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(sheetName);
            cell1.setCellStyle(style);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //水平居中
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style1.setFont(font1);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFont(font2);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style3 = wb.createCellStyle();
            style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style3.setFont(font3);


            HSSFCellStyle style4 = wb.createCellStyle();
//            style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style4.setFont(font3);

            HSSFCellStyle style5 = wb.createCellStyle();
//            style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style5.setFont(font4);

            HSSFRow row10 = sheet.createRow(1);
            row10.setHeightInPoints(MagicNumber.TWO_FIVE);
            sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 1));
            sheet.addMergedRegion(new Region(1, (short) 2, 1, (short) (int) MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = null;
            String end = null;
            if (Validator.isNotNull(startDate)) {
                begin = sdf.format(startDate);
            }
            if (Validator.isNotNull(endDate)) {
                end = sdf.format(endDate);
            }

            if (begin == null && end == null) {
                cell11.setCellValue("");
            } else {
                cell11.setCellValue(begin + "--" + end);
            }

            cell11.setCellStyle(style3);
//            cell11.setCellType(HSSFCellStyle.ALIGN_RIGHT);


            HSSFRow row2 = sheet.createRow(2);
            row2.setHeightInPoints(MagicNumber.TWO_ZERO);
            HSSFCell cell = null;
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row2.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style1);
            }

            String[][] values = new String[stationRepairFaultStatisticsBos.size()][];
            Integer countSum = 0;
            for (int i = 0; i < stationRepairFaultStatisticsBos.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                StationRepairFaultStatisticsBo obj = stationRepairFaultStatisticsBos.get(i);
                if (Validator.isNotNull(obj.getCorpName())) {
                    values[i][MagicNumber.ZERO] = obj.getCorpName() + "";
                }
                if (Validator.isNotNull(obj.getEstateTypeName())) {
                    values[i][MagicNumber.ONE] = obj.getEstateTypeName() + "";
                }
                if (Validator.isNotNull(obj.getFaultTypeName())) {
                    values[i][MagicNumber.TWO] = obj.getFaultTypeName() + "";
                }
                if (Validator.isNotNull(obj.getEstateTypeName())) {
                    values[i][MagicNumber.THREE] = obj.getCount() + "";
                }


//                values[i][MagicNumber.ZERO] = obj.getCorpName() + "";
//                values[i][MagicNumber.ONE] = obj.getEstateTypeName() + "";
//                values[i][MagicNumber.TWO] = obj.getFaultTypeName() + "";
//                values[i][MagicNumber.THREE] = obj.getCount() + "";
                countSum += Integer.parseInt(obj.getCount() + "");

            }

            HSSFCell cell2 = null;
            //创建内容
            for (int i = 0; i < values.length; i++) {
                HSSFRow row1 = sheet.createRow(i + MagicNumber.THREE);
                row1.setHeightInPoints(MagicNumber.ONE_FIVE);
                for (int j = 0; j < values[i].length; j++) {
                    if (j < values[i].length - 1) {
                        cell2 = row1.createCell(j);
                        cell2.setCellValue(values[i][j]);
                        cell2.setCellStyle(style2);
                    } else {
                        cell2 = row1.createCell(j);
                        Integer temp = Integer.parseInt(values[i][j]);

                        cell2.setCellValue(temp);
                        cell2.setCellStyle(style2);
                    }
                }
            }


            HSSFRow rowt = sheet.createRow(values.length + MagicNumber.THREE);
            rowt.setHeightInPoints(MagicNumber.ONE_FIVE);
            HSSFCell cell0 = rowt.createCell(0);
            cell0.setCellStyle(style2);
            cell0.setCellValue("总计：");
            HSSFCell cell3 = rowt.createCell(MagicNumber.THREE);
            cell3.setCellStyle(style2);
            cell3.setCellValue(countSum);

            HSSFRow rowL = sheet.createRow(values.length + (int) MagicNumber.FOUR);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + (int) MagicNumber.FOUR, (short) 0, values.length + (int) MagicNumber.FOUR, (short) 1));
            sheet.addMergedRegion(new Region(values.length + (int) MagicNumber.FOUR, (short) 2, values.length + (int) MagicNumber.FOUR, (short) (int) MagicNumber.THREE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            HSSFCell cell111 = rowL.createCell(2);
            cell111.setCellValue("导出时间：" + simpleDateFormat.format(new Date()));
            cell111.setCellStyle(style2);


            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();


            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", stationRepairFaultStatisticsBos.size());
//            builder.setResultEntity(stationRepairFaultStatisticsBos, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
//            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
//        return builder.getResponseEntity();
    }


    /**
     * 维修汇总导出
     *
     * @return
     */
    @RequestMapping(value = "repairFaultStatisticsExport", method = RequestMethod.POST)
    public void repairFaultStatisticsExport(HttpServletResponse response, Integer corpId, Date startDate, Date endDate, Integer reportWay, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();

        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            ObjCorporation objCorporation = objCorporationRepository.findOne(corpId);
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
//                stationRepairFaultStatisticsBo.setFaultTypeName(obj[MagicNumber.FIVE].toString());
                repairFaultStatisticsBOS.add(repairFaultStatisticsBO);
            });
            LOGGER.debug("sq2:{}", repairFaultStatisticsBOS.size());


            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名


            sheetName = "维修汇总";
            //标题
            title = new String[]{"公司", "工单类别", "工单类型", "故障总数"};

            HSSFSheet sheet = wb.createSheet(sheetName);

            // 1.生成字体对象
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) (int) MagicNumber.THREE_ZERO);
            font.setFontName("新宋体");
//            font.setColor(HSSFColor.BLUE.index);
            font.setBoldweight((short) (int) MagicNumber.ONE);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) (int) MagicNumber.ONE_FIVE);
            font1.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font1.setBoldweight((short) (int) MagicNumber.ONE);
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) (int) MagicNumber.ONE_FOUR);
            font2.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font2.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font4 = wb.createFont();
            font4.setFontHeightInPoints((short) (int) MagicNumber.NINE);
            font4.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font4.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font3 = wb.createFont();
            font3.setFontHeightInPoints((short) (int) MagicNumber.TWO_ZERO);
            font3.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font3.setBoldweight((short) (int) MagicNumber.ONE);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);

            sheet.setDefaultColumnWidth(MagicNumber.TWO_FIVE);

            //列的宽度是15个字符宽度
            row.setHeightInPoints(MagicNumber.THREE_FIVE);


            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setFont(font); // 调用字体样式对象

            HSSFCellStyle style6 = wb.createCellStyle();
            style6.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style6.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style6.setFont(font); // 调用字体样式对象


            //垂直居中
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.THREE));

            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(sheetName);
            cell1.setCellStyle(style);

            HSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style1.setFont(font1);

            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFont(font2);

            HSSFCellStyle style3 = wb.createCellStyle();
            style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style3.setFont(font3);


            HSSFCellStyle style4 = wb.createCellStyle();
            style4.setFont(font3);

            HSSFCellStyle style5 = wb.createCellStyle();
            style5.setFont(font4);


            HSSFRow row10 = sheet.createRow(1);
            row10.setHeightInPoints(MagicNumber.TWO_FIVE);
            sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 1));
            sheet.addMergedRegion(new Region(1, (short) 2, 1, (short) (int) MagicNumber.THREE));
//            sheet.addMergedRegion(new CellRangeAddress(0,0,0,MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = null;
            String end = null;
            if (Validator.isNotNull(startDate)) {
                begin = sdf.format(startDate);
            }
            if (Validator.isNotNull(endDate)) {
                end = sdf.format(endDate);
            }

            if (begin == null && end == null) {
                cell11.setCellValue("");
            } else {
                cell11.setCellValue(begin + "--" + end);
            }

            cell11.setCellStyle(style3);
//            cell11.setCellType(HSSFCellStyle.ALIGN_RIGHT);


            HSSFRow row2 = sheet.createRow(2);
            row2.setHeightInPoints(MagicNumber.TWO_ZERO);
            HSSFCell cell = null;
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row2.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style1);
            }

            String[][] values = new String[repairFaultStatisticsBOS.size()][];
            Integer countSum = 0;
            for (int i = 0; i < repairFaultStatisticsBOS.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                RepairFaultStatisticsBO obj = repairFaultStatisticsBOS.get(i);

                values[i][MagicNumber.ZERO] = obj.getCorpName() + "";

                if (obj.getReportWay() == 1) {
                    values[i][MagicNumber.ONE] = "站点";
                } else {
                    values[i][MagicNumber.ONE] = "车辆";
                }


                if (obj.getTypeId() == 1) {
                    values[i][MagicNumber.TWO] = "报修维修";
                } else {
                    values[i][MagicNumber.TWO] = "自修保养";
                }

                values[i][MagicNumber.THREE] = obj.getCount() + "";


                countSum += Integer.parseInt(obj.getCount() + "");

            }
//            wcf_left.setWrap(false); // 文字是否换行
            HSSFCell cell2 = null;
            //创建内容
            for (int i = 0; i < values.length; i++) {
                HSSFRow row1 = sheet.createRow(i + MagicNumber.THREE);
                row1.setHeightInPoints(MagicNumber.ONE_FIVE);
                for (int j = 0; j < values[i].length; j++) {
                    if (j < values[i].length - 1) {
                        cell2 = row1.createCell(j);
                        cell2.setCellValue(values[i][j]);
                        cell2.setCellStyle(style2);
                    } else {
                        cell2 = row1.createCell(j);
                        Integer temp = Integer.parseInt(values[i][j]);

                        cell2.setCellValue(temp);
                        cell2.setCellStyle(style2);
                    }


                }
            }


            HSSFRow rowt = sheet.createRow(values.length + MagicNumber.THREE);
            rowt.setHeightInPoints(MagicNumber.ONE_FIVE);
            HSSFCell cell0 = rowt.createCell(0);
            cell0.setCellStyle(style2);
            cell0.setCellValue("总计：");
            HSSFCell cell3 = rowt.createCell(MagicNumber.THREE);
            cell3.setCellStyle(style2);
            cell3.setCellValue(countSum);

            HSSFRow rowL = sheet.createRow(values.length + (int) MagicNumber.FOUR);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + (int) MagicNumber.FOUR, (short) 0, values.length + (int) MagicNumber.FOUR, (short) 1));
            sheet.addMergedRegion(new Region(values.length + (int) MagicNumber.FOUR, (short) 2, values.length + (int) MagicNumber.FOUR, (short) (int) MagicNumber.THREE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            HSSFCell cell111 = rowL.createCell(2);
            cell111.setCellValue("导出时间：" + simpleDateFormat.format(new Date()));
            cell111.setCellStyle(style2);


            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
//            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
//        return builder.getResponseEntity();
    }


    /**
     * 站点详情导出
     *
     * @return
     */
    @RequestMapping(value = "stationRepairDetailExport", method = RequestMethod.POST)
    public void repairFaultStatisticsExport(HttpServletResponse response, Integer projectId, Integer stationId,
                                            Integer estateTypeId, Integer faultTypeId,
                                            Integer statusId, Integer repairEmployee,
                                            Integer reportEmployee, Integer assignEmployee,
                                            Integer checkEmployee, Date endDate, Integer typeId,
                                            Date startDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();

        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            ObjCorporation objCorporation = objCorporationRepository.findOne(projectId);
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


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名


            sheetName = "站点维修明细";
            //标题
            title = new String[]{"工单编号", "站点名称", "站点编号", "设备类型", "设备名称",
                    "故障类型", "工单状态", "更换配件一", "更换配件二", "更换配件三", "报修人", "报修时间", "故障描述", "派单人", "派单时间", "派单备注", "维修人", "维修人", "维修时间", "维修备注", "审核人", "审核备注"};
            HSSFSheet sheet = wb.createSheet(sheetName);

            // 1.生成字体对象
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) (int) MagicNumber.THREE_ZERO);
            font.setFontName("新宋体");
//            font.setColor(HSSFColor.BLUE.index);
            font.setBoldweight((short) (int) MagicNumber.ONE);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) (int) MagicNumber.ONE_FIVE);
            font1.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font1.setBoldweight((short) (int) MagicNumber.ONE);
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) (int) MagicNumber.ONE_FOUR);
            font2.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font2.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font4 = wb.createFont();
            font4.setFontHeightInPoints((short) (int) MagicNumber.NINE);
            font4.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font4.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font3 = wb.createFont();
            font3.setFontHeightInPoints((short) (int) MagicNumber.TWO_ZERO);
            font3.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font3.setBoldweight((short) (int) MagicNumber.ONE);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);

            sheet.setDefaultColumnWidth(MagicNumber.TWO_FIVE);

            //列的宽度是15个字符宽度
            row.setHeightInPoints(MagicNumber.THREE_FIVE);


            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setFont(font); // 调用字体样式对象

            HSSFCellStyle style6 = wb.createCellStyle();
            style6.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style6.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style6.setFont(font); // 调用字体样式对象


            //垂直居中
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.TWO_ONE));

            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(sheetName);
            cell1.setCellStyle(style);

            HSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style1.setFont(font1);

            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFont(font2);

            HSSFCellStyle style3 = wb.createCellStyle();
            style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style3.setFont(font3);


            HSSFCellStyle style4 = wb.createCellStyle();
            style4.setFont(font3);

            HSSFCellStyle style5 = wb.createCellStyle();
            style5.setFont(font4);


            HSSFRow row10 = sheet.createRow(1);
            row10.setHeightInPoints(MagicNumber.TWO_FIVE);
            sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 1));
            sheet.addMergedRegion(new Region(1, (short) 2, 1, (short) (int) MagicNumber.THREE));
//            sheet.addMergedRegion(new CellRangeAddress(0,0,0,MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = null;
            String end = null;
            if (Validator.isNotNull(startDate)) {
                begin = sdf.format(startDate);
            }
            if (Validator.isNotNull(endDate)) {
                end = sdf.format(endDate);
            }

            if (begin == null && end == null) {
                cell11.setCellValue("");
            } else {
                cell11.setCellValue(begin + "--" + end);
            }

            cell11.setCellStyle(style3);
//            cell11.setCellType(HSSFCellStyle.ALIGN_RIGHT);


            HSSFRow row2 = sheet.createRow(2);
            row2.setHeightInPoints(MagicNumber.TWO_ZERO);
            HSSFCell cell = null;
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row2.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style1);
            }

            String[][] values = new String[vwWorkOrderFaultTypes.size()][];
            Integer countSum = 0;

            for (int i = 0; i < vwWorkOrderFaultTypes.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwWorkOrderFaultType obj = vwWorkOrderFaultTypes.get(i);

                values[i][MagicNumber.ZERO] = obj.getSerialNo() + "";
                values[i][MagicNumber.ONE] = obj.getStationName();
                values[i][MagicNumber.TWO] = obj.getStationNo();
                values[i][MagicNumber.THREE] = obj.getEstateTypeName() + "";
                values[i][MagicNumber.FOUR] = obj.getEstateName() + "";
                if (obj.getFaultName() != null) {
                    values[i][MagicNumber.FIVE] = obj.getFaultName() + "";
                }
                values[i][MagicNumber.SIX] = obj.getWorkOrderStatusNameCn();

                String[] replaceEstate;
                if (Validator.isNotNull(obj.getReplaceEstates())) {
                    replaceEstate = obj.getReplaceEstates().split(";");
                    if (Validator.isNotNull(replaceEstate[0])) {
                        values[i][MagicNumber.SEVEN] = replaceEstate[0];
                    }
                    if (Validator.isNotNull(replaceEstate[1])) {
                        values[i][MagicNumber.EIGHT] = replaceEstate[1];
                    }
                    if (Validator.isNotNull(replaceEstate[2])) {
                        values[i][MagicNumber.NINE] = replaceEstate[2];
                    }

                }
                values[i][MagicNumber.TEN] = obj.getReportEmployeeUserName();
                values[i][MagicNumber.ELEVEN] = simpleDateFormat.format(obj.getReportTime()) + "";
                if (Validator.isNotNull(obj.getFaultDescription())) {
                    values[i][MagicNumber.ONE_TWO] = obj.getFaultDescription();
                }
                if (Validator.isNotNull(obj.getAssignEmployee())) {
                    values[i][MagicNumber.ONE_THREE] = obj.getAssignEmployee() + "";
                }
                if (Validator.isNotNull(obj.getAssignTime())) {
                    values[i][MagicNumber.ONE_THREE] = simpleDateFormat.format(obj.getAssignTime()) + "";
                }
                if (Validator.isNotNull(obj.getAssignTime())) {
                    values[i][MagicNumber.ONE_FOUR] = simpleDateFormat.format(obj.getAssignTime()) + "";
                }
                if (Validator.isNotNull(obj.getAssignRemark())) {
                    values[i][MagicNumber.ONE_FIVE] = obj.getAssignRemark() + "";
                }
                if (Validator.isNotNull(obj.getRepairEmployee())) {
                    values[i][MagicNumber.ONE_SIX] = obj.getRepairEmployee() + "";
                }
                if (Validator.isNotNull(obj.getRepairStartTime())) {
                    values[i][MagicNumber.ONE_SEVEN] = simpleDateFormat.format(obj.getRepairStartTime()) + "";
                }
                if (Validator.isNotNull(obj.getRepairRemark())) {
                    values[i][MagicNumber.ONE_EIGHT] = obj.getRepairRemark() + "";
                }
                if (Validator.isNotNull(obj.getCheckEmployee())) {
                    values[i][MagicNumber.TWO_ZERO] = obj.getCheckEmployee() + "";
                }
                if (Validator.isNotNull(obj.getScoreDeductRemark())) {
                    values[i][MagicNumber.TWO_ONE] = obj.getScoreDeductRemark() + "";
                }

            }
//            wcf_left.setWrap(false); // 文字是否换行
            HSSFCell cell2 = null;
            //创建内容
            for (int i = 0; i < values.length; i++) {
                HSSFRow row1 = sheet.createRow(i + MagicNumber.THREE);
                row1.setHeightInPoints(MagicNumber.ONE_FIVE);
                for (int j = 0; j < values[i].length; j++) {
//                    if(j< values[i].length-1){
                    cell2 = row1.createCell(j);
                    cell2.setCellValue(values[i][j]);
                    cell2.setCellStyle(style2);
//                    }else{
//                        cell2 = row1.createCell(j);
//                        Integer temp =    Integer.parseInt(values[i][j]);
//
//                        cell2.setCellValue(temp);
//                        cell2.setCellStyle(style2);
//                    }


                }
            }


//            HSSFRow rowt = sheet.createRow(values.length + MagicNumber.THREE);
//            rowt.setHeightInPoints(MagicNumber.ONE_FIVE);
//            HSSFCell cell0 = rowt.createCell(0);
//            cell0.setCellStyle(style2);
//            cell0.setCellValue("总计：");
//            HSSFCell cell3 = rowt.createCell(MagicNumber.THREE);
//            cell3.setCellStyle(style2);
//            cell3.setCellValue( countSum);

            HSSFRow rowL = sheet.createRow(values.length + MagicNumber.THREE);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 0, values.length + MagicNumber.THREE, (short) 1));
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 2, values.length + MagicNumber.THREE, (short) (int) MagicNumber.THREE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);


            HSSFCell cell111 = rowL.createCell(2);
            cell111.setCellValue("导出时间：" + simpleDateFormat.format(new Date()));
            cell111.setCellStyle(style2);


            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
//            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
//        return builder.getResponseEntity();
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
                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrderFaultType_.assignEmployee), statusId));
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

    /**
     * 自行车详情导出
     *
     * @return
     */
    @RequestMapping(value = "bikeDetailExport", method = RequestMethod.POST)
    public void bikeStatisticsExport(HttpServletResponse response, Integer projectId, Integer stationId,
                                     Integer estateTypeId, Integer faultTypeId,
                                     Integer statusId, Integer repairEmployee, Integer bikeFrameNo,
                                     Integer reportEmployee, Integer assignEmployee,
                                     Integer checkEmployee, Date endDate,
                                     Date startDate, Integer typeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();

        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            ObjCorporation objCorporation = objCorporationRepository.findOne(projectId);

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


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名


            sheetName = "自行车维修明细";
            //标题
            title = new String[]{"工单编号", "站点名称", "站点编号", "车架号", "设备类型", "设备名称",
                    "故障类型", "工单状态", "更换配件一", "更换配件二", "更换配件三", "报修人", "报修时间", "故障描述", "派单人", "派单时间", "派单备注", "维修人", "维修人", "维修时间", "维修备注", "审核人", "审核备注"};
            HSSFSheet sheet = wb.createSheet(sheetName);

            // 1.生成字体对象
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) (int) MagicNumber.THREE_ZERO);
            font.setFontName("新宋体");
//            font.setColor(HSSFColor.BLUE.index);
            font.setBoldweight((short) (int) MagicNumber.ONE);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints((short) (int) MagicNumber.ONE_FIVE);
            font1.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font1.setBoldweight((short) (int) MagicNumber.ONE);
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 1.生成字体对象
            HSSFFont font2 = wb.createFont();
            font2.setFontHeightInPoints((short) (int) MagicNumber.ONE_FOUR);
            font2.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font2.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font4 = wb.createFont();
            font4.setFontHeightInPoints((short) (int) MagicNumber.NINE);
            font4.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font4.setBoldweight((short) (int) MagicNumber.ONE);

            // 1.生成字体对象
            HSSFFont font3 = wb.createFont();
            font3.setFontHeightInPoints((short) (int) MagicNumber.TWO_ZERO);
            font3.setFontName("新宋体");
//            fo1nt.setColor(HSSFColor.BLUE.index);
            font3.setBoldweight((short) (int) MagicNumber.ONE);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);

            sheet.setDefaultColumnWidth(MagicNumber.TWO_FIVE);

            //列的宽度是15个字符宽度
            row.setHeightInPoints(MagicNumber.THREE_FIVE);


            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setFont(font); // 调用字体样式对象

            HSSFCellStyle style6 = wb.createCellStyle();
            style6.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style6.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style6.setFont(font); // 调用字体样式对象


            //垂直居中
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.TWO_TWO));

            HSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(sheetName);
            cell1.setCellStyle(style);

            HSSFCellStyle style1 = wb.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style1.setFont(font1);

            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFont(font2);

            HSSFCellStyle style3 = wb.createCellStyle();
            style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style3.setFont(font3);


            HSSFCellStyle style4 = wb.createCellStyle();
            style4.setFont(font3);

            HSSFCellStyle style5 = wb.createCellStyle();
            style5.setFont(font4);


            HSSFRow row10 = sheet.createRow(1);
            row10.setHeightInPoints(MagicNumber.TWO_FIVE);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, MagicNumber.THREE));
//            sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 1));
//            sheet.addMergedRegion(new Region(1, (short) 2, 1, (short) MagicNumber.THREEE));
//            sheet.addMergedRegion(new CellRangeAddress(0,0,0,MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = null;
            String end = null;
            if (Validator.isNotNull(startDate)) {
                begin = sdf.format(startDate);
            }
            if (Validator.isNotNull(endDate)) {
                end = sdf.format(endDate);
            }

            if (begin == null && end == null) {
                cell11.setCellValue("");
            } else {
                cell11.setCellValue(begin + "--" + end);
            }

            cell11.setCellStyle(style3);

//            cell11.setCellType(HSSFCellStyle.ALIGN_RIGHT);


            HSSFRow row2 = sheet.createRow(2);
            row2.setHeightInPoints(MagicNumber.TWO_ZERO);
            HSSFCell cell = null;
            //创建标题
            for (int i = 0; i < title.length; i++) {
                cell = row2.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style1);
            }

            String[][] values = new String[vwWorkOrderBadComponentFaultTypeSums.size()][];
            Integer countSum = 0;

            for (int i = 0; i < vwWorkOrderBadComponentFaultTypeSums.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwWorkOrderBadComponentFaultTypeSum obj = vwWorkOrderBadComponentFaultTypeSums.get(i);

                values[i][MagicNumber.ZERO] = obj.getSerialNo() + "";
                values[i][MagicNumber.ONE] = obj.getStationName();
                values[i][MagicNumber.TWO] = obj.getStationNo();
                if (Validator.isNotNull(obj.getBikeFrameNo())) {
                    values[i][MagicNumber.THREE] = obj.getBikeFrameNo() + "";
                }
                values[i][MagicNumber.FOUR] = obj.getEstateTypeName() + "";
                values[i][MagicNumber.FIVE] = obj.getEstateName() + "";
                if (obj.getFaultTypeName() != null) {
                    values[i][MagicNumber.SIX] = obj.getFaultTypeName() + "";
                }
                values[i][MagicNumber.SEVEN] = obj.getWorkOrderStatusNameCn();

                String[] replaceEstate;
                if (Validator.isNotNull(obj.getReplaceEstates())) {
                    replaceEstate = obj.getReplaceEstates().split(";");
                    if (Validator.isNotNull(replaceEstate[0])) {
                        values[i][MagicNumber.EIGHT] = replaceEstate[0];
                    }
                    if (Validator.isNotNull(replaceEstate[1])) {
                        values[i][MagicNumber.NINE] = replaceEstate[1];
                    }
                    if (Validator.isNotNull(replaceEstate[2])) {
                        values[i][MagicNumber.TEN] = replaceEstate[2];
                    }

                }
                values[i][MagicNumber.ELEVEN] = obj.getReportEmployeeUserName();
                values[i][MagicNumber.ONE_TWO] = simpleDateFormat.format(obj.getReportTime()) + "";
                if (Validator.isNotNull(obj.getFaultDescription())) {
                    values[i][MagicNumber.ONE_THREE] = obj.getFaultDescription();
                }
                if (Validator.isNotNull(obj.getAssignEmployee())) {
                    values[i][MagicNumber.ONE_FOUR] = obj.getAssignEmployee() + "";
                }
                if (Validator.isNotNull(obj.getAssignTime())) {
                    values[i][MagicNumber.ONE_FIVE] = simpleDateFormat.format(obj.getAssignTime()) + "";
                }

                if (Validator.isNotNull(obj.getAssignRemark())) {
                    values[i][MagicNumber.ONE_SIX] = obj.getAssignRemark() + "";
                }
                if (Validator.isNotNull(obj.getRepairEmployee())) {
                    values[i][MagicNumber.ONE_SEVEN] = obj.getRepairEmployee() + "";
                }
                if (Validator.isNotNull(obj.getRepairStartTime())) {
                    values[i][MagicNumber.ONE_EIGHT] = simpleDateFormat.format(obj.getRepairStartTime()) + "";
                }
                if (Validator.isNotNull(obj.getRepairRemark())) {
                    values[i][MagicNumber.TWO_ZERO] = obj.getRepairRemark() + "";
                }
                if (Validator.isNotNull(obj.getCheckEmployee())) {
                    values[i][MagicNumber.TWO_ONE] = obj.getCheckEmployee() + "";
                }
                if (Validator.isNotNull(obj.getScoreDeductRemark())) {
                    values[i][MagicNumber.TWO_TWO] = obj.getScoreDeductRemark() + "";
                }

            }
//            wcf_left.setWrap(false); // 文字是否换行
            HSSFCell cell2 = null;
            //创建内容
            for (int i = 0; i < values.length; i++) {
                HSSFRow row1 = sheet.createRow(i + MagicNumber.THREE);
                row1.setHeightInPoints(MagicNumber.ONE_FIVE);
                for (int j = 0; j < values[i].length; j++) {
                    cell2 = row1.createCell(j);
                    cell2.setCellValue(values[i][j]);
                    cell2.setCellStyle(style2);


                }
            }

            HSSFRow rowL = sheet.createRow(values.length + MagicNumber.THREE);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 0, values.length + MagicNumber.THREE, (short) 1));
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 2, values.length + MagicNumber.THREE, (short) (int) MagicNumber.THREE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);


            HSSFCell cell111 = rowL.createCell(2);
            cell111.setCellValue("导出时间：" + simpleDateFormat.format(new Date()));
            cell111.setCellStyle(style2);


            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
//            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
//        return builder.getResponseEntity();
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
