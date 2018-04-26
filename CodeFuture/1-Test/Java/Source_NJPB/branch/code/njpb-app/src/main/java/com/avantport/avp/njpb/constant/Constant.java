package com.avantport.avp.njpb.constant;

/**
 * Created by len on 2017/8/15.
 * url地址
 */

public class Constant {

    //http://112.124.51.202
    //http://192.168.1.116
//    http://192.168.1.18
    public static String BASE_URL = "http://112.124.51.202:4071";
    //进行登录
    public static  String BASE_LOGIN_URL = "http://112.124.51.202:4079";
//    public static String LOGIN = "http://112.124.51.202:4079/sso/oauth/token";
    //上传手机信息
    public static String PHONEINFO = "http://112.124.51.202:4077/pushInfos/register";
    //获取用户信息
    public static String USERINFO = BASE_URL + "/users/findCurrentUserInfo";
    //我的盘点下拉刷新
    public static String MINE_CHECK_REFRESH = BASE_URL + "/inventoryChecks/findByOperationTimeGreaterThan";
    //我的盘点上拉加载更多
    public static String MINE_CHECK_LOADMORE = BASE_URL + "/inventoryChecks/findByOperationTimeLessThan";
    //我的盘点的数量和上次盘点的数量
    public static String MINE_CHECK_COUNT = BASE_URL + "/inventoryChecks/getCheckSumCount";

    //我的领料and我的归还
    public static String MINE_TAKEOUT_REFRESH = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeGreaterThan";
    public static String MINE_TAKEOUT_LOADMORE = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndOperationResultAndOperationTimeLessThan";


    //我的审核http://192.168.1.116:4071/stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeGreaterThan                                              /stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeLessThan
    public static String MINE_AUDIT_LOADMORE = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeLessThan";
    public static String MINE_AUDIT_REFRESH = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndProcessUserIdAndOperationTimeGreaterThan";

    //我的库存stockRecordPersonals/findByPartsType
    public static String MINE_STOCK = BASE_URL + "/stockRecordPersonals/findByPartsType";
    //物料审核
    public static String WORK_CHECK_APPLY_LOADMORE = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndOperationTimeLessThan";
    public static String WORK_CHECK_APPLY_REFRESH = BASE_URL + "/stockWorkOrders/findByStockWorkOrderTypeIdAndOperationTimeGreaterThan";

    //我的报修
    public static String MINE_REPAIES_LOADMORE = BASE_URL + "/workOrders/findByReportWayAndReportEmployeeAndOperationTimeLessThan";
    public static String MINE_REPAIES_REFRESH = BASE_URL + "/workOrders/findByReportWayAndReportEmployeeAndOperationTimeGreaterThan";

    //我的维修http://192.168.1.116:4071/workOrders/findByReportWayAndRepairEmployeeAndOperationTimeLessThan
    public static String MINE_SERVICE_LOADMORE = BASE_URL + "/workOrders/findByReportWayAndRepairEmployeeAndOperationTimeLessThan";
    public static String MINE_SERVICE_REFRESH = BASE_URL + "/workOrders/findByReportWayAndRepairEmployeeAndOperationTimeGreaterThan";
    //我的保养
    public static String MINE_MYSELF_LOADMORE = BASE_URL + "/workOrders/findByReportWayAndOperationTimeLessThan";
    public static String MINE_MYSELF_REFRESH = BASE_URL + "/workOrders/findByReportWayAndOperationTimeGreaterThan";

    //我的派单
    public static String MINE_SEND_LOADMORE = BASE_URL + "/workOrders/findByReportWayAndAssignEmployeeAndOperationTimeLessThan";
    public static String MINE_SEND_REFRESH = BASE_URL + "/workOrders/findByReportWayAndAssignEmployeeAndOperationTimeGreaterThan";

    //我的消息
    public static String MINE_NEWS_LOADMORE = BASE_URL + "/sysMessages/findByOperationTimeLessThan";
    public static String MINE_NEWS_REFRESH = BASE_URL + "/sysMessages/findByOperationTimeGreaterThan";

    //物料审核，工单详情
    public static String ORDER_DETAILS = BASE_URL + "/stockWorkOrders/findByStockWorkOrderId";
    public static String ORDER_CHECK_PASS = BASE_URL + "/stockWorkOrders/checkAgreeByStockWorkOrderId";
    //审核拒绝
    public static String ORDER_CHECK_REFUSE = BASE_URL + "/stockWorkOrders/checkRejectByStockWorkOrderId";

    //工单信息查询，待处理的工单信息
    public static String ORDER_MESSAGE_LOADMORE = BASE_URL + "/workOrders/findTodoTaskByOperationTimeLessThan";
    public static String ORDER_MESSAGE_REFRESH = BASE_URL + "/workOrders/findTodoTaskByOperationTimeGreaterThan";


    //工单信息，处理中
    public static String ORDER_MESSAGE_HANDLE_LOADMORE = BASE_URL + "/workOrders/findRunningTaskByOperationTimeLessThan";
    public static String ORDER_MESSAGE_HANDLE_REFRESH = BASE_URL + "/workOrders/findRunningTaskByOperationTimeGreaterThan";

    //获取下步的操作
    public static String NEXT_STEP = BASE_URL + "/workOrders/findTaskNextOperation";

    //工单信息查询——坏件/更换备件记录
    public static String BADCOMPONENTS = BASE_URL + "/workOrderBadComponents/findByWorkOrderId";

