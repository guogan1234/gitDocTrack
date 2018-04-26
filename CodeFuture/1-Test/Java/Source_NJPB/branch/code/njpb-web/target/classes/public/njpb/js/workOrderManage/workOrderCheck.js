/**
 * Created by len on 2017/9/14.
 */
var dataTable = null;
var isBtn = '';
var workOrderId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var allData = null;
var corpId = '';
var USERNAME = "";
var pageParam = {};


$(function () {
    pageSetUp();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('gdgl_list');
    initSelect();
    initDataTable();
    addBtn();

})

$(function () {
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByUserId?userId=' + USER_ID,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.results != null) {
                var rr = retData.results;
                corpId = rr[0].corpId;
                console.log(USERNAME);
                var url = "";
                if (corpId == 1) {
                    url = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 3;
                } else {
                    url = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 3 + "&corpId =" + corpId;
                }
                $.ajax({
                    type: 'GET',
                    url: url,
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data = [];
                        for (var i = 0; i < retData.results.length; i++) {
                            data.push({id: retData.results[i].userId, text: retData.results[i].userName});
                        }
                        $("#assign").select2({
                            data: data,
                            placeholder: '请选择',
                            allowClear: true
                        });

                        $.fn.modal.Constructor.prototype.enforceFocus = function () {
                        };

                    },
                    failure: function () {
                    }
                });
            }
        },
        failure: function () {
        }
    });

})

function initSelect() {
    //查询公司
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#workOrderCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });

            } else {
                data.push({id: retData.results[0].id, text: retData.results[0].corpName});
                $("#workOrderCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });
                $("#workOrderCorp").val(retData.results[0].id).trigger("change");
            }
        },
        failure: function () {
        }
    });

    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#workOrderEstateType").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });
        },
    });


    //工单状态
    $.ajax({
        type: 'GET',
        url: '/business-resource/workOrderStatus',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].nameCn});
            }
            $("#workOrderStatus").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });

        },
        failure: function () {
        }
    });


    var data = [];
    data.push({id: 1, text: "站点"});
    data.push({id: 2, text: "车辆"});

    $("#reportWay").select2({
        data: data,
        placeholder: '请选择',
        allowClear: true
    });

    var data2 = [];
    data2.push({id: 1, text: "报修维修工单"});
    data2.push({id: 2, text: "自修维修工单"});

    $("#workOrderType").select2({
        data: data2,
        placeholder: '请选择',
        allowClear: true
    });

}


function estatePartsType() {
    var partsType = $("#reportWay").val();
    if (partsType > 0) {
        //设备类型
        $.ajax({
            type: 'GET',
            url: '/business-resource/estateTypes?category=' + 1 + "&partsType=" + partsType,
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (retData) {

                $("#workOrderEstateType").empty();
                var data = [];
                data.push({id: 0, text: '请选择'})
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].name});
                }
                $("#workOrderEstateType").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });

                $.fn.modal.Constructor.prototype.enforceFocus = function () {
                };
            },
            failure: function () {
            }
        });
    }
}

function doSearch() {
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";

    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }
    if ($("#workOrderCorp").val() != null && $("#workOrderCorp").val() > 0) {
        pageParam.corpId = $("#workOrderCorp").val();//公司
    }

    if ($("#workOrderStatus").val() != null && $("#workOrderStatus").val() > 0) {
        pageParam.workOrderStatusId = $("#workOrderStatus").val();//状态
    }

    if ($("#workOrderEstateType").val() != null && $("#workOrderEstateType").val() > 0) {
        pageParam.estateTypeId = $("#workOrderEstateType").val();//设备类型ID
    } else {
        pageParam.estateTypeId = null;
    }
    if ($("#workOrderType").val() > 0) {
        pageParam.workOrderTypeId = $("#workOrderType").val();//工单类型
    }

    if ($("#reportWay").val() > 0 && $("#reportWay").val() != null) {
        pageParam.reportWay = $("#reportWay").val();//报修类别
    }

    if ($("#assign").val() > 0 && $("#assign").val() != null) {
        pageParam.assign = $("#assign").val();//报修类别
    }

    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endtime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }
    dataTable.ajax.reload()
}

