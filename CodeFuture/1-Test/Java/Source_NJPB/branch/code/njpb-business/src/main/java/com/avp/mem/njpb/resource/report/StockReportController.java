/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.report;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjCorporation;
import com.avp.mem.njpb.entity.entityBO.RepairFaultStatisticsBO;
import com.avp.mem.njpb.entity.entityBO.StationEstateFaultStatisticsBo;
import com.avp.mem.njpb.entity.entityBO.StationEstateStockListBO;
import com.avp.mem.njpb.entity.entityBO.StationEstateStockStatisticsBO;
import com.avp.mem.njpb.entity.stock.VwStockRecordMonthCount;
import com.avp.mem.njpb.entity.stock.VwStockRecordMonthCount_;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail_;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.repository.basic.ObjCorporationRepository;
import com.avp.mem.njpb.repository.stock.ObjStockRecordMonthCountRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordMonthCountRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordRepository;
import com.avp.mem.njpb.repository.stock.VwStockWorkOrderDetailRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.util.DateUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
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
 * Created by len on 2018/1/30.
 */
@RestController
public class StockReportController {
    @Autowired
    SysUserRepository sysUserRepository;


    @Autowired
    ObjCorporationRepository objCorporationRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    VwStockRecordMonthCountRepository vwStockRecordMonthCountRepository;
    @Autowired
    VwStockRecordRepository vwStockRecordRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(StockReportController.class);

    @Autowired
    ObjStockRecordMonthCountRepository objStockRecordMonthCountRepository;
    @Autowired
    VwStockWorkOrderDetailRepository vwStockWorkOrderDetailRepository;


