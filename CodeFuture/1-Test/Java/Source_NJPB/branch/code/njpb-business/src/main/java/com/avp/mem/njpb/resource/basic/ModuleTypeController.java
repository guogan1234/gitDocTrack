/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.entityBO.ModuleTypeTreeNode;
import com.avp.mem.njpb.entity.estate.AssoEstateModuleType;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amber Wang on 2017-09-04 下午 02:40.
 */
@RestController
public class ModuleTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleTypeController.class);

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    AssoEstateModuleTypeRepository assoEstateModuleTypeRepository;

    @Autowired
    VwEstateModuleTypeRepository vwEstateModuleTypeRepository;

    /**
     * 模块类型新建
     *
     * @param objEstateType
     * @param estateTypeIds
     * @return
     */
    @RequestMapping(value = "estateSubTypes", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity buildEstateSubType(@RequestBody ObjEstateType objEstateType,
                                             @RequestParam(value = "estateTypeIds") List<Integer> estateTypeIds) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //模块类型
        ObjEstateType objEstateSubType = new ObjEstateType();
        List<AssoEstateModuleType> assoEstateModuleTypes = new ArrayList<>();
        try {

            if (Validator.isNull(estateTypeIds) || estateTypeIds.size() == 0) {
                LOGGER.debug("estateTypeId不能为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "所属设备类型不能为空");
                return builder.getResponseEntity();
            }

            String moduleTypeName = objEstateType.getName();
            // 校验
            if (Validator.isNull(moduleTypeName)) {
                LOGGER.debug("estateSubTypeName不能为空");

                builder.setResponseCode(ResponseCode.PARAM_MISSING, "模块类型名称不能为空");
                return builder.getResponseEntity();
            }

            objEstateSubType = objEstateTypeRepository.findOneByName(moduleTypeName);
            if (Validator.isNotNull(objEstateSubType)) {

                Integer estateSubTypeId = objEstateSubType.getId();
                //数据库中已存在该模块类型
                LOGGER.debug("数据库中已经存在改模块类型，ID:{}，名称为:{}", estateSubTypeId, moduleTypeName);

                for (Integer estateTypeId : estateTypeIds) {
                    //判断是否重复关联
                    List<AssoEstateModuleType> duplCheck = assoEstateModuleTypeRepository.findByEstateTypeIdAndModuleTypeId(estateTypeId, estateSubTypeId);
                    LOGGER.debug("根据设备类型ID：{}，模块类型ID：{}查询关联关系，数据量为：{}", estateTypeId, estateSubTypeId, duplCheck.size());

                    if (duplCheck.size() > 0) {
                        LOGGER.debug("({})对应的模块类型已经与部分设备类型(ID：{}),不能重复关联", objEstateSubType.getName(), estateTypeId);

                        builder.setResponseCode(ResponseCode.CREATE_FAILED, objEstateSubType.getName() + "：对应的模块类型已经与部分设备类型有关联，不能重复关联");
                        return builder.getResponseEntity();
                    }

                    AssoEstateModuleType assoEstateModuleType = new AssoEstateModuleType(estateTypeId, estateSubTypeId);
                    assoEstateModuleTypes.add(assoEstateModuleType);
                }
            } else {
                //新建模块类型
                LOGGER.debug("数据库中不存在该模块类型，名称为:{}", moduleTypeName);

                objEstateSubType = objEstateTypeRepository.save(objEstateType);

                for (Integer estateTypeId : estateTypeIds) {
                    AssoEstateModuleType assoEstateModuleType = new AssoEstateModuleType(estateTypeId, objEstateSubType.getId());
                    assoEstateModuleTypes.add(assoEstateModuleType);
                }
            }

            assoEstateModuleTypeRepository.save(assoEstateModuleTypes);
            builder.setResponseCode(ResponseCode.CREATE_SUCCEED);

            LOGGER.debug("模块类型({})新建成功", moduleTypeName);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 模块类型编辑
     *
     * @param id
     * @param estateSubType
     * @return
     */
    @RequestMapping(value = "estateSubTypes/{id}", method = RequestMethod.PUT)
    public ResponseEntity editEstateSubType(@PathVariable("id") Integer id,
                                            @RequestBody ObjEstateType estateSubType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer estateSubTypeId = id;
            //校验
            if (estateSubTypeId == null || estateSubType.getName() == null) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            if (!objEstateTypeRepository.exists(estateSubTypeId)) {
                LOGGER.debug("模块类型不存在");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "模块类型不存在");
                return builder.getResponseEntity();
            }

            String estateTypeName = estateSubType.getName();

            //根据设备类型名称和设备类别查询数据
            Integer estateTypeNameCount = objEstateTypeRepository.countByNameAndCategoryAndIdNot(estateTypeName, BusinessRefData.ESTATE_TYPE_CATEGORY_MODULE, estateSubTypeId);

            if (estateTypeNameCount > MagicNumber.ZERO) {
                LOGGER.debug("设备类型【" + estateTypeName + "】已经存在！");
                builder.setResponseCode(ResponseCode.ALREADY_EXIST, "设备类型【" + estateTypeName + "】已经存在！");
                return builder.getResponseEntity();
            }

            estateSubType.setId(estateSubTypeId);

            objEstateTypeRepository.save(estateSubType);
            builder.setResponseCode(ResponseCode.UPDATE_SUCCEED);

            LOGGER.debug("模块类型修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 模块类型删除：1、如果该模块类型已经被使用，则不能删除；2、如果该模块类型下面有子模块类型，则不能删除；
     * 3、如果该模块类型同时从属于2个以上的模块类型，则删除这个中间表关联；4、如果该模块类型只属于一个模块类型，则删除模块类型记录和中间表记录
     *
     * @param assoId
     * @return
     */
    @RequestMapping(value = "estateSubTypes/{assoId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity deleteEstateSubType(@PathVariable("assoId") Integer assoId) {//assoId
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        int estateCount = 0;
        int assoCount = 0;
        try {
            //查询选中模块类型
            AssoEstateModuleType assoEstateModuleType = assoEstateModuleTypeRepository.findOne(assoId);
            ObjEstateType estateSubType = objEstateTypeRepository.findOne(assoEstateModuleType.getModuleTypeId());

            if (Validator.isNotNull(estateSubType)) {
                Integer moduleTypeId = estateSubType.getId();

                estateCount = objEstateRepository.countByEstateTypeId(moduleTypeId);
                if (estateCount > 0) {
                    LOGGER.debug("模块类型与设备存在关联，请先解除关联");
                    builder.setResponseCode(ResponseCode.DELETE_FAILED, "模块类型与设备存在关联，请先解除关联");
                    return builder.getResponseEntity();
                }


                assoEstateModuleType.setRemoveTime(new Date());
                assoEstateModuleTypeRepository.save(assoEstateModuleType);
                LOGGER.debug("模块类型删除成功");

                List<AssoEstateModuleType> assoCounts = assoEstateModuleTypeRepository.findByModuleTypeId(moduleTypeId);
                if (assoCounts.size() < 1) {
                    estateSubType.setRemoveTime(new Date());
                    objEstateTypeRepository.save(estateSubType);
                    LOGGER.debug("{}：对应的模块类型无其它关联，删除成功", moduleTypeId);
                }


                LOGGER.debug("({})对应的模块类型({})删除成功", moduleTypeId, estateSubType.getName());
                builder.setResponseCode(ResponseCode.DELETE_SUCCEED);
            } else {
                builder.setResponseCode(ResponseCode.NOT_EXIST);
                LOGGER.debug("assoId({})对应的模块类型Asso主键不存在", assoId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.DELETE_FAILED);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 获取模块类型界面Tree数据
     *
     * @return
     */
    @GetMapping("estateSubTypes/getTreeDatas")
    public ResponseEntity getTreeDatas() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<ModuleTypeTreeNode> moduleTypeTreeNodes = new ArrayList<>();
        try {
            objEstateTypeRepository.findByCategory(1).forEach(objEstateType -> {
                String treeNodeId = "estateTypeId_" + objEstateType.getId();
                String parent = "#";
                String text = objEstateType.getName();

                moduleTypeTreeNodes.add(new ModuleTypeTreeNode(treeNodeId, parent, text, null));
            });
            LOGGER.debug("迭代设备类型数据成功");
            List<VwEstateModuleType> vwEstateModuleTypes = vwEstateModuleTypeRepository.findAll();
            for (int i = 0; i < vwEstateModuleTypes.size(); i++) {
                VwEstateModuleType vwEstateModuleType=vwEstateModuleTypes.get(i);
                String treeNodeId = "estateTypeId_" + vwEstateModuleType+"_moduleTypeId_" + vwEstateModuleType.getModuleTypeId();
                String parent = "estateTypeId_" + vwEstateModuleType.getEstateTypeId();
                String text = vwEstateModuleType.getModuleName();
                moduleTypeTreeNodes.add(new ModuleTypeTreeNode(treeNodeId, parent, text, vwEstateModuleType));
            }


//            vwEstateModuleTypeRepository.findAll().forEach(vwEstateModuleType -> {
//                String treeNodeId = "estateTypeId_" + vwEstateModuleType.getEstateTypeId() + "_moduleTypeId_" + vwEstateModuleType.getModuleTypeId();
//                String parent = "estateTypeId_" + vwEstateModuleType.getEstateTypeId();
//                String text = vwEstateModuleType.getModuleName();
//
//                moduleTypeTreeNodes.add(new ModuleTypeTreeNode(treeNodeId, parent, text, vwEstateModuleType));
//            });
            LOGGER.debug("迭代模块类型数据成功");
            builder.setResultEntity(moduleTypeTreeNodes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 根据设备类型查询模块
     *
     * @param estateType
     * @return List<ObjEstateType>
     */
    @RequestMapping(value = "moduleTypes/findByEstateType", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjEstateType>> findAll(Integer estateType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwEstateModuleType> vwEstateModuleTypes = vwEstateModuleTypeRepository.findByEstateTypeId(estateType);
            LOGGER.debug("查询模块类型数据成功,数据量为:({})", vwEstateModuleTypes.size());
            builder.setResultEntity(vwEstateModuleTypes, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
