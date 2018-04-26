/**
 * Created by len on 2017/9/7.
 */
var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var estateNameFlg = 0;

$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');

    initLeftActive('estateType_list');
})

/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#mytoolbox').append(dataPlugin);
}

/* DataTable 加载 */
function initDataTable() {
    dataTable = $('#estate-list').dataTable({
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
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": "/business-resource/estateTypes?category=" + 1,
            "dataSrc": "results"
        },
        "columns": [{
            "data": "id"
        }, {
            "data": "name"
        }, {
            "data": "partsType"
        }, {
            "data": "workpoints"
        }],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input onclick="chkClick()"  type="checkbox" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            },

            {
                'targets': 2,
                'searchable': true,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    if (row.partsType == 1) {
                        return CONSTANTS.PARTTYPE.STATION;
                    } else {
                        return CONSTANTS.PARTTYPE.BICYCLE;
                    }
                }
            }, {
                'targets': 3,
                'searchable': true,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.workpoints);
                }
            },
            {
                'targets': 4,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]
    });
    //复选框全选控制
    var active_class = 'active';
    $('#estate-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function chkClick() {
    $('#estate-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

/*打开新建页面*/
function openNew() {
    isBtn = 'save';
    rowJson = new Object();

    $('#chkPartsType').css("display","block");
    $('#chkPartsType2').css("display","none") ;

    var data = [];
    data.push({id: 1, text: "站点"});
    data.push({id: 2, text: "自行车"});

    $("#partsType").select2({
        data: data,
        placeholder: '请选择',
        allowClear: true
    });

    $("#estateTypeName").val('');
    $("#workpoints").val('');
    $("#myModalLabel").html("新建设备类型");
    $("#addEstateManageForm").modal('show');
}

function openDialogDetail(domId, rowid) {
    logicalId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;


    $("#estateTypeName").val(json.name);

    // $("#estateTypeName").css("")
    // $("#partsType").val(json.partsType);

    // var data = [];
    // data.push({id: 1, text: "站点"});
    // data.push({id: 2, text: "车辆"});
    //
    // $("#partsType").select2({
    //     data: data,
    //     placeholder: '请选择',
    //     allowClear: true
    // });
    // $("#partsType").val(json.partsType).trigger("change");


    $('#chkPartsType').css("display","none");
    $('#chkPartsType2').css("display","block") ;

    if (json.partsType == 1) {
        $('#partsType2').val("站点");
    } else {
        $('#partsType2').val("车辆");
    }


    $("#workpoints").val(json.workpoints);

    $("#myModalLabel").html("设备类型详情");
    $("#addEstateManageForm").modal('show');
}

function estateNameCheck() {
    if ('updata' == isBtn && $("#estateName").val() == rowJson.estateName) {
        estateNameFlg = 1;
    }
    else {
        $.ajax({
            type: 'GET',
            url: '/business-resource/estateTypes/checkByEstateTypeNameAndCategory?estateTypeName=' + $("#estateTypeName").val() + '&category=' + 1,
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (data) {
                console.log(data.message);
                if (data.code > 0) {
                    estateNameFlg = 1;
                }
                else {
                    estateNameFlg = 0;
                }
            }, error: function (e) {
                estateNameFlg = 0;
            }
        });
    }
}

function verifyForm() {

    var regmcz = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
    var regmcy = /[a-zA-Z]/;

    if (!isNotEmpty($("#estateTypeName").val())) {
        layer.tips('不能为空', '#estateTypeName');
        return false;
    }

    if (estateNameFlg == 0) {
        layer.tips('名称已经存在', '#chkEstateName');
        return false;
    }
    if (!isNotEmpty($("#partsType").val())) {
        layer.tips('不能为空', '#chkPartsType');
        return false;
    }

    if($("#workpoints").val()>10 || $("#workpoints").val()< 0 ){
        layer.tips('部件类型工分最大10分，最小0分', '#chkWorkpoints');
        return false;
    }
    if( $("#workpoints").val().indexOf(".")>0){
        layer.tips('部件类型工分不可以是小数', '#chkWorkpoints');
        return false;
    }

    return true;
}

function verifyForm2() {

    var regmcz = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
    var regmcy = /[a-zA-Z]/;

    if (!isNotEmpty($("#estateTypeName").val())) {
        layer.tips('不能为空', '#estateTypeName');
        return false;
    }

    if (estateNameFlg == 0) {
        layer.tips('名称已经存在', '#chkEstateName');
        return false;
    }
    if (!isNotEmpty($("#partsType2").val())) {
        layer.tips('不能为空', '#chkPartsType');
        return false;
    }
    if($("#workpoints").val()>10 || $("#workpoints").val()< 0 ){
        layer.tips('设备类型工分最大10分，最小0分', '#chkWorkpoints');
        return false;
    }
    if( $("#workpoints").val().indexOf(".")>0){
        layer.tips('设备类型工分不可以是小数', '#chkWorkpoints');
        return false;
    }
    return true;
}


function clearData() {
    $("#estateTypeName").val('');
    $("#workpoints").val('');
}


function save() {
    $("#dangerMsg").html('');
    if ('updata' == isBtn && $("#estateTypeName").val() == rowJson.name) {
        estateNameFlg = 1;
    }

    // if (!verifyForm()) {
    //     return;
    // }
    var param = rowJson;
    param.name = $("#estateTypeName").val();

    param.workpoints = null2black($("#workpoints").val());
    param.category = 1;
    console.log(param);
    if ('save' == isBtn) {

        if (!verifyForm()) {
            return;
        }
        param.partsType = $("#partsType").val();
        $.ajax({
            type: 'POST',
            url: '/business-resource/estateTypes',
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                if (data.code == 10000) {
                    reloadDataTable();
                    clearData();
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
    else {
        if (!verifyForm2()) {
            return;
        }
        if($("#partsType2").val()=="站点"){
            param.partsType = 1;
        }else{
            param.partsType = 2;
        }

        $.ajax({
            type: 'PUT',
            url: '/business-resource/estateTypes/' + logicalId,
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //数据修改
                console.log(data.message);
                if (data.code == 30000) {
                    var myTable = $('#estate-list').DataTable();
                    myTable.cell(rowNo, 1).data(param.name);//更新行
                    rowJson.name = param.name;

                    myTable.cell(rowNo, 2).data(param.partsType);//更新行
                    rowJson.partsType = param.partsType;

                    myTable.cell(rowNo, 3).data(param.workpoints);//更新行
                    rowJson.workpoints = param.workpoints;

                    $("#btnEdit_" + logicalId).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''));

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
    $("#addEstateManageForm").modal('hide');
}

function cancel() {
    $("#addEstateManageForm").modal('hide');
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
                        url: '/business-resource/estateTypes/batchDelete?ids=' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 20000) {
                                reloadDataTable();
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
            }
        });
    }
}
function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/estateTypes?category=" + 1;
    reloadTable(dataTable, '#estate-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}