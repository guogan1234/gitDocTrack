/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.estate.ObjStation;
import com.avp.mem.njpb.entity.estate.VwObjStation;
import com.avp.mem.njpb.entity.estate.VwObjStation_;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.ObjStationRepository;
import com.avp.mem.njpb.repository.estate.VwEstateRepository;
import com.avp.mem.njpb.repository.estate.VwObjStationRepository;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

//import com.avp.mem.njpb.entity.estate.ObjStation_;

/**
 * Created by pw on 2017/7/26.
 */
@RestController
public class StationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationController.class);

    @Autowired
    ObjStationRepository objStationRepository;

    @Autowired
    VwObjStationRepository vwObjStationRepository;

    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    VwEstateRepository vwEstateRepository;

    /**
     * 站点条件查询
     *
     * @return
     */

    @RequestMapping(value = "stations", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwObjStation>> findAll(Integer corpId, String stationName, Sort sort) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            List<VwObjStation> list = vwObjStationRepository.findAll(where(byConditions(corpId, stationName)), sort);

            LOGGER.debug("查询站点数据成功，数据量为:({})", list.size());
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwObjStation> byConditions(Integer projectId, String stationName) {
        return new Specification<VwObjStation>() {
            public Predicate toPredicate(Root<VwObjStation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("stations/findByConditions请求的参数projectId值为:{}", projectId);
                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwObjStation_.projectId), projectId));
                }

                LOGGER.debug("stations/findByConditions请求的参数stationName值为:{}", stationName);
                if (stationName != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwObjStation_.stationName), stationName));
                }
                predicate.getExpressions().add(builder.isNull(root.get(VwObjStation_.removeTime)));

                return predicate;
            }
        };
    }
    // Dynamic End


    /**
     * 站点新建--编号查重
     *
     * @param stationNo
     * @return
     */
    @RequestMapping(value = "stations/checkStationNo", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> checkStationNo(@RequestParam(required = false) String stationNo) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationNo)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjStation objStation = objStationRepository.findByStationNo(stationNo);
            if (Validator.isNull(objStation)) {
                LOGGER.debug("objStation为空,数据库不存在该编号");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据站点编号：{}查询站点数据，数据库已经存在", stationNo);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点新建--名称查重
     *
     * @param stationName
     * @return
     */
    @RequestMapping(value = "stations/checkStationName", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> checkStationName(@RequestParam(required = false) String stationName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationName)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjStation objStation = objStationRepository.findByStationName(stationName);
            if (Validator.isNull(objStation)) {
                LOGGER.debug("objStation为空,数据库不存在该名称");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据站点名称：{}查询站点数据，数据库已经存在", stationName);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点编辑--编号查重
     *
     * @param stationNo
     * @return
     */
    @RequestMapping(value = "stations/checkStationNoAndIdNot", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> checkStationNoAndIdNot(@RequestParam(required = false) String stationNo, @RequestParam(required = false) Integer stationId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationNo) || Validator.isNull(stationId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjStation objStation = objStationRepository.findByStationNoAndIdNot(stationNo, stationId);
            if (Validator.isNull(objStation)) {
                LOGGER.debug("objStation为空,数据库不存在该编号");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据站点编号：{}查询站点数据，数据库已经存在", stationNo);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点编辑--名称查重
     *
     * @param stationName
     * @return
     */
    @RequestMapping(value = "stations/checkStationNameAndIdNot", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> checkStationNameAndIdNot(@RequestParam(required = false) String stationName, @RequestParam(required = false) Integer stationId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationName) || Validator.isNull(stationId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjStation objStation = objStationRepository.findByStationNameAndIdNot(stationName, stationId);
            if (Validator.isNull(objStation)) {
                LOGGER.debug("objStation为空,数据库不存在该名称");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据站点名称：{}查询站点数据，数据库已经存在", stationName);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备站点-新建
     *
     * @param objStation
     * @return
     */
    @RequestMapping(value = "stations", method = RequestMethod.POST)
    ResponseEntity saveObjStation(@RequestBody ObjStation objStation) {
        LOGGER.debug("站点新建");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            String stationName = objStation.getStationName();
            if (Validator.isNotNull(objStation)) {
                if (Validator.isNull(stationName)) {
                    LOGGER.debug("站点名字参数缺失");
                    builder.setResponseCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                objStation = objStationRepository.save(objStation);

                builder.setResultEntity(objStation, ResponseCode.CREATE_SUCCEED);
                int bikeCount = objStation.getEstateCount();
                int t = MagicNumber.MILLION;
                for (int j = 0; j < bikeCount; j++) {
                    String lastNo = "";
                    if (j < MagicNumber.NINE) {
                        lastNo = "0" + (j + 1);
                    } else {
                        lastNo = "" + (j + 1);
                    }

                    ObjEstate objEstate = new ObjEstate();
                    objEstate.setEstateTypeId(1);
                    objEstate.setStationId(objStation.getId());
                    objEstate.setCategory(0);
                    objEstate.setEstateName("车桩" + (j + 1));
                    objEstate.setEstateStatusId(1);
                    objEstate.setProjectId(objStation.getProjectId());
                    objEstate.setBicycleStakeBarCode(objStation.getStationNo() + lastNo);
                    objEstate.setEstateNo(t++);
                    objEstateRepository.save(objEstate);
                }
//                ObjBarcodeImage objImageBarCode = new ObjBarcodeImage();
//                objImageBarCode.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
//                objImageBarCode = objImageBarCodeRepository.save(objImageBarCode);
//                int t1 = MagicNumber.MILLION;
//                String barCodeSn = objImageBarCode.getBarCodeSn();
//                ObjEstate objEstate = new ObjEstate();
//                objEstate.setEstateTypeId(MagicNumber.THREE);
//                objEstate.setStationId(objStation.getId());
//                objEstate.setCategory(0);
//                objEstate.setEstateName("广告牌1");
//                objEstate.setEstateStatusId(1);
//                objEstate.setProjectId(objStation.getProjectId());
//                objEstate.setEstateSn(barCodeSn);
//                objEstate.setEstateNo(t1++);
//                objEstateRepository.save(objEstate);

//                ObjBarcodeImage objImageBarCode1 = new ObjBarcodeImage();
//                objImageBarCode1.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
//                objImageBarCode1 = objImageBarCodeRepository.save(objImageBarCode1);
//                int t2 = MagicNumber.MILLION;
//                String barCodeSn1 = objImageBarCode1.getBarCodeSn();
//                ObjEstate objEstate1 = new ObjEstate();
//                objEstate1.setEstateTypeId(MagicNumber.THREE);
//                objEstate1.setStationId(objStation.getId());
//                objEstate1.setCategory(0);
//                objEstate1.setEstateName("广告牌1");
//                objEstate1.setEstateStatusId(1);
//                objEstate1.setProjectId(objStation.getProjectId());
//                objEstate1.setEstateSn(barCodeSn1);
//                objEstate1.setEstateNo(t2++);
//                objEstateRepository.save(objEstate);


            } else {
                LOGGER.debug("站点传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点-编辑
     *
     * @param id
     * @param objStation
     * @return
     */
    @RequestMapping(value = "stations/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjStation(@PathVariable("id") Integer id, @RequestBody ObjStation objStation) {
        LOGGER.debug("updateObjStation/id,id是{}", id);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String stationName = objStation.getStationName();
            if (Validator.isNotNull(objStation)) {
                if (Validator.isNull(stationName)) {
                    LOGGER.debug("updateObjStation-----stationName参数缺失");
                    builder.setResponseCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                Boolean isExists = objStationRepository.exists(id);

                ObjStation objStation1 = objStationRepository.findOne(id);

                List<ObjEstate> objEstates = objEstateRepository.findByProjectId(objStation1.getProjectId());
                if (Validator.isNotNull(objEstates)) {
                    List<ObjEstate> objEstates2 = new ArrayList<>();
                    for (int i = 0; i < objEstates.size(); i++) {
                        ObjEstate objEstate = objEstates.get(i);
                        Date t = objEstate.getCreateTime();
                        objEstate.setId(objEstate.getId());
                        objEstate.setProjectId(objStation.getProjectId());
                        objEstate.setCreateTime(t);
                        objEstates2.add(objEstate);
                    }
                    objEstateRepository.save(objEstates2);
                }


                if (isExists) {
                    objStation.setId(id);
                    objStation.setCreateTime(objStation1.getCreateTime());
                    objStation.setRemark(objStation1.getRemark());
                    builder.setResultEntity(objStationRepository.save(objStation), ResponseCode.UPDATE_SUCCEED);
                } else {

                    builder.setResponseCode(ResponseCode.NOT_EXIST, "站点不存在");
                    LOGGER.debug("根据id({})查询用户，数据不存在", id);
                }

            } else {
                LOGGER.debug("update--objStation----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 站点-批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "stations/deleteMore", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteObjStation(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < MagicNumber.THREE_ZERO) {
                List<ObjStation> stations = objStationRepository.findByIdIn(ids);
                for (ObjStation r : stations) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                objStationRepository.save(stations);
                builder.setResponseCode(ResponseCode.DELETE_SUCCEED);
                LOGGER.debug("ids({})对应的批次号批量删除成功", ids);
            } else {
                LOGGER.debug("批量删除的数量必须在30条以内");
                builder.setResponseCode(ResponseCode.FAILED, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.DELETE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 站点--编号名称--模糊匹配
     *
     * @param stationNoName
     * @return
     */
    @RequestMapping(value = "stations/findByStationNoNameLike", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwObjStation>> findByStationNoNameLike(@RequestParam(required = false) Integer corpId, @RequestParam String stationNoName, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Page<VwObjStation> vwStations;
        try {
            LOGGER.debug("传入站点编号名为：{},公司id为：{}", stationNoName, corpId);
            if (Validator.isNull(corpId)) {
                vwStations = vwObjStationRepository.findByStationNoNameContaining(stationNoName, page);
            } else {
                vwStations = vwObjStationRepository.findByProjectIdAndStationNoNameContaining(corpId, stationNoName, page);
            }
            if (vwStations.getTotalElements() > 0) {
                LOGGER.debug("站点名字模糊匹配成功，数量为：{}", vwStations.getTotalElements());
                builder.setResultEntity(vwStations, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("没有相似的站点编号名称");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "没有相似的站点");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点--编号查询
     *
     * @param stationNo
     * @return
     */
    @RequestMapping(value = "stations/findByStationNo", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwObjStation>> findByStationNo(@RequestParam(required = false) Integer corpId, @RequestParam String stationNo) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("传入站点编号名为：{},公司id为：{}", stationNo, corpId);
            VwObjStation vwObjStation;
            if (Validator.isNull(corpId)) {
                vwObjStation = vwObjStationRepository.findByStationNo(stationNo);
            } else {
                vwObjStation = vwObjStationRepository.findByStationNoAndProjectId(stationNo, corpId);
            }
            if (Validator.isNotNull(vwObjStation)) {
                LOGGER.debug("站点编号为：{}", vwObjStation.getStationNo());
                builder.setResultEntity(vwObjStation, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("编号不存在");
                builder.setResponseCode(ResponseCode.OK, "您搜索的站点编号不存在");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 站点--名称查询
     *
     * @param corpId
     * @param stationName
     * @return
     */
    @RequestMapping(value = "stations/findByStationName", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwObjStation>> findByStationName(@RequestParam(required = false) Integer corpId, @RequestParam String stationName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("传入站点名为：{},公司id为：{}", stationName, corpId);
            VwObjStation vwObjStation;
            if (Validator.isNull(corpId)) {
                vwObjStation = vwObjStationRepository.findByStationName(stationName);
            } else {
                vwObjStation = vwObjStationRepository.findByStationNameAndProjectId(stationName, corpId);
            }
            if (Validator.isNotNull(vwObjStation)) {
                LOGGER.debug("站点名字为：{}", vwObjStation.getStationName());
                builder.setResultEntity(vwObjStation, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("站点不存在");
                builder.setResponseCode(ResponseCode.OK, "您搜索的站点不存在");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 通过二维码查找--站点
     *
     * @param stationSn
     * @return
     */
    @RequestMapping(value = "stations/findByStationSn", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> findByStationSn(@RequestParam(required = false) String stationSn) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationSn)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjStation objStation = objStationRepository.findByStationSn(stationSn);
            if (Validator.isNull(objStation)) {
                LOGGER.debug("objStation为空,数据库不存在该站点");
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
            } else {
                LOGGER.debug("站点编号：{}", objStation.getStationNo());
                builder.setResultEntity(objStation, ResponseCode.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
