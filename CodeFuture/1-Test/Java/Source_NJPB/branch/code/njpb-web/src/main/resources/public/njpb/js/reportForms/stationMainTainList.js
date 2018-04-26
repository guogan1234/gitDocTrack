/**
 * Created by len on 2018/1/29.
 */
/**
 * Created by len on 2018/1/24.
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
var reportEmployeeTemp=null;
var assignEmployeeTemp =null;
var checkEmployeeTemp = null;
$(function () {
    pageSetUp();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('report_station_maintain_list');
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
            data.push({id: -1, text: "请选择"});
            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#workOrderCorp").select2({
                    data: data,
                    // placeholder: '请选择',
                    allowClear: true
                });
                $("#workOrderCorp").val("-1").trigger("change");
            } else {
                data.push({id: retData.results[0].id, text: retData.results[0].corpName});
                $("#workOrderCorp").select2({
                    data: data,
                    // placeholder: '请选择',
                    allowClear: true
                });
                $("#workOrderCorp").val(retData.results[0].id).trigger("change");
            }
        },
    });


    //站点
    $.ajax({
        type: 'GET',
        url: '/business-resource/stations',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({
                        id: retData.results[i].id,
                        text: retData.results[i].stationNo + "" + retData.results[i].stationName
                    });
                }
                $("#stationId").select2({
                    data: data,
                    // placeholder: '请选择',
                    allowClear: true
                });
                $("#stationId").val("-1").trigger("change");
            }
        },
    });


    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#workOrderEstateType").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#workOrderEstateType").val("-1").trigger("change");
        },
    });
    //故障类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/faultType?',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#faultTypeId").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#faultTypeId").val("-1").trigger("change");
        },
    });

    //报修员
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 2,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#reportEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#reportEmployee").val("-1").trigger("change");
        },
    });
    //审核员
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 7,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#checkEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#checkEmployee").val("-1").trigger("change");
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
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].nameCn});
            }
            $("#statusId").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#statusId").val("-1").trigger("change");
        },
    });

}

function doSearch() {
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endTime").val() + " " + "23:59:59";

    if (beginTime1 > endtime1 && isNotEmpty($("#endTime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }
    if ($("#workOrderCorp").val() != -1) {
        pageParam.corpId = $("#workOrderCorp").val();//公司
    } else {
        pageParam.corpId = null;
    }


    if ($("#stationId").val() != -1) {
        pageParam.stationId = $("#stationId").val();//状态
    } else {
        pageParam.stationId = null;
    }

    if ($("#workOrderEstateType").val() != -1) {
        pageParam.estateTypeId = $("#workOrderEstateType").val();//设备类型ID
    } else {
        pageParam.estateTypeId = null;
    }

    if ($("#faultTypeId").val() != -1) {
        pageParam.faultTypeId = $("#faultTypeId").val();//设备类型ID
    } else {
        pageParam.faultTypeId = null;
    }

    if ($("#reportEmployee").val() != -1) {
        pageParam.reportEmployee = $("#reportEmployee").val();
    } else {
        pageParam.reportEmployee = null;
    }
    // if ($("#assignEmployee").val() != -1) {
    //     pageParam.assignEmployee = $("#assignEmployee").val();
    // } else {
    //     pageParam.assignEmployee = null;
    // }
    // if ($("#repairEmployee").val() != -1) {
    //     pageParam.repairEmployee = $("#repairEmployee").val();
    // } else {
    //     pageParam.repairEmployee = null;
    // }
    if ($("#checkEmployee").val() != -1) {
        pageParam.checkEmployee = $("#checkEmployee").val();
    } else {
        pageParam.checkEmployee = null;
    }

    if ($("#statusId").val() != -1) {
        pageParam.statusId = $("#statusId").val();//状态
    } else {
        pageParam.statusId = null;
    }

    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endTime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }
    dataTable.ajax.reload()
}

function searchStation() {
    var corpId = $("#workOrderCorp").val();
    var urlStations = "";
    var urlReportEmployee = "";
    var urlAssignEmployee = "";
    var urlRepairEmployee = "";
    var urlCheckEmployee = "";
    if (corpId == -1 || corpId == 1) {
        urlStations = '/business-resource/stations';
        urlReportEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 2;
        urlAssignEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 3;
        urlRepairEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 4;
        urlCheckEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 7;

    } else {
        urlStations = '/business-resource/stations?corpId=' + corpId;
        urlReportEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 2 + "&corpId=" + corpId;
        urlAssignEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 3 + "&corpId=" + corpId;
        urlRepairEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 4 + "&corpId=" + corpId;
        urlCheckEmployee = '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 7 + "&corpId=" + corpId;
    }


    //站点
    $.ajax({
        type: 'GET',
        url: urlStations,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({
                        id: retData.results[i].id,
                        text: retData.results[i].stationNo + "" + retData.results[i].stationName
                    });
                }
                $("#stationId").select2({
                    data: data,
                    // placeholder: '请选择',
                    allowClear: true
                });
                $("#stationId").val("-1").trigger("change");
            }
        },
    });

    //报修员
    $.ajax({
        type: 'GET',
        url: urlReportEmployee,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#reportEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#reportEmployee").val("-1").trigger("change");
        },
    });

    //派单员
    $.ajax({
        type: 'GET',
        url: urlAssignEmployee,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#assignEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#assignEmployee").val("-1").trigger("change");
        },
    });

    //维修员
    $.ajax({
        type: 'GET',
        url: urlRepairEmployee,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#repairEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#repairEmployee").val("-1").trigger("change");
        },
    });
    //审核员
    $.ajax({
        type: 'GET',
        url: urlCheckEmployee,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].userId, text: retData.results[i].userName});
            }
            $("#checkEmployee").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#checkEmployee").val("-1").trigger("change");
        },
    });


}

function searchFaultType() {
    var estateTypeId = $("#workOrderEstateType").val();
    var url = "";
    if (estateTypeId == -1) {
        url = '/business-resource/faultType';
    } else {
        url = '/business-resource/faultType?estateTypeId=' + estateTypeId;
    }
    //故障类型
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#faultTypeId").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#faultTypeId").val("-1").trigger("change");
        },
    });
}

/* DataTable 加载 */
function initDataTable() {
    var t =  dateToStr(new Date(), 'YYYY/MM/DD');
    var t2 = t+" "+"00:00:00";
    pageParam.beginTime = new Date(t2);
    var t3 =  dateToStr(new Date(), 'YYYY/MM/DD hh:mm:ss');
    pageParam.endTime = t3;

    $("#beginTime").val(t);
    $("#endTime").val(t);
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "count";
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
                    // case 9:
                    //     param.orderColumn = "reportTime";
                    //     break;
                    // case 10:
                    //     param.orderColumn = "reportTime";
                    //     break;
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
            // getQueryCondition: function (data) {
            //     var param = {};

            param.corpId = pageParam.corpId;
            param.stationId = pageParam.stationId;
            param.estateTypeId = pageParam.estateTypeId;
            param.faultTypeId = pageParam.faultTypeId;
            param.reportEmployee = pageParam.reportEmployee;
            // param.assignEmployee = pageParam.assignEmployee;
            // param.repairEmployee = pageParam.repairEmployee;
            param.checkEmployee = pageParam.checkEmployee;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.statusId = pageParam.statusId;
            param.partsType =1;
            param.typeId = 2;
            return param;
        }
    };

    dataTable = $('#workOrderPrint').dataTable({
        //"searching": false,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        "iDisplayLength": 5,
        "aaSorting": [[0, 'desc']],
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
                url: "/business-resource/stationRepairList/findByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    var returnData = {};
                    //
                    //
                    // returnData.recordsTotal = result.page.totalElements;
                    // returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);

                    // var api = this.api();
                    // var fda = dataTable.column(3).data().reduce(function (a, b) {
                    //         return (parseFloat(a) + parseFloat(b)).toFixed(2);
                    //     }
                    // );
                    var t = 0;
                    for (var i = 0; i < allData.length; i++) {
                        t += allData[i].count;
                    }
                    $(dataTable.column(0).footer()).html("工单总数：");
                    $(dataTable.column(7).footer()).html(t);
                }
            });
        },

        "oLanguage": {
            "sProcessing": "<span style='color:#ff0000;'><img src='/njpb/images/loading.gif'></span>",
            "sLoadingRecords": "载入中...",
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },

        /* 故障数-统计*/
        initComplete: function () {
            var api = this.api();
            var fda = api.column(7).data().reduce(function (a, b) {
                    return (parseFloat(a) + parseFloat(b)).toFixed(7);
                }
            );
            var j = parseInt(fda);
            $(api.column(0).footer()).html("工单总数：");
            $(api.column(7).footer()).html(j);
        },


        //列表表头字段
        columns: [
            {"data": "corpName"},
            {"data": "stationName"},
            {"data": "stationNo"},
            {"data": "estateTypeName"},
            {"data": "faultTypeName"},
            {"data": "workOrderStatusNameCn"},
            {"data": "reportEmployeeUserName"},
            {"data": "count"},
        ],


        "columnDefs": [
            // {
            //     'targets': 0,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return '<input type="checkbox"  onclick="chkClick()" name="ids" value=' + row.estateTypeId + ' id="chk' + row.id + '"  class="ace"/>';
            //     }
            // },
            // {
            //     'targets': 1,
            //     'orderable': false,
            //     'searchable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return null2black(row.estateTypeName);
            //     }
            // },
            {
                'targets': 3,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateTypeName);
                }
            },
            {
                'targets': 4,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.faultTypeName);
                }
            }, {
                'targets': 5,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.workOrderStatusNameCn);
                }
            },
            {
                'targets': 6,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.repairEmployeeUserName);
                }
            },
            {
                'targets': 8,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    //编辑
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.projectId + ',' + row.stationId + ',' + row.estateTypeId + ',' + row.faultTypeId + ',' + row.statusId + ',' + row.repairEmployee + ',' + meta.row + ',' + row.id + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }

        ]

    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#stationEstatesFault-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


