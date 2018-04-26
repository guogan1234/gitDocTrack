/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.importExcel;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.estate.ObjStation;
import com.avp.mem.njpb.repository.basic.EstateExcelRepository;
import com.avp.mem.njpb.repository.basic.ExcelStationRepository;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.ObjStationRepository;
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
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 2017/10/24.
 */
@RestController
public class StationExcelController {


    private static final Logger LOGGER = LoggerFactory.getLogger(EstateSuppliersExcelImportController.class);

    @Autowired
    EstateExcelRepository estateExcelRepository;
    @Autowired
    ExcelStationRepository excelStationRepository;
    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    @Autowired
    ObjStationRepository objStationRepository;
    @Autowired
    ObjEstateRepository objEstateRepository;

    /**
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/excelImport", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity wxe() throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<>();
        int i = 0;


        try {
            Integer uid = SecurityUtils.getLoginUserId();
            File file = new File("/var/njpb/stations.xlsx");
            in = new FileInputStream(file);
            //excel错误行数
            int excelRank = 0;
            listob = new ImportExcelUtil().getBankListByExcel(in, "stations.xlsx");
            LOGGER.debug("excel estateSuppliers size = {}", listob.size());

            try {
                for (i = 0; i < listob.size(); i++) {
                    List<Object> lo = listob.get(i);
                    excelRank = i + 2;
                    //ObjStationBak objStationBak = new ObjStationBak();
                    //ObjStationExcel objStationExcel = new ObjStationExcel();
                    ObjStation objStation = new ObjStation();
                    String val0 = String.valueOf(lo.get(MagicNumber.ZERO));
                    //车站号
                    objStation.setStationNo("0" + val0);
                    String val1 = String.valueOf(lo.get(MagicNumber.ONE));
                    //站点名称
                    objStation.setStationName(val1);
                    String val2 = String.valueOf(lo.get(MagicNumber.TWO));
                    //基站地址

                    String val3 = String.valueOf(lo.get(MagicNumber.THREE));
                    //车位数
                    Integer t = Integer.parseInt(val3);
                    objStation.setEstateCount(t);


                    String val4 = String.valueOf(lo.get(MagicNumber.FOUR));
                    //区域
                    if (val4.equals("鼓楼区")) {
                        objStation.setProjectId(MagicNumber.FIVE);
                    }
                    if (val4.equals("建邺区")) {
                        objStation.setProjectId(MagicNumber.FOUR);
                    }
                    if (val4.equals("秦淮区")) {
                        objStation.setProjectId(MagicNumber.THREE);
                    }
                    if (val4.equals("雨花区")) {
                        objStation.setProjectId(MagicNumber.THREE);
                    }
                    if (val4.equals("玄武区")) {
                        objStation.setProjectId(MagicNumber.TWO);
                    }
                    if (val4.equals("栖霞区")) {
                        objStation.setProjectId(MagicNumber.TWO);
                    }
                    Object ttt = lo.get(MagicNumber.FIVE);
                    String val5 = String.valueOf(lo.get(MagicNumber.FIVE));
                    //经度

                    double dd = Double.parseDouble(val5);
                    objStation.setLatitude(dd);
                    String val6 = String.valueOf(lo.get(MagicNumber.SIX));
                    //纬度
                    double ddd = Double.parseDouble(val6);
                    objStation.setLongitude(ddd);
                    String val7 = String.valueOf(lo.get(MagicNumber.SEVEN));
                    //详细位置描述
                    objStation.setRemark(val7);
                    objStationRepository.save(objStation);
                    ///excelStationRepository.save(objStationExcel);

                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("excel文件内容有误,错误位置 : {}行 ", i + MagicNumber.TWO);
                throw new IOException("excel文件内容有误,错误位置 : " + (i + MagicNumber.TWO) + "行 ");
            }

            LOGGER.debug("供应商excel导入成功,共导入数据{}条", i);
            builder.setResultEntity("数据导入成功,共导入" + (i) + "条数据");
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

    // 设备新建
    @RequestMapping(value = "estates/tt", method = RequestMethod.POST)
//    @javax.transaction.Transactional
    public ResponseEntity<RestBody<ObjStation>> buildEstate() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjStation> objStations = objStationRepository.findAll();
            int t = MagicNumber.MILLION;
            for (int i = 0; i < objStations.size(); i++) {
                ObjStation kk = objStations.get(i);
                int b = kk.getEstateCount();
                for (int j = 0; j < b; j++) {
                    String tb = "";
                    if (j < MagicNumber.NINE) {
                        tb = "0" + (j + 1);
                    } else {
                        tb = "" + (j + 1);
                    }
                    String bikeStackbarCode = kk.getStationNo() + "" + tb;

                    ObjEstate objEstate = new ObjEstate();
                    objEstate.setEstateTypeId(1);
                    objEstate.setStationId(kk.getId());
                    objEstate.setCategory(0);
                    objEstate.setEstateName("车桩" + (j + 1));
                    objEstate.setEstateStatusId(1);
                    objEstate.setProjectId(kk.getProjectId());
                    //objEstate.setEstateSn(barCodeSn);
                    objEstate.setBicycleStakeBarCode(bikeStackbarCode);
                    objEstate.setEstateNo(t++);
                    objEstateRepository.save(objEstate);
                }

                ObjBarcodeImage objImageBarCode4 = new ObjBarcodeImage();
                objImageBarCode4.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                objImageBarCode4 = objImageBarCodeRepository.save(objImageBarCode4);
                int t1 = MagicNumber.MILLION;
                String barCodeSn = objImageBarCode4.getBarCodeSn();
                ObjEstate objEstate = new ObjEstate();
                objEstate.setEstateTypeId(MagicNumber.TWO);
                objEstate.setStationId(kk.getId());
                objEstate.setCategory(0);
                objEstate.setEstateName("广告牌1");
                objEstate.setEstateStatusId(1);
                objEstate.setProjectId(kk.getProjectId());
                objEstate.setEstateSn(barCodeSn);
                objEstate.setEstateNo(t1++);
                objEstateRepository.save(objEstate);

                ObjBarcodeImage objImageBarCode1 = new ObjBarcodeImage();
                objImageBarCode1.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                objImageBarCode1 = objImageBarCodeRepository.save(objImageBarCode1);
                int t2 = MagicNumber.MILLION;
                String barCodeSn1 = objImageBarCode1.getBarCodeSn();
                ObjEstate objEstate1 = new ObjEstate();
                objEstate1.setEstateTypeId(MagicNumber.SIX);
                objEstate1.setStationId(kk.getId());
                objEstate1.setCategory(0);
                objEstate1.setEstateName("机柜1");
                objEstate1.setEstateStatusId(1);
                objEstate1.setProjectId(kk.getProjectId());
                objEstate1.setEstateSn(barCodeSn1);
                objEstate1.setEstateNo(t2++);
                objEstateRepository.save(objEstate1);


                ObjBarcodeImage objImageBarCode3 = new ObjBarcodeImage();
                objImageBarCode3.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                objImageBarCode3 = objImageBarCodeRepository.save(objImageBarCode3);
                int t3 = MagicNumber.MILLION;
                String barCodeSn3 = objImageBarCode3.getBarCodeSn();

                ObjEstate objEstate3 = new ObjEstate();
                objEstate3.setEstateTypeId(MagicNumber.TWO_ONE);
                objEstate3.setStationId(kk.getId());
                objEstate3.setCategory(0);
                objEstate3.setEstateName("LED屏1");
                objEstate3.setEstateStatusId(1);
                objEstate3.setProjectId(kk.getProjectId());
                objEstate3.setEstateSn(barCodeSn3);
                objEstate3.setEstateNo(t3++);
                objEstateRepository.save(objEstate3);

            }
            builder.setResponseCode(ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/excelImporttemp", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity xxx() throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<>();
        int i = 0;


        try {
            Integer uid = SecurityUtils.getLoginUserId();
            File file = new File("/var/njpb/stations.xlsx");
            in = new FileInputStream(file);
            //excel错误行数
            int excelRank = 0;
            listob = new ImportExcelUtil().getBankListByExcel(in, "stations.xlsx");
            LOGGER.debug("excel estateSuppliers size = {}", listob.size());

            try {
                for (i = 0; i < listob.size(); i++) {
                    List<Object> lo = listob.get(i);

                    ObjStation objStation = new ObjStation();
                    String val0 = String.valueOf(lo.get(MagicNumber.ZERO));
                    String stationNo = "0"+val0;
                    //车站号
                   ObjStation objStation1 =  objStationRepository.findByStationNo(stationNo);

                    if(Validator.isNull(objStation1)){
                        objStation.setStationNo(stationNo);
                        String val1 = String.valueOf(lo.get(MagicNumber.ONE));
                        //站点名称
                        objStation.setStationName(val1);

                        String val2 = String.valueOf(lo.get(MagicNumber.TWO));
                        //基站地址
                        String val3 = String.valueOf(lo.get(MagicNumber.THREE));


                        //车位数
                        Integer t = Integer.parseInt(val3);
                        objStation.setEstateCount(t);

                    String val4 = String.valueOf(lo.get(MagicNumber.FOUR));
                    //区域
                    if (val4.equals("第四分公司")) {
                        objStation.setProjectId(MagicNumber.FIVE);
                    }
                    if (val4.equals("第三分公司")) {
                        objStation.setProjectId(MagicNumber.FOUR);
                    }
                    if (val4.equals("第六分公司")) {
                        objStation.setProjectId(MagicNumber.SEVEN);
                    }
                    if (val4.equals("第二分公司")) {
                        objStation.setProjectId(MagicNumber.THREE);
                    }
                    if (val4.equals("第一分公司")) {
                        objStation.setProjectId(MagicNumber.TWO);
                    }
                    if (val4.equals("第五分公司")) {
                        objStation.setProjectId(MagicNumber.SIX);
                    }


                        Object ttt = lo.get(MagicNumber.FIVE);
                        String val5 = String.valueOf(lo.get(MagicNumber.FIVE));
                        //经度

                        double dd = Double.parseDouble(val5);
                        objStation.setLatitude(dd);
                        String val6 = String.valueOf(lo.get(MagicNumber.SIX));
                        //纬度
                        double ddd = Double.parseDouble(val6);
                        objStation.setLongitude(ddd);
                        String val7 = String.valueOf(lo.get(MagicNumber.SEVEN));
                        //详细位置描述
                        objStation.setRemark(val7);
                        ObjStation objStation2 = objStationRepository.save(objStation);
                        ///excelStationRepository.save(objStationExcel);

                        ObjBarcodeImage objImageBarCode1 = new ObjBarcodeImage();
                        objImageBarCode1.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                        objImageBarCode1.setBarCodeSn(objStation2.getStationNo()+"101");
                        objImageBarCode1 = objImageBarCodeRepository.save(objImageBarCode1);
                        int t2 = MagicNumber.MILLION;
                        ObjEstate objEstate1 = new ObjEstate();
                        objEstate1.setEstateTypeId(MagicNumber.SIX);
                        objEstate1.setStationId(objStation2.getId());
                        objEstate1.setCategory(0);
                        objEstate1.setEstateName("机柜01");
                        objEstate1.setEstateStatusId(1);
                        objEstate1.setProjectId(objStation2.getProjectId());
                        objEstate1.setEstateSn(objStation2.getStationNo()+"101");
                        objEstate1.setEstateNo(t2++);
                        objEstateRepository.save(objEstate1);


                    }else{

                        Object ttt = lo.get(MagicNumber.FIVE);
                        String val5 = String.valueOf(lo.get(MagicNumber.FIVE));
                        //经度

                        double dd = Double.parseDouble(val5);
                        objStation1.setLatitude(dd);

                        String val6 = String.valueOf(lo.get(MagicNumber.SIX));
                        //纬度
                        double ddd = Double.parseDouble(val6);
                        objStation1.setLongitude(ddd);

                        String val7 = String.valueOf(lo.get(MagicNumber.SEVEN));
//                        //详细位置描述
                        objStation1.setRemark(val7);
                        ObjStation objStation2 = objStationRepository.save(objStation1);

                        ObjBarcodeImage objImageBarCode1 = new ObjBarcodeImage();
                        objImageBarCode1.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                        objImageBarCode1.setBarCodeSn(objStation2.getStationNo()+"101");
                        objImageBarCode1 = objImageBarCodeRepository.save(objImageBarCode1);
                        int t2 = MagicNumber.MILLION;
                        ObjEstate objEstate1 = new ObjEstate();
                        objEstate1.setEstateTypeId(MagicNumber.SIX);
                        objEstate1.setStationId(objStation2.getId());
                        objEstate1.setCategory(0);
                        objEstate1.setEstateName("机柜01");
                        objEstate1.setEstateStatusId(1);
                        objEstate1.setProjectId(objStation2.getProjectId());
                        objEstate1.setEstateSn(objStation2.getStationNo()+"101");
                        objEstate1.setEstateNo(t2++);
                        objEstateRepository.save(objEstate1);
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("excel文件内容有误,错误位置 : {}行 ", i + MagicNumber.TWO);
                throw new IOException("excel文件内容有误,错误位置 : " + (i + MagicNumber.TWO) + "行 ");
            }

            LOGGER.debug("供应商excel导入成功,共导入数据{}条", i);
            builder.setResultEntity("数据导入成功,共导入" + (i) + "条数据");
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

    /**
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "advertisingBoard/excelImport", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity excelImport() throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<>();
        int i = 0;

        try {
            Integer uid = SecurityUtils.getLoginUserId();
            File file = new File("/var/njpb/advertisingBoard.xlsx");
            in = new FileInputStream(file);
            //excel错误行数
            int excelRank = 0;
            listob = new ImportExcelUtil().getBankListByExcel(in, "advertisingBoard.xlsx");
            LOGGER.debug("excel estateSuppliers size = {}", listob.size());

            try {
                for (i = 0; i < listob.size(); i++) {
                    if (i == MagicNumber.TWO_TWO_EIGHT_FIVE) {
                        break;
                    }
                    List<Object> lo = listob.get(i);
                    excelRank = i + 2;
                    Integer val = Integer.parseInt(String.valueOf(lo.get(MagicNumber.FOUR)));
                    int t3 = MagicNumber.MILLION;
                    for (int j = 0; j < val; j++) {
                        ObjEstate objEstate = new ObjEstate();
                        String val0 = "0" + String.valueOf(lo.get(MagicNumber.ONE));
                        ObjStation objStation = objStationRepository.findByStationNo(val0);


                        //车站号
                        objEstate.setStationId(objStation.getId());

                        objEstate.setEstateTypeId(2);

                        objEstate.setEstateSn(val0 + "20" + (j + 1));

                        objEstate.setEstateName("广告牌0" + (j + 1));

                        objEstate.setCategory(0);

                        objEstate.setEstateStatusId(1);

                        objEstate.setProjectId(objStation.getProjectId());

                        objEstate.setEstateNo(t3++);

                        objEstateRepository.save(objEstate);

                        LOGGER.debug("ok" +
                                "+++++" + j);
                        i++;
                        if (j == (val - 1)) {
                            i--;
                        }
                    }

//                    i--;
                    LOGGER.debug("ok================" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("excel文件内容有误,错误位置 : {}行 ", i + MagicNumber.TWO);
                throw new IOException("excel文件内容有误,错误位置 : " + (i + MagicNumber.TWO) + "行 ");
            }

            LOGGER.debug("供应商excel导入成功,共导入数据{}条", i);
            builder.setResultEntity("数据导入成功,共导入" + (i) + "条数据");
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
