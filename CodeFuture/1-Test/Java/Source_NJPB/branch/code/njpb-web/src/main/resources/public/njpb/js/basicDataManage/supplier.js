/**
 * Created by len on 2017/9/7.
 */
var dataTable = null;
var isBtn = '';
var supplierId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json

var supplierNameflg = 0;
$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('supplier_list');
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
    dataTable = $('#supplier-list').dataTable({
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
            "url": "/business-resource/estateSuppliers",
            "dataSrc": "results"
        },
        "columns": [{
            "data": "id"
        }, {
            "data": "supplierName"
        }, {
            "data": "supplierAddr"
        }, {
            "data": "supplierTel"
        }],
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
    $('#supplier-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function chkClick() {
    $('#supplier-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/estateSuppliers";
    reloadTable(dataTable, '#supplier-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
    //dataTable.ajax.reload();
}

function openNew() {

    isBtn = 'save';
    $("#myModalLabel").html("新建供应商");
    $("#addSupplierFrom").modal('show');
    $("#supplierName").val('');
    $("#place").val('');
    $("#phone").val('');
    //tipLogger();

}

function openDialogDetail(domId, rowid) {
    supplierId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;

    $("#supplierName").val(json.supplierName);
    $("#place").val(json.supplierAddr);
    $("#phone").val(json.supplierTel);
    
    $("#myModalLabel").html("供应商详情");
    $("#addSupplierFrom").modal('show');
}



function supplierNameCheck() {
    if ('updata' == isBtn && $("#supplierName").val() == rowJson.supplierName) {
        supplierNameflg = 1;
    }
    else {
        $.ajax({
            type: 'GET',
            url: '/business-resource/estateSuppliers/findBySupplierName?supplierName=' + $("#supplierName").val(),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                if (data.code == 40000) {
                    supplierNameflg = 0;
                }
                else {
                    supplierNameflg = 1;
                }
            }, error:function(e){
                supplierNameflg = 1;
        }
        });
    }
}


function save() {
    $("#dangerMsg").html('');
    if ('updata' == isBtn && $("#supplierName").val() == rowJson.supplierName) {
        supplierNameflg = 1;
    }
    if (!verifyForm()) {
        return;
    }
    var param = {
        supplierName: $("#supplierName").val(),
        supplierAddr: $("#place").val(),
        supplierTel: $("#phone").val(),
    }
    if ('save' == isBtn) {
        $.ajax({
            type: 'POST',
            url: '/business-resource/estateSuppliers',
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //新增
                if (data.code == 10000) {
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
    else {
        $.ajax({
            type: 'PUT',
            url: '/business-resource/estateSuppliers/' + supplierId,
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //数据修改
                if (data.code == 30000) {
                    console.log(data.message);
                    var myTable = $('#supplier-list').DataTable();

                    myTable.cell(rowNo, 1).data(param.supplierName);//更新行
                    rowJson.supplierName = param.supplierName;


                    myTable.cell(rowNo, 2).data(param.supplierAddr);//更新行
                    rowJson.supplierAddress = param.supplierAddr;

                    myTable.cell(rowNo, 3).data(param.supplierTel);//更新行
                    rowJson.supplierPhone = param.supplierTel;


                    $("#btnEdit_" + supplierId).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''));
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
    $("#addSupplierFrom").modal('hide');
}

function cancel() {
    $("#addSupplierFrom").modal('hide');
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
            title: '提示',
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
                        url: '/business-resource/estateSuppliers/deleteMore?ids=' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 20000) {
                                $('.alert-success').show();
                                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                            }
                            else {
                                $("#dangerMsg").html(data.responseJSON.message);
                                $('.alert-danger').show();
                                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                            }
                            reloadDataTable();
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


function verifyForm() {
    var regphone = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    var regFZF = /[\d|A-z|\u4E00-\u9FFF]/;
    var reg = /^[0-9]*$/;

    if (!isNotEmpty($("#supplierName").val())) {
        layer.tips('不能为空', '#chkSupplierName');
        return false;
    }
    if (supplierNameflg == 0) {
        layer.tips('名称已经存在', '#chkSupplierName');
        return false;
    }
    if (regFZF.test($("#supplierName").val()) == false) {
        layer.tips('含有特殊字符', '#chkSupplierName');
        return false;
    }


    if (!isNotEmpty($("#place").val())) {
        layer.tips('不能为空', '#chkPlace');
        return false;
    }
    if (!isNotEmpty($("#phone").val())) {
        layer.tips('不能为空', '#chkPhone');
        return false;
    }
    if (regphone.test($("#phone").val()) == false) {
        layer.tips('请填写正确的电话格式', '#chkPhone');
        return false;
    }
    return true;
}

function tipLogger() {
    BootstrapDialog.show({
        title: '提示',
        message: "我很不爽",
        type: BootstrapDialog.TYPE_SUCCESS, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
        closable: true, // <-- Default value is false
        draggable: true, // <-- Default value is false

        // setTimeout("style": "display:none", ALERTMESSAGE),
        buttons: [{
            label: '确定',
            cssClass: 'btn-danger',
            //btn-success   btn-pink   btn-purple
            //btn-danger   btn-inverse
            action: function (dialogItself) {
                dialogItself.close();

            }
        }]
    });

}