    /**
     * 站点设备出入库汇总
     *
     * @param startDate
     * @param estateTypeId
     * @param corpId
     * @param partsType
     * @return
     */
    @RequestMapping(value = "stationEstateStockStatistics/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateFaultStatisticsBo>> stationEstateStockStatistics(Date startDate, Integer estateTypeId, Integer corpId, Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(startDate)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            String yearMonth = sdf.format(startDate);
            String[] yearMonths = yearMonth.split("-");


           /*当前月份开始时间*/
            Date beginTime = DateUtil.getFirstDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]));

            Date beginTimeLastMonth = DateUtil.getFirstDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]) - 1);


           /*当前月份结束时间*/
            Date endTime = DateUtil.getLastDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]));

            Date endTimeLastMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]) - 1);


            List<StationEstateStockStatisticsBO> stationEstateStockStatisticsBOS = new ArrayList<>();
            if (startDate.getTime() > new Date().getTime()) {
                LOGGER.debug("没有数据!");
                builder.setResponseCode(ResponseCode.OK);
                return builder.getResponseEntity();
            } else {
                Integer corpIdt = null;

                if (Validator.isNull(corpId)) {
                    LOGGER.debug("公司不能为空!");
                    builder.setResponseCode(ResponseCode.FAILED);
                    return builder.getResponseEntity();
                } else {
                    if (corpId != 1) {
                        corpIdt = corpId;
                    }
                }

                List<VwStockRecordMonthCount> vwStockRecordMonthCounts = vwStockRecordMonthCountRepository.findAll(where(byConditions(beginTime, endTime, estateTypeId, corpIdt, partsType)));

                if (Validator.isNull(vwStockRecordMonthCounts)) {
                    LOGGER.debug("没有数据!");
                    builder.setResponseCode(ResponseCode.OK);
                    return builder.getResponseEntity();

                } else {

                    for (int i = 0; i < vwStockRecordMonthCounts.size(); i++) {
                        StationEstateStockStatisticsBO stationEstateStockStatisticsBO = new StationEstateStockStatisticsBO();
                        VwStockRecordMonthCount vwStockRecordMonthCount = vwStockRecordMonthCounts.get(i);
                        VwStockRecordMonthCount vwStockRecordMonthCount2 = vwStockRecordMonthCountRepository.findByLastUpdateTimeGreaterThanAndLastUpdateTimeLessThanAndEstateTypeIdAndCorpId(beginTimeLastMonth, endTimeLastMonth, vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId());

                        stationEstateStockStatisticsBO.setCorpId(vwStockRecordMonthCount.getCorpId());
                        stationEstateStockStatisticsBO.setCorpName(vwStockRecordMonthCount.getCorpName());
                        stationEstateStockStatisticsBO.setEstateTypeId(vwStockRecordMonthCount.getEstateTypeId());
                        stationEstateStockStatisticsBO.setEstateTypeName(vwStockRecordMonthCount.getEstateTypeName());
                        if (Validator.isNotNull(vwStockRecordMonthCount2)) {
                            stationEstateStockStatisticsBO.setLastMonthCount(vwStockRecordMonthCount2.getCount());
                        }
                        stationEstateStockStatisticsBO.setThisMonthStockCount(vwStockRecordMonthCount.getCount());
                    /*领料*/
                        List<VwStockWorkOrderDetail> vwStockWorkOrderDetailsOut = vwStockWorkOrderDetailRepository.findByEstateTypeIdAndCorpIdAndApplyTimeGreaterThanAndApplyTimeLessThanAndStockWorkOrderTypeIdAndStockWorkOrderStatusId(vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId(), beginTime, endTime, 1, MagicNumber.TWO_HUNDRED);
                    /*归还*/
                        List<VwStockWorkOrderDetail> vwStockWorkOrderDetailsIn = vwStockWorkOrderDetailRepository.findByEstateTypeIdAndCorpIdAndApplyTimeGreaterThanAndApplyTimeLessThanAndStockWorkOrderTypeIdAndStockWorkOrderStatusId(vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId(), beginTime, endTime, 2, MagicNumber.TWO_HUNDRED);
                        Integer out = 0;
                        Integer in = 0;
                        if (Validator.isNotNull(vwStockWorkOrderDetailsOut)) {
                            for (int j = 0; j < vwStockWorkOrderDetailsOut.size(); j++) {
                                Integer temp = vwStockWorkOrderDetailsOut.get(i).getCount();
                                if (Validator.isNotNull(temp)) {
                                    out += temp;
                                }

                            }
                        }
                        LOGGER.debug("本月出库数量：{}", out);
                        stationEstateStockStatisticsBO.setThisMonthOutStockCount(out);
                        if (Validator.isNotNull(vwStockWorkOrderDetailsIn)) {
                            for (int j = 0; j < vwStockWorkOrderDetailsIn.size(); j++) {
                                Integer temp = vwStockWorkOrderDetailsIn.get(i).getCount();
                                if (Validator.isNotNull(temp)) {
                                    in += temp;
                                }

                            }
                        }
                        LOGGER.debug("本月入库数量：{}", in);
                        stationEstateStockStatisticsBO.setThisMonthInStockCount(in);
                        stationEstateStockStatisticsBOS.add(stationEstateStockStatisticsBO);
                    }
                }
            }

            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", stationEstateStockStatisticsBOS.size());
            builder.setResultEntity(stationEstateStockStatisticsBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    public Specification<VwStockRecordMonthCount> byConditions(Date beginTime, Date endTime, Integer estateTypeId, Integer corpId, Integer partsType) {
        return new Specification<VwStockRecordMonthCount>() {
            public Predicate toPredicate(Root<VwStockRecordMonthCount> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("VwStockRecordMonthCount/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecordMonthCount_.corpId), corpId));
                }

                LOGGER.debug("VwStockRecordMonthCount/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecordMonthCount_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("VwStockRecordMonthCount/findByConditions请求的参数partsType值为:{}", partsType);
                if (partsType != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecordMonthCount_.partsType), partsType));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数startDate值为:{}", beginTime);
                if (beginTime != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwStockRecordMonthCount_.lastUpdateTime), beginTime));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数endDate值为:{}", endTime);
                if (endTime != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwStockRecordMonthCount_.lastUpdateTime), endTime));
                }

                return predicate;
            }
        };
    }



    /**
     * 站点设备出入库汇总导出
     *
     * @return
     */
    @RequestMapping(value = "stationEstateStockExport", method = RequestMethod.POST)
    public void stationEstateExport(HttpServletResponse response, Date startDate, Integer estateTypeId, Integer corpId, Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();
        StockReportController stockReportController = new StockReportController();
//        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            Integer uid = SecurityUtils.getLoginUserId();
            if (Validator.isNull(startDate)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
//                return builder.getResponseEntity();
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");

            String yearMonth = sdf1.format(startDate);
            String[] yearMonths = yearMonth.split("-");


           /*当前月份开始时间*/
            Date beginTime = DateUtil.getFirstDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]));

            Date beginTimeLastMonth = DateUtil.getFirstDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]) - 1);


           /*当前月份结束时间*/
            Date endTime = DateUtil.getLastDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]));

            Date endTimeLastMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(yearMonths[0]), Integer.parseInt(yearMonths[1]) - 1);


            List<StationEstateStockStatisticsBO> stationEstateStockStatisticsBOS = new ArrayList<>();
            if (startDate.getTime() > new Date().getTime()) {
                LOGGER.debug("没有数据!");
                builder.setResponseCode(ResponseCode.OK);
//                return builder.getResponseEntity();
            } else {
                Integer corpIdt = null;

                if (Validator.isNull(corpId)) {
                    LOGGER.debug("公司不能为空!");
                    builder.setResponseCode(ResponseCode.FAILED);
//                    return builder.getResponseEntity();
                } else {
                    if (corpId != 1) {
                        corpIdt = corpId;
                    }
                }

                List<VwStockRecordMonthCount> vwStockRecordMonthCounts = vwStockRecordMonthCountRepository.findAll(where(byConditions(beginTime, endTime, estateTypeId, corpIdt, partsType)));

                if (Validator.isNull(vwStockRecordMonthCounts)) {
                    LOGGER.debug("没有数据!");
                    builder.setResponseCode(ResponseCode.OK);
//                    return builder.getResponseEntity();

                } else {

                    for (int i = 0; i < vwStockRecordMonthCounts.size(); i++) {
                        StationEstateStockStatisticsBO stationEstateStockStatisticsBO = new StationEstateStockStatisticsBO();
                        VwStockRecordMonthCount vwStockRecordMonthCount = vwStockRecordMonthCounts.get(i);
                        VwStockRecordMonthCount vwStockRecordMonthCount2 = vwStockRecordMonthCountRepository.findByLastUpdateTimeGreaterThanAndLastUpdateTimeLessThanAndEstateTypeIdAndCorpId(beginTimeLastMonth, endTimeLastMonth, vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId());

                        stationEstateStockStatisticsBO.setCorpId(vwStockRecordMonthCount.getCorpId());
                        stationEstateStockStatisticsBO.setCorpName(vwStockRecordMonthCount.getCorpName());
                        stationEstateStockStatisticsBO.setEstateTypeId(vwStockRecordMonthCount.getEstateTypeId());
                        stationEstateStockStatisticsBO.setEstateTypeName(vwStockRecordMonthCount.getEstateTypeName());
                        if (Validator.isNotNull(vwStockRecordMonthCount2)) {
                            stationEstateStockStatisticsBO.setLastMonthCount(vwStockRecordMonthCount2.getCount());
                        }
                        stationEstateStockStatisticsBO.setThisMonthStockCount(vwStockRecordMonthCount.getCount());
                    /*领料*/
                        List<VwStockWorkOrderDetail> vwStockWorkOrderDetailsOut = vwStockWorkOrderDetailRepository.findByEstateTypeIdAndCorpIdAndApplyTimeGreaterThanAndApplyTimeLessThanAndStockWorkOrderTypeIdAndStockWorkOrderStatusId(vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId(), beginTime, endTime, 1, MagicNumber.TWO_HUNDRED);
                    /*归还*/
                        List<VwStockWorkOrderDetail> vwStockWorkOrderDetailsIn = vwStockWorkOrderDetailRepository.findByEstateTypeIdAndCorpIdAndApplyTimeGreaterThanAndApplyTimeLessThanAndStockWorkOrderTypeIdAndStockWorkOrderStatusId(vwStockRecordMonthCount.getEstateTypeId(), vwStockRecordMonthCount.getCorpId(), beginTime, endTime, 2, MagicNumber.TWO_HUNDRED);
                        Integer out = 0;
                        Integer in = 0;
                        if (Validator.isNotNull(vwStockWorkOrderDetailsOut)) {
                            for (int j = 0; j < vwStockWorkOrderDetailsOut.size(); j++) {
                                Integer temp = vwStockWorkOrderDetailsOut.get(i).getCount();
                                if (Validator.isNotNull(temp)) {
                                    out += temp;
                                }

                            }
                        }
                        LOGGER.debug("本月出库数量：{}", out);
                        stationEstateStockStatisticsBO.setThisMonthOutStockCount(out);
                        if (Validator.isNotNull(vwStockWorkOrderDetailsIn)) {
                            for (int j = 0; j < vwStockWorkOrderDetailsIn.size(); j++) {
                                Integer temp = vwStockWorkOrderDetailsIn.get(i).getCount();
                                if (Validator.isNotNull(temp)) {
                                    in += temp;
                                }

                            }
                        }
                        LOGGER.debug("本月入库数量：{}", in);
                        stationEstateStockStatisticsBO.setThisMonthInStockCount(in);
                        stationEstateStockStatisticsBOS.add(stationEstateStockStatisticsBO);
                    }
                }
            }





