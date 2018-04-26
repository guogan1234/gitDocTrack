package com.avp.mems.backstage.resource.basic;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.rest.RestBody;
import com.avp.mems.backstage.util.SecurityUtil;
import com.avp.mems.backstage.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujs on 2017/5/27.
 */
@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 企业微信报修时选择线路、站点
     *
     * @param
     * @return
     */
    @RequestMapping("/locations")
    ResponseEntity getLocations() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        String userName = SecurityUtil.getLoginUserName();

        List<BigInteger> lineIds = projectRepository.findLineIdByUserName(userName);

        List<Integer> lineIds_Integer =new ArrayList<>();

        for(BigInteger lineId :lineIds){
            lineIds_Integer.add(lineId.intValue());
        }

        List<Location> locations = locationRepository.findByIdInAndLevel(lineIds_Integer, (short) 0);

        for (Location location : locations) {
            location.setChild(locationRepository.findByParentId(location.getId()));
        }

        logger.debug("查询线路站点数据成功，线路总数据条目为:{}",locations.size());

        builder.setResultEntity(locations, ResponseCode.RETRIEVE_SUCCEED);


//        if (Validator.isNotNull(lineId)) {
//            List<Location> locationList = locationRepository.findByIdIn(lineId);
//            builder.setResultEntity(locationList, ResponseCode.RETRIEVE_SUCCEED);
//            return builder.getResponseEntity();
//        }
//        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }
}
