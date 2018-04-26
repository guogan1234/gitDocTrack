/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.dayReport;

import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwUser;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.sys.VwUserRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.DateUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by len on 2017/12/13.
 */
@Service
@EnableScheduling
public class DayReportExportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayReportExportController.class);


    @Value("${business.service.file.app}")
    private String url;
    @Value("${business.service.file.dayReport}")
    private String dayReport;

    @Autowired
    VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    VwUserRepository vwUserRepository;

    // @GetMapping("dayReport/xlsx")
    @Scheduled(cron = "0 0 6 * * ?")//0 0/3 *  * * ?
    public void exportDayReport() {

        try {
            LOGGER.debug("日报开始");

            File newFile = new File(url + "NJPB.xlsx");
            InputStream is = new FileInputStream(newFile);
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            LOGGER.debug("日报" + newFile.getAbsolutePath());
            LOGGER.debug("日报" + sheet.getSheetName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date());


            String dateYes = formatter.format(DateUtil.getBeforeDayBeginTIme());
            // 写数据
            FileOutputStream fos = new FileOutputStream(dayReport + dateYes + "NJPB.xlsx");

            LOGGER.debug("日报");
            /*今日新增工单*/
            XSSFRow row8 = sheet.getRow(MagicNumber.EIGHT);
            XSSFCell cell8 = row8.getCell(1);

            Date d1 = DateUtil.getBeforeDayBeginTIme();
            Date d2 = DateUtil.getBeforeDayEndTIme();
            List<VwWorkOrder> listDay = vwWorkOrderRepository.findByCreateTimeBetween(d1, d2);
            String workOrderCount8 = listDay.size() + "";
            cell8.setCellValue(workOrderCount8);
            LOGGER.debug("日报workOrderCount8" + workOrderCount8);
            cell8.setCellStyle(createBorderedStyle(workbook));

            /*新增工单环比增长*/
            Date d11 = DateUtil.getBeforeYesterdayBeginTIme();
            Date d22 = DateUtil.getBeforeYesterdayEndTIme();
            List<VwWorkOrder> listYesDay = vwWorkOrderRepository.findByCreateTimeBetween(d11, d22);
            XSSFRow row9 = sheet.getRow(MagicNumber.NINE);
            XSSFCell cell9 = row9.getCell(1);
            if (listYesDay.size() > 0) {
                int li = (listDay.size() - listYesDay.size());
                int li2 = listYesDay.size();
                float increase = ((float) li / (float) li2) * MagicNumber.ONE_HUNDRED;
                String temp = increase + "";
                String res = "";
                if (increase < 0) {
                    res = temp.substring(0, MagicNumber.FIVE) + "%";
                } else {
                    res = temp.substring(0, MagicNumber.FOUR) + "%";
                }
                LOGGER.debug("日报res" + res);
                cell9.setCellValue(res);
            } else {
                cell9.setCellValue("0");
            }
            cell9.setCellStyle(createBorderedStyle(workbook));



            /*今日已完成工单*/
            List<VwWorkOrder> listOver = vwWorkOrderRepository.findByCreateTimeBetweenAndStatusIdGreaterThanEqual(d1, d2, MagicNumber.EIGHT_HUNDRED);
            XSSFRow row10 = sheet.getRow(MagicNumber.TEN);
            XSSFCell cell10 = row10.getCell(1);
            String workOrderCount10 = listOver.size() + "";
            cell10.setCellValue(workOrderCount10);
            cell10.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount10" + workOrderCount10);


            /*一公司*/
            List<VwWorkOrder> list1 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, 2);
            XSSFRow row12 = sheet.getRow(MagicNumber.ONE_TWO);
            XSSFCell cell12 = row12.getCell(1);
            String workOrderCount12 = list1.size() + "";
            cell12.setCellValue(workOrderCount12);
            cell12.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount12" + workOrderCount12);


              /*二公司*/
            List<VwWorkOrder> list2 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.THREE);
            XSSFRow row13 = sheet.getRow(MagicNumber.ONE_THREE);
            XSSFCell cell13 = row13.getCell(1);
            String workOrderCount13 = list2.size() + "";
            cell13.setCellValue(workOrderCount13);
            cell13.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount13" + workOrderCount13);


              /*三公司*/
            List<VwWorkOrder> list3 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.FOUR);
            XSSFRow row14 = sheet.getRow(MagicNumber.ONE_FOUR);
            XSSFCell cell14 = row14.getCell(1);
            String workOrderCount14 = list3.size() + "";
            cell14.setCellValue(workOrderCount14);
            cell14.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount14" + workOrderCount14);


              /*四公司*/
            List<VwWorkOrder> list4 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.FIVE);
            XSSFRow row15 = sheet.getRow(MagicNumber.ONE_FIVE);
            XSSFCell cell15 = row15.getCell(1);
            String workOrderCount15 = list4.size() + "";
            cell15.setCellValue(workOrderCount15);
            cell15.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount15" + workOrderCount15);

            LOGGER.debug("日报四公司");

            /*今日活跃用户数*/
            Set<String> dayActiveManName = new HashSet<>();
            Set<Integer> dayActiveMan = new HashSet<>();
            Set<Integer> threeDayActive = new HashSet<>();


            for (int i = 0; i < listDay.size(); i++) {
                VwWorkOrder temp = listDay.get(i);
                if (temp.getReportEmployeeUserName() != null) {
                    dayActiveManName.add(temp.getReportEmployeeUserName());
                    dayActiveMan.add(temp.getReportEmployee());
                    threeDayActive.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployee() != null) {
                    dayActiveManName.add(temp.getAssignEmployeeUserName());
                    dayActiveMan.add(temp.getAssignEmployee());
                    threeDayActive.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployeeUserName() != null) {
                    dayActiveManName.add(temp.getRepairEmployeeUserName());
                    dayActiveMan.add(temp.getRepairEmployee());
                    threeDayActive.add(temp.getRepairEmployee());
                }
                LOGGER.debug("今日活跃用户-----" + dayActiveMan.size());


            }
            XSSFRow row4 = sheet.getRow(MagicNumber.THREE);
            XSSFCell cell4 = row4.getCell(1);
            String workOrderCount4 = dayActiveMan.size() + "";
            cell4.setCellValue(workOrderCount4);
            cell4.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报workOrderCount4" + workOrderCount4);

            LOGGER.debug("活跃用户环比增长");

            /*活跃用户环比增长*/
            Set<Integer> yesDayActiveMan = new HashSet<>();

            for (int i = 0; i < listYesDay.size(); i++) {
                VwWorkOrder temp = listYesDay.get(i);
                if (temp.getReportEmployee() != null) {
                    yesDayActiveMan.add(temp.getReportEmployee());
                    threeDayActive.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployee() != null) {
                    yesDayActiveMan.add(temp.getAssignEmployee());
                    threeDayActive.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployeeUserName() != null) {
                    yesDayActiveMan.add(temp.getRepairEmployee());
                    threeDayActive.add(temp.getRepairEmployee());
                }
            }
            XSSFRow row5 = sheet.getRow(MagicNumber.FOUR);
            XSSFCell cell5 = row5.getCell(1);
            if (listYesDay.size() > 0) {
                int li = (dayActiveMan.size() - yesDayActiveMan.size());
                int li2 = yesDayActiveMan.size();
                float increase = ((float) li / (float) li2) * MagicNumber.ONE_HUNDRED;
                String temp = increase + "";
                String res = "";
                if (increase < 0) {
                    res = temp.substring(0, MagicNumber.FIVE) + "%";
                } else {
                    res = temp.substring(0, MagicNumber.FOUR) + "%";
                }
                LOGGER.debug("日报workOrderCount5res" + res);

                cell5.setCellValue(res);
            } else {
                cell5.setCellValue("0");
            }
            cell5.setCellStyle(createBorderedStyle(workbook));


            LOGGER.debug("最近三天未使用系统的用户");
            /*最近三天未使用系统的用户*/
            Date d111 = DateUtil.getBeforeThreedayBeginTIme();
            Date d222 = DateUtil.getBeforeThreedayEndTIme();
            List<VwWorkOrder> listThree = vwWorkOrderRepository.findByCreateTimeBetween(d111, d222);
            for (int i = 0; i < listThree.size(); i++) {
                VwWorkOrder temp = listThree.get(i);
                if (temp.getReportEmployee() != null) {
                    threeDayActive.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployee() != null) {
                    threeDayActive.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployeeUserName() != null) {
                    threeDayActive.add(temp.getRepairEmployee());
                }
            }
            List<Integer> threeDayActiveList = new ArrayList<>();
            LOGGER.debug("最近三天未使用系统的用户" + threeDayActive.toString());
            threeDayActiveList.addAll(threeDayActive);
            LOGGER.debug("最近三天未使用系统的用户" + threeDayActiveList.toString());
            List<Integer> dayCorp = new ArrayList<>();
            dayCorp.add(2);
            dayCorp.add(MagicNumber.THREE);
            dayCorp.add(MagicNumber.FOUR);
            dayCorp.add(MagicNumber.FIVE);
            List<SysUser> sysUsers = sysUserRepository.findByIdGreaterThanAndCorpIdInAndIdNotInOrderByCreateTime(MagicNumber.ONE_SEVEN, dayCorp, threeDayActiveList);

            String threeDayNotActive = "";
            for (int i = 0; i < sysUsers.size(); i++) {

                if (i < sysUsers.size() - 1) {
                    threeDayNotActive += sysUsers.get(i).getUserName() + ",";
                } else {
                    threeDayNotActive += sysUsers.get(i).getUserName();
                }
            }
            XSSFRow row7 = sheet.getRow(MagicNumber.SIX);
            XSSFCell cell7 = row7.getCell(1);
            cell7.setCellValue(threeDayNotActive);
            cell7.setCellStyle(createBorderedStyle(workbook));
            LOGGER.debug("日报threeDayNotActive" + threeDayNotActive);


            LOGGER.debug("积极使用系统前五名用户");
            /*积极使用系统前五名用户*/
            List<String> manWorkOrderCountList = new ArrayList<>();
            List<Integer> dayActiveManList = new ArrayList<>();
            dayActiveManList.addAll(dayActiveMan);
            LOGGER.debug("积极使用系统前五名用户" + dayActiveManList.toString());
            int[] count = new int[dayActiveManList.size()];
            for (int i = 0; i < dayActiveManList.size(); i++) {
                int temp = dayActiveManList.get(i);
                VwUser vwUser = vwUserRepository.findOne(temp);
//                SysUser sysUser = sysUserRepository.findOne(temp);
                List<VwWorkOrder> vwWorkOrderListOfRepair = vwWorkOrderRepository.findByRepairEmployeeAndCreateTimeBetween(temp, d1, d2);
                List<VwWorkOrder> vwWorkOrderListOfReport = vwWorkOrderRepository.findByReportEmployeeAndCreateTimeBetween(temp, d1, d2);
                List<VwWorkOrder> vwWorkOrderListOfAssign = vwWorkOrderRepository.findByAssignEmployeeAndCreateTimeBetween(temp, d1, d2);
                String name = vwUser.getUserName();
                String corp = vwUser.getCorpName();
                String role = vwUser.getRoleNames();

                Integer t1 = 0;
                Integer t2 = 0;
                Integer t3 = 0;
                if (Validator.isNotNull(vwWorkOrderListOfRepair)) {
                    t1 = vwWorkOrderListOfRepair.size();
                }
                if (Validator.isNotNull(vwWorkOrderListOfReport)) {
                    t2 = vwWorkOrderListOfReport.size();
                }
                if (Validator.isNotNull(vwWorkOrderListOfAssign)) {
                    t3 = vwWorkOrderListOfAssign.size();
                }

                Integer manWorkOrderCount = t1 + t2 + t3;

                count[i] = manWorkOrderCount;
                LOGGER.debug("count--" + manWorkOrderCount);
                manWorkOrderCountList.add(temp + "_" + name + "_" + manWorkOrderCount + "_" + corp + "_" + role);


            }
            for (int i = 0; i < count.length; i++) {
                LOGGER.debug("用户bbbbbbbbbb-------------------" + count[i]);
            }
            LOGGER.debug("积极使用系统前五名用户-------------------" + manWorkOrderCountList.toString());
            int temp1 = 0;
            for (int i = 0; i < count.length; i++) {

                for (int j = 0; j < count.length - 1; j++) {

                    if (count[i] > count[j]) {
                        temp1 = count[i];
                        count[i] = count[j];
                        count[j] = temp1;
                    }

                }

            }

            for (int i = 0; i < count.length; i++) {
                LOGGER.debug("用户-------------------" + count[i]);
            }

            String[] sort = new String[manWorkOrderCountList.size()];

            for (int i = 0; i < count.length; i++) {

                for (int j = 0; j < manWorkOrderCountList.size(); j++) {
                    String temp = manWorkOrderCountList.get(j);
                    String[] t = temp.split("_");
                    Integer no = Integer.parseInt(t[2]);
                    if (count[i] == no) {
                        if (sort[i] == null) {
                            sort[i] = temp;
                            LOGGER.debug("用户-------------------" + temp);
                        } else {
                            i++;
                            sort[i] = temp;
                            LOGGER.debug("用户-------------------" + temp);

                        }

                    }
                }

            }


            String fiveName = "";
            for (int i = 0; i < sort.length; i++) {

                if (i < MagicNumber.FIVE) {
                    String temp = sort[i];
                    String[] t = temp.split("_");
                    if (i == MagicNumber.FOUR) {
                        fiveName += t[1];
                    } else {
                        fiveName += t[1] + ",";
                    }

                }

            }

            LOGGER.debug("日报fiveName" + fiveName);

            XSSFRow row6 = sheet.getRow(MagicNumber.FIVE);
            XSSFCell cell6 = row6.getCell(1);
            cell6.setCellValue(fiveName);
            cell6.setCellStyle(createBorderedStyle(workbook));

            LOGGER.debug("统计时间");
            /*统计时间*/
            XSSFRow row2 = sheet.getRow(MagicNumber.ONE);
            XSSFCell cell2 = row2.getCell(0);

            cell2.setCellValue("统计时间：" + dateString);
            cell2.setCellStyle(createGetCellStyle(workbook));


            /***************************************************************/
            XSSFSheet sheet1 = workbook.getSheetAt(1);
            /*统计时间*/
            XSSFRow sheet1row2 = sheet1.getRow(MagicNumber.ONE);
            XSSFCell sheet1cell2 = sheet1row2.getCell(0);
            SimpleDateFormat sheet1formatter = new SimpleDateFormat("yyyy-MM-dd");
            String sheet1dateString = sheet1formatter.format(new Date());
            sheet1cell2.setCellValue("统计时间：" + sheet1dateString);
            sheet1cell2.setCellStyle(createGetCellStyle(workbook));

            List<VwUser> vwUsers = vwUserRepository.findByIdGreaterThanAndCorpIdInAndIdNotInOrderByCreateTime(MagicNumber.ONE_SEVEN, dayCorp, dayActiveManList);

            List<String> sortAll = new ArrayList<>();

            for (int i = 0; i < sort.length; i++) {
                sortAll.add(sort[i]);
            }

            for (int i = 0; i < vwUsers.size(); i++) {
                VwUser vwUser = vwUsers.get(i);
                sortAll.add(vwUser.getId() + "_" + vwUser.getUserName() + "_" + 0 + "_" + vwUser.getCorpName() + "_" + vwUser.getRoleNames());
            }
            LOGGER.debug("统计最后" + sortAll.size());
            for (int i = 0; i < sortAll.size(); i++) {
                String temp = sortAll.get(i);
                String[] t = temp.split("_");

                XSSFRow row = sheet1.getRow(i + MagicNumber.THREE);
                XSSFCell sheet1cell11 = row.getCell(1);
                sheet1cell11.setCellValue(t[1]);
                sheet1cell11.setCellStyle(createBorderedStyle(workbook));
                LOGGER.debug("统计最后" + i + "---" + t[1]);

                XSSFCell sheet1cell22 = row.getCell(2);
                sheet1cell22.setCellValue(t[MagicNumber.THREE]);
                sheet1cell22.setCellStyle(createBorderedStyle(workbook));
                LOGGER.debug("统计最后" + i + "---" + t[MagicNumber.THREE]);

                XSSFCell sheet1cell33 = row.getCell(MagicNumber.THREE);
                sheet1cell33.setCellValue(t[MagicNumber.FOUR]);
                sheet1cell33.setCellStyle(createBorderedStyle(workbook));
                LOGGER.debug("统计最后" + i + "---" + t[MagicNumber.FOUR]);

                XSSFCell sheet1cell44 = row.getCell(MagicNumber.FOUR);
                sheet1cell44.setCellValue(t[2]);
                sheet1cell44.setCellStyle(createBorderedStyle(workbook));
                LOGGER.debug("统计最后" + i + "---" + t[2]);

            }
            LOGGER.debug("日报结束1");

            workbook.write(fos);
            LOGGER.debug("日报结束2");
            fos.flush();
            fos.close();
            is.close();
            workbook.close();
            LOGGER.debug("日报结束");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出出错， {}", e.getMessage());
        }
    }

    // 单元格样式:获取的第二行的第一个单元格的样式
    protected static CellStyle createGetCellStyle(Workbook work) {
        Sheet sheet = work.getSheetAt(0);
        Row rowCellStyle = sheet.getRow(1);
        CellStyle cellStyle = rowCellStyle.getCell(0).getCellStyle();
        return cellStyle;
    }

    // 单元格样式
    protected static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        return style;
    }
}
