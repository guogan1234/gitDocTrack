var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var pageParam = {};
var corpId = "";
var allData = null;

$(function () {
    pageSetUp();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('stationDeviceStock_list');
    initSelect();
    // initDataTable();
    addBtn();
});

function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '<button type="button" class="btn btn-primary" onclick="exportExcel()">批量导出</button>&nbsp'
    '</div> ';
    $('#top').html(dataPlugin);
}

//查询条件
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
            pageParam.corpId = corpId;
            initDataTable();

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

}

//查询
function search() {


    var selectCorpId = $("#selectCorp").val();      //所属公司
    var startDate = $("#beginTime").val() + "/01";
    var estateTypeId = $("#estateType").val();      //配件类型

    if ($("#selectCorp").val() != null && $("#selectCorp").val() > 0) {
        pageParam.corpId = selectCorpId;  //公司
    }

    if ($("#estateType").val() != null && $("#estateType").val() > 0) {
        pageParam.estateType = estateTypeId;  //配件
    }

    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = startDate;    //开始时间
    }
    dataTable.ajax.reload();

}
/* DataTable 加载 */
function initDataTable() {

    if (pageParam.beginTime == null) {
        pageParam.beginTime = dateToStr(new Date(), 'YYYY/MM/DD')
    }

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
            // param.sort = ""+ corpId + ',' +"desc";
            param.partsType = 1;
            param.corpId = pageParam.corpId;
            // param.estateTypeId = pageParam.estateType;
            param.startDate = pageParam.beginTime;
            return param;
        }
    };

    dataTable = $('#stationDeviceStock-list').dataTable({
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
                url: "/business-resource/stationEstateStockStatistics/findByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    var returnData = {};

                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);


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
            {
                "data": "corpName"
            }, {
                "data": "estateTypeName"
            }, {
                "data": "lastMonthCount"
            }, {
                "data": "thisMonthInStockCount"
            }, {
                "data": "thisMonthOutStockCount"
            }, {
                "data": "thisMonthStockCount"
            }
        ],
        "columnDefs": []

    }).api();


}


function chkClick() {
    $('#stationDeviceStock-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}





