package com.avp.mems.backstage.resource.report;

import com.avp.mems.backstage.entity.report.FaultComponentStatisticsWeek;
import com.avp.mems.backstage.entity.report.FaultDistributionLineDay;
import com.avp.mems.backstage.entity.report.FaultDistributionLineWeekLocationView;
import com.avp.mems.backstage.entity.report.FaultWeekReprtVO;
import com.avp.mems.backstage.repositories.report.FaultComponentStatisticsWeekRepository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineDayRespository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineWeekLocationViewRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
@RestController
public class FaultDistributionLineWeekController {

    @Autowired
    private FaultDistributionLineWeekLocationViewRepository faultDistributionLineMonthLocationViewRepository;

    @Autowired
    private FaultDistributionLineDayRespository faultDistributionLineDayRespository;

    @Autowired
    private FaultComponentStatisticsWeekRepository faultComponentStatisticsWeekRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/linefaultWeek/getLastWeekReport", method = RequestMethod.GET)
    ResponseEntity getFaultDistributionLineWeek(@Param("projectId") Long projectId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<FaultWeekReprtVO> projectFaultWeekReportList = new ArrayList<FaultWeekReprtVO>();
        List<BigInteger> lineIds = projectRepository.findLineIds(projectId);
        for (BigInteger lineId : lineIds) {
            FaultWeekReprtVO faultWeekReport = new FaultWeekReprtVO();

            Date lastWeekBeginTime = DateUtil.getLastWeekBeginTime(new Date());
            int weekOfYear_last_week = DateUtil.getWeekOfYear(lastWeekBeginTime);
            int year_last_week = DateUtil.getYear(DateUtil.getLastWeekBeginTime(lastWeekBeginTime));

            String dateStr = year_last_week + "." + weekOfYear_last_week;
            FaultDistributionLineWeekLocationView faultLineReport = faultDistributionLineMonthLocationViewRepository.findByWeekOfYearAndLocationId(dateStr, lineId.intValue());
            Date beforeWeekBeginDate = DateUtil.getBeforeWeekBeginTime();
            Date beforeWeekEndDate = DateUtil.getBeforeWeekEndTime();
            int weekOfMonth = DateUtil.getWeekOfMonth(new Date());
            List<FaultDistributionLineDay> faultLineDayList = faultDistributionLineDayRespository.findByLocationIdAndLocationLevelAndDayDateBetween(lineId.intValue(), (short) 0, beforeWeekBeginDate, beforeWeekEndDate);
            if (faultLineDayList.size() > 0) {
                faultLineReport.setFaultDistributionLineDays(faultLineDayList);
            }
            faultWeekReport.setFaultDistributionLineWeekLocationView(faultLineReport);
            //周_线路_设备 故障总计
            System.out.print("查询时间为:" + year_last_week + "." + weekOfYear_last_week);
//        FaultComponentStatisticsWeek faultComponentStatisticsWeek = faultComponentStatisticsWeekRepository.findByWeekOfYearAndLocationIdAndComponentTypeId(year_last_week + "." + weekOfYear_last_week, lineId, -1);
//        faultWeekReport.setFaultComponentStatisticsWeek(faultComponentStatisticsWeek);
            //页面柱状图显示
            List<FaultComponentStatisticsWeek> faultComponentStatisticsWeekList = new ArrayList<>();
            faultComponentStatisticsWeekList = faultComponentStatisticsWeekRepository.findByLocationIdAndWeekOfYearOrderByLocationIdAsc(lineId.intValue(), year_last_week + "." + weekOfYear_last_week);
            faultWeekReport.setFaultComponentStatisticsWeekList(faultComponentStatisticsWeekList);
            projectFaultWeekReportList.add(faultWeekReport);
        }
        builder.setResultEntity(projectFaultWeekReportList, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "/linefaultWeek/getLastWeekFaultComponentDetails", method = RequestMethod.GET)
    ResponseEntity getFaultComponentDetail(@Param("lineId") Integer lineId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int weekOfYear = DateUtil.getBeforeWeekOfYear();
        int year = DateUtil.getYear(new Date());
        String dateStr = year + "." + weekOfYear;
        List<FaultComponentStatisticsWeek> componentWeekFaultList = new ArrayList<FaultComponentStatisticsWeek>();
        componentWeekFaultList =  faultComponentStatisticsWeekRepository.findByLocationIdAndWeekOfYearOrderByLocationIdAsc(lineId,dateStr);
        builder.setResultEntity(componentWeekFaultList, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "/linefaultWeek/getLastWeekFaultLineDetails", method = RequestMethod.GET)
    ResponseEntity getFaultLineDetail(@Param("lineId") Integer lineId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int weekOfYear = DateUtil.getBeforeWeekOfYear();
        int year = DateUtil.getYear(new Date());
        String dateStr = year + "." + weekOfYear;
        List<FaultDistributionLineWeekLocationView> locationWeekFaults = new ArrayList<FaultDistributionLineWeekLocationView>();
        locationWeekFaults = faultDistributionLineMonthLocationViewRepository.findByWeekOfYearAndLocationIdOrParentId(dateStr,lineId,lineId.longValue());
        builder.setResultEntity(locationWeekFaults, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }
    /**
     * double保留小数点后面2位
     */
    public static Double getDoubleRound(Double f) {
        BigDecimal bg = new BigDecimal(f);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