    //根据配件类别获取模块类型
    public static String COMPONENTS_TYPE = BASE_URL + "/estateTypes/findModuleTypesByPartsType";
    //获取工单的详情
    public static String ORDER_MEASSAGE_DETAILS = BASE_URL + "/workOrders/findByWorkOrderId";

    //获得图片的资源
    public static String ORDER_PICTURE = BASE_URL + "/workOrderResources/findByWorkOrderIdAndResourceType";

    //获取公司的信息
    public static String COMPANY_INFO = BASE_URL + "/corporations";
    //获取站点信息
    public static String STATION_INFO = BASE_URL + "/stations/findByStationNoNameLike";

    //获取设备的类型
    public static String DEVICE_TYPE = BASE_URL + "/estateTypes";

    //查询设备，无权限
    public static String SEARCH_DEVICE_NOLIMIT = BASE_URL + "/estates/findByEstateTypeIdAndCorpId";

    //查询设备，有权限
    public static String SEARCH_DEVICE_LIMIT = BASE_URL + "/estates/findByEstateTypeIdAndUid";

    //角色查询用户
    public static String FIND_USER_ROLE = BASE_URL + "/users/findByRoleIdAndCorpId";
    //工单报修
    public static String ORDER_REPAIRS = BASE_URL + "/workOrders";

    //获取自行车的供应商
    public static String BIKE_SUPPLIER = BASE_URL + "/estateSuppliers";

    //车辆芯片查车辆
    public static String FIND_BIKE_CARD = BASE_URL + "/estates/findByBikeFrameNo";

    //获取设备上次维修的记录
    public static String LAST_SERVING_INFO = BASE_URL + "/workOrders/findByEstateId";

    //根据类别获取配件类型

    public static String PART_TYPE = BASE_URL + "/estateTypes/findByPartsType";

    //校验当前时间是否可以进行盘点
    public static String CHECK_TIME = BASE_URL + "/inventoryChecks/checkTime";
    //盘点记录
    public static String CHECK_RECODE = BASE_URL + "/inventoryChecks";

    //审核和归还
    public static String CHECK_AND_RETURN = BASE_URL + "/stockWorkOrders";
    //自修保养
    public static String KEEPBYMYSELF = BASE_URL + "/workOrders/maintenance";
    //上传图片
    public static String UPLOAD_FILES = BASE_URL + "/files/uploadImages";

    //扫一扫获取设备
    public static String QRCODE_DATA = BASE_URL + "/estateBarCodes/findByBarCodeSn";

    //更换条码
    public static String CHANGE_QRCODE = BASE_URL + "/estates/replaceBarCode";

    //获得图片资源
    public static String DOWN_IMAGE = BASE_URL + "/workOrderResources/findByWorkOrderId";

    //工单退回
    public static String ORDER_BACK = BASE_URL + "/workOrders/back/";

    //获取维修人
    public static String REPAIRS_PROPLE = BASE_URL + "/users/findByRoleIdAndCorpId";
    //派单
    public static String SEND_ORDER = BASE_URL + "/workOrders/assign";
    //工单签到
    public static String ORDER_SIGN = BASE_URL + "/workOrders/signIn/";

    //工单审核，工单结束
    public static String ORDER_FINISH = BASE_URL + "/workOrders/repairCheck/";

    //工单确认，接单
    public static String order_Receiving = BASE_URL + "/workOrders/confirm/";

    //工单遗留
    public static String ORDER_LEAVE = BASE_URL + "/workOrders/leaveOver/";

    //结束报修流程
    public static String ORDER_CHECKOVER = BASE_URL + "/workOrders/endReport/";

    //工单维修完成
    public static String ORDER_COMPLETE = BASE_URL + "/workOrders/complete/";

    //库存工单下一步
    public static String STOCKWORKORDER_NEXT = BASE_URL + "/stockWorkOrders/findTaskNextOperation";

    //库存工单驳回后取消
    public static String STOP_APPLY = BASE_URL + "/stockWorkOrders/cancel";

    //库存工单的图片资源
    public static  String STOCKWORKORDER_RESOURCES = BASE_URL+"http://192.168.1.116:4071/stockWorkOrderResources/findByStockWorkOrderId";

    //修改用户的密码
    public static String CHANGE_PASSWORD = BASE_URL + "/users/changePassword";

    //工单的流转记录//获得流转记录
    public static String ORDER_RECODE = BASE_URL + "/workOrderHistories/findByWorkOrderId";

    //获得用户的操作权限
    public static String WORK_LIMIT = BASE_URL + "/users/findByUserId";

    //获取当前用户的总的工分
    public static String WORK_SCORE = BASE_URL + "/workOrders/findByUidAll";
    //按月获取当前用户的工分
    public static String WORK_MONTH_SCORE = BASE_URL + "/workOrders/findByUidAndMonth";
    //工分详情
    public static String WORK_SCORE_DETAILS = BASE_URL + "/workOrdersScoreDetails/findByMonth";

    //获取设备的维修记录
    public static String ESTATE_SERVICE_RECODE = BASE_URL+"/workOrders/findByEstateIdAndRepair";

    //设备类型获取模块类型
    public static String STATION_MOUDLE_TYPE = BASE_URL+"/moduleTypes/findByEstateType";


    //版本进行更新
    public static String UPDATA_VERSION = BASE_URL+"/versions/findTopAppVersionByOsType";
}
