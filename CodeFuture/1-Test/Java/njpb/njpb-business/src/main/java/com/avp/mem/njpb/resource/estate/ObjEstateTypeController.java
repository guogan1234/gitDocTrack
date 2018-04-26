package com.avp.mem.njpb.resource.estate;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjEstate;
import com.avp.mem.njpb.entity.ObjEstateType;
import com.avp.mem.njpb.reponsitory.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by Amber Wang on 2017-07-17 下午 06:37.
 */
@RestController
public class ObjEstateTypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    /**
     * 设备类型查询
     * @return
     */
    @RequestMapping(value = "estate", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findAll(String estateNo,String estateName,Integer stationId,Integer category,Integer estateTypeId,Integer estateStatusId,String estateSn,Integer supplierId,String estatePath,Integer projectId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjEstateType> estateTypes = objEstateTypeRepository.findByRemoveTimeIsNull();
            //objEstateTypeRepository.findAll(where(byConditions(estateNo, estateName, stationId, category, estateTypeId, estateStatusId, estateSn, supplierId, estatePath, projectId)));


            logger.debug("查询设备类型成功,数据量为:{}", estateTypes.size());

            builder.setResultEntity(estateTypes,ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
    // Dynamic Query Utils
    public  Specification<ObjEstate> byConditions(String estateNo, String estateName, Integer stationId, Integer category, Integer estateTypeId, Integer estateStatusId, String estateSn, Integer supplierId, String estatePath, Integer projectId) {
        return new Specification<ObjEstate>() {
            public Predicate toPredicate(Root<ObjEstate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
//
//                logger.debug("estate/findByConditions请求的参数estateNo值为:{}", estateNo);
//                if (estateNo != null) {
//                    // 过滤条件项目不为空时根据用户id和项目id查询数据
//                    predicate.getExpressions().add(builder.equal(root.get(ObjEstate_.estateNo), estateNo));
//                }
//
//                predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.uid), uid));
//
//                logger.debug("forms/findByConditions请求的参数materialCodeId值为:{}", materialCodeId);
//                if (materialCodeId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.materialCodeId), materialCodeId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数batchId值为:{}", batchId);
//                if (batchId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.batchId), batchId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数dateFrom值为:{}", dateFrom);
//                if (dateFrom != null) {
//                    predicate.getExpressions().add(builder.greaterThan(root.get(VwUserForm_.createTime), dateFrom));
//                }
//
//                logger.debug("forms/findByConditions请求的参数dateTo值为:{}", dateTo);
//                if (dateTo != null) {
//                    predicate.getExpressions().add(builder.lessThan(root.get(VwUserForm_.createTime), dateTo));
//                }
//
//                logger.debug("forms/findByConditions请求的参数dateTo值为:{}", dateTo);
//                if (organizationId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.organizationId), organizationId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数placeId值为:{}", placeId);
//                if (placeId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.placeId), placeId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
//                if (estateTypeId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.estateTypeId), estateTypeId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数estateSubTypeId值为:{}", estateSubTypeId);
//                if (estateSubTypeId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.estateSubTypeId), estateSubTypeId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数formTypeId值为:{}", formTypeId);
//                if (formTypeId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.formTypeId), formTypeId));
//                }
//
//                logger.debug("forms/findByConditions请求的参数formStatusId值为:{}", formStatusId);
//                if (formStatusId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwUserForm_.formStatusId), formStatusId));
//                }
//
//                predicate.getExpressions().add(builder.isNull(root.get(VwUserForm_.isDelete)));

                return predicate;
            }
        };
    }
    // Dynamic End

//    /**
//     * 设备类型-查重
//     * @param estateTypeName
//     * @return
//     */
//    @RequestMapping(value = "estateTypes/findByEstateTypeName", method = RequestMethod.GET)
//    ResponseEntity<RestBody<ObjEstateType>> findByEstateTypeName(@RequestParam String estateTypeName) {
//        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        try {
//            ObjEstateType estateTypes = objEstateTypeRepository.findByNameAndRemoveTimeIsNull(estateTypeName);
//            logger.debug("设备类型"+estateTypes.getName());
//            builder.setResultEntity(estateTypes, ResponseCode.RETRIEVE_SUCCEED);
//            return builder.getResponseEntity();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
//        }
//        return builder.getResponseEntity();
//    }
//
//    /**
//     * 设备类型-新建
//     * @param objEstateType
//     * @return
//     */
//    @RequestMapping(value = "estateTypes", method = RequestMethod.POST)
//    ResponseEntity saveObjEstateType(@RequestBody ObjEstateType objEstateType) {
//        logger.debug("saveObjEstateType----------");
//        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        try {
//            Integer userId = SecurityUtils.getLoginUserId();
//            String estateTypeName =  objEstateType.getName();
//            if (Validator.isNotNull(objEstateType)) {
//                ObjEstateType estateTypes = objEstateTypeRepository.findByNameAndRemoveTimeIsNull(estateTypeName);
//                if (Validator.isNotNull(estateTypes)) {
//                    logger.debug("设备类型【" + estateTypeName + "】已经存在！");
//                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】已经存在！");
//                    return builder.getResponseEntity();
//                }
//                objEstateType.setCreateBy(userId);
//                objEstateType.setLastUpdateBy(userId);
//                objEstateType = objEstateTypeRepository.save(objEstateType);
//                builder.setResultEntity(objEstateType, ResponseCode.CREATE_SUCCEED);
//            } else {
//                logger.debug("objEstateType----传入对象为空");
//                builder.setErrorCode(ResponseCode.PARAM_MISSING);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            builder.setErrorCode(ResponseCode.CREATE_FAILED);
//        }
//        return builder.getResponseEntity();
//    }

    /**
     * 设备类型-编辑
     * @param id
     * @param objEstateType
     * @return
     */
    @RequestMapping(value = "estateTypes/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjEstateType(@PathVariable("id") Integer id, @RequestBody ObjEstateType objEstateType) {
        logger.debug("updateObjEstateType/id,id是{}", id);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String estateName = objEstateType.getName();
            if (Validator.isNotNull(objEstateType)) {

                ObjEstateType estateTypes = objEstateTypeRepository.findByNameAndRemoveTimeIsNull(estateName);
                if (Validator.isNotNull(estateTypes)) {
                    logger.debug("update--设备类型【" + estateTypes + "】已经存在！");
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "update--设备类型【" + estateName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objEstateType.setId(id);
                objEstateType = objEstateTypeRepository.save(objEstateType);
                builder.setResultEntity(objEstateType, ResponseCode.UPDATE_SUCCEED);
            } else {
                logger.debug("update--objEstateType----传入对象为空");
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 设备类型-批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "estateTypes/batchDelete", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteObjEstateType(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                logger.debug("ids({})参数缺失", ids);
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < 30) {
                List<ObjEstateType> estateTypes = objEstateTypeRepository.findByIdInAndRemoveTimeIsNull(ids);
                for (ObjEstateType r : estateTypes) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                builder.setResultEntity(objEstateTypeRepository.save(estateTypes), ResponseCode.DELETE_SUCCEED);
                logger.debug("ids({})对应的批次号批量删除成功", ids);
            } else {
                logger.debug("批量删除的数量必须在30条以内");
                builder.setErrorCode(ResponseCode.BAD_REQUEST, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.DELETE_FAILED);
        }
        return builder.getResponseEntity();
    }




}
