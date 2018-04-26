/**
 * Created by len on 2017/9/2.
 */
var dataTable = null;
var isBtn = '';
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var stationName = 0;
var treeMap = new Array();//初始查询所有数据
var stationNo = 0;
var corpId = "";
var corpIdi = "";
var checkEmployeeUserName ="";
$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    initCorp();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('ES_station');
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
    dataTable = $('#station-list').dataTable({
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
        "iDisplayLength": 20,
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        // "ajax": {
        //     "url": "/business-resource/stations?corpId=" + corpId,
        //     "dataSrc": "results"
        // },
        "columns": [{
            "data": "id"
        }, {
            "data": "stationNo"
        }, {
            "data": "stationName"
        }, {
            "data": "checkUserName"
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
            // {
            //     'targets': 3,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return null2black(row.stationNo)
            //     }
            // },
            {
                'targets': 3,
                'searchable': true,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.checkUserName)
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
    $('#station-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function chkClick() {
    $('#station-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function reloadDataTable(corpId) {
    var button = $("#div_button").html();
    var url = "/business-resource/stations?corpId=" + corpId;
    reloadTable(dataTable, '#station-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
    //dataTable.ajax.reload();
}

function initCorp() {
    treeData = new Array();
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var userCorpLevel = CONSTANTS.CORPLEVEL.FATHER;
            var data;
            for (var i = 0; i < retData.results.length; i++) {
                data = retData.results[i];
                if (data.corpLevel == CONSTANTS.CORPLEVEL.FATHER) {
                    userCorpLevel = CONSTANTS.CORPLEVEL.CHILDREN;
                }
            }
            if (userCorpLevel == CONSTANTS.CORPLEVEL.FATHER) {
                treeData.push({"id": data.id, "parent": "#", "text": data.corpName, state: {opened: true}});//加载父节点
                corpId = data.id;
            } else {
                for (var i = 0; i < retData.results.length; i++) {
                    var data = retData.results[i];
                    if (data.corpLevel == 0) {
                        treeData.push({"id": data.id, "parent": "#", "text": data.corpName, state: {opened: true}});//加载父节点
                        corpId = data.id;
                    }
                    else {
                        treeData.push({
                            "id": data.id,
                            "parent": data.corpLevel,
                            "text": data.corpName,
                            state: {opened: true}
                        });//加载子节点
                    }
                    treeMap.push(data);
                }
            }
            search(corpId);
            corpIdi = corpId;
            $('#corp-tree').jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    'force_text': true,
                    "themes": {"stripes": true},
                    'data': treeData
                },
                "types": {
                    "#": {"max_children": 1, "max_depth": 9, "valid_children": ["root"]},
                    "root": {"icon": "", "valid_children": ["default"]},
                    "default": {"icon": "/njpb/images/dep.png", "valid_children": ["default", "file"]},
                    "file": {"icon": "/njpb/images/dep.png", "valid_children": []}
                },
                "plugins": ["ui", "wholerow", "types"]
            }).bind("select_node.jstree", function (event, data) {
                select_node(event, data);
            }).bind("loaded.jstree", function (event, data) {
                var inst = data.instance;
                // var obj = inst.get_node(event.target);
                // inst.select_node(obj);
            });
        },
        failure: function () {
        }
    });
}

function select_node(e, d) {
    nodeId = d.node.id;
    // console.log(nodeId);
    search(nodeId);
    corpIdi = nodeId;
}

//根据公司查询站点
function search(corpId) {
    delflg = 0;
    var button = $("#div_button").html();
    var oldoptions = dataTable.fnSettings();
    var newoptions = $.extend(oldoptions, {
        "ajax": {
            url: "/business-resource/stations",
            data: {corpId: corpId,sort:"lastUpdateTime,desc"},
            dataSrc: 'results'
        },
        "iDisplayLength": Number($('select[name=user-list_length]').val()),
        fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            if (!isNotEmpty(aData)) {
                delflg = 0;
            }
            else {
                delflg = 1;
            }
        }
    })
    dataTable.fnDestroy();
    $('#station-list').dataTable(newoptions);
    addBtn();
    $('#div_button').append(button);
}

function openNew() {
    isBtn = 'save';

    $("#stationNo").val('');
    $("#stationName").val('');
    $("#selectCorp").val('');
    $("#longitude").val('');

    $("#latitude").val('');
    $("#remark").val('');
    $("#suggestId").val('');

    //审核员
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 7 +"&corpId=" + corpIdi,
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



    $("#bikeCount").attr("readOnly",false);
    $("#bikeCount").val('');
    getCorp();
    $("#myModalLabel").html("新建站点");
    $("#addStationForm").modal('show');

}

