/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.stockMonthCount;

import com.avp.mem.njpb.entity.stock.ObjStockRecord;
import com.avp.mem.njpb.entity.stock.ObjStockRecordMonthCount;
import com.avp.mem.njpb.repository.stock.ObjStockRecordMonthCountRepository;
import com.avp.mem.njpb.repository.stock.ObjStockRecordRepository;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/1/30.
 */
@Service
@EnableScheduling
public class StockReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockReportService.class);


    @Autowired
    ObjStockRecordRepository objStockRecordRepository;

    @Autowired
    ObjStockRecordMonthCountRepository objStockRecordMonthCountRepository;

    @Scheduled(cron = "0 0 23 * * ?")
//        @RequestMapping(value = "exportStockReport", method = RequestMethod.GET)
    public void exportStockReport() {

        List<ObjStockRecord> objStockRecords = objStockRecordRepository.findAll();

        List<ObjStockRecordMonthCount> objStockRecordMonthCounts = new ArrayList<>();

        for (int i = 0; i < objStockRecords.size(); i++) {
            ObjStockRecord objStockRecord = objStockRecords.get(i);
            ObjStockRecordMonthCount objStockRecordMonthCount = objStockRecordMonthCountRepository.findTopByEstateTypeIdAndCorpIdOrderByLastUpdateTimeDesc(objStockRecord.getEstateTypeId(), objStockRecord.getCorpId());
            if (Validator.isNull(objStockRecordMonthCount)) {
                ObjStockRecordMonthCount objStockRecordMonthCount2 =new ObjStockRecordMonthCount();
                objStockRecordMonthCount2.setCount(objStockRecord.getCount());
                objStockRecordMonthCount2.setEstateTypeId(objStockRecord.getEstateTypeId());
                objStockRecordMonthCount2.setCorpId(objStockRecord.getCorpId());
                objStockRecordMonthCounts.add(objStockRecordMonthCount2);
            }else{
               Date lastUpdateTime =  objStockRecordMonthCount.getLastUpdateTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String lastUpdateTimeString = sdf.format(lastUpdateTime);
                String lastUpdateTimeStringNow = sdf.format(new Date());
                if(lastUpdateTimeStringNow.equals(lastUpdateTimeString)){
                    objStockRecordMonthCount.setCount(objStockRecord.getCount());
                    objStockRecordMonthCount.setLastUpdateTime(new Date());
                    objStockRecordMonthCounts.add(objStockRecordMonthCount);
                }else{
                    ObjStockRecordMonthCount objStockRecordMonthCountT = new ObjStockRecordMonthCount();
                    objStockRecordMonthCountT.setEstateTypeId(objStockRecord.getEstateTypeId());
                    objStockRecordMonthCountT.setCorpId(objStockRecord.getCorpId());
                    objStockRecordMonthCountT.setCount(objStockRecord.getCount());
                    objStockRecordMonthCounts.add(objStockRecordMonthCountT);
                }
            }

        }
        objStockRecordMonthCountRepository.save(objStockRecordMonthCounts);

    }


}
