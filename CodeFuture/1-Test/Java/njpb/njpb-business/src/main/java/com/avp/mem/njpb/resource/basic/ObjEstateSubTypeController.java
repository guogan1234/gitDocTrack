//package com.avp.mem.njpb.resource.basic;
//
//import com.avp.mem.njpb.api.rest.ResponseBuilder;
//import com.avp.mem.njpb.api.rest.ResponseCode;
//import com.avp.mem.njpb.api.rest.RestBody;
//import com.avp.mem.njpb.entity.ObjEstateSubType;
//import com.avp.mem.njpb.reponsitory.basic.ObjEstateSubTypeRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by six on 2017/7/20.
// */
//@RestController
//public class ObjEstateSubTypeController {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    ObjEstateSubTypeRepository objEstateSubTypeRepository;
//
//    /**
//     * 模块类型--查询所有
//     * @return
//     */
//    @RequestMapping(value = "estateSubTypes", method = RequestMethod.GET)
//    ResponseEntity<RestBody<ObjEstateSubType>> findAll() {
//        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        try {
//            List<ObjEstateSubType> estateSubTypes = objEstateSubTypeRepository.findByRemoveTimeIsNull();
//            logger.debug("查询模块类型成功,数据量为:{}", estateSubTypes.size());
//
//            builder.setResultEntity(estateSubTypes, ResponseCode.RETRIEVE_SUCCEED);
//            return builder.getResponseEntity();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
//        }
//        return builder.getResponseEntity();
//    }
//
//    /**
//     * 模块类型查询
//     * @param estateSubTypesName
//     * @return
//     */
//    @RequestMapping(value = "estateSubTypes/findByEstateSubTypesName", method = RequestMethod.GET)
//    ResponseEntity<RestBody<ObjEstateSubType>> findByPlaceName(@RequestParam String estateSubTypesName) {
//        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        try {
//            ObjEstateSubType estateSubTypes = objEstateSubTypeRepository.findByNameAndRemoveTimeIsNull(estateSubTypesName);
//            logger.debug("模块名字"+estateSubTypes.getName());
//            builder.setResultEntity(estateSubTypes, ResponseCode.RETRIEVE_SUCCEED);
//            return builder.getResponseEntity();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
//        }
//        return builder.getResponseEntity();
//    }
//
//    /**
//     * 模块类型-新建
//     * @param objEstateSubType
//     * @param estateTypeIds
//     * @return
//     */
////    @RequestMapping(value = "estateSubTypes", method = RequestMethod.POST)
////    ResponseEntity saveObjEstateSubType(@RequestBody ObjEstateSubType objEstateSubType,
////                                      @RequestParam(value = "estateTypeIds") List<Integer> estateTypeIds) {
////        logger.debug("saveObjEstateSubType----------");
////        ResponseBuilder builder = ResponseBuilder.createBuilder();
////        try {
////            Integer userId = SecurityUtils.getLoginUserId();
////            String estateSubTypeName = objEstateSubType.getName();
////            if (Validator.isNotNull(objEstateSubType)) {
////                if (estateTypeIds == null || estateTypeIds.size() == 0) {
////                    logger.debug("estateTypeId不能为空");
////                    builder.setErrorCode(ResponseCode.PARAM_MISSING, "所属设备类型不能为空");
////                    return builder.getResponseEntity();
////                }
////                ObjEstateSubType estateSubTypes = objEstateSubTypeRepository.findByNameAndRemoveTimeIsNull(estateSubTypeName);
////                if (Validator.isNotNull(estateSubTypes)) {
////                    //数据库中已存在该模块类型
////                    logger.debug("数据库中已经存在该模块类型，ID:{}，名称为:{}", estateSubTypes.getId(), estateSubTypes.getName());
////
////                    for (Integer estateTypeId : estateTypeIds) {
////
////                        //判断是否重复关联
////                        List<VwEstateSubType> duplCheck = vwEstateSubTypeRepository.findByEstateTypeIdAndEstateSubTypeIdAndIsDeleteIsNull(estateTypeId, estateSubTypeId);
////                        logger.debug("根据设备类型ID：{}，模块类型ID：{}查询关联关系，数据量为：{}", estateTypeId, estateSubTypeId, duplCheck.size());
////
////                        if (duplCheck.size() > 0) {
////                            logger.debug("({})对应的模块类型已经与部分设备类型(ID：{}),不能重复关联", objEstateSubType.getName(), estateTypeId);
////
////                            resultEntity = ResultEntity.getResultEntity(90);
////                            resultEntity.setResultMassage(estateSubType.getName() + "：对应的模块类型已经与部分设备类型有关联，不能重复关联");
////                            return resultEntity;
////                        }
////
////                        AssoEstateModuleType assoEstateModuleType = new AssoEstateModuleType(estateTypeId, estateSubType.getId(), uid, uid);
////                        assoEstateModuleTypes.add(assoEstateModuleType);
////                    }
////
////                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "模块类型【" + estateSubTypeName + "】已经存在！");
////                    return builder.getResponseEntity();
////                }
////
////
////
////
////
////                objEstateSubType.setCreateBy(userId);
////                objEstateSubType.setLastUpdateBy(userId);
////                objEstateSubType = objEstatePlaceRepository.save(objEstateSubType);
////                builder.setResultEntity(objEstatePlace, ResponseCode.CREATE_SUCCEED);
////            } else {
////                logger.debug("objEstatePlace----传入对象为空");
////                builder.setErrorCode(ResponseCode.PARAM_MISSING);
////            }
////        } catch (Exception e) {
////            logger.error(e.getMessage());
////            builder.setErrorCode(ResponseCode.CREATE_FAILED);
////        }
////        return builder.getResponseEntity();
////    }
//
//    /**
//     * 场地管理-编辑
//     * @param id
//     * @param objEstatePlace
//     * @return
//     */
////    @RequestMapping(value = "estatePlaces/{id}", method = RequestMethod.PUT)
////    ResponseEntity updateObjEstatePlace(@PathVariable("id") Integer id, @RequestBody ObjEstatePlace objEstatePlace) {
////        logger.debug("updateObjEstatePlace/id,id是{}", id);
////        ResponseBuilder builder = ResponseBuilder.createBuilder();
////        try {
////            String placeName = objEstatePlace.getPlaceName();
////            if (Validator.isNotNull(objEstatePlace)) {
////                if (Validator.isNull(placeName)) {
////                    logger.debug("updateObjEstatePlace-----placeName参数缺失");
////                    builder.setErrorCode(ResponseCode.PARAM_MISSING);
////                    return builder.getResponseEntity();
////                }
////                ObjEstatePlace estatePlaces = objEstatePlaceRepository.findByPlaceNameAndRemoveTimeIsNull(placeName);
////                if (Validator.isNotNull(estatePlaces)) {
////                    logger.debug("update--站点名称【" + placeName + "】已经存在！");
////                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "update--站点名称【" + placeName + "】已经存在！");
////                    return builder.getResponseEntity();
////                }
////                objEstatePlace.setId(id);
////                objEstatePlace = objEstatePlaceRepository.save(objEstatePlace);
////                builder.setResultEntity(objEstatePlace, ResponseCode.UPDATE_SUCCEED);
////            } else {
////                logger.debug("update--objEstatePlace----传入对象为空");
////                builder.setErrorCode(ResponseCode.PARAM_MISSING);
////            }
////        } catch (Exception e) {
////            logger.error(e.getMessage());
////            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
////        }
////
////        return builder.getResponseEntity();
////    }
////
////    /**
////     * 场地管理--批量删除
////     * @param ids
////     * @return
////     */
////    @RequestMapping(value = "estatePlaces/batchDelete", method = RequestMethod.DELETE)
////    ResponseEntity batchDeleteObjEstatePlace(@RequestParam List<Integer> ids) {
////        ResponseBuilder builder = ResponseBuilder.createBuilder();
////        Integer userId = SecurityUtils.getLoginUserId();
////        try {
////            if (ids.isEmpty()) {
////                logger.debug("ids({})参数缺失", ids);
////                builder.setErrorCode(ResponseCode.PARAM_MISSING);
////                return builder.getResponseEntity();
////            }
////            if (ids.size() < 30) {
////                List<ObjEstatePlace> estatePlaces = objEstatePlaceRepository.findByIdInAndRemoveTimeIsNull(ids);
////                for (ObjEstatePlace r : estatePlaces) {
////                    r.setRemoveTime(new Date());
////                    r.setLastUpdateBy(userId);
////                }
////                builder.setResultEntity(objEstatePlaceRepository.save(estatePlaces), ResponseCode.DELETE_SUCCEED);
////                logger.debug("ids({})对应的批次号批量删除成功", ids);
////            } else {
////                logger.debug("批量删除的数量必须在30条以内");
////                builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
////            }
////        } catch (Exception e) {
////            logger.error(e.getMessage());
////            builder.setErrorCode(ResponseCode.DELETE_FAILED);
////        }
////        return builder.getResponseEntity();
////    }
//
//
//}
