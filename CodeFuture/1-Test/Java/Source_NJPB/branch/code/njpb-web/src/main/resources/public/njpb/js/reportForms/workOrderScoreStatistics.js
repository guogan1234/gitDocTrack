/**
 * Created by len on 2017/10/10.
 */
/**
 * Created by len on 2017/9/19.
 */
var dataTable = null;
var dataTable_details = null;
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var allData = null;
var USERNAME = "";
var pageParam = {};
var corpId = "";


$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('report_score');
    initSelect();
})

$(function () {
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByUserId?userId=' + USER_ID,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.result != null) {
                var rr = retData.result;
                USERNAME = rr.userName;
                console.log(USERNAME);
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
            corpId = retData.results[0].id;
            selectUser();
            //selectuserName();
            // for (var i = 0; i < retData.results.length; i++) {
            //     data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            // }
            // $("#selectCorp").select2({
            //     data: data,
            //     placeholder: '请选择',
            //     allowClear: true
            // });
            //
            // $.fn.modal.Constructor.prototype.enforceFocus = function () {
            // };

            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#selectCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });

            } else {
                data.push({id: retData.results[0].id, text: retData.results[0].corpName});
                $("#selectCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });
                $("#selectCorp").val(retData.results[0].id).trigger("change");
            }

        },
        failure: function () {
        }
    });

}

function chkUser() {
    corpId = $("#selectCorp").val();
    selectUser();
}
// function selectUser() {
//     var url = "";
//     if(corpId == 1 || corpId == ""){
//         url = '/business-resource/users/findByCorpId?corpId='+ 1;
//     }else if(corpId >1){
//         url = '/business-resource/stations/findByStationNoNameLike?corpId=' + corpId;
//     }else{
//         layer.tips("请选择公司","#chkCorp");
//         return;
//     }
//     //站点
//     // $("#stationId").select2({
//     //     language: {
//     //         inputTooShort: function (args) {
//     //             // args.minimum is the minimum required length
//     //             // args.input is the user-typed text
//     //             return "请输入查询关键字";
//     //         },
//     //         inputTooLong: function (args) {
//     //             // args.maximum is the maximum allowed length
//     //             // args.input is the user-typed text
//     //             return "You typed too much";
//     //         },
//     //         errorLoading: function () {
//     //             return "加载错误";
//     //         },
//     //         loadingMore: function () {
//     //             return "加载更多";
//     //         },
//     //         noResults: function () {
//     //             return "没有查到结果";
//     //         },
//     //         searching: function () {
//     //             return "查询中...";
//     //         },
//     //         maximumSelected: function (args) {
//     //             // args.maximum is the maximum number of items the user may select
//     //             return "加载错误";
//     //         }
//     //     },
//     //     theme: "bootstrap",
//     //     allowClear: true,
//     //     placeholder: "请选择",
//     //     ajax: {
//     //         type: 'GET',
//     //         url: url,
//     //         dataType: 'json',
//     //         delay: 250,
//     //         data: function (params) {
//     //             return {
//     //                 stationNoName: params.term, // search term
//     //                 page: params.page,
//     //                 size: 30
//     //             };
//     //         },
//     //         processResults: function (data, params) {
//     //             // parse the results into the format expected by Select2
//     //             // since we are using custom formatting functions we do not need to
//     //             // alter the remote JSON data, except to indicate that infinite
//     //             // scrolling can be used
//     //             // params.page = params.page || 0;
//     //
//     //             return {
//     //                 results: data.results,
//     //                 pagination: {
//     //                     more: (params.page * 30) < data.totalElements
//     //                 }
//     //             };
//     //         },
//     //         cache: true
//     //     },
//     //     escapeMarkup: function (markup) {
//     //         return markup;
//     //     }, // let our custom formatter work
//     //     minimumInputLength: 1,
//     //     templateResult: function (repo) {
//     //         if (repo.loading)
//     //             return repo.text;
//     //         var markup = "<div class='select2-result-repository__description' id='s_pch_option_"
//     //             + repo.id
//     //             + "' >"
//     //             + repo.stationNoName + "</div>";
//     //
//     //         return markup;
//     //     }, // omitted for brevity, see the source of this page
//     //     templateSelection: function (repo) {
//     //         return repo.stationNoName;
//     //     } // omitted for brevity, see the source of this page
//     // });
// }

function selectUser() {
    //查询操作人
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByCorpId?corpId=' + corpId,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            corpId = retData.results[0].id;
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].userName});
            }
            $("#userName").select2({
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


function doSearch() {
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if ($("#selectCorp").val() != null && $("#selectCorp").val() > 0 && $("#selectCorp").val() != "") {
        pageParam.corpId = $("#selectCorp").val();//状态
    } else {
        pageParam.corpId = null;
    }


    if ($("#userName").val() != null && $("#userName").val() > 0 && $("#userName").val() != "") {
        pageParam.userName = $("#userName").val();//状态
    } else {
        pageParam.userName = null;
    }
    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endtime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }
    dataTable.ajax.reload();
}


function detailCancel() {
    $("#detailFormScore").modal('hide');
}

function addBtn() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#reportBtns').append(dataPluginCD);
}

