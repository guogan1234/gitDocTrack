/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.report;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.DateUtil;
import com.avp.mem.njpb.util.MagicNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by len on 2017/11/15.
 */
@RestController
public class DayReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayReportController.class);
    @Autowired
    VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    /**
     * 测试统计日报接口（）
     *
     * @return
     */
    @RequestMapping(value = "dayCount/newWorkOrderUpOfCorp", method = RequestMethod.GET)
    ResponseEntity<RestBody<String>> newWorkOrderUPOfCorp() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String t = "";
            Date d1 = DateUtil.getBeforeDayBeginTIme();
            Date d2 = DateUtil.getBeforeDayEndTIme();
            List<VwWorkOrder> listDay = vwWorkOrderRepository.findByCreateTimeBetween(d1, d2);
            t += "今日新增工单:" + listDay.size() + "-----------";

            Date d11 = DateUtil.getBeforeYesterdayBeginTIme();
            Date d22 = DateUtil.getBeforeYesterdayEndTIme();
            List<VwWorkOrder> list1 = vwWorkOrderRepository.findByCreateTimeBetween(d11, d22);

            if (list1.size() > 0) {
                String temp = ((listDay.size() - list1.size()) / list1.size()) * MagicNumber.ONE_HUNDRED + "%";
                t += "新增工单环比增长:" + temp + "-------------";
            }

            List<VwWorkOrder> listOver = vwWorkOrderRepository.findByCreateTimeBetweenAndStatusIdGreaterThanEqual(d1, d2, MagicNumber.EIGHT_HUNDRED);
            t += "今日已完成工单:" + listOver.size() + "-----------------";

            List<VwWorkOrder> list = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, 2);
            List<VwWorkOrder> list2 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.THREE);
            List<VwWorkOrder> list3 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.FOUR);
            List<VwWorkOrder> list4 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.FIVE);
            List<VwWorkOrder> list5 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.SIX);
            List<VwWorkOrder> list6 = vwWorkOrderRepository.findByCreateTimeBetweenAndProjectId(d1, d2, MagicNumber.SEVEN);
            t += "各分公司新增工单明细" + "1:" + list.size() + "-----2:" + list2.size() + "---3:" + list3.size() + "---4:" + list4.size() + "---5:" + list5.size() + "---6:" + list6.size() + "----------------------";


            List<String> qianwuming = new ArrayList<>();
            Set<String> huoyue = new HashSet<>();
            Set<Integer> huoyueId = new HashSet<>();


            for (int i = 0; i < listDay.size(); i++) {
                VwWorkOrder temp = listDay.get(i);
                if (temp.getReportEmployeeUserName() != null) {
                    huoyue.add(temp.getReportEmployeeUserName());
                    qianwuming.add(temp.getReportEmployeeUserName());
                    huoyueId.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployeeUserName() != null) {
                    huoyue.add(temp.getAssignEmployeeUserName());
                    qianwuming.add(temp.getAssignEmployeeUserName());
                    huoyueId.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployeeUserName() != null) {
                    huoyue.add(temp.getRepairEmployeeUserName());
                    qianwuming.add(temp.getRepairEmployeeUserName());
                    huoyueId.add(temp.getRepairEmployee());
                }

            }
            t += "今日活跃用户数:" + huoyueId.size() + "--------------------" + "今日活跃前5名:";

            Iterator<Integer> tempbbbbbb = huoyueId.iterator();


            List<Integer> qianwumingtempbbbbbbbbbbbb = new ArrayList<>();

            qianwumingtempbbbbbbbbbbbb.addAll(huoyueId);
//            while (tempbbbbbb.hasNext()) {
//                qianwumingtempbbbbbbbbbbbb.add(tempbbbbbb.next());
//            }

            for (int i = 0; i < qianwumingtempbbbbbbbbbbbb.size(); i++) {
                int b = qianwumingtempbbbbbbbbbbbb.get(i);
                SysUser sysUser = sysUserRepository.findOne(b);
                int s = vwWorkOrderRepository.findByRepairEmployeeAndCreateTimeBetween(b, d1, d2).size() +
                        vwWorkOrderRepository.findByReportEmployeeAndCreateTimeBetween(b, d1, d2).size() +
                        vwWorkOrderRepository.findByAssignEmployeeAndCreateTimeBetween(b, d1, d2).size();
                t += "" + sysUser.getUserName() + ":" + s + "-----------";
            }

            List<String> qianwumingyes = new ArrayList<>();
            Set<String> huoyueyes = new HashSet<>();
            Set<Integer> huoyueIdyes = new HashSet<>();
            for (int i = 0; i < list1.size(); i++) {
                VwWorkOrder temp = list1.get(i);
                if (temp.getReportEmployeeUserName() != null) {
                    huoyueyes.add(temp.getReportEmployeeUserName());
                    qianwumingyes.add(temp.getReportEmployeeUserName());
                    huoyueIdyes.add(temp.getReportEmployee());
                    huoyueId.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployeeUserName() != null) {
                    huoyueyes.add(temp.getAssignEmployeeUserName());
                    qianwumingyes.add(temp.getAssignEmployeeUserName());
                    huoyueIdyes.add(temp.getAssignEmployee());
                    huoyueId.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployeeUserName() != null) {
                    huoyueyes.add(temp.getRepairEmployeeUserName());
                    qianwumingyes.add(temp.getRepairEmployeeUserName());
                    huoyueIdyes.add(temp.getRepairEmployee());
                    huoyueId.add(temp.getRepairEmployee());
                }
            }

            if (list1.size() > 0) {
                String temp = ((huoyueId.size() - huoyueIdyes.size()) / huoyueIdyes.size()) * MagicNumber.ONE_HUNDRED + "%";
                t += "活跃用户环比增长:" + temp + "-------------";
            }


            Date d111 = DateUtil.getBeforeThreedayBeginTIme();
            Date d222 = DateUtil.getBeforeThreedayEndTIme();
            List<VwWorkOrder> listThree = vwWorkOrderRepository.findByCreateTimeBetween(d11, d22);

            for (int i = 0; i < listThree.size(); i++) {
                VwWorkOrder temp = listThree.get(i);
                if (temp.getReportEmployee() != null) {
                    huoyueId.add(temp.getReportEmployee());
                }
                if (temp.getAssignEmployee() != null) {
                    huoyueId.add(temp.getAssignEmployee());
                }
                if (temp.getRepairEmployee() != null) {
                    huoyueId.add(temp.getRepairEmployee());
                }
            }

            Iterator<Integer> tempbbbb = huoyueId.iterator();
            List<Integer> qianwumingtempbbbbbbbb = new ArrayList<>();
            qianwumingtempbbbbbbbb.addAll(huoyueId);



            List<Integer> qianwumingCorp = new ArrayList<>();
            qianwumingCorp.add(2);
            qianwumingCorp.add(MagicNumber.THREE);
            qianwumingCorp.add(MagicNumber.FOUR);
            qianwumingCorp.add(MagicNumber.FIVE);
            qianwumingCorp.add(MagicNumber.SIX);
            List<SysUser> sysUsers = sysUserRepository.findByIdNotInAndCorpIdIn(qianwumingtempbbbbbbbb, qianwumingCorp);
            t += "最近三天未使用系统的用户:";
            for (int i = 0; i < sysUsers.size(); i++) {
                t += sysUsers.get(i).getUserName() + "---------";
            }


            builder.setResultEntity(t, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
