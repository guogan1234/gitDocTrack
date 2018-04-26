/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstateSupplier;
import com.avp.mem.njpb.repository.basic.ObjEstateSupplierRepository;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/19.
 */
@RestController
public class EstateSupplierController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EstateSupplierController.class);

    @Autowired
    ObjEstateSupplierRepository objEstateSupplierRepository;

    /**
     * 供应商查询
     *
     * @return
     */
    @RequestMapping(value = "estateSuppliers", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateSupplier>> findAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjEstateSupplier> estateSuppliers = objEstateSupplierRepository.findAll();
            LOGGER.debug("查询设备类型成功,数据量为：{}", estateSuppliers.size());
            builder.setResultEntity(estateSuppliers, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 供应商--名称查重
     *
     * @param supplierName
     * @return
     */
    @RequestMapping(value = "estateSuppliers/findBySupplierName", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateSupplier>> findBySupplierName(@RequestParam String supplierName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjEstateSupplier estateSuppliers = objEstateSupplierRepository.findOneBySupplierName(supplierName);
            if (estateSuppliers != null) {
                LOGGER.debug("{}供应商已经存在", estateSuppliers.getSupplierName());
                builder.setResultEntity(estateSuppliers, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("{}供应商不存在", estateSuppliers.getSupplierName());
                builder.setResponseCode(ResponseCode.OK, "此供应商不存在");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-新建
     *
     * @param objEstateSupplier
     * @return
     */
    @RequestMapping(value = "estateSuppliers", method = RequestMethod.POST)
    ResponseEntity saveObjEstateSupplier(@RequestBody ObjEstateSupplier objEstateSupplier) {
        LOGGER.debug("saveObjEstateSupplier----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer userId = SecurityUtils.getLoginUserId();
            String supplierName = objEstateSupplier.getSupplierName();
            if (Validator.isNotNull(objEstateSupplier)) {
                if (Validator.isNull(supplierName)) {
                    LOGGER.debug("saveObjEstateSupplier-----supplierName参数缺失");
                    builder.setResponseCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                ObjEstateSupplier estateSuppliers = objEstateSupplierRepository.findOneBySupplierName(supplierName);
                if (Validator.isNotNull(estateSuppliers)) {
                    LOGGER.debug("供应商名称【" + supplierName + "】已经存在！");
                    builder.setResponseCode(ResponseCode.ALREADY_EXIST, "供应商名称【" + supplierName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objEstateSupplier.setCreateBy(userId);
                objEstateSupplier.setLastUpdateBy(userId);
                objEstateSupplier = objEstateSupplierRepository.save(objEstateSupplier);
                builder.setResultEntity(objEstateSupplier, ResponseCode.CREATE_SUCCEED);
            } else {
                LOGGER.debug("ObjEstateSupplier----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备供应商-编辑
     *
     * @param id
     * @param objEstateSupplier
     * @return
     */
    @RequestMapping(value = "estateSuppliers/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjEstateSupplier(@PathVariable("id") Integer id, @RequestBody ObjEstateSupplier objEstateSupplier) {
        LOGGER.debug("updateObjEstateSupplier/id,id是{}", id);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String supplierName = objEstateSupplier.getSupplierName();
            if (Validator.isNotNull(objEstateSupplier)) {
                if (Validator.isNull(supplierName)) {
                    LOGGER.debug("updateObjEstateSupplier-----supplierName参数缺失");
                    builder.setResponseCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                Boolean isExists = objEstateSupplierRepository.exists(id);
                if (isExists) {
                    objEstateSupplier.setId(id);
                    objEstateSupplier = objEstateSupplierRepository.save(objEstateSupplier);
                    builder.setResultEntity(objEstateSupplier, ResponseCode.UPDATE_SUCCEED);
                } else {
                    builder.setResponseCode(ResponseCode.NOT_EXIST, "供应商不存在");
                    LOGGER.debug("根据id({})查询用户，数据不存在", id);
                }

            } else {
                LOGGER.debug("update--objEstateSupplier----传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
    @RequestMapping(value = "estateSuppliers/deleteMore", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteObjEstateSupplier(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < MagicNumber.THREE_ZERO) {
                List<ObjEstateSupplier> estateSuppliers = objEstateSupplierRepository.findByIdIn(ids);
                for (ObjEstateSupplier r : estateSuppliers) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                objEstateSupplierRepository.save(estateSuppliers);
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


}
