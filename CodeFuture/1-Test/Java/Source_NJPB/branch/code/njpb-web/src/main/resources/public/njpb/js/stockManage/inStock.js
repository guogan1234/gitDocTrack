/**
 * Created by len on 2017/9/13.
 */
var dataTable_stock = null;

var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var pageParam = {};
var chkModuleCountTag = 0;
var corpId ="";
// var url = "/business-resource/stockRecords/findConditions?corpId=" + corpId;


$(function () {
    pageSetUp();

    //initDataTable();
    initSelect();
    //addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('inStock_list');
})

/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '<button type="button" class="btn btn-success" onclick="openNew()">新建</button>&nbsp'+
        '<button type="button" class="btn btn-primary" onclick="exportExcel()">批量导出</button>&nbsp'
        '</div> ';
    $('#top').html(dataPlugin);
}

//查询
function search() {
    var corp = null;
    var category = null;
    var estateTypeId = null;
    var url = "/business-resource/stockRecords/findConditions";
    if($("#selectCorp").val() == null || $("#selectCorp").val() ==""){
        layer.tips('不能为空', '#cheCorp');
        return ;

    }
        if ($("#selectCorp").val() > 0  && $("#selectCorp").val() != 1) {
            corp = "corpId=" + $("#selectCorp").val();
            url += "?" + corp;
        }



    if ($("#category").val() != "") {
        category = "category=" + $("#category").val();
        if (corp == null) {
            url += "?" + category;
        } else {
            url += "&" + category;
        }

    }
    if ($("#estateTypeId").val() > 0) {
        estateTypeId = "estateTypeId=" + $("#estateTypeId").val();

        if (corp == null && category == null) {
            url += "?" + estateTypeId;
        } else {
            url += "&" + estateTypeId;
        }
    }

    var button = $("#div_button").html();
    reloadTable(dataTable_stock, '#inStock-list', url, 'results');

    addBtn();
    $('#div_button').html(button);



}


function selectCategory() {
    if( $("#category").val()!="" && $("#category").val()!=null){
        var t =$("#category").val();
        //配件类型
        $.ajax({
            type: 'GET',
            url: '/business-resource/estateTypes?partsType='+ $("#category").val(),
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
            },
        });
    }
}
function reloadTableSearch(corpId) {
    var url = "/business-resource/stockRecords/findConditions?corpId="+corpId;
    var button = $("#div_button").html();
    reloadTable(dataTable_stock, '#inStock-list', url, 'results');
    addBtn();
    $('#div_button').html(button);
}
/* DataTable 加载 */
function initDataTable() {
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            //if (data.order&&data.order.length&&data.order[0]) {
               // switch (data.order[0].column) {
            //         // case 1:
            //         //     param.orderColumn = "projectTitle";
            //         //     break;
            //         case 2:
            //             param.orderColumn = "projectNo";
            //             break;
            //         case 3:
            //             param.orderColumn = "estateSn";
            //             break;
            //         case 4:
            //             param.orderColumn = "estateName";
            //             break;
            //         // case 5:
            //         //     param.orderColumn = "materialCodeName";
            //         //     break;
            //         // case 6:
            //         //     param.orderColumn = "batchName";
            //         //     break;
            //         case 7:
            //             param.orderColumn = "estateTypeName";
            //             break;
            //         case 8:
            //             param.orderColumn = "estateStatusName";
            //             break;
            //         // case 9:
            //         //     param.orderColumn = "qualityDate";
            //         //     break;
            //         // case 10:
            //         //     param.orderColumn = "qualityDay";
            //         //     break;
            //         // default:
            //         //     param.orderColumn = "projectTitle";
            //         //     break;
               // }
            //param.orderDir = data.order[0].dir;
            //}
            //排序
            //param.sort = param.orderColumn + ',' + param.orderDir;
            //组装查询参数
            // param.projectId = pageParam.projectId;//项目名称
            //param.materialCodeId = pageParam.materialCodeId;// 物料代码
            //param.batchId = pageParam.batchId;// 批次号
            // param.estateTypeId = pageParam.estateTypeId;// 设备类型ID
            param.corpId = pageParam.corpId;
            param.category = pageParam.category;//类别
            param.estateTypeId = pageParam.estateTypeId;

            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTable_stock = $('#inStock-list').dataTable({
        "processing": true,
        "serverSide": false,
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
        "iDisplayLength": 10,
        "aaSorting": [[5, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#top'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        // "ajax": {
        //     "url": "/business-resource/stockRecords/findConditions",
        //     "dataSrc": "results"
        // },

        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/stockRecords/findConditions?corpId=" + corpId,
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


        "columns": [{
            "data": "id"
        }, {
            "data": "category"
        }, {
            "data": "estateTypeName"
        }, {
            "data": "count"
        }, {
            "data": "corpName"
        }, {
            "data": "createName"
        }, {
            "data": "createTime"
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
                    return '<input type="checkbox"  onclick="chkClick()" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            }, {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    if (row.category == 1) {
                        return CONSTANTS.PARTTYPE.STATION;
                    } else {
                        return CONSTANTS.PARTTYPE.BICYCLE;
                    }

                }
            }, {
                'targets': 6,
                'searchable': true,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': true,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.createTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }
                    return "";
                }
            }]


    });
    //复选框全选控制
    var active_class = 'active';
    $('#inStock-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });




}

