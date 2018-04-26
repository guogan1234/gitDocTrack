/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.exportExcel;


import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.view.VwUserEstateBarCode;
import com.avp.mem.njpb.entity.view.VwUserEstateBarCode_;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateBarCodeRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.service.estate.EstateService;
import com.avp.mem.njpb.util.ExcelUtil;
import com.avp.mem.njpb.util.MagicNumber;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

//import com.avp.mem.njpb.entity.view.VwUserEstateBarCode_;
//import com.avp.mem.njpb.entity.view.VwUserEstateBarCode_;

/**
 * Created by len on 2017/1/5.
 */
@RestController
public class EstateBarCodesExportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateBarCodesExportController.class);

    @Autowired
    private VwUserEstateBarCodeRepository vwUserEstateBarCodeRepository;

    @Autowired
    private EstateService estateService;

    @Autowired
    private VwWorkOrderRepository vwWorkOrderRepository;

    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    //1、uid权限设置2、乱码处理
    //设备/模块二维码导出--web
    @RequestMapping(value = "estateBarCodes/exportHasUsed", method = RequestMethod.POST)
    public void exportHasUsed(HttpServletResponse response, @RequestParam(value = "barCodeIds", required = false) List<Integer> barCodeIds) {

        //查询list
        List<VwUserEstateBarCode> vwUserEstateBarCodeList = new ArrayList<>();

        List<String> barcodeSns = new ArrayList<>();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "二维码";
            //sheet名

            //标题
            /*String[] title = new String[]{"项目", "项目编号", "设备/模块编号", "设备/模块名称", "设备序列号", "物料代码",
//                    "设备/模块sn",
                    "二维码", "批次号", "设备类型", "模块类型"};*/
            String[] title = new String[]{"公司", "站点", "设备名称", "二维码", "芯片码", "设备供应商", "设备类型"};

            if (barCodeIds == null || barCodeIds.size() <= 0) {
                vwUserEstateBarCodeList = vwUserEstateBarCodeRepository.findByUid(uid);
                LOGGER.debug("查询设备模块二维码，数量为({})", vwUserEstateBarCodeList.size());
            } else {
                LOGGER.debug("参数数量为({})", barCodeIds.size());
                vwUserEstateBarCodeList = vwUserEstateBarCodeRepository.findByUidAndIdIn(uid, barCodeIds);
                LOGGER.debug("根据二维码id({})查询设备模块二维码，数量为({})", barCodeIds, vwUserEstateBarCodeList.size());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[vwUserEstateBarCodeList.size()][];
            for (int i = 0; i < vwUserEstateBarCodeList.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                VwUserEstateBarCode obj = vwUserEstateBarCodeList.get(i);
                values[i][MagicNumber.ZERO] = obj.getCorpName();
                values[i][MagicNumber.ONE] = obj.getStationName();
                values[i][MagicNumber.TWO] = obj.getEstateName();
                values[i][MagicNumber.THREE] = obj.getBarCodeSn();
                values[i][MagicNumber.FOUR] = String.valueOf(obj.getBikeFrameNo());

                values[i][MagicNumber.FIVE] = obj.getSupplierName();
                values[i][MagicNumber.SIX] = obj.getEstateTypeName();

//                barcodeSns.add(obj.getBarCodeSn());
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出使用的二维码出错， {}", e.getMessage());
        }
    }

    //1、uid权限设置2、乱码处理
    //设备/模块二维码导出--web
    @RequestMapping(value = "estateBarCodes/exportNotUsed", method = RequestMethod.POST)
    public void exportNotUsed(HttpServletResponse response, @RequestParam(value = "barCodeIds", required = false) List<Integer> barCodeIds) {

        //查询list
        List<ObjBarcodeImage> objBarcodeImages = new ArrayList<>();

        List<String> barcodeSns = new ArrayList<>();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "二维码";
            //sheet名

            //标题
            /*String[] title = new String[]{"项目", "项目编号", "设备/模块编号", "设备/模块名称", "设备序列号", "物料代码",
//                    "设备/模块sn",
                    "二维码", "批次号", "设备类型", "模块类型"};*/
            String[] title = new String[]{"二维码"};

            if (barCodeIds == null || barCodeIds.size() <= 0) {
                objBarcodeImages = objImageBarCodeRepository.findAll();
                LOGGER.debug("查询设备模块二维码，数量为({})", objBarcodeImages.size());
            } else {
                LOGGER.debug("参数数量为({})", barCodeIds.size());
                objBarcodeImages = objImageBarCodeRepository.findByIdIn(barCodeIds);
                LOGGER.debug("根据二维码id({})查询设备模块二维码，数量为({})", barCodeIds, objBarcodeImages.size());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String[][] values = new String[objBarcodeImages.size()][];
            for (int i = 0; i < objBarcodeImages.size(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string
                ObjBarcodeImage obj = objBarcodeImages.get(i);
                values[i][MagicNumber.ZERO] = obj.getBarCodeSn();

//                barcodeSns.add(obj.getBarCodeSn());
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

//            LOGGER.debug("更新设备的导出时间...");
//            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出未使用二维码出错， {}", e.getMessage());
        }
    }

    //设备/模块二维码导出自定义size--web
    @RequestMapping(value = "estateBarCodes/exportCustomSize", method = RequestMethod.POST)
    public void exportCustomSize(HttpServletResponse response,
                                 @RequestParam(value = "projectId", required = false) Integer projectId,
                                 @RequestParam(value = "estateTypeId", required = false) Integer estateTypeId,
                                 @RequestParam(value = "hasExport", required = false) Boolean hasExport,
                                 @RequestParam(value = "hasActivate", required = false) Boolean hasActivate,
                                 @RequestParam(value = "size", required = false) Integer size
//                                 Pageable page
    ) {

        List<String> barcodeSns = new ArrayList<>();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            String fileName = System.currentTimeMillis() + ".xls"; //文件名
            String sheetName = "设备模块二维码";
            //sheet名

            //标题
            String[] title = new String[]{"项目", "二维码"};
            Pageable pageable = new PageRequest(0, size, null);

            Page<VwUserEstateBarCode> vwUserEstateBarCodePage = vwUserEstateBarCodeRepository.findAll(where(byConditions(projectId, estateTypeId, uid, hasExport, hasActivate)), pageable);

            if (vwUserEstateBarCodePage.getTotalElements() <= 0) {
                LOGGER.debug("查询设备模块二维码详细数据成功，结果为空");
            } else {
                LOGGER.debug("查询设备模块二维码详细数据成功，数据量为:({})", vwUserEstateBarCodePage.getTotalElements());
            }

            List<VwUserEstateBarCode> vwUserEstateBarCodeList = vwUserEstateBarCodePage.getContent();
            String[][] values = new String[vwUserEstateBarCodeList.size()][];

            for (int i = 0; i < vwUserEstateBarCodePage.getTotalElements(); i++) {
                values[i] = new String[title.length];
                //将对象内容转换成string

                VwUserEstateBarCode obj = vwUserEstateBarCodeList.get(i);
                values[i][0] = obj.getEstateName();
                values[i][1] = obj.getBarCodeMessage();

                barcodeSns.add(obj.getBarCodeSn());
            }

            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);

            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

            LOGGER.debug("更新设备的导出时间...");
            estateService.updateEstateExportTime(barcodeSns);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("findByCondotions {}", e.getMessage());
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Specification<VwUserEstateBarCode> byConditions(Integer projectId, Integer estateTypeId, Integer uid, Boolean hasExport, Boolean hasActivate) {
        return new Specification<VwUserEstateBarCode>() {
            public Predicate toPredicate(Root<VwUserEstateBarCode> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (projectId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.projectId), projectId));
                }
                if (estateTypeId != null) {
                    predicate.getExpressions().add(
                            builder.equal(root.get(VwUserEstateBarCode_.estateTypeId), estateTypeId));
                }
                if (hasActivate != null) {
                    if (hasActivate) {
                        //已经激活
                        predicate.getExpressions().add(
                                builder.isNotNull(root.get(VwUserEstateBarCode_.activateTime)));
                    } else {
                        predicate.getExpressions().add(
                                builder.isNull(root.get(VwUserEstateBarCode_.activateTime)));
                    }
                }

                if (hasExport != null) {
                    if (hasExport) {
                        //已经激活
                        predicate.getExpressions().add(
                                builder.isNotNull(root.get(VwUserEstateBarCode_.exportTime)));
                    } else {
                        predicate.getExpressions().add(
                                builder.isNull(root.get(VwUserEstateBarCode_.exportTime)));
                    }
                }

                predicate.getExpressions().add(builder.equal(root.get(VwUserEstateBarCode_.uid), uid));

                predicate.getExpressions().add(builder.isNotNull(root.get(VwUserEstateBarCode_.id)));

                return predicate;
            }
        };
    }



