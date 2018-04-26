/**
 * Created by len on 2018/1/26.
 */

var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var allData = null;
var pfIds = '';
var USERNAME = "";
var pageParam = {};
var corpId = "";
var estateTypeName = "";
var estateTypeIdDetail = "";
var temp =0;

$(function () {
    pageSetUp();

    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('report_bike_repair');
    initSelect();


})

$(function () {
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByUserId?userId=' + USER_ID,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.resultEntity != null) {
                var rr = retData.resultEntity;
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
            data.push({id: -1, text: "请选择"});
            pageParam.corpId = retData.results[0].id;
            var t =  dateToStr(new Date(), 'YYYY/MM/DD');
            var t2 = t+" "+"00:00:00";
            pageParam.beginTime = new Date(t2);
            var t3 =  dateToStr(new Date(), 'YYYY/MM/DD hh:mm:ss');
            pageParam.endTime = t3;

            $("#beginTime").val(t);
            $("#endtime").val(t);
            initDataTable();
            addBtn();


            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#selectCorp").select2({
                    data: data,
                    // placeholder: '请选择',
                    allowClear: true
                });
                $("#selectCorp").val("-1").trigger("change");
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
    });

    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?partsType=2&category=2',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {

            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#moduleTypeId").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#moduleTypeId").val("-1").trigger("change");

        },
    });


    //故障类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/faultType?partsType=' + 2,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#faultType").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#faultType").val("-1").trigger("change");
        },
    });


}

function addBtn() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '<button type="button" class="btn btn-primary" onclick="exportExcel()">批量导出</button>&nbsp'+

        '</div> ';
    $('#reportBtns').html(dataPluginCD);
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
                    data.push({id: retData.results[i].id + 1, text: retData.results[i].name});
                }
                $("#workOrderEstateType").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });
            },

        });
    }
}

function doRepairSearch() {
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";
    if ($("#selectCorp").val() == null || $("#selectCorp").val() < 0 || $("#selectCorp").val() == "") {
        layer.tips('请选择公司', '#chkCorp');
        return;
    } else {
        pageParam.corpId = $("#selectCorp").val();//公司
    }
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if ($("#estateTypeId").val() != null && $("#estateTypeId").val() > 0 && $("#estateTypeId").val() != "") {
        pageParam.estateTypeId = $("#estateTypeId").val();//状态
    }
    if ($("#faultType").val() != null && $("#faultType").val() > 0 && $("#faultType").val() != "") {
        pageParam.faultTypeId = $("#faultType").val();//状态
    }

    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endtime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }

    dataTable.ajax.reload();
    addBtn();
    //  var oldoptions = dataTable.fnSettings();
    // var newoptions = $.extend(oldoptions, {
    //     "ajax" : {
    //         url: "/business-resource/estateFaultStatistics/findByConditions",
    //         data:{corpId: pageParam.corpId, estateTypeId:pageParam.estateTypeId,startDate:pageParam.beginTime,endDate: pageParam.endTime,reportWay:1},
    //         dataSrc: 'results'
    //     },
    //     "iDisplayLength" : Number($('select[name=stationEstatesFault-list_length]').val()),
    //     fnRowCallback: function(nRow, aData, iDisplayIndex, iDisplayIndexFull)    {
    //     }
    // })
    // dataTable.fnDestroy();
    // $('#stationEstatesFault-list').dataTable(newoptions);
}

function detailCancel() {
    $("#detailForm").modal('hide');
}

/* DataTable 加载 */
function initDataTable() {
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
            param.estateTypeId = pageParam.estateTypeId;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.reportWay = 2;
            param.typeId=1;
            param.faultTypeId = pageParam.faultTypeId;
            return param;
        }
    };

    dataTable = $('#stationEstatesFault-list').dataTable({
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
                url: "/business-resource/stationFaultStatistics/findByConditions",
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
                    for(var  i =0;i<allData.length;i++){
                        t += allData[i].count;
                    }
                    $(dataTable.column(0).footer()).html("故障总数：");
                    $(dataTable.column(3).footer()).html(t);
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


        //列表表头字段
        columns: [
            {"data": "corpName"},
            {"data": "estateTypeName"},
            {"data": "faultTypeName"},
            {"data": "count"},
        ],

        /* 故障数-统计*/
        // initComplete: function () {
        //     var api = this.api();
        //     var fda = api.column(3).data().reduce(function (a, b) {
        //             return (parseFloat(a) + parseFloat(b)).toFixed(2);
        //         }
        //     );
        //     var j= parseInt(fda);
        //     $(api.column(0).footer()).html("故障总数：");
        //     $(api.column(3).footer()).html(j);
        // },






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
            // {
            //     'targets': 2,
            //     'orderable': false,
            //     'searchable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return null2black(row.count);
            //     }
            // },
            // {
            //     'targets': 3,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         //编辑
            //         return '<button id="btnEdit_' + row.estateTypeId + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
            //             + '" data-toggle="modal" onclick="openDialogDetail(' + row.projectId + ',' + row.estateTypeId + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
            //     }
            // }

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


// function search() {
//
//
//     var url = "/business-resource/users?corpId=" + corpId;
//     reloadTable(dataTable, '#stationEstatesFaultDetail-list', url, 'results');
//     // var oldoptions = dataTable.fnSettings();
//     // var newoptions = $.extend(oldoptions, {
//     //     "ajax" : {
//     //         url: "/business-resource/stockWorkOrderDetails/findConditions",
//     //         data:{corpId:$("#selectCorp").val(), category:category,estateTypeId:estateTypeId,stockWorkOrderTypeId:stockWorkOrderTypeId},
//     //         dataSrc: 'results'
//     //     },
//     //     "iDisplayLength" : Number($('select[name=kcmx-list_length]').val()),
//     //     fnRowCallback: function(nRow, aData, iDisplayIndex, iDisplayIndexFull)    {
//     //     }
//     // })
//     // dataTable.fnDestroy();
//     // $('#kcmx-list').dataTable(newoptions);
//
// }


function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

function exportExcel() {
    var beginTime1 = $("#beginTime").val() + " " + "00:00:00";
    var endtime1 = $("#endtime").val() + " " + "23:59:59";

    // if(!isNotEmpty($("#selectCorp").val())){
    //     layer.tips('请先选择公司', '#chkCorp');
    //     return;
    // }
    var url = '/business-resource/stationFaultStatisticsExport?reportWay=2&typeId=1';

    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if ($("#selectCorp").val()!= -1) {
        url += '&corpId=' + $("#selectCorp").val();
    }else{
        url += '&corpId='+pageParam.corpId;
    }

    if (isNotEmpty($("#estateTypeId").val()) && $("#estateTypeId").val()!=-1) {
        url += '&estateTypeId=' + $("#estateTypeId").val();
    }

    if ($("#faultType").val()!=-1) {
        url += '&faultTypeId=' + $("#faultType").val();
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


function changeFaultType() {

    var estateTypeId = $("#estateTypeId").val();

    var url = '/business-resource/faultType?partsType=' + 1;

    if (estateTypeId != "-1") {
        url += "&estateTypeId=" + estateTypeId;
    }

    //故障类型
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            $("#faultType").select2().empty();
            data.push({id: -1, text: "请选择"});
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#faultType").select2({
                data: data,
                // placeholder: '请选择',
                allowClear: true
            });
            $("#faultType").val("-1").trigger("change");
        },
    });


}