//            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            ObjCorporation objCorporation = objCorporationRepository.findOne(corpId);

//             stockReportController.stationEstateStockStatistics(startDate, estateTypeId, corpId, partsType);


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名


            sheetName = "站点设备出入库列表明细";
            //标题
            title = new String[]{"所属公司", "配件类型", "上月结存", "本月入库", "本月出库",
                    "本月结存"};
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
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.FIVE));

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
            sheet.addMergedRegion(new Region(1, (short) (int) MagicNumber.FOUR, 1, (short) (int) MagicNumber.FIVE));
//            sheet.addMergedRegion(new CellRangeAddress(0,0,0,MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(MagicNumber.FOUR);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String begin = null;
            String end = null;
            if (Validator.isNotNull(startDate)) {
                begin = sdf.format(startDate);
            }
//            if (Validator.isNotNull(endDate)) {
//                end = sdf.format(endDate);
//            }

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

            String[][] values = new String[stationEstateStockStatisticsBOS.size()][];
            Integer countSum = 0;

            for (int i = 0; i < stationEstateStockStatisticsBOS.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                StationEstateStockStatisticsBO obj = stationEstateStockStatisticsBOS.get(i);
                if (Validator.isNotNull(obj.getCorpName())) {
                    values[i][MagicNumber.ZERO] = obj.getCorpName() + "";
                }
                if (Validator.isNotNull(obj.getEstateTypeName())) {
                    values[i][MagicNumber.ONE] = obj.getEstateTypeName();
                }



                if (Validator.isNotNull(obj.getLastMonthCount())) {
                    values[i][MagicNumber.TWO] = obj.getLastMonthCount() + "";
                }
                if (Validator.isNotNull(obj.getThisMonthInStockCount())) {
                    values[i][MagicNumber.THREE] = obj.getThisMonthInStockCount() + "";
                }
                if (Validator.isNotNull(obj.getThisMonthOutStockCount())) {
                    values[i][MagicNumber.FOUR] = obj.getThisMonthOutStockCount() + "";
                }
                if (Validator.isNotNull(obj.getThisMonthStockCount())) {
                    values[i][MagicNumber.FIVE] = simpleDateFormat.format(obj.getThisMonthStockCount()) + "";
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


                }
            }

            HSSFRow rowL = sheet.createRow(values.length + MagicNumber.THREE);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 0, values.length + MagicNumber.THREE, (short) 1));
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) (int) MagicNumber.FOUR, values.length + MagicNumber.THREE, (short) (int) MagicNumber.FIVE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);


            HSSFCell cell111 = rowL.createCell(MagicNumber.FOUR);
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
     * 站点设备出入库列表
     *
     * @return
     */
    @RequestMapping(value = "stationEstateStockList/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<StationEstateStockListBO>> stationEstateStockList(Integer corpId, Date startDate, Date endDate, Integer estateTypeId,
                                                                              Integer stockWorkOrderTypeId, Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<StationEstateStockListBO> stationEstateStockListBOS = new ArrayList<>();

        try {

            String sql2 = "select corp_id,corp_name,count(id),estate_type_id,estate_type_name,stock_work_order_type_id,stock_work_order_type_name,process_user_id,process_user_name from business.vw_stock_work_order_detail where 1=1 and remove_time is null and stock_work_order_status_id = 500 ";

            if (Validator.isNotNull(corpId) && corpId != -1) {
                if (corpId == 1) {
                    sql2 += " and corp_id in (:corpId) ";
                } else {
                    sql2 += " and corp_id = :corpId ";
                }
            }

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                sql2 += " and estate_type_id = :estateTypeId ";

            }
            if (Validator.isNotNull(partsType) && partsType != -1) {
                sql2 += " and parts_type = :partsType ";

            }

            if (Validator.isNotNull(stockWorkOrderTypeId)) {
                sql2 += " and stock_work_order_type_id = :stockWorkOrderTypeId ";
            }


            if (Validator.isNotNull(startDate)) {

                sql2 += " and apply_time > :startDate ";
            }
            if (Validator.isNotNull(endDate)) {

                sql2 += " and apply_time < :endDate ";
            }

            sql2 += "group by corp_id,corp_name," +
                    "estate_type_id,estate_type_name,stock_work_order_type_id,stock_work_order_type_name,process_user_id,process_user_name";

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

            if (Validator.isNotNull(estateTypeId) && estateTypeId != -1) {
                nativeQuery2.setParameter("estateTypeId", estateTypeId);
            }

            if (Validator.isNotNull(stockWorkOrderTypeId)) {

                nativeQuery2.setParameter("stockWorkOrderTypeId", stockWorkOrderTypeId);
            }

            if (Validator.isNotNull(partsType) && partsType != -1) {
                nativeQuery2.setParameter("partsType", partsType);
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
                StationEstateStockListBO stationEstateStockListBO = new StationEstateStockListBO();
                Object[] obj = (Object[]) result;

                if (obj[MagicNumber.ZERO] != null) {
                    stationEstateStockListBO.setCorpId((Integer) obj[MagicNumber.ZERO]);
                    stationEstateStockListBO.setCorpName(obj[MagicNumber.ONE].toString());
                }
                stationEstateStockListBO.setCount((BigInteger) obj[MagicNumber.TWO]);

                if (obj[MagicNumber.THREE] != null) {
                    stationEstateStockListBO.setEstateTypeId((Integer) obj[MagicNumber.THREE]);
                    stationEstateStockListBO.setEstateTypeName(obj[MagicNumber.FOUR].toString());
                }
                if (obj[MagicNumber.FIVE] != null) {
                    stationEstateStockListBO.setStockWorkOrderTypeId((Integer) obj[MagicNumber.FIVE]);
                    stationEstateStockListBO.setStockWorkOrderTypeName(obj[MagicNumber.SIX].toString());
                }
                if (obj[MagicNumber.SEVEN] != null) {
                    stationEstateStockListBO.setProcessUserId((Integer) obj[MagicNumber.SEVEN]);
                    stationEstateStockListBO.setProcessUserName(obj[MagicNumber.EIGHT].toString());
                }

                stationEstateStockListBOS.add(stationEstateStockListBO);
            });
            LOGGER.debug("sq2:{}", stationEstateStockListBOS.size());


            LOGGER.debug("查询站点设备故障情况统计数据成功，数据量为:({})", stationEstateStockListBOS.size());
            builder.setResultEntity(stationEstateStockListBOS, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点设备出入库列表详情
     *
     * @return
     */
    @RequestMapping(value = "stationEstateStockListDetail/findByConditions", method = RequestMethod.GET)
    ResponseEntity stationEstateStockListDetail(Integer corpId, Integer partsType,
                                                Integer estateTypeId, Integer processUserId,
                                                Integer stockWorkOrderTypeId, Date endDate,
                                                Date startDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer statusId = MagicNumber.FIVE_HUNDRED;
            List<VwStockWorkOrderDetail> vwStockWorkOrderDetails = vwStockWorkOrderDetailRepository.findAll(where(queryConditions(corpId, estateTypeId, partsType, processUserId, statusId,
                    startDate, endDate, stockWorkOrderTypeId)));

            builder.setResultEntity(vwStockWorkOrderDetails, ResponseCode.OK);

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    public Specification<VwStockWorkOrderDetail> queryConditions(Integer corpId, Integer estateTypeId, Integer partsType, Integer processUserId, Integer stockWorkOrderStatusId,
                                                                 Date startDate, Date endDate, Integer stockWorkOrderTypeId) {
        return new Specification<VwStockWorkOrderDetail>() {
            public Predicate toPredicate(Root<VwStockWorkOrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("queryConditions/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.corpId), corpId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数partsType值为:{}", partsType);
                if (partsType != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.partsType), partsType));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数processUserId值为:{}", processUserId);
                if (processUserId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.processUserId), processUserId));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数startDate值为:{}", startDate);
                if (startDate != null) {
                    predicate.getExpressions().add(builder.greaterThan(root.get(VwStockWorkOrderDetail_.applyTime), startDate));
                }

                LOGGER.debug("queryConditions/findByConditions请求的参数endDate值为:{}", endDate);
                if (endDate != null) {
                    predicate.getExpressions().add(builder.lessThan(root.get(VwStockWorkOrderDetail_.applyTime), endDate));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数stockWorkOrderTypeId值为:{}", stockWorkOrderTypeId);
                if (stockWorkOrderTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.stockWorkOrderTypeId), stockWorkOrderTypeId));
                }
                LOGGER.debug("queryConditions/findByConditions请求的参数stockWorkOrderStatusId值为:{}", stockWorkOrderStatusId);
                if (stockWorkOrderStatusId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.stockWorkOrderStatusId), stockWorkOrderStatusId));
                }

                return predicate;
            }
        };
    }


    /**
     * 站点详情导出
     *
     * @return
     */
    @RequestMapping(value = "stationEstateStockListDetailExport", method = RequestMethod.POST)
    public void repairFaultStatisticsExport(HttpServletResponse response, Integer corpId, Integer partsType,
                                            Integer estateTypeId, Integer processUserId,
                                            Integer stockWorkOrderTypeId, Date endDate,
                                            Date startDate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<RepairFaultStatisticsBO> repairFaultStatisticsBOS = new ArrayList<>();

        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            ObjCorporation objCorporation = objCorporationRepository.findOne(corpId);

            Integer statusId = MagicNumber.FIVE_HUNDRED;
            List<VwStockWorkOrderDetail> vwStockWorkOrderDetails = vwStockWorkOrderDetailRepository.findAll(where(queryConditions(corpId, estateTypeId, partsType, processUserId, statusId,
                    startDate, endDate, stockWorkOrderTypeId)));


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFWorkbook wb = new HSSFWorkbook();

            String sheetName = "";
            String[] title = new String[]{};
            String fileName = System.currentTimeMillis() + ".xls"; //文件名


            sheetName = "站点设备出入库列表明细";
            //标题
            title = new String[]{"所属公司", "配件类型", "数量", "出入库方式", "仓管员",
                    "申请时间"};
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
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (int) MagicNumber.FIVE));

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
            sheet.addMergedRegion(new Region(1, (short) (int) MagicNumber.FOUR, 1, (short) (int) MagicNumber.FIVE));
