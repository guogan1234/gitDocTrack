/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.estate.AssoEstateModuleType;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.estate.ObjEstateType_;
import com.avp.mem.njpb.entity.view.VwEstateModuleType;
import com.avp.mem.njpb.repository.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.repository.basic.VwEstateModuleTypeRepository;
import com.avp.mem.njpb.repository.estate.AssoEstateModuleTypeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

//import com.avp.mem.njpb.entity.estate.ObjEstateType_;

/**
 * Created by Amber Wang on 2017-07-17 下午 06:37.
 * <p>
 * 设备类型中自行车设备类型只有一个并且不允许修改
 */
@RestController
public class EstateTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateTypeController.class);

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    AssoEstateModuleTypeRepository assoEstateModuleTypeRepository;

    @Autowired
    VwEstateModuleTypeRepository vwEstateModuleTypeRepository;

    /**
     * 设备类型查询
     *
     * @param category
     * @param partsType
     * @return List<ObjEstateType>
     */
    @RequestMapping(value = "estateTypes", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findAll(Integer category, Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjEstateType> list = objEstateTypeRepository.findAll(where(byConditions(category, partsType)));

            LOGGER.debug("查询设备类型数据成功,category:{},partsType:{},数据量为:({})", category, partsType, list.size());

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<ObjEstateType> byConditions(Integer category, Integer partsType) {
        return new Specification<ObjEstateType>() {
            public Predicate toPredicate(Root<ObjEstateType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(ObjEstateType_.category), category));
                }

                if (partsType != null) {
                    predicate.getExpressions().add(builder.equal(root.get(ObjEstateType_.partsType), partsType));
                }
                LOGGER.debug("byConditions 执行成功...");

                return predicate;
            }
        };
    }
    // Dynamic End

    /**
     * 模块类型查询
     *
     * @return List<VwEstateModuleType>
     */
    @RequestMapping(value = "estateTypes/modules", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstateModuleType>> findModulesAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwEstateModuleType> vwEstateModuleTypes = vwEstateModuleTypeRepository.findAll();
            LOGGER.debug("查询模块类型数据成功,,数据量为:({})", vwEstateModuleTypes.size());
            builder.setResultEntity(vwEstateModuleTypes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据设备类型获取模块类型数据
     *
     * @param estateTypeId
     * @return
     */
    @RequestMapping(value = "estateTypes/findByEstateTypeId", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findByEstateTypeId(@RequestParam Integer estateTypeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<AssoEstateModuleType> assoEstateModuleTypes = assoEstateModuleTypeRepository.findByEstateTypeId(estateTypeId);
            LOGGER.debug("设备类型是：{}", estateTypeId);
            List<Integer> moduleTypeIds = new ArrayList<>();
            for (AssoEstateModuleType temp : assoEstateModuleTypes) {
                moduleTypeIds.add(temp.getModuleTypeId());
            }
            List<ObjEstateType> objEstateTypes = objEstateTypeRepository.findByIdIn(moduleTypeIds);
            LOGGER.debug("设备类型数量：{}", objEstateTypes.size());
            builder.setResultEntity(objEstateTypes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备类型-新建
     *
     * @param objEstateType
     * @return
     */
    @RequestMapping(value = "estateTypes", method = RequestMethod.POST)
    ResponseEntity saveObjEstateType(@RequestBody ObjEstateType objEstateType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(objEstateType) || Validator.isNull(objEstateType.getName())) {
                LOGGER.debug("前端传入的设备的类型名称不能为空！");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "设备类型名称不能为空！");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(objEstateType.getPartsType())) {
                LOGGER.debug("前端传入的设备类的型配件类型不能为空！");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "设备类型配件类型不能为空！");
                return builder.getResponseEntity();
            }

            String estateTypeName = objEstateType.getName();

            //根据设备类型名称和设备类别
            Integer estateTypeNameCount = objEstateTypeRepository.countByNameAndCategory(estateTypeName, BusinessRefData.ESTATE_TYPE_CATEGORY_ESTATE);

            if (estateTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("设备类型【" + estateTypeName + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】已经存在！");
                return builder.getResponseEntity();
            }

            objEstateType.setCategory(BusinessRefData.ESTATE_TYPE_CATEGORY_ESTATE);

            objEstateType = objEstateTypeRepository.save(objEstateType);
            builder.setResultEntity(objEstateType, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备类型-编辑
     *
     * @param id
     * @param objEstateType
     * @return
     */
    @RequestMapping(value = "estateTypes/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjEstateType(@PathVariable("id") Integer id, @RequestBody ObjEstateType objEstateType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String estateTypeName = objEstateType.getName();
            if (Validator.isNotNull(objEstateType)) {

                //根据设备类型名称和设备类别
                Integer estateTypeNameCount = objEstateTypeRepository.countByNameAndCategoryAndIdNot(estateTypeName, BusinessRefData.ESTATE_TYPE_CATEGORY_ESTATE, id);

                if (estateTypeNameCount > MagicNumber.ZERO) {
                    LOGGER.debug("设备类型【" + estateTypeName + "】已经存在！");
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】已经存在！");
                    return builder.getResponseEntity();
                }

                objEstateType.setId(id);
                objEstateType = objEstateTypeRepository.save(objEstateType);
                builder.setResultEntity(objEstateType, ResponseCode.UPDATE_SUCCEED);

                LOGGER.debug("设备类型保存成功");

            } else {
                LOGGER.debug("update--objEstateType----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    // 设备类型查重-编辑时查重
    @RequestMapping(value = "estateTypes/checkByEstateTypeNameAndCategoryAndIdNot", method = RequestMethod.GET)
    ResponseEntity<ObjEstateType> checkByEstateTypeNameAndCategoryAndIdNot(@RequestParam String estateTypeName, @RequestParam Integer category, @RequestParam Integer id) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer estateTypeNameCount = objEstateTypeRepository.countByNameAndCategoryAndIdNot(estateTypeName, category, id);
            if (estateTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("设备类型【" + estateTypeName + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】存在！");
            } else {
                LOGGER.debug("设备类型【" + estateTypeName + "】不存在，可以操作！");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED, "设备类型【" + estateTypeName + "】不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    // 设备类型查重-新建时查重
    @RequestMapping(value = "estateTypes/checkByEstateTypeNameAndCategory", method = RequestMethod.GET)
    ResponseEntity<ObjEstateType> checkByEstateTypeNameAndCategory(@RequestParam String estateTypeName, @RequestParam Integer category) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer estateTypeNameCount = objEstateTypeRepository.countByNameAndCategory(estateTypeName, category);
            if (estateTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("设备类型【" + estateTypeName + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】存在！");
            } else {
                LOGGER.debug("设备类型【" + estateTypeName + "】不存在，可以操作！");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED, "设备类型【" + estateTypeName + "】不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 设备类型-批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "estateTypes/batchDelete", method = RequestMethod.DELETE)
    public ResponseEntity batchDeleteObjEstateType(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<ObjEstateType> objEstateTypes4Delete = new ArrayList<>();
        try {
            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() > BusinessRefData.BATCH_COUNT) {
                LOGGER.debug("批量删除的数据量大于30条");
                builder.setResponseCode(ResponseCode.UPDATE_FAILED, "删除的条目应小于30条");
                return builder.getResponseEntity();
            }

            List<ObjEstate> estateList = objEstateRepository.findByEstateTypeIdIn(ids);

            //step1:校验该设备类型是否被设备使用
            if (estateList.size() > MagicNumber.ZERO) {
                LOGGER.debug("设备类型已经与设备进行关联，请先解除关联");
                builder.setResponseCode(ResponseCode.UPDATE_FAILED, "设备类型已经与设备进行关联，请先解除关联");
                return builder.getResponseEntity();
            }

            //step2:校验该设备类型下是否存在模块类型
            List<AssoEstateModuleType> assoEstateModuleTypes = assoEstateModuleTypeRepository.findByEstateTypeIdIn(ids);
            if (assoEstateModuleTypes.size() > MagicNumber.ZERO) {
                LOGGER.debug("设备类型已经与模块类型进行关联，请先解除关联");
                builder.setResponseCode(ResponseCode.UPDATE_FAILED, "设备类型已经与模块类型进行关联，请先解除关联");
                return builder.getResponseEntity();
            }

            List<ObjEstateType> objEstateTypes = objEstateTypeRepository.findByIdIn(ids);
            objEstateTypes.forEach(objEstateType -> {
                objEstateType.setRemoveTime(new Date());
                objEstateTypes4Delete.add(objEstateType);
            });

            LOGGER.debug("需要删除的设备类型数量为：{}", objEstateTypes4Delete.size());
            objEstateTypeRepository.save(objEstateTypes4Delete);
            builder.setResponseCode(ResponseCode.DELETE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.DELETE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据类别获取设备、模块类型
     *
     * @return
     */
    @RequestMapping(value = "estateTypes/findByPartsType", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findByPartsType(@RequestParam Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<ObjEstateType> estateTypes = new ArrayList<>();
        try {

            if (Validator.isNull(partsType)) {
                estateTypes = objEstateTypeRepository.findAll();
            } else {
                LOGGER.debug("类别不为空，则查询指定类别的设备、模块类型数据");
                estateTypes = objEstateTypeRepository.findByPartsType(partsType);
            }

            LOGGER.debug("根据类别：{}查询设备、模块类型成功，数量为：{}", partsType, estateTypes.size());
            builder.setResultEntity(estateTypes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据类别获取模块类型
     *
     * @return
     */
    @RequestMapping(value = "estateTypes/findModuleTypesByPartsType", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findModuleTypeByPartsType(@RequestParam Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(partsType)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("根据类别获取模块类型({}),参数缺失，请求失败", partsType);
                return builder.getResponseEntity();
            }

            List<ObjEstateType> estateTypes = objEstateTypeRepository.findByPartsTypeAndCategory(partsType, BusinessRefData.ESTATE_TYPE_CATEGORY_MODULE);

            LOGGER.debug("根据类别：{}查询模块类型成功，数量为：{}", partsType, estateTypes.size());
            builder.setResultEntity(estateTypes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "estateTypes/findModuleTypesByEstateId", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findModuleTypeId(@RequestParam Integer estateId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try{
            if (Validator.isNull(estateId)) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("根据类别获取模块类型({}),参数缺失，请求失败", estateId);
                return builder.getResponseEntity();
            }

            ObjEstateType estateType = objEstateTypeRepository.findOne(estateId);

            LOGGER.debug("设备名：{}", estateType.getName());
            builder.setResultEntity(estateType, ResponseCode.RETRIEVE_SUCCEED);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }






}