function chkClick() {
    $('#inStock-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}
function initSelect() {
    //配件类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes' ,
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
            corpId = retData.results[0].id;

            initDataTable();
            addBtn();
            pageParam.corpId = corpId;
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

    var data = [];
    data.push({id: 1, text: "站点"});
    data.push({id: 2, text: "车辆"});

    $("#category").select2({
        data: data,
        placeholder: '请选择',
        allowClear: true
    });
}
function checkModuleCount() {

    if(Number($("#moduleCount").val()) > 10000){
        layer.tips("数量过大，请小于10000","#chkModuleCount");
        // chkModuleCountTag = 1;
        //layer.tips('不能为空', '#chkSelectModule');
    }else{
        chkModuleCountTag =1;
    }

}

function openNew() {
    isBtn = 'save';
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
            $("#selectModule").select2({
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
    $("#moduleCount").val("");
    //$("#moduleCategory").val("");
    var data = [];
    data.push({id: 1, text: "站点"});
    data.push({id: 2, text: "车辆"});

    $("#moduleCategory").select2({
        data: data,
        placeholder: '请选择',
        allowClear: true
    });

//查询公司
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            }
            $("#stockCorp").select2({
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

    $("#inStock").html("库存入库");
    $("#inStockForm").modal('show');
}

function inStockSave() {
    $("#dangerMsg").html('');
    if (!inStockCheck()) {
        return;
    }


    var param = {
        estateTypeId: $("#selectModule").val(),
        count: $("#moduleCount").val(),
        corpId: $("#stockCorp").val(),
    }
    console.log(param);

    if ('save' == isBtn) {
        $.ajax({
            type: 'POST',
            url: '/business-resource/stockRecords' ,
            dataType: 'JSON',
            data: JSON.stringify(param),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 10000) {
                    reloadTableSearch(param.corpId);
                    $('.alert-success').show();
                    setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                } else {
                    $("#dangerMsg").html(data.responseJSON.message);
                    $('.alert-danger').show();
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            },error : function (data) {
                $("#dangerMsg").html(data.responseJSON.message);
                $('.alert-danger').show();
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        });
    }
    else {
        rowJson.userAccount = param.userAccount;
        rowJson.userName = param.userName;
        //rowJson.userPassword = param.userPassword;
        rowJson.userPhone = param.userPhone;
        rowJson.corpId = param.corpId;
        rowJson.userEmail = param.userEmail;
        rowJson.userQq = param.userQq;
        rowJson.userWechart = param.userWechart;
        rowJson.expiryTime = param.expiryTime;
        $.ajax({
            type: 'PUT',
            url: '/business-resource/users/' + userId + '?roleIds=' + $("#selectRole").val(),
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(rowJson),
            success: function (data) {
                console.log(data.massage);
                if (data.code == 30000) {
                    //rowJson.roleIds = data.results.roleIds;
                    //数据修改
                    var myTable = $('#user-list').DataTable();

                    myTable.cell(rowNo, 1).data(rowJson.userAccount);//更新行

                    myTable.cell(rowNo, 2).data(rowJson.userName);//更新行

                    myTable.cell(rowNo, 3).data(rowJson.userEmail);//更新行

                    myTable.cell(rowNo, 4).data(rowJson.userPhone);//更新行

                    myTable.cell(rowNo, 5).data(rowJson.expiryTime);//更新行

                    $("#btnEdit_" + data.result.id).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''))
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
    }
    $("#inStockForm").modal('hide');
}

function inStockCancel() {
    $("#inStockForm").modal('hide');
}


function tableReload() {
    var button = $("#div_button").html();
    var oldoptions = dataTable.fnSettings();
    var newoptions = $.extend(oldoptions, {
        "ajax": {
            "url": "/stock-resource/inventories",
            dataSrc: 'results'
        },
        "iDisplayLength": Number($('select[name=inStock-list_length]').val()),
        fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        }
    })
    dataTable.fnDestroy();
    $('#inStock-list').dataTable(newoptions);
    addBtn();
    $('#div_button').append(button);
}

function inStockCheck() {
    if(chkModuleCountTag ==0){
        layer.tips("数量过大，请小于10000","#chkModuleCount");
        return false;
    }

    if (!isNotEmpty($("#selectModule").val()) || $("#selectModule").val() == -1 || $("#selectModule").val() == 0) {
        layer.tips('不能为空', '#chkSelectModule');
        return false;
    }
    if (!isNotEmpty($("#moduleCategory").val()) || $("#moduleCategory").val() == -1 || $("#ckbh").val() == 0) {
        layer.tips('不能为空', '#chkModuleCategory');
        return false;
    }
    if (!isNotEmpty($("#stockCorp").val()) || $("#stockCorp").val() == -1 || $("#stockCorp").val() == 0) {
        layer.tips('不能为空', '#chkStockCorp');
        return false;
    }
    if (!isNotEmpty($("#moduleCount").val())) {
        layer.tips('不能为空', '#chkModuleCount');
        return false;
    }

    return true;
}



function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

function exportExcel() {

    var url = '/business-resource/stock/exportStock?1=1';
    // if (!isNotEmpty($("#selectCorp").val())) {
    //     layer.tips('请先选择公司', '#cheCorp');
    //     return;
    // }

    if (isNotEmpty($("#selectCorp").val())) {
        if($("#selectCorp").val() != 1){
            url += '&corpId=' + $("#selectCorp").val();
        }
    } else {
        if (corpId != 1) {
            url += '&projectId=' + corpId;
        }
    }

    if (isNotEmpty($("#category").val())) {
        url += '&category=' + $("#category").val();
    }

    if (isNotEmpty($("#estateTypeId").val())) {
        url += '&estateTypeId=' + $("#estateTypeId").val();
    }

    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();
}