//            sheet.addMergedRegion(new CellRangeAddress(0,0,0,MagicNumber.THREE));

            HSSFCell cell10 = row10.createCell(0);
            cell10.setCellValue(objCorporation.getCorpName());
            cell10.setCellStyle(style3);

            HSSFCell cell11 = row10.createCell(MagicNumber.FOUR);
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

            String[][] values = new String[vwStockWorkOrderDetails.size()][];
            Integer countSum = 0;

            for (int i = 0; i < vwStockWorkOrderDetails.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwStockWorkOrderDetail obj = vwStockWorkOrderDetails.get(i);
                if (Validator.isNotNull(obj.getCorpName())) {
                    values[i][MagicNumber.ZERO] = obj.getCorpName() + "";
                }
                if (Validator.isNotNull(obj.getEstateTypeName())) {
                    values[i][MagicNumber.ONE] = obj.getEstateTypeName();
                }
                if (Validator.isNotNull(obj.getCount())) {
                    values[i][MagicNumber.TWO] = obj.getCount() + "";
                }
                if (Validator.isNotNull(obj.getStockWorkOrderTypeName())) {
                    values[i][MagicNumber.THREE] = obj.getStockWorkOrderTypeName() + "";
                }
                if (Validator.isNotNull(obj.getProcessUserName())) {
                    values[i][MagicNumber.FOUR] = obj.getProcessUserName() + "";
                }
                if (Validator.isNotNull(obj.getApplyTime())) {
                    values[i][MagicNumber.FIVE] = simpleDateFormat.format(obj.getApplyTime()) + "";
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

            HSSFRow rowL = sheet.createRow(values.length + MagicNumber.THREE);
            rowL.setHeightInPoints(MagicNumber.TWO_ZERO);
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) 0, values.length + MagicNumber.THREE, (short) 1));
            sheet.addMergedRegion(new Region(values.length + MagicNumber.THREE, (short) (int) MagicNumber.FOUR, values.length + MagicNumber.THREE, (short) (int) MagicNumber.FIVE));
            HSSFCell cell110 = rowL.createCell(0);
            cell110.setCellValue("导出人：" + sysUser.getUserName());
            cell110.setCellStyle(style2);


            HSSFCell cell111 = rowL.createCell(MagicNumber.FOUR);
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


}



