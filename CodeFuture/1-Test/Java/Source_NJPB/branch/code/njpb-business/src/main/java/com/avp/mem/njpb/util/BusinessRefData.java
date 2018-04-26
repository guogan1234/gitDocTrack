/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.util;

public interface BusinessRefData {

    /*角色类型*/
    /*仓库管理员*/
    Integer ROLE_STOCK_KEEPER = 5;
    /*预制角色*/
    Integer ROLE_GRADE_PREFABRICATE = 1;

    /*公司等级*/
    Integer CORP_HEAD = 1;

    /*消息状态—已发送*/
    Integer MESSAGE_FINISH = 0;
    /*消息状态—保存*/
    Integer MESSAGE_SAVE = 1;


    /*已出库*/
    Integer ESTATE_IN_STOCK = 1;
    /*已入库*/
    Integer ESTATE_OUT_STOCK = 2;

    /*二维码状态-关联设备*/
    Integer BAR_CODE_RELEVANCE = 1;
    /*二维码状态-空码*/
    Integer BAR_CODE_NO_RELEVANCE = 0;

    /*操作记录-入库*/
    Integer OPERATION_TYPE_IN_STOCK = 1;
    /*操作记录-入库*/
    Integer OPERATION_TYPE_OUT_STOCK = 2;

    /*设备类别-站点*/
    Integer ESTATE_CATEGORY_STATION = 0;
    /*设备类别-车辆*/
    Integer ESTATE_CATEGORY_BICYCLE = 1;


    /*设备类型-设备*/
    Integer ESTATE_TYPE_CATEGORY_ESTATE = 1;
    /*设备类型-模块*/
    Integer ESTATE_TYPE_CATEGORY_MODULE = 2;

    /*配件类型-站点*/
    Integer ESTATE_PARTS_TYPE_STATION = 1;
    /*配件类型-车辆*/
    Integer ESTATE_PARTS_TYPE_BICYCLE = 2;


    /*库存工单类型-领料*/
    Integer STOCK_WORK_ORDER_TYPE_PICKING = 1;
    /*库存工单类型-归还*/
    Integer STOCK_WORK_ORDER_TYPE_BACK = 2;
    /*库存工单类型-报废*/
    Integer STOCK_WORK_ORDER_TYPE_SCRAP = 4;

    /*库存工单状态-已申请*/
    Integer STOCK_WORK_ORDER_STATUS_APPLY = 100;
    /*库存工单状态-确认*/
    Integer STOCK_WORK_ORDER_STATUS_AFFIRM = 200;
    /*库存工单状态-驳回*/
    Integer STOCK_WORK_ORDER_STATUS_REJECT = 300;
    /*库存工单状态-修改*/
    Integer STOCK_WORK_ORDER_STATUS_UPDATE = 400;
    /*库存工单状态-驳回*/
    Integer STOCK_WORK_ORDER_STATUS_CANCEL = 500;

    /*设备类型*/
    Integer ESTATE_TYPE_BICYCLE = 2;

    /*系统参数id*/
    Integer SYSTEM_PARAM_ID = 1;

    /* 工单已创建*/
    Integer WOSTATUS_WO_CREATED = 100;
    /* 工单已驳回*/
    Integer WOSTATUS_WO_BACK = 200;
    /* 工单已修改*/
    Integer WOSTATUS_WO_MODIFY = 300;
    /* 报修已派发*/
    Integer WOSTATUS_WO_SENDED = 400;
    /* 维修已确认*/
    Integer WOSTATUS_RPR_CONFIRM = 500;

    /* 维修已退回*/
    Integer WOSTATUS_RPR_BACK = 550;

    /* 工单已签到*/
    Integer WOSTATUS_WO_ARRIVE = 600;
    /* 工单遗留*/
    Integer WOSTATUS_WO_UNHANDLED = 700;
    /* 维修已完成*/
    Integer WOSTATUS_RPR_COMPLATED = 800;
    /* 工单已完成*/
    Integer WOSTATUS_WO_COMPLATED = 900;
    /* 响应超时*/
    Integer WOSTATUS_RESPONSE_TIMEOUT = 1000;

    //二维码表注释
    /*二维码设备类型*/
    Integer IMGCODE_ESTATE_TYPE = 0;
    /*二维码模块类型*/
    Integer IMGCODE_MODULE_TYPE = 1;
    /*二维码场地类型*/
    Integer IMGCODE_PLACE_TYPE = 2;

    Integer CORP_LEVEL_ZERO = 0;

    //estate表注释
    /*设备类型*/
    Integer ESTATE_TYPE = 0;
    /*模块类型*/
    Integer MODULE_TYPE = 1;

    //物料代码条数
    Integer EXCEL_NUM = 2000;
    Integer EXCEL_USER_NUM = 100;

    //设备状态表注释

    //批量数量
    Integer BATCH_COUNT = 30;
    Integer BATCH_BIG_COUNT = 100;

    // AUTH RESOURECE
    Integer AUTH_RES_MOBILE = 100;

    String DEFAULT_PASSWORD = "123456";

    Integer REPORT_WAY_STATION = 1;
    Integer REPORT_WAY_BIKE = 2;

    /*工单类型-报修维修*/
    Integer WORK_ORDER_TYPE_REPAIR = 1;
    /*工单类型-自修保养*/
    Integer WORK_ORDER_TYPE_SELF_REPAIR = 2;

    /*自行车车架号捷安特-最小编号*/
    Integer ESTATE_SN_GIANT_MINIMUM = 2000001;
    /*自行车车架号捷安特-最大编号*/
    Integer ESTATE_SN_GIANT_MAXIMUM = 2022000;
    /*自行车车架号富士达-最小编号*/
    Integer ESTATE_SN_FUJITEC_MINIMUM = 2022001;
    /*自行车车架号富士达-最大编号*/
    Integer ESTATE_SN_FUJITEC_MAXIMUM = 2062000;


    /*供应商-捷安特*/
    Integer ESTATE_SUPPLIER_GIANT = 1;
    /*供应商-艾玛*/
    Integer ESTATE_SUPPLIER_EMMA = 2;
    /*供应商-富士达*/
    Integer ESTATE_SUPPLIER_FUJITEC = 3;

    // 工单类型-报修维修工单
    Integer WOTYPE_REPORT_WO = 1;
    // 工单类型-自修保养
    Integer WOTYPE_MAINTENANCE_WO = 2;

    Integer WO_RESOURCE_CATEGORY_REPORT = 1;
    Integer WO_RESOURCE_CATEGORY_ASSIGN = 2;
    Integer WO_RESOURCE_CATEGORY_REPAIR = 3;
    Integer WO_RESOURCE_CATEGORY_MAINTENANCE = 4;

    String WO_ERROR_MESSAGE_PART = "工单";

    Integer DEFAULT_TIME_OUT_MINUTE = 120;

    Integer MS_PER_MINUTE = 60000;

    /*手机权限*/
    Integer RESOURCE_TYPE = 1;

    Integer FIRST_ESTATE_NO = 10000001;

    Integer POSITIONSOURCE_REPORT = 1;
    Integer POSITIONSOURCE_CONFIRM = 2;
    Integer POSITIONSOURCE_ARRIVE = 3;
    Integer POSITIONSOURCE_FINISH = 4;
    Integer POSITIONSOURCE_LEAVE = 5;

}
