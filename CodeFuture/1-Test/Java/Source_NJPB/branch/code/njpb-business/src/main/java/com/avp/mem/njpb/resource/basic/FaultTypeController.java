/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.estate.ObjFaultType;
import com.avp.mem.njpb.entity.view.VwFaultType;
import com.avp.mem.njpb.entity.view.VwFaultType_;
import com.avp.mem.njpb.repository.basic.ObjFaultTypeRepository;
import com.avp.mem.njpb.repository.basic.VwFaultTypeRepository;
import com.avp.mem.njpb.repository.estate.AssoEstateModuleTypeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by len on 2018/1/15.
 */
@RestController
public class FaultTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaultTypeController.class);

    @Autowired
    ObjFaultTypeRepository objFaultTypeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    AssoEstateModuleTypeRepository assoEstateModuleTypeRepository;

    @Autowired
    VwFaultTypeRepository vwFaultTypeRepository;

    /**
     * 故障类型新建
     *
     * @param objFaultType
     * @return
     */
    @RequestMapping(value = "faultType/build", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity buildFaultType(@RequestBody ObjFaultType objFaultType) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //故障类型
        try {
            Integer userId = SecurityUtils.getLoginUserId();

            if (Validator.isNull(objFaultType)) {
                LOGGER.debug("objFaultType不能为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "objFaultType不能为空");
                return builder.getResponseEntity();
            }

            String faultTypeName = objFaultType.getName();
            // 校验
            if (Validator.isNull(faultTypeName)) {
                LOGGER.debug("faultTypeName不能为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "故障类型名称不能为空");
                return builder.getResponseEntity();
            }

            ObjFaultType objFaultType1 = objFaultTypeRepository.findOneByName(faultTypeName);

            if (Validator.isNotNull(objFaultType1)) {
                LOGGER.debug(faultTypeName + "已经存在");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "故障类型已经存在");
                return builder.getResponseEntity();
            } else {
                objFaultTypeRepository.save(objFaultType);
            }
            builder.setResponseCode(ResponseCode.CREATE_SUCCEED);

            LOGGER.debug("模块类型({})新建成功", faultTypeName);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 故障类型编辑
     *
     * @param id
     * @param objFaultType
     * @return
     */
    @RequestMapping(value = "faultType/{id}", method = RequestMethod.PUT)
    public ResponseEntity editFaultSubType(@PathVariable("id") Integer id,
                                           @RequestBody ObjFaultType objFaultType) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer faultTypeId = id;
            //校验
            if (faultTypeId == null || objFaultType.getName() == null) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (!objFaultTypeRepository.exists(faultTypeId)) {
                LOGGER.debug("故障类型不存在");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "故障类型不存在");
                return builder.getResponseEntity();
            }

            String faultTypeName = objFaultType.getName();


            Integer faultTypeNameCount = objFaultTypeRepository.countByNameAndIdNot(faultTypeName, faultTypeId);

            if (faultTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("故障类型【" + faultTypeName + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + faultTypeName + "】已经存在！");
                return builder.getResponseEntity();
            }
           objFaultType.setId(faultTypeId);

            objFaultTypeRepository.save(objFaultType);
            builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);
            LOGGER.debug("故障类型修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "faultType/deleteMore", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteFaultType(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < MagicNumber.THREE_ZERO) {
                List<ObjFaultType> objFaultTypes = objFaultTypeRepository.findByIdIn(ids);
                for (ObjFaultType r : objFaultTypes) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                objFaultTypeRepository.save(objFaultTypes);
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
     * 故障类型查询
     *
     * @param category
     * @param partsType
     * @return List<ObjEstateType>
     */
    @RequestMapping(value = "faultType", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwFaultType>> findAll(Integer category, Integer partsType, String name, Integer estateTypeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwFaultType> list = vwFaultTypeRepository.findAll(where(byConditions(category, partsType, name, estateTypeId)));

            LOGGER.debug("查询设备类型数据成功,category:{},partsType:{},name:{},数据量为:({})", category, partsType, name, list.size());

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwFaultType> byConditions(Integer category, Integer partsType, String name, Integer estateTypeId) {
        return new Specification<VwFaultType>() {
            public Predicate toPredicate(Root<VwFaultType> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

//                if (category != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwFaultType_.category), category));
//                }

                if (partsType != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwFaultType_.partsType), partsType));
                }

                if (name != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwFaultType_.name), name));
                }
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwFaultType_.estateTypeId), estateTypeId));
                }
                LOGGER.debug("byConditions 执行成功...");

                return predicate;
            }
        };
    }


    /**
     * 故障类型编辑查重
     *
     * @return List<ObjEstateType>
     */
    @RequestMapping(value = "faultTypeEdit/{id}", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findAll(String name, @PathVariable("id") Integer id) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer faultTypeNameCount = objFaultTypeRepository.countByNameAndIdNot(name, id);

            if (faultTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("故障类型【" + name + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + name + "】已经存在！");
                return builder.getResponseEntity();
            } else {
                builder.setResponseCode(ResponseCode.OK);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

}
