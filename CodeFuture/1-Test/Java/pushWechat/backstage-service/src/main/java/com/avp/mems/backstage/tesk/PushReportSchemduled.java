package com.avp.mems.backstage.tesk;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.entity.basic.LocationVo;
import com.avp.mems.backstage.entity.work.WorkOrder;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/16.
 */
@Component
public class PushReportSchemduled {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //	@Scheduled(cron = "0 15 10 ? * *")   每天10点15分出发
    @Scheduled(cron = "0 * 1 * * ? ")   //每10秒执行一次
    public void excuteYesterdayReoprt() {
        Date yesterdayStartDate = getYesterDayStartTime();
        Date yesterdayEndDate = getYesterDayEndTime();
        System.out.println("推送开始： 开始时间：" + yesterdayStartDate + "  结束时间：" + yesterdayEndDate);
        List<LocationVo> locationVo = findYesterdayReport(yesterdayStartDate, yesterdayEndDate);
        if (locationVo.size() > 0)
            System.out.println("总数为：" + locationVo.get(0).getCount() + "  已完成：" + locationVo.get(0).getFineshedCount() + " w未完成：" + locationVo.get(0).getNotFineshedCount());

    }

    public List<LocationVo> findYesterdayReport(Date startDate, Date endDate) {

        List<BigInteger> lineIds = projectRepository.findLineIds(6L);

        List<Integer> lineIds_Integer = new ArrayList<>();

        for (BigInteger lineId : lineIds) {
            lineIds_Integer.add(lineId.intValue());
        }

        List<Location> locations = locationRepository.findByIdInAndLevelOrderByIdAsc(lineIds_Integer, (short) 0);

        for (Location location : locations) {
            location.setChild(locationRepository.findByParentId(location.getId()));
        }

        //获取到指定项目下的所有的线路和站点数据，需要查询相应的站点数据
        List<LocationVo> locationVos_line = new ArrayList<>();

        for (Location location_line : locations) {
            List<Location> locations_station = location_line.getChild();

            List<LocationVo> locationVos_station = new ArrayList<>();

            Integer fineshedCount_line = 0;
            Integer notFineshedCount_line = 0;
            Integer count_line = 0;

            for (Location location_station : locations_station) {
                //定义三个参数
                Integer fineshedCount_station = 0;
                Integer notFineshedCount_station = 0;
                Integer count_station = 0;

                //获取指定站点下的所有工单数据
                List<WorkOrder> workOrders = workOrderRepository.findByProjectIdAndLocationIdAndLastUpdateTimeBetween(6L, (long) location_station.getId(), startDate, endDate);

                for (WorkOrder workOrder : workOrders) {
                    Long status = workOrder.getStatusId();
                    if (status > 6 && status != 8) {
                        fineshedCount_station++;
                        fineshedCount_line++;
                    } else {
                        notFineshedCount_station++;
                        notFineshedCount_line++;
                    }
                    count_station++;
                    count_line++;
                }

                //把当前站点对象赋给VO
                LocationVo locationVo_station = new LocationVo();

                location_station.setChild(null);
                locationVo_station.setLocation(location_station);
                locationVo_station.setCount(count_station);
                locationVo_station.setFineshedCount(fineshedCount_station);
                locationVo_station.setNotFineshedCount(notFineshedCount_station);

                locationVos_station.add(locationVo_station);

            }


            LocationVo locationVo_line = new LocationVo();

            location_line.setChild(null);
            locationVo_line.setLocation(location_line);
            locationVo_line.setChilds(locationVos_station);
            locationVo_line.setCount(count_line);
            locationVo_line.setFineshedCount(fineshedCount_line);
            locationVo_line.setNotFineshedCount(notFineshedCount_line);

            locationVos_line.add(locationVo_line);
        }

        return locationVos_line;
    }

    public Date getYesterDayStartTime() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        return yesterday.getTime();
    }

    public Date getYesterDayEndTime() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 23);
        yesterday.set(Calendar.MINUTE, 59);
        yesterday.set(Calendar.SECOND, 59);
        return yesterday.getTime();
    }
}