/*DataTable 添加 添加按钮*/
// function addBtn() {
//     var dataPlugin =
//         '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
//         '</div> ';
//     $('#mytoolbox').append(dataPlugin);
// }

/* DataTable 加载 */
function initDataTable() {
    var $wrapper = $('#div-table-container');
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "serialNo";
                        break;
                    //         case 3:
                    //             param.orderColumn = "reportTime";
                    //             break;
                    //         case 4:
                    //             param.orderColumn = "organizationName";
                    //             break;
                    //         case 5:
                    //             param.orderColumn = "estateTypeName";
                    //             break;
                    //         case 6:
                    //             param.orderColumn = "orderTypeNameCn";
                    //             break;
                    //         case 7:
                    //             param.orderColumn = "faultDescriptionNameCn";
                    //             break;
                    //         case 8:
                    //             param.orderColumn = "workOrderStatusNameCn";
                    //             break;
                    case 11:
                        param.orderColumn = "reportTime";
                        break;
                    //         case 10:
                    //             param.orderColumn = "assignEmployeeUserName";
                    //             break;
                    //         case 11:
                    //             param.orderColumn = "repairEmployeeUserName";
                    //             break;
                    //         default:
                    //             param.orderColumn = "serialNo";
                    //             break;
                }
                param.orderDir = data.order[0].dir;
            }
            //排序
            param.sort = param.orderColumn + ',' + param.orderDir;
            //组装查询参数
            param.corpId = pageParam.corpId;
            param.workOrderStatusId = pageParam.workOrderStatusId;
            param.estateTypeId = pageParam.estateTypeId;
            param.workOrderTypeId = pageParam.workOrderTypeId;
            param.reportWay = pageParam.reportWay;
            param.beginTime = pageParam.beginTime;
            param.endTime = pageParam.endTime;
            param.assignEmployee = pageParam.assign;
            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTable = $('#workOrder-list').dataTable({
        "searching": false,
        "destroy": true,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
        // "scrollX": true,
        "oLanguage": {
            "sProcessing": "处理中...",
            "sLoadingRecords": "载入中...",
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            "sLengthMenu": "每页 _MENU_ 条记录",
            'sZeroRecords': '没有数据',
            'sInfo': "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            'sInfoEmpty': '没有数据',
            'sInfoFiltered': '(过滤总件数 _MAX_ 条)',
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "iDisplayLength": 5,
        "aaSorting": [[11, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#reportBtns'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/workOrders/findByUidAndConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    var returnData = {};


                    returnData.recordsTotal = result.page.totalElements;
                    returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);
                    //setTimeout仅为测试延迟效果
//                    setTimeout(function () {
//                        //封装返回数据
//                        var returnData = {};
//                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
//                        returnData.recordsTotal = result.total;//返回数据全部记录
//                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
//                        returnData.data = result.data;//返回的数据列表
                    console.log(returnData);
//                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
//                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
//                        callback(returnData);
//                    }, 200);
                }
            });
        },
        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "serialNo"},
            {"data": "workOrderTypeNameCn"},
            {"data": "reportWay"},
            {"data": "corpName"},
            {"data": "stationName"},
            {"data": "stationNo"},
            {"data": "estateTypeName"},
            {"data": "estateName"},
            {"data": "reportEmployeeUserName"},
            {"data": "assignEmployeeUserName"},
            {"data": "reportTime"},
            // {"data": "assignTime"},
            // {"data": "repairConfirmTime"},
            // {"data": "faultDescription"},
        ],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input type="checkbox"  onclick="chkClick()" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            },
            {
                'targets': 1,
                'orderable': true,
                'searchable': true,
                'className': 'dt-body-center',
                'bSortable': true,
                'render': function (data, type, row, meta) {
                    return null2black(row.serialNo);
                }
            },
            {
                'targets': 2,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.workOrderTypeNameCn);

                }
            },
            {
                'targets': 3,
                'orderable': false,
                'searchable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.reportWay);
                    if (tm == 1) {
                        return "站点报修";
                    } else if (tm == 2) {
                        return "车辆报修"
                    } else {
                        return "";
                    }
                }
            }, {
                'targets': 4,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.corpName);
                }
            },
            {
                'targets': 5,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.stationName);
                }
            }, {
                'targets': 6,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.stationNo);
                }
            },
            {
                'targets': 7,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateTypeName);
                }
            }, {
                'targets': 8,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateName);
                }
            },
            {
                'targets': 9,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.reportEmployeeUserName);
                }
            }, {
                'targets': 10,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.assignEmployeeUserName);
                }
            },
            {
                "render": function (data, type, row) {
                    var tm = null2black(row.reportTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }
                    return "";
                },
                "targets": 11,
                'orderable': true,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
            },
            // {
            //     'targets': 12,
            //     'orderable': false,
            //     'searchable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return null2black(row.faultDescription);
            //     }
            // },
            {
                'targets': 12,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    //编辑
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.orderTypeId + ',' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }
        ]

    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#workOrder-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