function chkClick() {
    $('#workOrderPrint> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function openDialogDetail(projectId, stationId, estateTypeId, faultTypeId, statusId, repairEmployee, rowid, domId) {

    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endTime").val() + " " + "23:59:59";

    if (beginTime1 > endtime1 && isNotEmpty($("#endTime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }
    rowNo = rowid;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(rowJson)
    urlTemp = "/business-resource/workOrders/list?1=1&typeId = 2;";

    if (projectId != null) {
        urlTemp += "&projectId=" + projectId;
        projectIdTemp = projectId;
    }

    if (stationId != null) {
        urlTemp += "&stationId=" + stationId;
        stationIdTemp = stationId;
    }
    if (estateTypeId != null) {
        urlTemp += "&estateTypeId=" + estateTypeId;
        estateTypeIdTemp = estateTypeId;
    }
    if (faultTypeId != null) {
        urlTemp += "&faultTypeId=" + faultTypeId;
        faultTypeIdTemp = faultTypeId;
    }
    if (statusId != null) {
        urlTemp += "&statusId=" + statusId;
        statusIdTemp = statusId;
    }
    // if (repairEmployee != null) {
    //     urlTemp += "&repairEmployee=" + repairEmployee;
    //     repairEmployeeTemp = repairEmployee;
    // }
    if ($("#reportEmployee").val() != -1) {
        urlTemp += "&reportEmployee=" + $("#reportEmployee").val();
        reportEmployeeTemp = $("#reportEmployee").val()
    }
    // if ($("#assignEmployee").val() != -1) {
    //     urlTemp += "&assignEmployee=" + $("#assignEmployee").val();
    //     assignEmployeeTemp = $("#assignEmployee").val();
    // }
    if ($("#checkEmployee").val() != -1) {
        urlTemp += "&checkEmployee=" + $("#checkEmployee").val();
        checkEmployeeTemp = $("#checkEmployee").val();
    }
    if (isNotEmpty($("#beginTime").val())) {
        urlTemp += "&startDate=" + beginTime1;

    }
    if (isNotEmpty($("#endTime").val())) {
        urlTemp += "&endDate" + endtime1;//结束时间
    }

    loadWorkOrderHistories();

    $("#detailModalLabel").html("站点列表详情");
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
            "url": urlTemp,
            "dataSrc": "results"
        },
        "columns": [{
            "data": "serialNo"
        }, {
            "data": "stationName"
        },
            {
                "data": "stationNo"
            },
            {
                "data": "estateTypeName"
            }, {
                "data": "estateName"
            }, {
                "data": "faultName"
            },
            {
                "data": "workOrderStatusNameCn"
            },
            {
                "data": "replaceEstates"
            }, {
                "data": "replaceEstates"
            },
            {
                "data": "replaceEstates"
            },


            {
                "data": "reportEmployeeUserName"
            }, {
                "data": "reportTime"
            },
            {
                "data": "faultDescription"
            }, {
                "data": "assignEmployeeUserName"
            }, {
                "data": "assignTime"
            },
            {
                "data": "assignRemark"
            }, {
                "data": "repairEmployeeUserName"
            },
            {
                "data": "repairStartTime"
            }, {
                "data": "repairRemark"
            }, {
                "data": "checkEmployee"
            }, {
                "data": "scoreDeductRemark"
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
                    return null2black(row.serialNo);
                }
            },
            {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.stationName);

                }
            },
            {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.stationNo);

                }
            },
            {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateTypeName);

                }
            },
            {
                'targets': 4,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateName);

                }
            },
            {
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.faultName);

                }
            },
            {
                'targets': 7,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    var t = row.replaceEstates.split(";");

                    return null2black(t[0]);
                }
            },
            {
                'targets': 8,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    var t = row.replaceEstates.split(";");

                    return null2black(t[1]);
                }
            },
            {
                'targets': 9,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    var t = row.replaceEstates.split(";");

                    return null2black(t[2]);
                }
            },

            {
                'targets': 11,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    // return null2black(row.reportTime);

                    var tm = null2black(row.reportTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }
                }
            },



            {
                'targets': 12,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.faultDescription);
                }
            },
            {
                'targets': 14,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.assignTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }

                }
            },
            {
                'targets': 15,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.assignRemark);
                }
            },

            {
                'targets': 16,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.repairEmployeeUserName);
                }
            }, {
                'targets': 17,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    var tm = null2black(row.repairStartTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }
                }
            }, {
                'targets': 18,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.repairRemark);
                }
            }, {
                'targets': 19,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.checkEmployee);
                }
            }, {
                'targets': 20,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.scoreDeductRemark);
                }
            },
            // {
            //     'targets': 2,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         var tm = null2black(row.lastOperationTime);
            //         if (isNotEmpty(tm)) {
            //             return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
            //         } else {
            //             return tm;
            //         }
            //     }
            // }, {
            //     'targets': 3,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         var tm = null2black(row.operationTime) + " ";
            //
            //         var temp = tm.split(".")
            //         var temp1 = "";
            //         if (temp[0].indexOf('days') > 0) {
            //             temp1 = temp[0].replace('days', '天');
            //             return temp1;
            //         } else if (temp[0].indexOf('day') > 0) {
            //             temp1 = temp[0].replace('day', '天');
            //             return temp1;
            //         } else {
            //             return temp[0];
            //         }
            //
            //     }
            // }
        ]
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