function openDialogDetail(domId, rowid) {
    stationId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;

    $("#stationNo").val(json.stationNo);
    $("#stationName").val(json.stationName);

    $("#bikeCount").attr("readOnly", "true");



    //审核员
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByRoleIdAndCorpId?roleId=' + 7+"&corpId=" + corpIdi,
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
            $("#checkEmployee").val(""+json.checkUserId).trigger("change");
        },
    });

    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var selectCorp = "<option id='-1' value='-1' json-data='-1'>请选择</option>";
            for (var i = 0; i < retData.results.length; i++) {
                // html += "<option id='shenf_" + retData.resultList[i].id + "' value=" + retData.resultList[i].id+ " json-data='" + JSON.stringify(retData.resultList[i]) + "'>" + retData.resultList[i].province + "</option>";
                selectCorp += "<option id='selectCorp" + retData.results[i].id + "' value=" + retData.results[i].id + " json-data='" + JSON.stringify(retData.results[i]) + "'>" + retData.results[i].corpName + "</option>";

            }
            $("#selectCorp").html(selectCorp);
            $("#selectCorp").select2();
            $("#selectCorp").val(json.projectId).trigger("change");
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });
    $("#longitude").val(json.longitude);
    $("#latitude").val(json.latitude);
    $("#bikeCount").val(json.estateCount);

    theLocation();

    $("#myModalLabel").html("站点详情");
    $("#addStationForm").modal('show');

}


//站点名称查重
function stationNameCheck() {
    $.ajax({
        type: 'GET',
        url: '/business-resource/stations/checkStationName?stationName=' + stationName,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.code > 0) {
                stationName = 1;
            }
            else {
                stationName = 0;
            }
        }, error: function (e) {
            stationName = 0;
        }
    });
}
//站点编号查重
function stationNoCheck() {
    $.ajax({
        type: 'GET',
        url: '/business-resource/stations/checkStationNo?stationNo=' + $("#stationNo").val(),
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.code > 0) {
                stationNo = 1;
            }
            else {
                stationNo = 0;
            }
        }, error: function (e) {
            stationNo = 0;
        }
    });
}

function save() {
    $("#dangerMsg").html('');

    if ('updata' == isBtn && $("#stationName").val() == rowJson.stationName) {
        stationName = 1;
    }
    if ('updata' == isBtn && $("#stationNo").val() == rowJson.stationNo) {
        stationNo = 1;
    }
    if (!verifyForm()) {
        return;
    }
var checkUserIdTemp="";
    if($("#checkEmployee").val()== -1){
        checkUserIdTemp = null;
        checkEmployeeUserName =  "";
    }else{
        checkUserIdTemp = $("#checkEmployee").val();
        checkEmployeeUserName =  $("#checkEmployee").find("option:selected").text();
    }
    var param = {
        stationNo: $("#stationNo").val(),
        stationName: $("#stationName").val(),
        projectId: $("#selectCorp").val(),
        longitude: $("#longitude").val(),
        latitude: $("#latitude").val(),
        estateCount: $("#bikeCount").val(),
        checkUserId: checkUserIdTemp,
    };
    // checkEmployeeUserName =  $("#checkEmployee").find("option:selected").text();
    if ('save' == isBtn) {
        $.ajax({
            type: 'POST',
            url: '/business-resource/stations',
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //新增
                if (data.code == 10000) {
                    reloadDataTable(param.projectId);
                    corpIdi = param.projectId;
                    clearMap();
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
            url: '/business-resource/stations/' + stationId,
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //数据修改
                if (data.code == 30000) {
                    console.log(data.message);
                    var myTable = $('#station-list').DataTable();

                    myTable.cell(rowNo, 1).data(param.stationNo);//更新行
                    rowJson.stationNo = param.stationNo;

                    myTable.cell(rowNo, 2).data(param.stationName);//更新行
                    rowJson.stationName = param.stationName;

                    myTable.cell(rowNo, 3).data(checkEmployeeUserName);//更新行
                    rowJson.checkUserName = checkEmployeeUserName;


                    $("#btnEdit_" + stationId).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''));
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
    $("#addStationForm").modal('hide');
}

function cancel() {

    $("#addStationForm").modal('hide');
    clearMap();
}
function clearMyMap() {
    clearMap();
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
                        url: '/business-resource/stations/deleteMore?ids=' + str,
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
                            reloadDataTable(corpIdi);
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
    if (!isNotEmpty($("#stationNo").val())) {
        layer.tips('不能为空', '#chkStationNo');
        return false;
    }
    if (!isNotEmpty($("#stationName").val())) {
        layer.tips('不能为空', '#chkStationName');
        return false;
    }
    if (!isNotEmpty($("#selectCorp").val())) {
        layer.tips('不能为空', '#chkCorp');
        return false;
    }

    if (stationNo == 0) {
        layer.tips('名称已经存在', '#chkStationNo');
        return false;
    }
    if (stationName == 0) {
        layer.tips('名称已经存在', '#chkStationName');
        return false;
    }
    return true;
}


function getCorp() {
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var selectCorp = "<option id='-1' value='' json-data='-1'>请选择</option>";
            for (var i = 0; i < retData.results.length; i++) {

                selectCorp += "<option id='selectCorp" + retData.results[i].id + "' value=" + retData.results[i].id + " json-data='" + JSON.stringify(retData.results[i]) + "'>" + retData.results[i].corpName + "</option>";

            }
            $("#selectCorp").html(selectCorp);
            $("#selectCorp").select2();


            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });
}


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
                    turnForm.action = "/business-resource/stationBarcodeImages/create?ids=" + str;
                    turnForm.submit();

                }
            }
        });
    }
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}