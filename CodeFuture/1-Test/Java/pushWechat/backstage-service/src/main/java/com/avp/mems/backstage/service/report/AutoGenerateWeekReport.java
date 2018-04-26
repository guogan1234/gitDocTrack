/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.service.report;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.entity.report.FaultComponentStatisticsWeek;
import com.avp.mems.backstage.entity.report.FaultDistributionLineWeek;
import com.avp.mems.backstage.entity.work.WorkOrder;
import com.avp.mems.backstage.entity.work.WorkOrderComponentView;
import com.avp.mems.backstage.repositories.basic.ComponentRepository;
import com.avp.mems.backstage.repositories.basic.EquipmentRepository;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.report.FaultComponentStatisticsMonthRepository;
import com.avp.mems.backstage.repositories.report.FaultComponentStatisticsWeekRepository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineWeekRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderComponentViewRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderRepository;
import com.avp.mems.backstage.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;

/**
 * @author amber
 */
@Component
public class AutoGenerateWeekReport {

    private static final Long line1 = 1131L;
    private static final Long line2 = 1149L;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private FaultDistributionLineWeekRepository faultDistributionLineWeekRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private FaultComponentStatisticsWeekRepository faultComponentStatisticsWeekRepository;

    @Autowired
    private FaultComponentStatisticsMonthRepository faultComponentStatisticsMonthRepository;

    @Autowired
    private WorkOrderComponentViewRepository workOrderComponentViewRepository;


    private static Logger logger = LoggerFactory.getLogger(AutoGenerateWeekReport.class);

    /**
     * 线路维修概况
     */
    @Scheduled(cron = "${business.scheduled.weekcron}")
    public void executeFaultDistributionLineWeek() {
        logger.info("AutoGenerateWeekReport-executeFaultDistributionLineWeek- Job started.");

        Date lastWeekBeginTime = DateUtil.getLastWeekBeginTime(new Date());

        Date lastWeekEndTime = DateUtil.getLastWeekEndTime(new Date());

        int weekOfMonth = DateUtil.getWeekOfMonth(lastWeekBeginTime);

        int weekOfYear_now = DateUtil.getWeekOfYear(lastWeekBeginTime);
        int weekOfYear_last_week = DateUtil.getWeekOfYear(lastWeekBeginTime);

        int year_now = DateUtil.getYear(lastWeekBeginTime);
        int year_last_week = DateUtil.getYear(DateUtil.getLastWeekBeginTime(lastWeekBeginTime));

        List<BigInteger> lineIds = projectRepository.findLineIds(6L);

        List<Integer> lineIds_Integer = new ArrayList<>();

        for (BigInteger lineId : lineIds) {
            lineIds_Integer.add(lineId.intValue());
        }

        List<Location> locations = locationRepository.findByIdInAndLevelOrderByIdAsc(lineIds_Integer, (short) 0);

        for (Location location : locations) {
            location.setChild(locationRepository.findByParentId(location.getId()));
        }

        for (Location location_line : locations) {
            List<Location> locations_station = location_line.getChild();

            Integer lineId = location_line.getId();

            Integer fineshedCount_line = 0;
            Integer notFineshedCount_line = 0;
            Integer qodCount_line = 0;
            Integer count_line = 0;
            double growthRate_line = 0.0;
            double responseRatio_line = 0.0;

            for (Location location_station : locations_station) {
                //定义参数
                Integer fineshedCount_station = 0;
                Integer notFineshedCount_station = 0;
                Integer qodCount_station = 0;
                Integer count_station = 0;
                double responseRatio_station = 0.0;
                double growthRate_station = 0.0;

                Integer stationId = location_station.getId();
                //获取指定站点下的所有工单数据
                List<WorkOrder> workOrders = workOrderRepository.findByProjectIdAndLocationIdAndLastUpdateTimeBetween(6L, (long) location_station.getId(), lastWeekBeginTime, lastWeekEndTime);

                for (WorkOrder workOrder : workOrders) {
                    Long status = workOrder.getStatusId();
                    if (status > 6 && status != 8) {
                        fineshedCount_station++;
                        fineshedCount_line++;

                        if (!DateUtils.isSameDay(workOrder.getCreationTime(), workOrder.getLastUpdateTime())) {
                            qodCount_station++;
                            qodCount_line++;
                        }
                    } else {
                        notFineshedCount_station++;
                        notFineshedCount_line++;

                        qodCount_station++;
                        qodCount_line++;
                    }

                    count_station++;
                    count_line++;
                }

                FaultDistributionLineWeek faultDistributionLineWeek_station = new FaultDistributionLineWeek();

                faultDistributionLineWeek_station.setFaultAmount(count_station);
                faultDistributionLineWeek_station.setFinishAmount(fineshedCount_station);
                faultDistributionLineWeek_station.setQodAmount(qodCount_station);
                faultDistributionLineWeek_station.setLocationId(location_station.getId());
                faultDistributionLineWeek_station.setLocationLevel(1);//1:station

                if (count_station != 0 && qodCount_station != 0) {
                    responseRatio_station = 1 - qodCount_station / count_station.doubleValue();
                }

                faultDistributionLineWeek_station.setResponseRatio(responseRatio_station);//响应率


                FaultDistributionLineWeek faultDistributionLineWeek_station_last = faultDistributionLineWeekRepository.findByWeekOfYearAndLocationId(year_last_week + "." + weekOfYear_last_week, stationId);

                if (faultDistributionLineWeek_station_last != null) {
                    growthRate_station = faultDistributionLineWeek_station_last.getFaultAmount() / count_station.doubleValue();
                }

                faultDistributionLineWeek_station.setGrowthRate(growthRate_station);//增长率
                faultDistributionLineWeek_station.setWeekOfMonth(weekOfMonth);
                faultDistributionLineWeek_station.setWeekOfYear(year_now + "." + weekOfYear_now);

                faultDistributionLineWeekRepository.save(faultDistributionLineWeek_station);
            }

            FaultDistributionLineWeek faultDistributionLineWeek_line = new FaultDistributionLineWeek();

            faultDistributionLineWeek_line.setFaultAmount(count_line);
            faultDistributionLineWeek_line.setFinishAmount(fineshedCount_line);
            faultDistributionLineWeek_line.setQodAmount(qodCount_line);
            faultDistributionLineWeek_line.setLocationId(location_line.getId());
            faultDistributionLineWeek_line.setLocationLevel(0);//0:line


            if (count_line != 0 && qodCount_line != 0) {
                responseRatio_line = 1 - qodCount_line / count_line.doubleValue();
            }

            faultDistributionLineWeek_line.setResponseRatio(responseRatio_line);//响应率

            FaultDistributionLineWeek faultDistributionLineWeek_line_last = faultDistributionLineWeekRepository.findByWeekOfYearAndLocationId(year_last_week + "." + weekOfYear_last_week, lineId);

            if (faultDistributionLineWeek_line_last != null) {
                growthRate_line = faultDistributionLineWeek_line_last.getFaultAmount() / count_line.doubleValue();
            }

            faultDistributionLineWeek_line.setGrowthRate(growthRate_line);//增长率
            faultDistributionLineWeek_line.setWeekOfMonth(weekOfMonth);
            faultDistributionLineWeek_line.setWeekOfYear(year_now + "." + weekOfYear_now);

            faultDistributionLineWeekRepository.save(faultDistributionLineWeek_line);
        }

        logger.info("AutoGenerateWeekReport-executeFaultDistributionLineWeek- Job done.");
    }