function chkClick() {
    $('#workOrder-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function openDialogDetail(typeId, domId, rowid) {
    $("#closeWorkOrder").show();
    $("#chkCloseRemark").show();
    workOrderId = domId;
    rowNo = rowid;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(rowJson);
    var badComponents = "";

    $.ajax({
        type: 'GET',
        url: '/business-resource/workOrderBadComponents/findByWorkOrderId?workOrderId=' + workOrderId,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            var date = data.results;
            for (var i = 0; i < date.length; i++) {
                if (i < date.length - 1) {
                    // var t = date[i].name+"："+date[i].replaceCount+" ";
                    badComponents += date[i].name + ":" + date[i].replaceCount + "， ";
                } else {
                    badComponents += date[i].name + ":" + date[i].replaceCount + " ";
                }
            }

            $("#badComponentsDate").attr("value", badComponents);
        },
        error: function (data) {

        }
    });


    $("#serialNo").attr("value", null2black(json.serialNo));
    //$("#serialNo").val(null2black(json.serialNo));

    $("#badComponentsDate").attr("value", badComponents);

    // $("#reportType").val(json.workOrderTypeNameCn);
    $("#reportType").attr("value", null2black(json.workOrderTypeNameCn));


    // $("#reportEmployee").val(null2black(json.reportEmployeeUserName));
    $("#reportEmployee").attr("value", null2black(json.reportEmployeeUserName));

    // $("#reportTime").val(dateToStr(new Date(null2black(json.reportTime)), 'YYYY-MM-DD HH:mm:ss'));
    $("#reportTime").attr("value", dateToStr(new Date(null2black(json.reportTime)), 'YYYY-MM-DD HH:mm:ss'));

    // $("#statusName").val(null2black(json.workOrderStatusNameCn));
    $("#statusName").attr("value", null2black(json.workOrderStatusNameCn));

    // $("#faultDescription").val(null2black(json.faultDescription));
    $("#faultDescription").attr("value", null2black(json.faultDescription));

    // $("#stationName").val(null2black(json.stationName));
    $("#stationName").attr("value", null2black(json.stationName));

    // $("#stationNo").val(null2black(json.stationNo));
    $("#stationNo").attr("value", null2black(json.stationNo));


    // $("#longitude").val(null2black(json.longitude));
    // $("#latitude").val(null2black(json.latitude));
    //$("#createTime").val(null2black(json.createTime));
    //$("#workOrderReportWay").val(null2black(json.reportWay));

    if (json.reportWay == 1) {
        // $("#workOrderReportWay").val("站点报修");
        $("#workOrderReportWay").attr("value", "站点报修");
    } else {
        // $("#workOrderReportWay").val("车辆报修");
        $("#workOrderReportWay").attr("value", "车辆报修");
    }

    // $("#maintainRemark").val(null2black(json.maintainRemark));
    $("#maintainRemark").attr("value", null2black(json.maintainRemark));

    // $("#corpName").val(null2black(json.corpName));
    $("#corpName").attr("value", null2black(json.corpName));

    // $("#bikeFrameNo").val(null2black(json.bikeFrameNo));
    $("#bikeFrameNo").attr("value", null2black(json.bikeFrameNo));

    // $("#assignEmployeeUserName").val(null2black(json.assignEmployeeUserName));
    $("#assignEmployeeUserName").attr("value", null2black(json.assignEmployeeUserName));

    if (isNotEmpty(json.assignTime)) {
        // $("#assignTime").val(dateToStr(new Date(json.assignTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#assignTime").attr("value", dateToStr(new Date(json.assignTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#assignTime").val("");
        $("#assignTime").attr("value", "");
    }
    // $("#assignTime").val(dateToStr(new Date(null2black(json.assignTime)), 'YYYY-MM-DD HH:mm:ss'));


    // $("#assignRemark").val(null2black(json.assignRemark));
    $("#assignRemark").attr("value", null2black(json.assignRemark));


    // $("#level").val(null2black(json.level));
    $("#level").attr("value", null2black(json.level));

    // $("#repairEmployeeUserName").val(null2black(json.repairEmployeeUserName));
    $("#repairEmployeeUserName").attr("value", null2black(json.repairEmployeeUserName));


    // $("#repairConfirmTime").val(dateToStr(new Date(null2black(json.repairConfirmTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairStartTime").val(dateToStr(new Date(null2black(json.repairStartTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairEndTime").val(dateToStr(new Date(null2black(json.repairEndTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairRemark").val(null2black(json.repairRemark));
    $("#repairRemark").attr("value", null2black(json.repairRemark));


    repairConfirmTime = json.repairConfirmTime;
    if (isNotEmpty(json.repairConfirmTime)) {
        // $("#repairConfirmTime").val(dateToStr(new Date(json.repairConfirmTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairConfirmTime").attr("value", dateToStr(new Date(json.repairConfirmTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairConfirmTime").val("");
        $("#repairConfirmTime").attr("value", "");
    }
    if (isNotEmpty(json.repairStartTime)) {
        // $("#repairStartTime").val(dateToStr(new Date(json.repairStartTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairStartTime").attr("value", dateToStr(new Date(json.repairStartTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairStartTime").val("");
        $("#repairStartTime").attr("value", "");
    }
    repairEndTime = json.repairEndTime;
    if (isNotEmpty(json.repairEndTime)) {
        // $("#repairEndTime").val(dateToStr(new Date(json.repairEndTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairEndTime").attr("value", dateToStr(new Date(json.repairEndTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairEndTime").val("");
        $("#repairEndTime").attr("value", "");
    }

    // $("#reponseOverTime").val(dateToStr(new Date(null2black(json.reponseOverTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairOverTime").val(dateToStr(new Date(null2black(json.repairOverTime)), 'YYYY-MM-DD HH:mm:ss'));

    if (isNotEmpty(json.reponseOverTime)) {
        //$("#reponseOverTime").val(dateToStr(new Date(json.reponseOverTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#reponseOverTime").attr("value", dateToStr(new Date(json.reponseOverTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#reponseOverTime").val("");
        $("#reponseOverTime").attr("value", "");
    }
    if (isNotEmpty(json.repairOverTime)) {
        // $("#repairOverTime").val(dateToStr(new Date(json.repairOverTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairOverTime").attr("value", dateToStr(new Date(json.repairOverTime), 'YYYY-MM-DD HH:mm:ss'));
        //var overTime = repairEndTime - json.repairOverTime
        //$("#repairOverTime").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
    } else {
        // $("#repairOverTime").val("");
        $("#repairOverTime").attr("value", "");
    }


    // $("#backRemark").val(null2black(json.backRemark));
    $("#backRemark").attr("value", null2black(json.backRemark));

    // $("#estateNo").val(null2black(json.estateNo));
    $("#estateNo").attr("value", null2black(json.estateNo));

    // $("#estateName").val(null2black(json.estateName));
    $("#estateName").attr("value", null2black(json.estateName));
    //$("#category").val(null2black(json.category));
    if (null2black(json.category) == 0) {
        // $("#category").val("站点");
        $("#category").attr(
            "value", "站点");
    } else {

        // $("#category").val("车辆");
        $("#category").attr("value", "车辆");
    }

    if (json.estateSn != null) {
        // $("#estateSn").val(null2black(json.estateSn));
        $("#estateSn").attr("value", null2black(json.estateSn));
    } else if (json.bicycleStakeBarCode != null) {
        // $("#estateSn").val(null2black(json.bicycleStakeBarCode));
        $("#estateSn").attr("value", null2black(json.bicycleStakeBarCode));
    } else {
        // $("#estateSn").val("");
        $("#estateSn").attr("value", "");
    }
    $("#closeRemark").attr("value", null2black(rowJson.closeRemark));

    // $("#supplierName").val(null2black(json.supplierName));
    $("#supplierName").attr("value", null2black(json.supplierName));

    // $("#estateTypeName").val(null2black(json.estateTypeName));
    $("#estateTypeName").attr("value", null2black(json.estateTypeName));
    // $("#responseTimeOutDate").val(dateToStr(new Date(null2black(json.responseTimeOutDate)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairTimeOutDate").val(dateToStr(new Date(null2black(json.repairTimeOutDate)), 'YYYY-MM-DD HH:mm:ss'));
    if (isNotEmpty(json.responseTimeOutDate)) {
        //$("#responseTimeOutDate").val(dateToStr(new Date(json.responseTimeOutDate), 'YYYY-MM-DD HH:mm:ss'));
        var overTime = repairConfirmTime - json.responseTimeOutDate;
        // $("#responseTimeOutDate").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
        $("#responseTimeOutDate").attr("value", dateToStr(new Date(overTime), 'HH:mm:ss'));

    } else {
        // $("#responseTimeOutDate").val("");
        $("#responseTimeOutDate").attr("value", "");
    }
    if (isNotEmpty(json.repairTimeOutDate)) {
        //$("#repairTimeOutDate").val(dateToStr(new Date(json.repairTimeOutDate), 'YYYY-MM-DD HH:mm:ss'));
        var overTime = repairEndTime - json.repairTimeOutDate
        // $("#repairTimeOutDate").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
        $("#repairTimeOutDate").attr("value", dateToStr(new Date(overTime), 'HH:mm:ss'));
    } else {
        // $("#repairTimeOutDate").val("");
        $("#repairTimeOutDate").attr("value", "");
    }


    // if (null2black(json.fixed) == true) {
    //     $("#fixed").val("完成");
    // } else {
    //     $("#fixed").val("未完成");
    // }

    if (json.assignEmployeeUserName == null || (json.statusId < CONSTANTS.STATUS.UNDISTRIBUTED && json.statusId > CONSTANTS.STATUS.FINISH)) {
        $("#chkAssignEmployeeUserName").css('display', 'none');
        $("#chkAssignTime").css('display', 'none');
        $("#chkAssignRemark").css('display', 'none');
        $("#chkLevel").css('display', 'none');
        // document.getElementById('chkAssignEmployeeUserName').style.display = "none";
        // document.getElementById('chkAssignTime').style.display = "none";
        // document.getElementById('chkAssignRemark').style.display = "none";
        // document.getElementById('chkLevel').style.display = "none";

    }


    if (json.repairEmployeeUserName == null || (json.statusId < CONSTANTS.STATUS.REPAIR && json.statusId > CONSTANTS.STATUS.FINISH)) {
        $("#chkRepairEmployeeUserName").css('display', 'none');
        $("#chkRepairConfirmTime").css('display', 'none');
        $("#chkRepairStartTime").css('display', 'none');
        $("#chkRepairEndTime").css('display', 'none');
        $("#chkRepairRemark").css('display', 'none');
        // document.getElementById('chkRepairEmployeeUserName').style.display = "none";
        // document.getElementById('chkRepairConfirmTime').style.display = "none";
        // document.getElementById('chkRepairStartTime').style.display = "none";
        // document.getElementById('chkRepairEndTime').style.display = "none";
        // document.getElementById('chkRepairRemark').style.display = "none";
    }

    if (json.reponseOverTime == null) {
        $("#chkReponseOverTime").css('display', 'none');
        //document.getElementById('chkReponseOverTime').style.display = "none";
    }
    if (json.repairOverTime == null) {
        $("#chkRepairOverTime").css('display', 'none');
        //document.getElementById('chkRepairOverTime').style.display = "none";
    }

    if (json.maintainRemark == null) {
        $("#chkMaintainRemark").css('display', 'none');
    }

    if (json.bikeFrameNo == null) {
        $("#chkBikeFrameNo").css('display', 'none');
    }
    if (json.statusId != CONSTANTS.STATUS.BACK || json.backRemark == null) {
        $("#chkBackRemark").css('display', 'none');

        // document.getElementById('chkBackRemark').style.display = "none";
    }

    if (json.estateTypeId != 7) {
        $("#chkSupplierName").css('display', 'none');
    }

    if (json.estateNo == null) {
        $("#chkEstateNo").css('display', 'none');
        $("#chkEstateName").css('display', 'none');
        $("#chkCategory").css('display', 'none');
        $("#chkEstateSn").css('display', 'none');
        //$("#chkSupplierName").css('display', 'none');
        $("#chkEstateTypeName").css('display', 'none');
        // document.getElementById('chkEstateNo').style.display = "none";
        // document.getElementById('chkEstateName').style.display = "none";
        // document.getElementById('chkCategory').style.display = "none";
        // document.getElementById('chkEstateSn').style.display = "none";
        // document.getElementById('chkSupplierName').style.display = "none";
        // document.getElementById('chkEstateTypeName').style.display = "none";
    }
    if (json.responseTimeOutDate == null) {
        $("#chkResponseTimeOutDate").css('display', 'none');
        //document.getElementById('chkResponseTimeOutDate').style.display = "none";
    }
    if (json.repairTimeOutDate == null) {
        $("#chkRepairTimeOutDate").css('display', 'none');

        //document.getElementById('chkRepairTimeOutDate').style.display = "none";
    }

    if (json.statusId >= CONSTANTS.STATUS.REPAIRFINISH && json.statusId <= CONSTANTS.STATUS.FINISH) {
        // $("#chkBadComponentsDate").css('display', 'none');
    } else {
        $("#chkBadComponentsDate").css('display', 'none');
    }


    if(json.statusId != CONSTANTS.STATUS.LEAVE && json.statusId != CONSTANTS.STATUS.OVERTIME){
        $("#closeWorkOrder").hide();
        $("#chkCloseRemark").hide();
    }
    if(rowJson.closeRemark == "" || rowJson.closeRemark == null ){
        $("#chkCloseRemark").hide();

    }

if( (json.statusId == CONSTANTS.STATUS.OVERTIME  || json.statusId == CONSTANTS.STATUS.LEAVE) && (rowJson.closeRemark != "" && rowJson.closeRemark != null)){
        $("#closeWorkOrder").hide();
    }


    //照片
    $.ajax({
        type: 'GET',
        url: '/business-resource/workOrderResources/findByWorkOrderId?workOrderId=' + workOrderId,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {

            var reportPicture = [];
            var assignPicture = [];
            var repairPicture = [];
            var maintainPicture = [];
            for (var i = 0; i < retData.results.length; i++) {
                var category = retData.results[i].category;
                if (category == 1) {
                    reportPicture.push(retData.results[i].fileUrl);
                }
                if (category == 2) {
                    assignPicture.push(retData.results[i].fileUrl);
                }
                if (category == 3) {
                    repairPicture.push(retData.results[i].fileUrl);
                }
                if (category == 4) {
                    maintainPicture.push(retData.results[i].fileUrl);
                }
                // var file = retData.results[i].fileUrl;
                // html += '<span class="col-sm-2"> <img src= ' + file + '  width="128px" height="128px" /></span>';
            }
            $("#reportList").css('display', 'block');
            if (reportPicture.length > 0) {
                var html = '';
                for (var i = 0; i < reportPicture.length; i++) {
                    if (i == 3) {
                        html += '<span class="col-sm-2"> <img src= ' + reportPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;" class="next"/></span>';
                    } else {
                        html += '<span class="col-sm-2"> <img src= ' + reportPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;" /></span>';
                    }


                }
                $('#imgReportList').html(html);
            }
            else {
                $("#reportList").css('display', 'none');
            }
            $("#assignList").css('display', 'block');
            if (assignPicture.length > 0) {
                var html = '';
                for (var i = 0; i < assignPicture.length; i++) {
                    html += '<span class="col-sm-2"> <img src= ' + assignPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/></span>';
                }
                $('#imgAssignList').html(html);
            }
            else {
                $("#assignList").css('display', 'none');
            }
            $("#repairList").css('display', 'block');
            if (repairPicture.length > 0) {
                var html = '';
                for (var i = 0; i < repairPicture.length; i++) {
                    html += '<span class="col-sm-2">  <img src= ' + repairPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer; "/></span>';

                }
                $('#imgRepairList').html(html);
            }
            else {
                $("#repairList").css('display', 'none');
            }
            $("#maintainList").css('display', 'block');
            if (maintainPicture.length > 0) {
                var html = '';
                for (var i = 0; i < maintainPicture.length; i++) {
                    html += '<span class="col-sm-2"> <img src= ' + maintainPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/></span>';
                }
                $('#imgMaintainList').html(html);
            }
            else {
                $("#maintainList").css('display', 'none');
            }

        }
    });


    loadWorkOrderHistories();

    $("#detailModalLabel").html("工单详情");
    $("#detailForm").modal('show');


}


