package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjStation;
import com.avp.mem.njpb.reponsitory.basic.ObjStationRepository;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by pw on 2017/7/26.
 */
@RestController
public class ObjStationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjStationRepository objStationRepository;

    /**
     * 站点查询
     * @return
     */
    @RequestMapping(value = "stations", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> findAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjStation> stations = objStationRepository.findByRemoveTimeIsNull();
            logger.debug("查询站点成功,数据量为:{}", stations.size());
            builder.setResultEntity(stations, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点--名称查重
     * @param stationName
     * @return
     */
    @RequestMapping(value = "stations/findByStationName", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> findByStationName(@RequestParam String stationName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjStation station = objStationRepository.findByStationNameAndRemoveTimeIsNull(stationName);
            if(station != null){
                logger.debug("供应商名字"+station.getStationName());
                builder.setResultEntity(station, ResponseCode.RETRIEVE_SUCCEED);
                return builder.getResponseEntity();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-新建
     * @param objStation
     * @return
     */
    @RequestMapping(value = "stations", method = RequestMethod.POST)
    ResponseEntity saveObjStation(@RequestBody ObjStation objStation) {
        logger.debug("saveObjStation----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer userId = SecurityUtils.getLoginUserId();
            String stationName = objStation.getStationName();
            if (Validator.isNotNull(objStation)) {
                if (Validator.isNull(stationName)) {
                    logger.debug("saveObjStation-----stationName参数缺失");
                    builder.setErrorCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                ObjStation stations = objStationRepository.findByStationNameAndRemoveTimeIsNull(stationName);
                if (Validator.isNotNull(stations)) {
                    logger.debug("站点名称【" + stationName + "】已经存在！");
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "供应商名称【" + stationName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objStation.setCreateBy(userId);
                objStation.setLastUpdateBy(userId);
                objStation = objStationRepository.save(objStation);
                builder.setResultEntity(objStation, ResponseCode.CREATE_SUCCEED);
            } else {
                logger.debug("ObjStation----传入对象为空");
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-编辑
     * @param id
     * @param objStation
     * @return
     */
    @RequestMapping(value = "stations/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjStation(@PathVariable("id") Integer id, @RequestBody ObjStation objStation) {
        //测试：@PathVariable("id") Integer id
        //"stations/{id}"
        logger.debug("updateObjStation/id,id是{}", id);
//        logger.debug("updateObjStation/id,id是{}", str);
//        Integer id = 1;
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String stationName = objStation.getStationName();
            if (Validator.isNotNull(objStation)) {
                if (Validator.isNull(stationName)) {
                    logger.debug("updateObjStation-----stationName参数缺失");
                    builder.setErrorCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                ObjStation station = objStationRepository.findByStationNameAndRemoveTimeIsNull(stationName);
                if (Validator.isNotNull(station)) {
                    logger.debug("update--站点名称【" + stationName + "】已经存在！");
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "update--站点名称【" + stationName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objStation.setId(id);
                objStation = objStationRepository.save(objStation);
                builder.setResultEntity(objStation, ResponseCode.UPDATE_SUCCEED);
            } else {
                logger.debug("update--objStation----传入对象为空");
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "stations/batchDelete", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteObjStation(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                logger.debug("ids({})参数缺失", ids);
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < 30) {
                List<ObjStation> stations = objStationRepository.findByIdInAndRemoveTimeIsNull(ids);
                for (ObjStation r : stations) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                builder.setResultEntity(objStationRepository.save(stations), ResponseCode.DELETE_SUCCEED);
                logger.debug("ids({})对应的批次号批量删除成功", ids);
            } else {
                logger.debug("批量删除的数量必须在30条以内");
                builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        return builder.getResponseEntity();
    }
}
