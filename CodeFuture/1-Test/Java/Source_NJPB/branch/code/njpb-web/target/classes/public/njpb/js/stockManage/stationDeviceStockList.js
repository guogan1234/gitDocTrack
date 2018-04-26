/**
 * Created by len on 2018/2/22.
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
var processUserIdTemp = null;
var stockWorkOrderTypeIdTemp =null;
var estateTypeIdTemp =null;
var projectIdTemp = null;
$(function () {
    pageSetUp();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('stationDeviceStockList');
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


    //配件类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];

            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#estateType").select2({
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

    var data = [];
    data.push({id: -1, text: "请选择"});
    data.push({id: 1, text: "领料"});
    data.push({id: 2, text: "归还"});

    $("#stockWorkOrderTypeId").select2({
        data: data,
        allowClear: true
    });
    $("#stockWorkOrderTypeId").val('-1').trigger("change");
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


    if ($("#stockWorkOrderTypeId").val() != -1) {
        pageParam.stockWorkOrderTypeId = $("#stockWorkOrderTypeId").val();//状态
    } else {
        pageParam.stockWorkOrderTypeId = null;
    }

    if ($("#estateType").val() != -1) {
        pageParam.estateTypeId = $("#estateType").val();//设备类型ID
    } else {
        pageParam.estateTypeId = null;
    }

    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endTime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }
    dataTable.ajax.reload()
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

                }
                param.orderDir = data.order[0].dir;
            }
            //排序
            param.sort = param.orderColumn + ',' + param.orderDir;
            // getQueryCondition: function (data) {
            //     var param = {};

            param.corpId = pageParam.corpId;
            param.estateTypeId = pageParam.estateTypeId;
            param.stockWorkOrderTypeId = pageParam.stockWorkOrderTypeId;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.partsType = 1;
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
                url: "/business-resource/stationEstateStockList/findByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log()
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
                    // var t = 0;
                    // for (var i = 0; i < allData.length; i++) {
                    //     t += allData[i].count;
                    // }
                    // $(dataTable.column(0).footer()).html("工单总数：");
                    // $(dataTable.column(7).footer()).html(t);
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
        // initComplete: function () {
        //     var api = this.api();
        //     var fda = api.column(7).data().reduce(function (a, b) {
        //             return (parseFloat(a) + parseFloat(b)).toFixed(7);
        //         }
        //     );
        //     var j = parseInt(fda);
        //     $(api.column(0).footer()).html("工单总数：");
        //     $(api.column(7).footer()).html(j);
        // },


        //列表表头字段
        columns: [
            {"data": "corpName"},
            {"data": "estateTypeName"},
            {"data": "stockWorkOrderTypeName"},
            {"data": "processUserName"},
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
                'targets': 1,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateTypeName);
                }
            },


            {
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    //编辑
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.corpId + ','  + row.estateTypeId + ',' + row.stockWorkOrderTypeId + ',' + row.processUserId + ',' + meta.row + ',' + row.id + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
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

function openDialogDetail(corpId, estateTypeId,stockWorkOrderTypeId, processUserId, rowid, domId) {

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
    urlTemp = "/business-resource/stationEstateStockListDetail/findByConditions?1=1&partsType=1";

    if (corpId != null) {
        urlTemp += "&corpId=" + corpId;
        projectIdTemp = corpId;
    }

    if (stockWorkOrderTypeId != null) {
        urlTemp += "&stockWorkOrderTypeId=" + stockWorkOrderTypeId;
        stockWorkOrderTypeIdTemp = stockWorkOrderTypeId;
    }

    if (estateTypeId != null) {
        urlTemp += "&estateTypeId=" + estateTypeId;
        estateTypeIdTemp = estateTypeId;
    }


    if (processUserId != null) {
        urlTemp += "&processUserId=" + processUserId;
        processUserIdTemp = processUserId;
    }


    if (isNotEmpty($("#beginTime").val())) {
        urlTemp += "&startDate=" + beginTime1;

    }
    if (isNotEmpty($("#endTime").val())) {
        urlTemp += "&endDate" + endtime1;//结束时间
    }

    loadWorkOrderHistories();

    $("#detailModalLabel").html("站点库存列表详情");
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
        columns: [
            {"data": "corpName"},
            {"data": "estateTypeName"},
            {"data": "count"},
            {"data": "stockWorkOrderTypeName"},
            {"data": "processUserName"},
            {"data": "applyTime"},
        ],
        "columnDefs": [

            {
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    var tm = null2black(row.applyTime);
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }


                }
            },

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

    var url = '/business-resource/stationEstateStockListDetailExport?1=1&partsType=1';
    if ( projectIdTemp!= null) {
        url += '&corpId=' + projectIdTemp;
    }

    if (stockWorkOrderTypeIdTemp!=null) {
        url += '&stockWorkOrderTypeId=' + stockWorkOrderTypeIdTemp;
    }
    if (estateTypeIdTemp!=null) {
        url += '&estateTypeId=' +estateTypeIdTemp;
    }
    if (processUserIdTemp!= null) {
        url += '&processUserId=' +processUserIdTemp;
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