//    @RequestMapping(value = "workOrderScore/exportWorkOrder", method = RequestMethod.POST)
//    public void exportWorkOrder(HttpServletResponse response,   @RequestParam(value = "projectId", required = false) Integer projectId,
//                                @RequestParam(value = "repairEmployee", required = false) Integer repairEmployee,
//                                @RequestParam(value = "beginTime", required = false) Date beginTime,
//                                @RequestParam(value = "endTime", required = false) Date endTime) {
//
//        //查询list
//        List<VwWorkOrder> vwWorkOrders = new ArrayList<>();
//        try {
//            String fileName = System.currentTimeMillis() + ".xls"; //文件名
//            String sheetName = "工分统计";
//            //sheet名
//
//
////            String[] title = new String[]{"工分统计"};
//            String[] title = new String[]{"工单编号", "类别", "设备名称", "工分", "所属公司", "姓名", "操作时间"};
//                vwWorkOrders = vwWorkOrderRepository.findAll(where(byConditionsWorkOrder(projectId, repairEmployee, beginTime, endTime)));
//                LOGGER.debug("数量为({})",  vwWorkOrders.size());
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            String[][] values = new String[vwWorkOrders.size()][];
//
//            for (int i = 0; i < vwWorkOrders.size(); i++) {
//                values[i] = new String[title.length];
//                //将对象内容转换成string
//                VwWorkOrder obj = vwWorkOrders.get(i);
//                values[i][MagicNumber.ZERO] = obj.getSerialNo();
//                String category = "";
//                if (obj.getCategory()== MagicNumber.ZERO){
//                    category ="站点";
//                }else{
//                    category ="车辆";
//                }
//                values[i][MagicNumber.ONE] = category;
//                values[i][MagicNumber.TWO] = obj.getEstateName();
//                values[i][MagicNumber.THREE] = obj.getWorkOrderScore()+"";
//                values[i][MagicNumber.FOUR] = obj.getCorpName();
//                values[i][MagicNumber.FIVE] = obj.getRepairEmployeeUserName();
//                Date d = obj.getLastUpdateTime();
//                String lastUpdateTime = sdf.format(d);
//                values[i][MagicNumber.SIX] = lastUpdateTime;
////                barcodeSns.add(obj.getBarCodeSn());
//            }
//
//            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
//
//            this.setResponseHeader(response, fileName);
//            OutputStream os = response.getOutputStream();
//            wb.write(os);
//            os.flush();
//            os.close();
//
////            LOGGER.debug("更新设备的导出时间...");
////            estateService.updateEstateExportTime(barcodeSns);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("出错， {}", e.getMessage());
//        }
//    }
//
//    public static Specification<VwWorkOrder> byConditionsWorkOrder(Integer projectId, Integer repairEmployee, Date beginTime, Date endTime) {
//        return new Specification<VwWorkOrder>() {
//            public Predicate toPredicate(Root<VwWorkOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                Predicate predicate = builder.conjunction();
//                if (projectId != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(VwWorkOrder_.projectId), projectId));
//                }
//                if (repairEmployee != null) {
//                    predicate.getExpressions().add(
//                            builder.equal(root.get(VwWorkOrder_.repairEmployee), repairEmployee));
//                }
//
//                if (beginTime != null) {
//                    predicate.getExpressions().add(
//                            builder.greaterThan(root.get(VwWorkOrder_.lastUpdateTime), beginTime));
//                }
//
//                if (endTime != null) {
//                    predicate.getExpressions().add(
//                            builder.lessThan(root.get(VwWorkOrder_.lastUpdateTime), endTime));
//                }
//                return predicate;
//            }
//        };
//    }
//














}
