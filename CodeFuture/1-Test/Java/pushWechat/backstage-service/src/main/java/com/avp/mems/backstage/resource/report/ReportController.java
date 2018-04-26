package com.avp.mems.backstage.resource.report;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.entity.basic.LocationVo;
import com.avp.mems.backstage.entity.work.WorkOrder;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amber Wang on 2017-05-29 下午 05:32.
 */
@RestController
public class ReportController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "userWechats/faultTypeDistributions/search/findDailyReportNow", method = RequestMethod.GET)
    ResponseEntity findDailyReportNow() {

        logger.debug("url:faultTypeDistributions/search/findDailyReport----------");

        ResponseBuilder builder = ResponseBuilder.createBuilder();

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
                List<WorkOrder> workOrders = workOrderRepository.findByProjectIdAndLocationIdAndLastUpdateTimeBetween(6L, (long) location_station.getId(), DateUtil.getTodayStartTime(),new Date());
Date t = DateUtil.getTodayStartTime();
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


        builder.setResultEntity(locationVos_line, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }



    @RequestMapping(value = "userWechats/faultTypeDistributions/search/findDailyReportNoParam", method = RequestMethod.GET)
    ResponseEntity findDailyReportNoParam() {

        logger.debug("url:faultTypeDistributions/search/findDailyReport----------");

        ResponseBuilder builder = ResponseBuilder.createBuilder();

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
                List<WorkOrder> workOrders = workOrderRepository.findByProjectIdAndLocationIdAndLastUpdateTimeBetween(6L, (long) location_station.getId(), DateUtil.getBeforeDayBeginTIme(),DateUtil.getBeforeDayEndTIme());

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


        builder.setResultEntity(locationVos_line, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }



}