function initDataTable() {
    var $wrapper = $('#div-table-container');
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    // case 1:
                    //     param.orderColumn = "serialNo";
                    //     break;
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
                            case 7:
                                param.orderColumn = "lastUpdateTime";
                                break;
                    //         case 8:
                    //             param.orderColumn = "workOrderStatusNameCn";
                    //             break;
                    // case 9:
                    //     param.orderColumn = "reportTime";
                    //     break;
                    //         case 10:
                    //             param.orderColumn = "assignEmployeeUserName";
                    //             break;
                    //         case 11:
                    //             param.orderColumn = "repairEmployeeUserName";
                    //             break;
                    default:
                        param.orderColumn = "serialNo";
                        break;
                }
                param.orderDir = data.order[0].dir;
            }
            //排序
            param.sort = param.orderColumn + ',' + param.orderDir;
            //组装查询参数
            param.corpId = pageParam.corpId;
            param.workOrderStatusId = 900;
            param.repairEmployee = pageParam.userName;
            param.beginTime = pageParam.beginTime;
            param.endDate = pageParam.endTime;

            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTable = $('#workOrderScore-list').dataTable({
        // "searching": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
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
        "aaSorting": [[1, 'desc']],
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
                url: "/business-resource/workOrders/findByConditions",
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
            {"data": "reportWay"},
            {"data": "estateName"},
            {"data": "workOrderScore"},
            {"data": "corpName"},
            {"data": "repairEmployeeUserName"},
            {"data": "lastUpdateTime"},
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
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
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
                    var tm = null2black(row.reportWay);
                    if (tm == 1) {
                        return "站点";
                    } else if (tm == 2) {
                        return "车辆";
                    } else {
                        return "";
                    }
                }
            }, {
                'targets': 3,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateName);

                }
            }, {
                'targets': 4,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.workOrderScore);

                }
            },
            {
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.corpName);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return "";
                    }

                }
            },
            {
                'targets': 6,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.repairEmployeeUserName);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return "";
                    }

                }
            },
            {
                'targets': 7,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.lastUpdateTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return "";
                    }

                }
            },
            {
                'targets': 8,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.orderTypeId + ',' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }


        ]
    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#workOrderScore-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function chkClick() {
    $('#workOrderScore-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function openDialogDetail(typeId, domId, rowid) {

    workOrderId = domId;
    rowNo = rowid;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(rowJson);
    $("#one").css("width", "20px");

    $("#serialNo").attr("value",null2black(json.serialNo));
    //$("#serialNo").val(null2black(json.serialNo));


    // $("#reportType").val(json.workOrderTypeNameCn);
    $("#reportType").attr("value",null2black(json.workOrderTypeNameCn));


    // $("#reportEmployee").val(null2black(json.reportEmployeeUserName));
    $("#reportEmployee").attr("value",null2black(json.reportEmployeeUserName));

    // $("#reportTime").val(dateToStr(new Date(null2black(json.reportTime)), 'YYYY-MM-DD HH:mm:ss'));
    $("#reportTime").attr("value",dateToStr(new Date(null2black(json.reportTime)), 'YYYY-MM-DD HH:mm:ss'));

    // $("#statusName").val(null2black(json.workOrderStatusNameCn));
    $("#statusName").attr("value",null2black(json.workOrderStatusNameCn));

    // $("#faultDescription").val(null2black(json.faultDescription));
    $("#faultDescription").attr("value",null2black(json.faultDescription));

    // $("#stationName").val(null2black(json.stationName));
    $("#stationName").attr("value",null2black(json.stationName));

    // $("#stationNo").val(null2black(json.stationNo));
    $("#stationNo").attr("value",null2black(json.stationNo));


    // $("#longitude").val(null2black(json.longitude));
    // $("#latitude").val(null2black(json.latitude));
    //$("#createTime").val(null2black(json.createTime));
    //$("#workOrderReportWay").val(null2black(json.reportWay));

    if (json.reportWay == 1) {
        // $("#workOrderReportWay").val("站点报修");
        $("#workOrderReportWay").attr("value","站点报修");
    } else {
        // $("#workOrderReportWay").val("车辆报修");
        $("#workOrderReportWay").attr("value","车辆报修");
    }

    // $("#maintainRemark").val(null2black(json.maintainRemark));
    $("#maintainRemark").attr("value",null2black(json.maintainRemark));

    // $("#corpName").val(null2black(json.corpName));
    $("#corpName").attr("value",null2black(json.corpName));

    // $("#bikeFrameNo").val(null2black(json.bikeFrameNo));
    $("#bikeFrameNo").attr("value",null2black(json.bikeFrameNo));

    // $("#assignEmployeeUserName").val(null2black(json.assignEmployeeUserName));
    $("#assignEmployeeUserName").attr("value",null2black(json.assignEmployeeUserName));

    if (isNotEmpty(json.assignTime)) {
        // $("#assignTime").val(dateToStr(new Date(json.assignTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#assignTime").attr("value",dateToStr(new Date(json.assignTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#assignTime").val("");
        $("#assignTime").attr("value","");
    }
    // $("#assignTime").val(dateToStr(new Date(null2black(json.assignTime)), 'YYYY-MM-DD HH:mm:ss'));


    // $("#assignRemark").val(null2black(json.assignRemark));
    $("#assignRemark").attr("value",null2black(json.assignRemark));


    // $("#level").val(null2black(json.level));
    $("#level").attr("value",null2black(json.level));

    // $("#repairEmployeeUserName").val(null2black(json.repairEmployeeUserName));
    $("#repairEmployeeUserName").attr("value",null2black(json.repairEmployeeUserName));


    // $("#repairConfirmTime").val(dateToStr(new Date(null2black(json.repairConfirmTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairStartTime").val(dateToStr(new Date(null2black(json.repairStartTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairEndTime").val(dateToStr(new Date(null2black(json.repairEndTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairRemark").val(null2black(json.repairRemark));
    $("#repairRemark").attr("value",null2black(json.repairRemark));


    repairConfirmTime = json.repairConfirmTime;
    if (isNotEmpty(json.repairConfirmTime)) {
        // $("#repairConfirmTime").val(dateToStr(new Date(json.repairConfirmTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairConfirmTime").attr("value",dateToStr(new Date(json.repairConfirmTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairConfirmTime").val("");
        $("#repairConfirmTime").attr("value","");
    }
    if (isNotEmpty(json.repairStartTime)) {
        // $("#repairStartTime").val(dateToStr(new Date(json.repairStartTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairStartTime").attr("value",dateToStr(new Date(json.repairStartTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairStartTime").val("");
        $("#repairStartTime").attr("value","");
    }
    repairEndTime = json.repairEndTime;
    if (isNotEmpty(json.repairEndTime)) {
        // $("#repairEndTime").val(dateToStr(new Date(json.repairEndTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairEndTime").attr("value",dateToStr(new Date(json.repairEndTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#repairEndTime").val("");
        $("#repairEndTime").attr("value","");
    }

    // $("#reponseOverTime").val(dateToStr(new Date(null2black(json.reponseOverTime)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairOverTime").val(dateToStr(new Date(null2black(json.repairOverTime)), 'YYYY-MM-DD HH:mm:ss'));

    if (isNotEmpty(json.reponseOverTime)) {
        //$("#reponseOverTime").val(dateToStr(new Date(json.reponseOverTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#reponseOverTime").attr("value",dateToStr(new Date(json.reponseOverTime), 'YYYY-MM-DD HH:mm:ss'));
    } else {
        // $("#reponseOverTime").val("");
        $("#reponseOverTime").attr("value","");
    }
    if (isNotEmpty(json.repairOverTime)) {
        // $("#repairOverTime").val(dateToStr(new Date(json.repairOverTime), 'YYYY-MM-DD HH:mm:ss'));
        $("#repairOverTime").attr("value",dateToStr(new Date(json.repairOverTime), 'YYYY-MM-DD HH:mm:ss'));
        //var overTime = repairEndTime - json.repairOverTime
        //$("#repairOverTime").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
    } else {
        // $("#repairOverTime").val("");
        $("#repairOverTime").attr("value","");
    }


    // $("#backRemark").val(null2black(json.backRemark));
    $("#backRemark").attr("value",null2black(json.backRemark));

    // $("#estateNo").val(null2black(json.estateNo));
    $("#estateNo").attr("value",null2black(json.estateNo));

    // $("#estateName").val(null2black(json.estateName));
    $("#estateName").attr("value",null2black(json.estateName));
    //$("#category").val(null2black(json.category));
    if (null2black(json.category) == 0) {
        // $("#category").val("站点");
        $("#category").attr("value","站点");
    } else {
        // $("#category").val("车辆");
        $("#category").attr("value","车辆");
    }

    if (json.estateSn != null) {
        // $("#estateSn").val(null2black(json.estateSn));
        $("#estateSn").attr("value",null2black(json.estateSn));
    } else if (json.bicycleStakeBarCode != null) {
        // $("#estateSn").val(null2black(json.bicycleStakeBarCode));
        $("#estateSn").attr("value",null2black(json.bicycleStakeBarCode));
    } else {
        // $("#estateSn").val("");
        $("#estateSn").attr("value","");
    }


    // $("#supplierName").val(null2black(json.supplierName));
    $("#supplierName").attr("value",null2black(json.supplierName));

    // $("#estateTypeName").val(null2black(json.estateTypeName));
    $("#estateTypeName").attr("value",null2black(json.estateTypeName));
    // $("#responseTimeOutDate").val(dateToStr(new Date(null2black(json.responseTimeOutDate)), 'YYYY-MM-DD HH:mm:ss'));
    // $("#repairTimeOutDate").val(dateToStr(new Date(null2black(json.repairTimeOutDate)), 'YYYY-MM-DD HH:mm:ss'));
    if (isNotEmpty(json.responseTimeOutDate)) {
        //$("#responseTimeOutDate").val(dateToStr(new Date(json.responseTimeOutDate), 'YYYY-MM-DD HH:mm:ss'));
        var overTime = repairConfirmTime - json.responseTimeOutDate;
        // $("#responseTimeOutDate").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
        $("#responseTimeOutDate").attr("value",dateToStr(new Date(overTime), 'HH:mm:ss'));

    } else {
        // $("#responseTimeOutDate").val("");
        $("#responseTimeOutDate").attr("value","");
    }
    if (isNotEmpty(json.repairTimeOutDate)) {
        //$("#repairTimeOutDate").val(dateToStr(new Date(json.repairTimeOutDate), 'YYYY-MM-DD HH:mm:ss'));
        var overTime = repairEndTime - json.repairTimeOutDate
        // $("#repairTimeOutDate").val(dateToStr(new Date(overTime), 'HH:mm:ss'));
        $("#repairTimeOutDate").attr("value",dateToStr(new Date(overTime), 'HH:mm:ss'));
    } else {
        // $("#repairTimeOutDate").val("");
        $("#repairTimeOutDate").attr("value","");
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

    if(json.estateTypeId != 7){
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
                    html += '<span class="col-sm-2"> <img src= ' + reportPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/></span>';
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
                    html += '<span class="col-sm-2">  <img src= ' + repairPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/></span>';


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
    $("#detailFormScore").modal('show');


}

function loadWorkOrderHistories() {
    dataTable2 = $('#workOrderDetail').dataTable({
        "destroy": true,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        "oLanguage": {
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            "sLengthMenu": "每页 _MENU_ 条记录",
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
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": "/business-resource/workOrderHistories/findByWorkOrderId?workOrderId=" + workOrderId,
            "dataSrc": "results"
        },
        "columns": [{
            "data": "userName"
        }, {
            "data": "txt"
        }, {
            "data": "operationRemark"
        }, {
            "data": "lastOperationTime"
        }, {
            "data": "operationTime"
        }, {
            "data": "workOrderScore"
        }, {
            "data": "workOrderScoreDeduct"
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
            }, {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.operationRemark);
                }
            }, {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.lastOperationTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }
                }
            }, {
                'targets': 4,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.operationTime);
                    if (tm.indexOf('days') > 0) {
                        tm = tm.replace('days', '天');
                        return tm;
                    } else if (tm.indexOf('day') > 0) {
                        tm = tm.replace('day', '天');
                        return tm;
                    } else {
                        return tm;
                    }

                }
            }, {
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.workOrderScore);
                    if (isNotEmpty(tm)) {
                        return tm;
                    } else {
                        return 0;
                    }
                }
            }, {
                'targets': 6,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.workOrderScoreDeduct);
                    if (isNotEmpty(tm)) {
                        return tm;
                    } else {
                        return 0;
                    }
                }
            }


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


function printOut() {
    $("#workOrderStatisticsPrint").jqprint({
        debug : false,
        importCSS : true,
        printContainer : true,
        operaSupport : true
        }

    );
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

function exportExcel() {

    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";

    // if (!isNotEmpty($("#selectCorp").val())) {
    //     layer.tips('请先选择公司', '#chkCorp');
    //     return;
    // }

    var url = '/business-resource/workOrderScore/exportWorkOrder?1=1';
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if (isNotEmpty($("#selectCorp").val())) {
        if($("#selectCorp").val() != 1){
            url += '&corpId='+$("#selectCorp").val();
        }
    } else {
        if (corpId != 1) {
            url += '&projectId=' + corpId;
        }
    }

    if (isNotEmpty($("#userName").val())) {
        url += '&repairEmployee=' + $("#userName").val();
    }
    if (isNotEmpty($("#beginTime").val())) {
        url += '&beginTime=' + beginTime1;
    }
    if (isNotEmpty($("#endtime").val())) {
        url += '&endTime=' + endtime1;
    }

    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();

}
