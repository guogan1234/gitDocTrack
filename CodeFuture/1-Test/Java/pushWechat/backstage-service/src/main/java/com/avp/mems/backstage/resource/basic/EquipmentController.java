package com.avp.mems.backstage.resource.basic;

import com.avp.mems.backstage.entity.basic.Equipment;
import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.repositories.basic.EquipmentRepository;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.repositories.user.UserProjectRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.SecurityUtil;
import com.avp.mems.backstage.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amber Wang on 2017/5/28
 */
@RestController
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 需要校验权限
     *
     * @param barCode
     * @return
     */
    @RequestMapping(value = "equipments/search/findByBarcode", method = RequestMethod.GET)
    ResponseEntity scanByBarcode(@RequestParam("barCode") String barCode) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<Location> locationList = new ArrayList<>();
        try {
            String username = SecurityUtil.getLoginUserName();
            if (Validator.isNotNull(barCode)) {
                Map<String, Object> resultMap = new HashMap<>();
                Equipment equipment = equipmentRepository.findByBarCode(barCode);

                if(equipment==null){
                    builder.setErrorCode(ResponseCode.NOT_EXIST);
                    return builder.getResponseEntity();
                }
                boolean projectEquals = false;

                List<BigInteger> lineIds = projectRepository.findLineIdByUserName(username);
//
//                locationRepository.findByParentId(location.getId());

                Integer equipmentLineId = locationRepository.findOne(equipment.getLocationId().intValue()).getParentId();

                if (lineIds.contains(BigInteger.valueOf(equipmentLineId))) {
                    projectEquals = true;
                }

                if (!projectEquals) {
                    builder.setErrorCode(ResponseCode.PERMISSION_DENIED);
                    return builder.getResponseEntity();
                }

                Long locationId = equipment.getLocationId();
                if (Validator.isNotNull(locationId)) {
                    Location location = locationRepository.findOne(locationId.intValue());
                    locationList.add(location);
                    if (Validator.isNotNull(location.getParentId())) {
                        Location lineLocation = locationRepository.findOne(location.getParentId().intValue());
                        locationList.add(lineLocation);
                    }
                }
                resultMap.put("location", locationList);
                resultMap.put("equipment", equipment);
                builder.setResultEntity(resultMap, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.BAD_REQUEST);
        }
        return builder.getResponseEntity();
    }


    @RequestMapping(value = "equipments/search/findByStationId", method = RequestMethod.GET)
    ResponseEntity findByStationIdAndProfessionId(@RequestParam("stationId") Long stationId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<Equipment> equipments = new ArrayList<>();
        SecurityUtil.getLoginUserName();
        try {
            if (Validator.isNotNull(stationId) ) {
                equipments = equipmentRepository.findByLocationId(stationId);

                builder.setResultEntity(equipments, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.BAD_REQUEST);
        }
        return builder.getResponseEntity();
    }
}
