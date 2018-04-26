/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.importExcel;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstateSupplier;
import com.avp.mem.njpb.repository.basic.ObjEstateSupplierRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.ImportExcelUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amber on 2017/01/10.
 */
@RestController
public class EstateSuppliersExcelImportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateSuppliersExcelImportController.class);

    @Autowired
    private ObjEstateSupplierRepository objEstateSupplierRepository;

    /**
     * 供应商excel导入
     *
     * @param excelFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/estateSuppliers/excelImport", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity objEstateSupplierExcelImport(
            @RequestParam(value = "excelFile", required = false) MultipartFile excelFile)
            throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        List<ObjEstateSupplier> estateSuppliers = new ArrayList<>();
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<>();
        int i = 0;
        ObjEstateSupplier supplier = new ObjEstateSupplier();
        //用于查重
        ObjEstateSupplier objEstateSupplierNo = new ObjEstateSupplier();
        ObjEstateSupplier objEstateSupplierName = new ObjEstateSupplier();
        //ObjEstateSupplier c = new ObjEstateSupplier();
        try {
            if (excelFile.isEmpty()) {
                throw new Exception("文件不存在！");
            }
            Integer uid = SecurityUtils.getLoginUserId();
            in = excelFile.getInputStream();
            //excel错误行数
            int excelRank = 0;
            listob = new ImportExcelUtil().getBankListByExcel(in, excelFile.getOriginalFilename());
            LOGGER.debug("excel estateSuppliers size = {}", listob.size());

            try {

                if (listob.size() > BusinessRefData.EXCEL_NUM) {
                    LOGGER.debug("excel中导入供应商条数必须在({})以内", BusinessRefData.EXCEL_NUM);
                    throw new IOException("excel中导入供应商条数必须在【" + BusinessRefData.EXCEL_NUM + "】以内");
                }

                for (i = 0; i < listob.size(); i++) {
                    List<Object> lo = listob.get(i);
                    excelRank = i + 2;
                    ObjEstateSupplier estateSupplier = new ObjEstateSupplier();
                    estateSupplier.setCreateBy(uid);

                    //供应商编号查重
                    //LOGGER.debug("String.valueOf(lo.get(0))：({})", String.valueOf(lo.get(0)));
                    String val0 = String.valueOf(lo.get(MagicNumber.ZERO));
                    if (Validator.isNotNull(val0)) {
                        LOGGER.debug("val0不为空");
                        // objEstateSupplierNo = objEstateSupplierRepository.findOneBySupplierNoAndRemoveTimeIsNull(val0);
//                        if (objEstateSupplierNo != null) {
//                            LOGGER.debug("【供应商序列号({})已经存在，不能重复添加】", objEstateSupplierNo.getSupplierNo());
//                            throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商编号【" + objEstateSupplierNo.getSupplierNo() + "】已存在");
//                        } else {
//                            estateSupplier.setSupplierNo(val0);
//                        }
                    } else {
                        LOGGER.debug("【供应商编号({})未填写】", val0);
                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商编号【" + val0 + "】未填写");
                    }

                    //供应商名称查重
                    String val1 = String.valueOf(lo.get(MagicNumber.ONE));
                    if (Validator.isNotNull(val1)) {
                        objEstateSupplierName = objEstateSupplierRepository.findOneBySupplierName(val1);
                        if (objEstateSupplierName != null) {
                            LOGGER.debug("【供应商名称({})已经存在，不能重复添加】", objEstateSupplierName.getSupplierName());
                            throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商名称【" + objEstateSupplierName.getSupplierName() + "】已存在");
                        } else {
                            estateSupplier.setSupplierName(val1);
                        }
                    } else {
                        LOGGER.debug("【供应商名称({})未填写】", val1);
                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商名称【" + val1 + "】未填写");
                    }

                    String val2 = String.valueOf(lo.get(MagicNumber.TWO));
//                    if (Validator.isNotNull(val2)) {
//                        estateSupplier.setSupplierAbbr(String.valueOf(lo.get(2)));
//                    }

                    String val3 = String.valueOf(lo.get(MagicNumber.THREE));
                    if (Validator.isNull(val3)) {
                        LOGGER.debug("【供应商联系人({})未填写】", val3);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商联系人【" + val3 + "】未填写");
                    }
//                    else {
                    // estateSupplier.setSupplierContacts(val3);
//                    }

                    String val4 = String.valueOf(lo.get(MagicNumber.FOUR));
                    if (Validator.isNull(val4)) {
                        LOGGER.debug("【联系电话({})未填写】", val4);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。联系电话【" + val4 + "】未填写");
                    }
//                    else {
                    //estateSupplier.setSupplierMobile(val4);
//                    }

                    String val5 = String.valueOf(lo.get(MagicNumber.FIVE));
                    if (Validator.isNull(val5)) {
                        LOGGER.debug("【备注({})未填写】", val5);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。地址【" + val5 + "】未填写");
                    }
//                    else {
                    //estateSupplier.setSupplierRemark(val5);
//                    }

                    String val6 = String.valueOf(lo.get(MagicNumber.SIX));
//                    if (Validator.isNull(val6)) {
//                        LOGGER.debug("【地址({})未填写】", val6);
////                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。地址【" + val6 + "】未填写");
//                    } else {
//                        estateSupplier.setSupplierAddress(val6);
//                    }

                    LOGGER.debug("供应商数据 : {}", estateSupplier.toString());
//                    estateSuppliers.add(estateSupplier);
                    supplier = objEstateSupplierRepository.save(estateSupplier);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("excel文件内容有误,错误位置 : {}行 ", i + MagicNumber.TWO);
                throw new IOException("excel文件内容有误,错误位置 : " + (i + MagicNumber.TWO) + "行 ");
            }

            LOGGER.debug("供应商excel导入成功,共导入数据{}条", i);
            // LOGGER.debug("供应商excel导入成功,准备上传excel文件");
            // ObjFile file = fileUploadUtil.uploadExcel(excelFile);
            builder.setResultEntity("数据导入成功,共导入" + (i) + "条数据");
            // resultEntity = ResultEntity.getResultEntity(30);
            // resultEntity.setResultMassage("数据导入成功,共导入" + (i) + "条数据");
            // resultEntity.setResultEntity(file);
        } catch (IOException ce) {
            ce.printStackTrace();
            if (builder.getResponseEntity() == null) {
                // 说明该catch代码不是手动触发的
                //resultEntity = ResultEntity.getResultEntity(40);
                builder.setResponseCode(ResponseCode.BAD_REQUEST, "catch代码不是手动触发的");
            }
            LOGGER.error(ce.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, ce.getMessage());
            //resultEntity.setResultMassage(ce.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            e.printStackTrace();
            if (builder.getResponseEntity() == null) {
                // 说明该catch代码不是手动触发的
                // resultEntity = ResultEntity.getResultEntity(40);
                builder.setResponseCode(ResponseCode.BAD_REQUEST, "catch代码不是手动触发的");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }
}