function detailCancel() {
    $("#detailForm").modal('hide');
}
function loadWorkOrderHistories() {
    dataTable2 = $('#workOrderDetail').dataTable({
        "destroy": true,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,

        "oLanguage": {
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            // "sLengthMenu": "每页 _MENU_ 条记录",
            'sZeroRecords': '没有数据',
            'sInfo': '第 _PAGE_ 页 / 总 _PAGES_ 页,共 _TOTAL_ 项',
            'sInfoEmpty': '没有数据',
            'sInfoFiltered': '(过滤总件数 _MAX_ 条)',
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "iDisplayLength": 10,
        "aaSorting": [[2, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'<''>><'col-sm-5'>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'><'col-sm-6'>>",
        "ajax": {
            "url": "/business-resource/workOrderHistories/findByWorkOrderId?workOrderId=" + workOrderId,
            "dataSrc": "results"
        },
        "columns": [{
            "data": "userName"
        }, {
            "data": "txt"
        },
            //     {
            //     "data": "operationRemark"
            // },
            {
                "data": "createTime"
            }, {
                "data": "operationTime"
            }
        ],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.userName);
                }
            },
            {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.txt);

                }
            },
            // {
            //     'targets': 2,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return null2black(row.operationRemark);
            //     }
            // },
            {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.createTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }
                }
            }, {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.operationTime) + " ";

                    var temp = tm.split(".")
                    var temp1 = "";
                    if (temp[0].indexOf('days') > 0) {
                        temp1 = temp[0].replace('days', '天');
                        return temp1;
                    } else if (temp[0].indexOf('day') > 0) {
                        temp1 = temp[0].replace('day', '天');
                        return temp1;
                    } else {
                        return temp[0];
                    }

                }
            }]
    });
    //复选框全选控制
    var active_class = 'active';
    $('#workOrderDetail > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}
function chkplClick() {

    $('#workOrderDetail> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function addBtn() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#reportBtns').append(dataPluginCD);
}


function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

function exportExcel() {
    // if (!isNotEmpty($("#selectCorp").val())) {
    //     layer.tips('请先选择公司', '#chkCorp');
    //     return;
    // }
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";
    var url = '/business-resource/workOrder/exportWorkOrder?1=1';
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if (isNotEmpty($("#workOrderCorp").val())) {
        if ($("#workOrderCorp").val() != 1) {
            url += '&corpId=' + $("#workOrderCorp").val();
        }
    }

    if (isNotEmpty($("#workOrderCorp").val())) {
        if ($("#workOrderCorp").val() != 1) {
            url += '&corpId=' + $("#workOrderCorp").val();
        }
    } else {
        if (corpId != 1) {
            url += '&projectId=' + corpId;
        }
    }
    if (isNotEmpty($("#reportWay").val())) {
        url += '&reportWay=' + $("#reportWay").val();
    }

    if (isNotEmpty($("#workOrderEstateType").val())) {
        url += '&estateTypeId=' + $("#workOrderEstateType").val();
    }

    if (isNotEmpty($("#workOrderStatus").val())) {
        url += '&workOrderStatusId=' + $("#workOrderStatus").val();
    }

    if (isNotEmpty($("#workOrderType").val())) {
        url += '&workOrderTypeId=' + $("#workOrderType").val();
    }

    if (isNotEmpty($("#assign").val())) {
        url += '&assignEmployee=' + $("#assign").val();
    }

    if (isNotEmpty($("#beginTime").val())) {
        url += '&startDate=' + beginTime1;
    }

    if (isNotEmpty($("#endtime").val())) {
        url += '&endDate=' + endtime1;
    }

    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();
}

function btnDelete() {

    $("#dangerMsg").html('');
    var str = '';
    for (var i = 0; i < document.getElementsByName('ids').length; i++) {
        if (document.getElementsByName('ids')[i].checked) {
            if (str == '') str += document.getElementsByName('ids')[i].value;
            else str += ',' + document.getElementsByName('ids')[i].value;
        }
    }
    if (str == '') {
        BootstrapDialog.show({
            title: '提示',
            message: '您没有选择任何内容',
            type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            buttons: [{
                label: '确定',
                cssClass: 'btn-warning',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
    }
    else {
        BootstrapDialog.confirm({
            title: '删除确认',
            message: '是否确认删除?',
            type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: '取消', // <-- Default value is 'Cancel',
            btnOKLabel: '确定', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function (result) {
                // result will be true if button was click, while it will be false if users close the dialog directly.
                if (result) {
                    //调用ajax删除
                    $.ajax({
                        type: 'DELETE',
                        url: '/business-resource/workOrders/deleteMore?ids=' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 20000) {
                                doSearch();
                                $('.alert-success').show();
                                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                            }
                            else {
                                $("#dangerMsg").html(data.responseJSON.massage);
                                $('.alert-danger').show();
                                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                            }
                        },
                        error: function (data) {
                            $("#dangerMsg").html(data.responseJSON.massage);
                            $('.alert-danger').show();
                            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                        }
                    });
                }
            }
        });
    }
}


function printOut() {

    $("#workOrderPrint").jqprint({
        debug: false,
        importCSS: true,
        printContainer: true,
        operaSupport: true
    });
}

function closeWorkOrder() {
    $("#bel1").css("display",'block');
    $("#closeRemarkText").val("");

}

function saveCloseRemark(){
    $("#dangerMsg").html('');
    var closeRemarkText = $("#closeRemarkText").val();
    if(closeRemarkText ==null){
        closeRemarkText="";
        }

    var t = document.getElementById('btn_sendAll1').innerHTML;
    $.ajax({
        type: 'PUT',
        url: '/business-resource/workOrders/close?workOrderId=' + workOrderId+"&closeRemark="+closeRemarkText,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.code == 30000) {
                // rowJson.closeRemark = closeRemarkText;
                dataTable.ajax.reload();
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
            } else {
                $("#dangerMsg").html(data.responseJSON.message);
                $('.alert-danger').show();
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        },
        error: function (e) {
            $("#dangerMsg").html(e.responseJSON.message);
            $('.alert-danger').show();
            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
        }
    });
    $("#bel1").css("display",'none');
    $("#detailForm").modal('hide');
}

function deleCloseRemark(){
    $("#bel1").css("display",'none');
}

function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/sysMessages";
    reloadTable(dataTable, '#message-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}