/**
 * Created by len on 2017/9/2.
 */


var dataTable = null;
var count = 50;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var barCodeUnusedIds = new Map();
var pageParam = {};

$(function () {
    pageSetUp();
    // addBtnused()
    initDataTable();
    addBtn();
    initDataTableUnused();
    addBtnUnused();
    initSelect();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('ES_barCode');
})

function addBtnUnused() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#barCodeUnusedBtns').append(dataPluginCD);
}

// function addBtnused() {
//     var dataPluginCDt =
//         '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
//
//         '</div> ';
//     $('#barCodeBtns').html(dataPluginCDt);
// }
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '<button type="button" class="btn btn-primary" onclick="printBarCode()">打印二维码</button>&nbsp'+

    '</div> ';
    $('#top').html(dataPlugin);
}


//打开新建
function openNewBarCode() {
    rowJson = new Object();
    $("#barCodeNo").val("");
    $("#myModalLabel").html("新建条码");
    $("#addBarCodeForm").modal('show');
}

function save() {
    $("#dangerMsg").html('');
    if (!addVerifyFormUnused()) {
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/business-resource/estateBarCodes/produceByCount?count='+$("#barCodeNo").val(),
        dataType: 'JSON',
        // data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                searchUnused();
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
            }
            else {
                $("#dangerMsg").html(data.responseJSON.message);
                $('.alert-danger').show();
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        },
        error: function (data) {
            $("#dangerMsg").html(data.responseJSON.message);
            $('.alert-danger').show();
            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
        }
    });
    $("#addBarCodeForm").modal('hide');


}

function cancel(){
    $("#addBarCodeForm").modal('hide');
    $("#addBarCodeForm").empty();
}


function addVerifyFormUnused() {

    if (!isNotEmpty($("#barCodeNo").val())) {
        layer.tips('不能为空', '#chkBarCodeNo');
        return false;
    }

    if ($("#barCodeNo").val() < 0 || $("#barCodeNo").val() > count) {
        layer.tips('数量大于0 ，且要小于等于50', '#chkBarCodeNo');
        return false;
    }


    return true;
}

//查询
function search() {
    pageParam.corpId = $("#selectCorp").val();//公司
    pageParam.stationId = $("#stationId").val();// 站点
    pageParam.estateTypeId = $("#estateTypeId").val();// 设备类型
    dataTable.ajax.reload();
}


function searchUnused() {
    dataTableUnused.ajax.reload();
    chkUnusedClick();

    barCodeUnusedIds = new Map();
}

