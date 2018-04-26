package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjEstateSupplier;
import com.avp.mem.njpb.reponsitory.basic.ObjEstateSupplierRepository;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/19.
 */
@RestController
public class ObjEstateSupplierController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjEstateSupplierRepository objEstateSupplierRepository;

    /**
     * 供应商查询
     * @return
     */
    @RequestMapping(value = "estateSuppliers", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateSupplier>> findAll() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjEstateSupplier> estateSuppliers = objEstateSupplierRepository.findByRemoveTimeIsNull();
            logger.debug("查询设备类型成功,数据量为:{}", estateSuppliers.size());
            builder.setResultEntity(estateSuppliers, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 供应商--名称查重
     * @param supplierName
     * @return
     */
    @RequestMapping(value = "estateSuppliers/findBySupplierName", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateSupplier>> findBySupplierName(@RequestParam String supplierName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjEstateSupplier estateSuppliers = objEstateSupplierRepository.findBySupplierName(supplierName);
            if(estateSuppliers != null){
                logger.debug("供应商名字"+estateSuppliers.getSupplierName());
                builder.setResultEntity(estateSuppliers, ResponseCode.RETRIEVE_SUCCEED);
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
     * @param objEstateSupplier
     * @return
     */
    @RequestMapping(value = "estateSuppliers", method = RequestMethod.POST)
    ResponseEntity saveObjEstateSupplier(@RequestBody ObjEstateSupplier objEstateSupplier) {
        logger.debug("saveObjEstateSupplier----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer userId = SecurityUtils.getLoginUserId();
            String supplierName = objEstateSupplier.getSupplierName();
            if (Validator.isNotNull(objEstateSupplier)) {
                if (Validator.isNull(supplierName)) {
                    logger.debug("saveObjEstateSupplier-----supplierName参数缺失");
                    builder.setErrorCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                ObjEstateSupplier estateSuppliers = objEstateSupplierRepository.findBySupplierName(supplierName);
                if (Validator.isNotNull(estateSuppliers)) {
                    logger.debug("供应商名称【" + supplierName + "】已经存在！");
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "供应商名称【" + supplierName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objEstateSupplier.setCreateBy(userId);
                objEstateSupplier.setLastUpdateBy(userId);
                objEstateSupplier = objEstateSupplierRepository.save(objEstateSupplier);
                builder.setResultEntity(objEstateSupplier, ResponseCode.CREATE_SUCCEED);
            } else {
                logger.debug("ObjEstateSupplier----传入对象为空");
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
     * @param objEstateSupplier
     * @return
     */
    @RequestMapping(value = "estateSuppliers/{id}", method = RequestMethod.PUT)
    ResponseEntity updateObjEstateSupplier(@PathVariable("id") Integer id, @RequestBody ObjEstateSupplier objEstateSupplier) {
        logger.debug("updateObjEstateSupplier/id,id是{}", id);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            String supplierName = objEstateSupplier.getSupplierName();
            if (Validator.isNotNull(objEstateSupplier)) {
                if (Validator.isNull(supplierName)) {
                    logger.debug("updateObjEstateSupplier-----supplierName参数缺失");
                    builder.setErrorCode(ResponseCode.PARAM_MISSING);
                    return builder.getResponseEntity();
                }
                ObjEstateSupplier estateSuppliers = objEstateSupplierRepository.findBySupplierName(supplierName);
                if (Validator.isNotNull(estateSuppliers)) {
                    logger.debug("update--供应商名称【" + supplierName + "】已经存在！");
                    builder.setErrorCode(ResponseCode.ALREADY_EXIST, "update--供应商名称【" + supplierName + "】已经存在！");
                    return builder.getResponseEntity();
                }
                objEstateSupplier.setId(id);
                objEstateSupplier = objEstateSupplierRepository.save(objEstateSupplier);
                builder.setResultEntity(objEstateSupplier, ResponseCode.UPDATE_SUCCEED);
            } else {
                logger.debug("update--objEstateSupplier----传入对象为空");
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
    @RequestMapping(value = "estateSuppliers/batchDelete", method = RequestMethod.DELETE)
    ResponseEntity batchDeleteObjEstateSupplier(@RequestParam List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer userId = SecurityUtils.getLoginUserId();
        try {
            if (ids.isEmpty()) {
                logger.debug("ids({})参数缺失", ids);
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (ids.size() < 30) {
                List<ObjEstateSupplier> estateSuppliers = objEstateSupplierRepository.findByIdIn(ids);
                for (ObjEstateSupplier r : estateSuppliers) {
                    r.setRemoveTime(new Date());
                    r.setLastUpdateBy(userId);
                }
                builder.setResultEntity(objEstateSupplierRepository.save(estateSuppliers), ResponseCode.DELETE_SUCCEED);
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
