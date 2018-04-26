package com.avp.mem.njpb.util;

public enum BusinessRefData {

    /* 工单已创建*/
    WOSTATUS_WO_CREATED(1), 
    /* 报修已派发*/
    WOSTATUS_RPT_SENDED(2),
    /* 调度已派发*/
    WOSTATUS_AS_SENDED(3),
    /* 维修已确认*/
    WOSTATUS_RPR_CONFIRM(4),
    /* 交接已确认*/
    WOSTATUS_HANDOVER_CONFIRM(5),
    /* 交接拒绝*/
    WOSTATUS_HANDOVER_REJECT(6),
    /* 交接邀请*/
    WOSTATUS_HANDOVER_INVITE(7),
    /* 维修已到达*/
    WOSTATUS_RPR_ARRIVED(8),
    /* 工单遗留*/
    WOSTATUS_WO_UNHANDLED(9),
    /* 被拆分*/
    WOSTATUS_WO_SPLIT(10),
    /* 工单执行中 */
    WOSTATUS_PROCESSING(10),
    /* 维修已完成*/
    WOSTATUS_RPR_COMPLATED(11),
    /* 工单已完成*/
    WOSTATUS_WO_COMPLATED(12),

    //二维码表注释
    /*二维码设备类型*/
    IMGCODE_ESTATE_TYPE(0),
    /*二维码模块类型*/
    IMGCODE_MODULE_TYPE(1),
    /*二维码场地类型*/
    IMGCODE_PLACE_TYPE(2),

    //表单状态
    //已关联
    FORM_HAS_RELEVANCE(0),
    //已保存
    FORM_HAS_SAVE(100),
    //已提交
    FORM_HAS_COMMIT(200),
    //已关闭
    FORM_HAS_CLOSE(300),

    //表单类型
    //生产调试
    FORM_SCTS(10),
    //生产质检
    FORM_SCZJ(11),
    //现场调试
    FORM_XCTS(20),
    //现场安装
    FORM_XCAZ(21),
    //试样测试
    FORM_SYCS(40),
    //月度保养
    FORM_YDBY(1030),
    //季度保养
    FORM_JDBY(1031),
    //年度保养
    FORM_NDBY(1032),
    //半年度保养
    FORM_BNDBY(1033),


    //estate表注释
    /*设备类型*/
    ESTATE_TYPE(0),
    /*模块类型*/
    MODULE_TYPE(1),

    //物料代码条数
    EXCEL_NUM(2000),
    EXCEL_USER_NUM(100),

    //设备状态表注释
    /*生产调试*/
    ESTATE_SCTS_STATUS(1),
    /*出厂*/
    ESTATE_CC_STATUS(2),
    /*运营*/
    ESTATE_YY_STATUS(3),
    /*质保期内*/
    ESTATE_ZBQN_STATUS(4),
    /*质保期外*/
    ESTATE_ZBQW_STATUS(5),

    //设备场地操作记录表
    /*场地安装设备*/
    PLACE_INSTALL_ESTATE(1),
    /*设备安装模块*/
    ESTATE_INSTALL_MODULE(2),
    /*设备安装到场地*/
    ESTATE_INSTALL_TO_PLACE(3),
    /*模块安装到设备*/
    MODULE_INSTALL_TO_ESTATE(4),
    /*模块安装到模块*/
    MODULE_INSTALL_TO_MODULE(5),
    /*场地拆除设备*/
    PLACE_UNINSTALL_ESTATE(6),
    /*设备拆除模块*/
    ESTATE_UNINSTALL_MODULE(7),
    /*模块拆除子模块*/
    MODULE_UNINSTALL_MODULE(8),


    //知识库
    KNOWLEDGE_FILE_IMAGE(0),
    KNOWLEDGE_FILE_VIDEO(1),

    //批量数量
    BATCH_COUNT(30),
    BATCH_BIG_COUNT(100),

    // AUTH RESOURECE
    AUTH_RES_MOBILE(100),

    //保养计划类型
    //月度
    TASK_PLAN_MONTH(1030),
    //季度
    TASK_PLAN_QUARTER(1031),
    //年度
    TASK_PLAN_YEAR(1032),
    //半年
    TASK_PLAN_HALF_YEAR(1033),


    
    // 工单类型-维修工单
    WOTYPE_REPAIR(21),
    // 工单类型-月度保养工单
    WOTYPE_MAINTENANCE_MONTH(22),
    // 工单类型-年度保养工单
    WOTYPE_MAINTENANCE_YEAR(23),
    // 工单类型-季度保养工单
    WOTYPE_MAINTENANCE_QUARTER(24),
    // 工单类型-半年度保养工单
    WOTYPE_MAINTENANCE_HALF_YEAR(25),
    // 工单类型-安装工单
    WOTYPE_INSTALL(26),
    
    SYS_VERSION_PROJECT_TYPE_FORM_SCTS(1),
    SYS_VERSION_PROJECT_TYPE_FORM_SCZJ(2),
    SYS_VERSION_PROJECT_TYPE_FORM_XCAZ(3),
    SYS_VERSION_PROJECT_TYPE_FORM_XCTS(4),
    SYS_VERSION_PROJECT_TYPE_FORM_BY(5),
    SYS_VERSION_PROJECT_TYPE_FORM_SYCS(6),
    SYS_VERSION_PROJECT_TYPE_BASIC(100);


    private final int value;

    private BusinessRefData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