/* DataTable 加载 */
function initDataTable() {
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 2:
                        param.orderColumn = "barCodeSn";
                        break;
                    // case 2:
                    //     param.orderColumn = "bikeFrameNo";
                    //     break;
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
            param.corpId = pageParam.corpId;
            param.stationId = pageParam.stationId;
            param.category = 0;//类别
            param.estateTypeId = pageParam.estateTypeId;
            param.relation = 1;
            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTable = $('#barCodeUsed-list').dataTable({
        "destroy": true,
        "searching": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
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
        "iDisplayLength": 20,
        "aaSorting": [[2, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#top'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/estateBarCodes/findUsedByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.recordsTotal = result.page.totalElements;
                    returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "estateNo"},
            {"data": "barCodeSn"},
            {"data": "estateTypeName"},
            {"data": "estateName"},
            {"data": "stationNoName"},
            {"data": "corpName"}
        ],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input onclick="chkClick()" type="checkbox" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            }, {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = row.estateNo;
                    return null2black(tm);
                }
            },{
                'targets': 2,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var temp = row.barCodeSn;
                    if(isNotEmpty(temp)){
                        return temp;
                    }else{
                        return null2black(row.bicycleStakeBarCode);
                    }
                }
            },{
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateTypeName);

                }
            }, {
                'targets': 4,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.estateName);

                }
            },{
                'targets': 5,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = row.stationNoName;
                    return null2black(tm);
                }
            },{
                'targets': 6,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = row.corpName;
                    return null2black(tm);
                }
            }
        ]
    }).api();

    //复选框全选控制
    // $("#checkAll").on("click", function () {
    //     if ($(this).prop("checked") === true) {
    //
    //         for (var i = 0; i < $("input[name='ids']").length; i++) {
    //             sbIds.put("ids" + $("input[name='ids']")[i].value, $("input[name='ids']")[i].value);
    //         }
    //
    //         $("input[name='ids']").prop("checked", $(this).prop("checked"));
    //         $('#barCodeUsed-list tbody tr').addClass('active');
    //     } else {
    //
    //         for (var i = 0; i < $("input[name='ids']").length; i++) {
    //             sbIds.remove("ids" + $("input[name='ids']")[i].value);
    //         }
    //
    //         $("input[name='ids']").prop("checked", false);
    //         $('#barCodeUsed-list tbody tr').removeClass('active');
    //     }
    // });

    var active_class = 'active';
    $('#barCodeUsed-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


/* DataTable 加载 */
function initDataTableUnused() {
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};

            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTableUnused = $('#barCodeUnused-list').dataTable({
        "destroy": true,
        "searching": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
        "oLanguage": {
            "sProcessing": "<span style='color:#ff0000;'><img src='/njpb/images/loading.gif'></span>",
            "sLoadingRecords": "载入中...",
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有数据",
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
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#barCodeUnusedBtns'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            chkUnusedClick();
            //ajax请求数据
            //var colSort = "sort=" + param.orderColumn + ',' + param.orderDir;
            $.ajax({
                type: "GET",
                url: "/business-resource/estateBarCodes/findNotUsed",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.recordsTotal = result.page.totalElements;
                    returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "barCodeSn"},
            // {"data": "createBy"},
            {"data": "createTime"}
        ],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    if (isNotEmpty(barCodeUnusedIds.get("unUsedIds" + row.id))) {
                        return '<input onclick="chkUnusedClick(' + row.id + ')" type="checkbox" name="unUsedIds" checked="true"  value=' + row.id + ' id="barCodeUnusedIds' + row.id + '"  class="ace"/>';
                    }
                    else {
                        return '<input onclick="chkUnusedClick(' + row.id + ')" type="checkbox" name="unUsedIds" value=' + row.id + ' id="barCodeUnusedIds' + row.id + '"  class="ace"/>';
                    }
                }
            },{
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.barCodeSn);

                }
            },{
        'targets': 2,
            'searchable': false,
            'orderable': false,
            'className': 'dt-body-center',
            'bSortable': false,
            'render': function (data, type, row, meta) {
                var tm = null2black(row.createTime)
                if (isNotEmpty(tm)) {
                    return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                }
                return "";
        }
    }]

    }).api();

    $("#checkAll").on("click", function () {
        if ($(this).prop("checked") === true) {

            for (var i = 0; i < $("input[name='unUsedIds']").length; i++) {
                barCodeUnusedIds.put("unUsedIds" + $("input[name='unUsedIds']")[i].value, $("input[name='unUsedIds']")[i].value);
            }

            $("input[name='unUsedIds']").prop("checked", $(this).prop("checked"));
            $('#barCodeUnused-list tbody tr').addClass('active');
        } else {

            for (var i = 0; i < $("input[name='unUsedIds']").length; i++) {
                barCodeUnusedIds.remove("unUsedIds" + $("input[name='unUsedIds']")[i].value);
            }

            $("input[name='unUsedIds']").prop("checked", false);
            $('#barCodeUnused-list tbody tr').removeClass('active');
        }
    });



}

function chkClick() {
    $('#barCodeUsed-list> thead > tr > th input[type=checkbox]').prop('checked', false);

}


