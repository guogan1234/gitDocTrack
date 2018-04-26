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



$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initDataTableDetails();
    initLeftActive('report_bike');
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
function addBtn() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#reportBtns').append(dataPluginCD);
}

function initSelect() {
    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?partsType='+2 ,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#estateTypeId").select2({
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
    var beginTime1 = $("#beginTime").val()+" "+"00:00:00";
    var endtime1= $("#endtime").val()+" "+"23:59:59";

    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if ($("#estateTypeId").val() != null && $("#estateTypeId").val() > 0 && $("#estateTypeId").val() != "") {
        pageParam.estateTypeId = $("#estateTypeId").val();//状态
    }else{
        pageParam.estateTypeId = null;
    }
    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endtime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }

    dataTable.ajax.reload();

}


function initDataTableDetails() {
    var $wrapper = $('#div-table-container');
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //         //组装排序参数
            //       //  if (data.order && data.order.length && data.order[0]) {
            //       //       switch (data.order[0].column) {
            //       //           case 2:
            //       //               param.orderColumn = "serialNo";
            //       //               break;
            //       //           case 3:
            //       //               param.orderColumn = "reportTime";
            //       //               break;
            //       //           case 4:
            //       //               param.orderColumn = "organizationName";
            //       //               break;
            //       //           case 5:
            //       //               param.orderColumn = "estateTypeName";
            //       //               break;
            //       //           case 6:
            //       //               param.orderColumn = "orderTypeNameCn";
            //       //               break;
            //       //           case 7:
            //       //               param.orderColumn = "faultDescriptionNameCn";
            //       //               break;
            //       //           case 8:
            //       //               param.orderColumn = "workOrderStatusNameCn";
            //       //               break;
            //       //           case 9:
            //       //               param.orderColumn = "reportEmployeeUserName";
            //       //               break;
            //       //           case 10:
            //       //               param.orderColumn = "assignEmployeeUserName";
            //       //               break;
            //       //           case 11:
            //       //               param.orderColumn = "repairEmployeeUserName";
            //       //               break;
            //       //           default:
            //       //               param.orderColumn = "serialNo";
            //       //               break;
            //       //       }
            //       //       param.orderDir = data.order[0].dir;
            //       //   }
            //         //排序
            //         // param.sort = param.orderColumn + ',' + param.orderDir;
            //组装查询参数
            param.estateTypeId = pageParam.estateTypeId;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.reportWay = 2;

            return param;
        }
    };
    dataTable_details = $('#bikeFaultDetail-list').dataTable({
        "searching": true,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        "oLanguage": {
            "sProcessing": "处理中...",
            "sLoadingRecords": "载入中...",
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
        "iDisplayLength": 5,
        "aaSorting": [[10, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/estateFaultStatisticDetails/findByConditions",
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
            // {"data": "estateSn"},
            {"data": "bikeFrameNo"},
            {"data": "estateName"},
            {"data": "supplierName"},
            {"data": "stationName"},
            {"data": "faultDescription"},
            {"data": "repairRemark"},
            {"data": "workOrderStatusNameCn"},
            {"data": "reportEmployeeUserName"},
            {"data": "reportTime"},
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
            //     {
            //         'targets': 1,
            //         'orderable': false,
            //         'searchable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.serialNo);
            //         }
            //     },
            //     {
            //         'targets': 2,
            //         'orderable': false,
            //         'searchable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.estateSn);
            //         }
            //     },
            //     {
            //         'targets': 3,
            //         'searchable': false,
            //         'orderable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.estateName);
            //         }
            //     },{
            //         'targets': 4,
            //         'searchable': false,
            //         'orderable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            // return null2black(row.stationName);
            //         }
            //     },
            //     {
            //         'targets': 5,
            //         'orderable': false,
            //         'searchable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.corpName);
            //         }
            //     },
            //     {
            //         'targets': 6,
            //         'orderable': false,
            //         'searchable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.faultDescription);
            //         }
            //     },
            //     {
            //         'targets': 7,
            //         'searchable': false,
            //         'orderable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.repairRemark);
            //         }
            //     },
            //     {
            //         'targets': 8,
            //         'searchable': false,
            //         'orderable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.workOrderStatusNameCn);
            //         }
            //     },
            //     {
            //         'targets': 9,
            //         'searchable': false,
            //         'orderable': false,
            //         'className': 'dt-body-center',
            //         'bSortable': false,
            //         'render': function (data, type, row, meta) {
            //             return null2black(row.reportEmployeeUserName);
            //         }
            //     },
            {
                'targets': 10,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.reportTime);
                    if(isNotEmpty(tm)){
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:ss:mm')
                    }
                }
            }]

    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#bikeFaultDetail-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function  detailCancel() {
    $("#detailForm").modal('hide');
}

/* DataTable 加载 */
function initDataTable() {
    var t =  dateToStr(new Date(), 'YYYY/MM/DD');
    var t2 = t+" "+"00:00:00";
    pageParam.beginTime = new Date(t2);
    var t3 =  dateToStr(new Date(), 'YYYY/MM/DD hh:mm:ss');
    pageParam.endTime = t3;

    $("#beginTime").val(t);
    $("#endtime").val(t);
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //         //组装排序参数
            //       //  if (data.order && data.order.length && data.order[0]) {
            //       //       switch (data.order[0].column) {
            //       //           case 2:
            //       //               param.orderColumn = "serialNo";
            //       //               break;
            //       //           case 3:
            //       //               param.orderColumn = "reportTime";
            //       //               break;
            //       //           case 4:
            //       //               param.orderColumn = "organizationName";
            //       //               break;
            //       //           case 5:
            //       //               param.orderColumn = "estateTypeName";
            //       //               break;
            //       //           case 6:
            //       //               param.orderColumn = "orderTypeNameCn";
            //       //               break;
            //       //           case 7:
            //       //               param.orderColumn = "faultDescriptionNameCn";
            //       //               break;
            //       //           case 8:
            //       //               param.orderColumn = "workOrderStatusNameCn";
            //       //               break;
            //       //           case 9:
            //       //               param.orderColumn = "reportEmployeeUserName";
            //       //               break;
            //       //           case 10:
            //       //               param.orderColumn = "assignEmployeeUserName";
            //       //               break;
            //       //           case 11:
            //       //               param.orderColumn = "repairEmployeeUserName";
            //       //               break;
            //       //           default:
            //       //               param.orderColumn = "serialNo";
            //       //               break;
            //       //       }
            //       //       param.orderDir = data.order[0].dir;
            //       //   }
            //         //排序
            //         // param.sort = param.orderColumn + ',' + param.orderDir;
            //组装查询参数
            param.estateTypeId = pageParam.estateTypeId;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.reportWay = 2;

            return param;
        }
    };
    dataTable = $('#bikeFault-list').dataTable({
        //"searching": false,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        // "oLanguage": {
        //     "sProcessing": "<span style='color:#ff0000;'><img src='/njpb/images/loading.gif'></span>",
        //     "sLoadingRecords": "载入中...",
        //     "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
        //     "sLengthMenu": "每页 _MENU_ 条记录",
        //     'sZeroRecords': '没有数据',
        //     'sInfo': '第 _PAGE_ 页 / 总 _PAGES_ 页,共 _TOTAL_项',
        //     'sInfoEmpty': '没有数据',
        //     'sInfoFiltered': '(过滤总件数 _MAX_ 条)',
        //     "oPaginate": {
        //         "sPrevious": "上一页",
        //         "sNext": "下一页"
        //     },
        //     "oAria": {
        //         "sSortAscending": ": 以升序排列此列",
        //         "sSortDescending": ": 以降序排列此列"
        //     }
        // },

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
                url: "/business-resource/estateFaultStatistics/findByConditions",
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

                },
                error:function(e){
                    if(e.responseJSON .code == -40000){
                        var returnData = {};
                        returnData.data = null;
                        callback(returnData);
                    }
                }
            });
        },


        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "estateTypeName"},
            {"data": "count"},
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
            {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    //编辑
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail('+ row.estateTypeId + ',' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]

    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#bikeFault-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}





function openDialogDetail( typeId,domId, rowid) {

    logicalId = domId;
    rowNo = rowid;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(rowJson);
    pageParam.estateTypeId = typeId;

    dataTable_details.ajax.reload();
    $("#detailModalLabel").html("车辆故障明细");
    $("#detailForm").modal('show');
}

// function search() {
//
//
//     var url = "/business-resource/users?corpId=" + corpId;
//     reloadTable(dataTable, '#bikeFaultDetail-list', url, 'results');
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


function chkplClick() {
    $('#workOrderDetail> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}
function exportExcel() {

    var beginTime1 = $("#beginTime").val()+" "+"00:00:00";
    var endtime1= $("#endtime").val()+" "+"23:59:59";
    var url = '/business-resource/estateFault/exportFaultStatistics?reportWay=2';
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    // if(isNotEmpty($("#selectCorp").val()))
    // {
    //     url += '&corpId='+$("#selectCorp").val();
    // }else{
    //     url += '&corpId='+ corpId;
    // }

    if(isNotEmpty($("#estateTypeId").val()))
    {
        url += '&estateTypeId='+$("#estateTypeId").val();
    }
    if(isNotEmpty($("#beginTime").val()))
    {
        url += '&startDate='+ beginTime1;
    }
    if(isNotEmpty($("#endtime").val()))
    {
        url += '&endDate='+endtime1;
    }

    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();
}