function exportDetail() {

    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endTime").val() + " " + "23:59:59";

    var url = '/business-resource/stationRepairDetailExport?1=1';
    if ( projectIdTemp!= null) {
        url += '&projectId=' + projectIdTemp;
    }

    if (stationIdTemp!=null) {
        url += '&stationId=' + stationIdTemp;
    }
    if (estateTypeIdTemp!=null) {
        url += '&estateTypeId=' +estateTypeIdTemp;
    }
    if (faultTypeIdTemp!= null) {
        url += '&faultTypeId=' +faultTypeIdTemp;
    }

    if (reportEmployeeTemp!= null) {
        url += '&reportEmployee=' +reportEmployeeTemp;
    }
    // if (assignEmployeeTemp!=null) {
    //     url += '&assignEmployee=' +assignEmployeeTemp;
    // }
    // if (repairEmployeeTemp != null) {
    //     url += '&repairEmployeeTemp=' +repairEmployeeTemp;
    // }
    if (checkEmployeeTemp!=null) {
        url += '&checkEmployee=' +checkEmployeeTemp;
    }

    if (statusIdTemp!=null) {
        url += '&statusId=' + statusIdTemp;
    }

    if (isNotEmpty($("#beginTime").val())) {
        url += '&startDate=' + beginTime1;//开始时间
    }
    if (isNotEmpty($("#endTime").val())) {
        url += '&endDate=' + endtime1;
    }
    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();
    $("#detailForm").modal('hide');

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
    $("#bel1").css("display", 'block');
    $("#closeRemarkText").val("");

}


function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/sysMessages";
    reloadTable(dataTable, '#message-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}