    /**
     * 模块故障频发率统计
     */
    @Scheduled(cron = "${business.scheduled.weekcron}")
    public void executeFaultComponentStatisticsMonth() {
        logger.info("AutoGenerate-executeFaultComponentStatisticsMonth-Report Job started.");

        List<Long> lineIds = new ArrayList<>();
        lineIds.add(line1);
        lineIds.add(line2);

        this.executeGenerateFaultComMonthReport(lineIds);

        logger.info("AutoGenerateMonthReport-executeFaultComponentStatisticsMonth- Job done.");
    }


    public  Integer getComponentTypeCountByLineIdAndComponentTypeId(Integer lineId, BigInteger componentTypeId) {
        Integer line_componentType_componentCount = componentRepository.countComponentByTypeIdAndLineId(componentTypeId.longValue(),lineId.intValue());
        return line_componentType_componentCount;
    }

    public Integer getComponentTypeCountByLineId(Integer lineId) {
        Integer line_compponetCount = componentRepository.countComponentByLineId(lineId.intValue());
        return line_compponetCount;
    }

    private void executeGenerateFaultComMonthReport(List<Long> lineIds) {
        for(Long lineId :lineIds){

            Date lastWeekBeginTime = DateUtil.getLastWeekBeginTime(new Date());

            Date lastWeekEndTime = DateUtil.getLastWeekEndTime(new Date());

            int weekOfMonth = DateUtil.getWeekOfMonth(lastWeekBeginTime);//UI只做显示

            int weekOfYear_now = DateUtil.getWeekOfYear(lastWeekBeginTime);
            int weekOfYear_last_week = DateUtil.getWeekOfYear(lastWeekBeginTime);

            int year_now = DateUtil.getYear(lastWeekBeginTime);
            int year_last_week = DateUtil.getYear(DateUtil.getLastWeekBeginTime(lastWeekBeginTime));

            //1131 一号线 1149 二号线
            BigInteger projectId = BigInteger.valueOf(6);
            List<WorkOrderComponentView> workOrderComponentViews_line1 = workOrderComponentViewRepository.findByProjectIdAndLineParentIdAndLastUpdateTimeBetween(projectId, BigInteger.valueOf(lineId), lastWeekBeginTime, lastWeekEndTime);

            Integer faultCount_line1 = 0;
            Integer equipmentCount_line1 = 0;
            //月度设备故障频发率=当月设备故障总数/设备总台数/月度天数*100%
            double faultRate_line1 = 0.0;
            double growthRate_line1 = 0.0;
            Map<BigInteger, Integer> map_component_type = new HashMap<>();

            Map<BigInteger, String> map_component_type_names = new HashMap<>();

            //计算每个模块类型的故障数量
            for (WorkOrderComponentView workOrderComponentView1 : workOrderComponentViews_line1) {
                BigInteger comTypeId = workOrderComponentView1.getComtypeId();

                if (map_component_type.keySet().contains(comTypeId)) {
                    //该模块类型已经处理过
                    map_component_type.put(comTypeId, map_component_type.get(comTypeId) + 1);
                } else {
                    //该模块类型还未处理过
                    map_component_type.put(comTypeId, 1);

                    map_component_type_names.put(comTypeId, workOrderComponentView1.getComName());
                }

                faultCount_line1++;
            }

            for (Map.Entry<BigInteger, Integer> entry : map_component_type.entrySet()) {
                BigInteger key = entry.getKey();
                Integer value = entry.getValue();

                Integer equipmentCount_station = getComponentTypeCountByLineIdAndComponentTypeId(lineId.intValue(), key);
                //---------------保存每种模块类型的故障数量-----------------------
                FaultComponentStatisticsWeek faultComponentStatisticsWeek_line1 = new FaultComponentStatisticsWeek();

                faultComponentStatisticsWeek_line1.setEquipmentCount(equipmentCount_station);
                faultComponentStatisticsWeek_line1.setFaultCount(value);
                faultComponentStatisticsWeek_line1.setComponentTypeName(map_component_type_names.get(key));

                if (value != 0) {
                    faultRate_line1 = value / equipmentCount_station.doubleValue() / 30;
                }
                faultComponentStatisticsWeek_line1.setFaultRate(faultRate_line1);

                //TODO 查询逻辑需要修改
                FaultComponentStatisticsWeek faultComponentStatisticsWeek_station_last = faultComponentStatisticsWeekRepository.findByWeekOfYearAndLocationIdAndComponentTypeId(year_last_week + "." + weekOfYear_last_week, lineId.intValue(), key.intValue());

                if (faultComponentStatisticsWeek_station_last != null) {
                    Integer fault_count_last = faultComponentStatisticsWeek_station_last.getFaultCount();

                    if (fault_count_last != null && fault_count_last != 0) {
                        growthRate_line1 = (value - fault_count_last) / fault_count_last.doubleValue();
                    }
                }
                faultComponentStatisticsWeek_line1.setGrowthRate(growthRate_line1);

                faultComponentStatisticsWeek_line1.setWeekOfMonth(weekOfYear_now);
                faultComponentStatisticsWeek_line1.setWeekOfYear(year_now + "." + weekOfYear_now);
                faultComponentStatisticsWeek_line1.setComponentTypeId(key.intValue());
                faultComponentStatisticsWeek_line1.setLocationId(lineId.intValue());
                faultComponentStatisticsWeek_line1.setLocationLevel(1);

                faultComponentStatisticsWeekRepository.save(faultComponentStatisticsWeek_line1);
            }


            //查询线路下的所有的模块的数量
            Integer com_count_line1 = getComponentTypeCountByLineId(lineId.intValue());

            FaultComponentStatisticsWeek faultComponentStatisticsWeek_line1 = new FaultComponentStatisticsWeek();

            faultComponentStatisticsWeek_line1.setEquipmentCount(com_count_line1);
            faultComponentStatisticsWeek_line1.setFaultCount(faultCount_line1);

            if (faultCount_line1 != 0) {
                faultRate_line1 = faultCount_line1 / com_count_line1.doubleValue() / 30;
            }
            faultComponentStatisticsWeek_line1.setFaultRate(faultRate_line1);

            //TODO 查询逻辑需要修改
            FaultComponentStatisticsWeek faultComponentStatisticsWeek_station_last = faultComponentStatisticsWeekRepository.findByWeekOfYearAndLocationIdAndComponentTypeId(year_last_week + "." + weekOfYear_last_week, lineId.intValue(),-1);

            if (faultComponentStatisticsWeek_station_last != null) {
                Integer fault_count_last = faultComponentStatisticsWeek_station_last.getFaultCount();

                if (fault_count_last != null && fault_count_last != 0) {
                    growthRate_line1 = (faultCount_line1 - fault_count_last) / fault_count_last.doubleValue();
                }
            }
            faultComponentStatisticsWeek_line1.setGrowthRate(growthRate_line1);

            faultComponentStatisticsWeek_line1.setWeekOfMonth(weekOfMonth);
            faultComponentStatisticsWeek_line1.setWeekOfYear(year_now + "." + weekOfYear_now);
            faultComponentStatisticsWeek_line1.setComponentTypeId(-1);
            faultComponentStatisticsWeek_line1.setLocationId(lineId.intValue());
            faultComponentStatisticsWeek_line1.setLocationLevel(0);

            faultComponentStatisticsWeekRepository.save(faultComponentStatisticsWeek_line1);
        }

    }


}