function initSelect() {
    //站点
    $("#stationId").select2({
        language: {
            inputTooShort: function (args) {
                // args.minimum is the minimum required length
                // args.input is the user-typed text
                return "请输入查询关键字";
            },
            inputTooLong: function (args) {
                // args.maximum is the maximum allowed length
                // args.input is the user-typed text
                return "You typed too much";
            },
            errorLoading: function () {
                return "加载错误";
            },
            loadingMore: function () {
                return "加载更多";
            },
            noResults: function () {
                return "没有查到结果";
            },
            searching: function () {
                return "查询中...";
            },
            maximumSelected: function (args) {
                // args.maximum is the maximum number of items the user may select
                return "加载错误";
            }
        },
        theme: "bootstrap",
        allowClear: true,
        placeholder: "请选择",
        ajax: {
            type: 'GET',
            url: '/business-resource/stations/findByStationNoNameLike',
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    stationNoName: params.term, // search term
                    page: params.page,
                    size: 30
                };
            },
            processResults: function (data, params) {
                // parse the results into the format expected by Select2
                // since we are using custom formatting functions we do not need to
                // alter the remote JSON data, except to indicate that infinite
                // scrolling can be used
                // params.page = params.page || 0;

                return {
                    results: data.results,
                    pagination: {
                        more: (params.page * 30) < data.totalElements
                    }
                };
            },
            cache: true
        },
        escapeMarkup: function (markup) {
            return markup;
        }, // let our custom formatter work
        minimumInputLength: 1,
        templateResult: function (repo) {
            if (repo.loading)
                return repo.text;
            var markup = "<div class='select2-result-repository__description' id='s_pch_option_"
                + repo.id
                + "' >"
                + repo.stationNoName + "</div>";

            return markup;
        }, // omitted for brevity, see the source of this page
        templateSelection: function (repo) {
            return repo.stationNoName;
        } // omitted for brevity, see the source of this page
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

    //查询公司
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];

            if(retData.results.length>1){
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#selectCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });

            }else{
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


function chkUnusedClick(val) {
    $('#barCodeUnused-list> thead > tr > th input[type=checkbox]').prop('checked', false);

    if ($("#barCodeUnusedIds" + val).is(':checked')) {
        barCodeUnusedIds.put("unUsedIds" + val, val);
    }
    else {
        barCodeUnusedIds.remove("unUsedIds" + val);
    }
}



function exportBarCodeExcel() {
    if (barCodeUnusedIds.size() == 0) {
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
        var str = new Array();
        var array = barCodeUnusedIds.keySet();
        for (var i in array) {
            var key = array[i];
            str.push(barCodeUnusedIds.get(key));
        }
        var turnForm = document.createElement("form");
        document.body.appendChild(turnForm);

        turnForm.method = 'post';
        turnForm.action = '/business-resource/estateBarCodes/exportNotUsed?barCodeIds=' + str;
        turnForm.submit();
    }
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}
// function printBarCode(){
//     // var param = {};
//     // param.corpId = $("#selectCorp").val();//公司
//     // param.stationId =  $("#stationId").val();// 站点
//     // param.category = 0;//类别
//     // param.estateTypeId = $("#estateTypeId").val();// 设备类型
//     // param.relation = 1;
//     $("#dangerMsg").html('');
//
//     var turnForm = document.createElement("form");
//     document.body.appendChild(turnForm);
//     turnForm.method = 'post';
//     turnForm.action = "/business-resource/barcodeImages/create?category=0&relation=1&estateTypeId=2";
//     turnForm.submit();
//
//
// }

function printBarCode() {



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
            title: '打印二维码确认',
            message: '是否确认打印二维码?',
            type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: '取消', // <-- Default value is 'Cancel',
            btnOKLabel: '确定', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function (result) {
                // result will be true if button was click, while it will be false if users close the dialog directly.
                if (result) {
                    var turnForm = document.createElement("form");
                    document.body.appendChild(turnForm);
                    turnForm.method = 'post';
                    turnForm.action = "/business-resource/barcodeImages/create?ids=" + str;
                    turnForm.submit();


                }
            }
        });
    }
}