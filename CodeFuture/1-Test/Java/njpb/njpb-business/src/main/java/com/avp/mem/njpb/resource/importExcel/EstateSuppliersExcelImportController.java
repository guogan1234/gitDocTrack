package com.avp.mem.njpb.resource.importExcel;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjEstateSupplier;
import com.avp.mem.njpb.reponsitory.basic.ObjEstateSupplierRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.ImportExcelUtil;
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

    private static final Logger logger = LoggerFactory.getLogger(EstateSuppliersExcelImportController.class);

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
        ObjEstateSupplier objEstateSupplier_no = new ObjEstateSupplier();
        ObjEstateSupplier objEstateSupplier_name = new ObjEstateSupplier();
        //ObjEstateSupplier c = new ObjEstateSupplier();
        try {
            if (excelFile.isEmpty()) {
                throw new Exception("文件不存在！");
            }
            int uid = SecurityUtils.getLoginUserId();
            in = excelFile.getInputStream();
            //excel错误行数
            int excelRank = 0;
            listob = new ImportExcelUtil().getBankListByExcel(in, excelFile.getOriginalFilename());
            logger.debug("excel estateSuppliers size = {}", listob.size());

            try {

                if (listob.size() > BusinessRefData.EXCEL_NUM.getValue()) {
                    logger.debug("excel中导入供应商条数必须在({})以内", BusinessRefData.EXCEL_NUM.getValue());
                    throw new IOException("excel中导入供应商条数必须在【" + BusinessRefData.EXCEL_NUM.getValue() + "】以内");
                }

                for (i = 0; i < listob.size(); i++) {
                    List<Object> lo = listob.get(i);
                    excelRank = i + 2;
                    ObjEstateSupplier estateSupplier = new ObjEstateSupplier();
                    estateSupplier.setCreateBy(uid);

                    //供应商编号查重
                    //logger.debug("String.valueOf(lo.get(0))：({})", String.valueOf(lo.get(0)));
                    String val_0 = String.valueOf(lo.get(0));
                    if (Validator.isNotNull(val_0)) {
                       // objEstateSupplier_no = objEstateSupplierRepository.findOneBySupplierNoAndRemoveTimeIsNull(val_0);
//                        if (objEstateSupplier_no != null) {
//                            logger.debug("【供应商序列号({})已经存在，不能重复添加】", objEstateSupplier_no.getSupplierNo());
//                            throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商编号【" + objEstateSupplier_no.getSupplierNo() + "】已存在");
//                        } else {
//                            estateSupplier.setSupplierNo(val_0);
//                        }
                    } else {
                        logger.debug("【供应商编号({})未填写】", val_0);
                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商编号【" + val_0 + "】未填写");
                    }

                    //供应商名称查重
                    String val_1 = String.valueOf(lo.get(1));
                    if ( Validator.isNotNull(val_1)) {
                        objEstateSupplier_name = objEstateSupplierRepository.findBySupplierName(val_1);
                        if (objEstateSupplier_name != null) {
                            logger.debug("【供应商名称({})已经存在，不能重复添加】", objEstateSupplier_name.getSupplierName());
                            throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商名称【" + objEstateSupplier_name.getSupplierName() + "】已存在");
                        } else {
                            estateSupplier.setSupplierName(val_1);
                        }
                    } else {
                        logger.debug("【供应商名称({})未填写】", val_1);
                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商名称【" + val_1 + "】未填写");
                    }

                    String val_2 = String.valueOf(lo.get(2));
//                    if (Validator.isNotNull(val_2)) {
//                        estateSupplier.setSupplierAbbr(String.valueOf(lo.get(2)));
//                    }

                    String val_3 = String.valueOf(lo.get(3));
                    if (Validator.isNull(val_3)) {
                        logger.debug("【供应商联系人({})未填写】", val_3);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。供应商联系人【" + val_3 + "】未填写");
                    } else {
                       // estateSupplier.setSupplierContacts(val_3);
                    }

                    String val_4 = String.valueOf(lo.get(4));
                    if (Validator.isNull(val_4)) {
                        logger.debug("【联系电话({})未填写】", val_4);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。联系电话【" + val_4 + "】未填写");
                    } else {
                        //estateSupplier.setSupplierMobile(val_4);
                    }

                    String val_5 = String.valueOf(lo.get(5));
                    if (Validator.isNull(val_5)) {
                        logger.debug("【备注({})未填写】", val_5);
//                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。地址【" + val_5 + "】未填写");
                    } else {
                        //estateSupplier.setSupplierRemark(val_5);
                    }

                    String val_6 = String.valueOf(lo.get(6));
//                    if (Validator.isNull(val_6)) {
//                        logger.debug("【地址({})未填写】", val_6);
////                        throw new IOException("excel文件内容有误,错误位置 : " + excelRank + "行 。地址【" + val_6 + "】未填写");
//                    } else {
//                        estateSupplier.setSupplierAddress(val_6);
//                    }

                    logger.debug("供应商数据 : {}", estateSupplier.toString());
//                    estateSuppliers.add(estateSupplier);
                    supplier = objEstateSupplierRepository.save(estateSupplier);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("excel文件内容有误,错误位置 : {}行 ", i + 2);
                throw new IOException("excel文件内容有误,错误位置 : " + (i + 2) + "行 ");
            }

            logger.debug("供应商excel导入成功,共导入数据{}条", i);
            // logger.debug("供应商excel导入成功,准备上传excel文件");
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
                builder.setErrorCode(ResponseCode.BAD_REQUEST,"catch代码不是手动触发的");
            }
            logger.error(ce.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED,ce.getMessage());
            //resultEntity.setResultMassage(ce.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            e.printStackTrace();
            if (builder.getResponseEntity() == null) {
                // 说明该catch代码不是手动触发的
               // resultEntity = ResultEntity.getResultEntity(40);
                builder.setErrorCode(ResponseCode.BAD_REQUEST,"catch代码不是手动触发的");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }
}
