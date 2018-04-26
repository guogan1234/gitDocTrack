/**
 * Created by len on 2017/9/7.
 */
var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var treeData = new Array();//树所需数据
var treeMap = new Array();//初始查询所有数据
var allData = new Map();//初始查询所有数据

var nodeId = '';
var checkAccount = 0;
var checkCorp = 1;
var corpId = "";
var corpIdAll = "";
var userId = "";

$(function () {
    pageSetUp();
    initCorp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');

    initLeftActive('user_list');
})

/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#mytoolbox').append(dataPlugin);
}

//公司树初始化
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
            corpIdAll=retData.results[0].id;
            for (var i = 0; i < retData.results.length; i++) {
                 data = retData.results[i];
                if (data.corpLevel == CONSTANTS.CORPLEVEL.FATHER) {
                    userCorpLevel = CONSTANTS.CORPLEVEL.CHILDREN;
                }
            }
            if(userCorpLevel == CONSTANTS.CORPLEVEL.FATHER){
                treeData.push({"id": data.id,  "parent": "#", "text": data.corpName, state: {opened: true}});//加载父节点
                corpId =  data.id;
            }else {
                for (var i = 0; i < retData.results.length; i++) {
                    var data = retData.results[i];
                    if (data.corpLevel == 0) {
                        treeData.push({"id": data.id, "parent": "#", "text": data.corpName, state: {opened: true}});//加载父节点
                        corpId =  data.id;
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
            // corpIdAll = corpId;
            $('#user-tree').jstree({
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
        },

    });
}

function delete_node(e, d) {
    $.ajax({
        type: 'DELETE',
        url: '/business-resource/titles/' + d.node.id,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data.resultMassage);
            if (data.resultCode > 0) {
                //更新树
                var newTreeMap = new Array();
                for (var i = 0; i < treeMap.length; i++) {
                    var j = treeMap[i];
                    if (d.node.id != j.id) {
                        newTreeMap.push(j);
                    }
                }
                treeMap = newTreeMap;

                var newTreeData = new Array();
                for (var i = 0; i < treeData.length; i++) {
                    var k = treeData[i];
                    if (d.node.id != k.id) {
                        newTreeData.push(k);
                    }
                }
                treeData = newTreeData;

                $('#user-tree').jstree(true).settings.core.data = treeData;
                $('#user-tree').jstree(true).refresh();
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
            }
            else {
                $('.alert-danger').show();
                $("#dangerMsg").html(data.responseJSON.message);
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        }
    });

}
//选择节点，通过节点查询对应的用户
function select_node(e, d) {
    nodeId = d.node.id;
    console.log(nodeId);
    search(nodeId);
    corpIdAll = nodeId;
}




/* DataTable 加载 */
function initDataTable() {
    dataTable = $('#user-list').dataTable({
        "columnDefs": [{
            orderable: false,//禁用排序
            targets: [0, 6]   //指定的列
        }],
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
        "iDisplayLength": 5,
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        // "ajax": {
        //     "url": "/business-resource/users/findByCorpId?corpId=" + corpId,
        //     "dataSrc": "results"
        // },
        "columns": [{
            "data": "id"
        }, {
            "data": "userAccount"
        }, {
            "data": "userName"
        }, {
            "data": "userEmail"
        }, {
            "data": "userPhone"
        }, {
            "data": "expiryTime"
        } ],

        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row) {
                    return '<input type="checkbox" onclick="chkClick()" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            },
            {
                'targets': 3,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {

                    return null2black(row.userEmail);

                }
            }, {
                'targets': 5,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.expiryTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }
                    return "";

                }
            },
            {
                'targets': 6,
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
    $('#user-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


function chkClick() {
    $('#user-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}


//根据公司查询用户
function search(corpId) {
    checkCorp = 0;
    var button = $("#div_button").html();
    var oldoptions = dataTable.fnSettings();
    var newoptions = $.extend(oldoptions, {
        "ajax": {
            url: "/business-resource/users/findByCorpId",
            data: {corpId: corpId},
            dataSrc: 'results'
        },
        "iDisplayLength": Number($('select[name=user-list_length]').val()),
        fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            if (!isNotEmpty(aData)) {
                checkCorp = 0;
            }
            else {
                checkCorp = 1;
            }
        }
    })
    dataTable.fnDestroy();
    $('#user-list').dataTable(newoptions);
    addBtn();
    $('#div_button').append(button);
}

function openNew() {
    console.log(nodeId);
    if (!isNotEmpty(nodeId)) {
    }
    isBtn = 'save';

    $("#userAccount").attr("disabled", false);
    $("#userAccount").val("");
    $("#userName").val("");
    $("#userPhone").val("");
    $("#userEmail").val("");
    $("#validityTime").val("");
    $("#initialPassword").val("123456");
    //角色
    $.ajax({
        type: 'GET',
        url: '/business-resource/roles',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].roleName});
            }
            $("#selectRole").select2({
                //placeholder: "请选择",
                tags: true,
                data: data,
                allowClear: true
            });
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });


    //$("#selectRole").select2().val();


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
            $("#selectCorp").select2({
                data: data,
                placeholder:'请选择',
                allowClear:true
            });

            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });
    // $("#selectCorp").removeAttr("readonly"); //去除readonly属性

    $("#right").hide();
    $("#error").hide();

    $("#mygdlxModalLabel").html("新建用户");

    $("#userForm").modal('show');
}

function checkUserAccount() {
    var regZH = /\d|A-z|/;
    if (!isNotEmpty($("#userAccount").val())) {
        layer.tips('不能为空', '#checkUser');
        return false;
    }

    if (regZH.test($("#userAccount").val()) == false) {
        layer.tips('只能输入字母数字 ', '#checkUser');
        return false;
    }
    if ('updata' == isBtn && $("#userAccount").val() == rowJson.userAccount) {
        checkAccount = 1;
    }


    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByUserAccount?userAccount=' + $("#userAccount").val(),
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data.message);
            if (data.code > 0 ) {
                checkAccount = 1;
                $("#right").show();
                $("#error").hide();
            }
            else{
                checkAccount = 0;
                $("#right").hide();
                $("#error").show();
                layer.tips('账号已经存在', '#chkUSER');
                return false;
            }
        },
        error:function(e){
            checkAccount = 0;
            $("#right").hide();
            $("#error").show();
                layer.tips('账号已经存在', '#chkUSER');
                 return false;


        },



    });

}

function openDialogDetail(domId, rowid) {
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    userId = null2black(json.id);
    corpId = null2black(json.corpId);

    $("#userAccount").val(null2black(json.userAccount));
    $("#userName").val(null2black(json.userName));
    $("#userPhone").val(null2black(json.userPhone));
    $("#userEmail").val(null2black(json.userEmail));
    //$("#userQq").val(null2black(json.userQq));
    //$("#userWechart").val(null2black(json.userWechart));
    console.log(json.expiryTime);
    if (isNotEmpty(json.expiryTime)) {
        $("#validityTime").val(dateToStr(json.expiryTime, 'YYYY-MM-DD'));
    }
    else {
        $("#validityTime").val('');
    }

    //角色
    $.ajax({
        type: 'GET',
        url: '/business-resource/roles',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].roleName});
            }
            $("#selectRole").select2({
                data: data,
                placeholder:'请选择',
                allowClear:true
            });

            if (isNotEmpty(json.roleIds)) {
                var rList = json.roleIds.split(",");
                var sList = new Array();
                for (var i = 0; i < rList.length; i++) {
                    sList.push(rList[i]);
                }
            }
            $("#selectRole").val(sList).trigger("change");


        },

    });

    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data[0] = {id: -1, text: '请选择'}
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            }

            $("#selectCorp").select2({
                data: data,
                placeholder:'请选择',
                allowClear:true

            });

            $("#selectCorp").val(corpIdAll).trigger("change");

        }
    });

   // $("#selectCorp").attr("readonly"); //添加readonly属性 & $("#ID").attr({ readonly: 'true' });


    $("#right").hide();
    $("#error").hide();
    $("#mygdlxModalLabel").html("用户详情");
    $("#userAccount").attr("disabled", true);
    $("#userForm").modal('show');
}

function save() {
    $("#dangerMsg").html('');
    if (!checkUser()) {
        return;
    }

    var roleNames = '';
    for (var i = 0; i < $("#selectRole").val().length; i++) {
        var id = $("#selectRole").val()[i];
        var name = $("#selectRole" + id).text();
        console.log(name);
        if (i == $("#selectRole").val().length - 1) {
            roleNames = roleNames + name;
        }
        else {
            roleNames = roleNames + name + ",";
        }
    }

    var validityTime = null;
    if (isNotEmpty($("#validityTime").val())) {
        validityTime = new Date($("#validityTime").val());
    }
    var param = {
        userAccount: $("#userAccount").val(),
        userName: $("#userName").val(),
        userPhone: $("#userPhone").val(),
        userEmail: $("#userEmail").val(),
        userQq: $("#userQq").val(),
        userWechart: $("#userWechart").val(),
        expiryTime: validityTime,
        corpId: $("#selectCorp").val(),
        roleName: roleNames
    }
    console.log(param);

    if ('save' == isBtn) {
        $.ajax({
            type: 'POST',
            url: '/business-resource/users?roleIds='+ $("#selectRole").val() ,
            dataType: 'JSON',
            data: JSON.stringify(param),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == 10000) {
                    search(data.result.corpId);
                    $('.alert-success').show();
                    corpIdAll = data.result.corpId;
                    setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    $("#userForm").modal('hide');
                } else {
                    $("#dangerMsg").html(data.responseJSON.message);
                    $('.alert-danger').show();
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            },
            error:function (e) {
                $("#dangerMsg").html(e.responseJSON.message);
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
        //rowJson.roleName = param.roleName;
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
                    search(param.corpId);

                    $('.alert-success').show();
                    corpIdAll = corpId;
                    // setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    //rowJson.roleIds = data.results.roleIds;
                    //数据修改
                    // var myTable = $('#user-list').DataTable();
                    //
                    // myTable.cell(rowNo, 1).data(rowJson.userAccount);//更新行
                    //
                    // myTable.cell(rowNo, 2).data(rowJson.userName);//更新行
                    //
                    // myTable.cell(rowNo, 3).data(rowJson.userEmail);//更新行
                    //
                    // myTable.cell(rowNo, 4).data(rowJson.userPhone);//更新行
                    //
                    // myTable.cell(rowNo, 5).data(rowJson.expiryTime);//更新行
                    //
                    // $("#btnEdit_" + data.result.id).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''))
                    $('.alert-success').show();
                    setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    $("#userForm").modal('hide');
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

function reloadDataTable(corpId) {
    var button = $("#div_button").html();
    var url = "/business-resource/users?corpId="+corpId ;
    reloadTable(dataTable, '#user-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}


function cancel() {
    $("#userForm").modal('hide');
}
function gotoDr() {
    $("#pldrForm").modal('hide');
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
                        url: '/business-resource/users/deleteMore?ids=' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 20000) {
                                search(corpIdAll);
                                //reloadDataTable(corpIdAll);
                                $('.alert-success').show();
                                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                            }
                            else {
                                $("#dangerMsg").html(data.responseJSON.massage);
                                $('.alert-danger').show();
                                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                            }
                        },
                        error: function (data) {
                            $("#dangerMsg").html(data.responseJSON.massage);
                            $('.alert-danger').show();
                            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                        }
                    });
                }
            }
        });
    }
}


//modal-dialog 重置密码
function passWordReset() {

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
            title: '密码重置确认',
            message: '是否重置密码为："123456"?',
            type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: '取消', // <-- Default value is 'Cancel',
            btnOKLabel: '确定', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function (result) {
                // result will be true if button was click, while it will be false if users close the dialog directly.
                if (result) {
                    //调用ajax修改
                    $.ajax({
                        type: 'PUT',
                        url: '/business-resource/users/reSetPassword?ids=' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 30000) {
                                search(corpIdAll);
                                //reloadDataTable(corpId);
                                $('.alert-success').show();
                                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                            }
                            else {
                                $("#dangerMsg").html(data.responseJSON.massage);
                                $('.alert-danger').show();
                                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                            }
                        },
                        error: function (data) {
                            $("#dangerMsg").html(data.responseJSON.massage);
                            $('.alert-danger').show();
                            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                        }
                    });
                }
            }
        });
    }


}



function checkUser() {
    var regLXDH = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    var regFZF = /[\d|A-z|\u4E00-\u9FFF]/;
    var regYX = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
    var regQQ = /^[1-9][0-9]{4,9}$/;
    var regWX = /^[a-z_\d]+$/;
    var regmm = /[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\] ]/;

    if (!isNotEmpty($("#userName").val())) {
        layer.tips('不能为空', '#chkUserName');
        return false;
    }
    if (regFZF.test($("#userName").val()) == false) {
        layer.tips('只能输入中英文数字 ', '#chkUserName');
        return false;
    }

    if (!isNotEmpty($("#userEmail").val())) {
        layer.tips('不能为空', '#chkUserEmail');
        return false;
    }
    if (isNotEmpty($("#userEmail").val()) && regYX.test($("#userEmail").val()) == false) {
        layer.tips('请输入正确的邮箱', '#chkUserEmail');
        return false;
    }
    if (!isNotEmpty($("#userPhone").val())) {
        layer.tips('不能为空', '#chkUserPhone');
        return false;
    }
    if (regLXDH.test($("#userPhone").val()) == false) {
        layer.tips('请输入正确的电话号码 ', '#chkUserPhone');
        return false;
    }
    if (!isNotEmpty($("#selectRole").val()) || $("#selectRole").val() == -1) {
        layer.tips('请选择角色', '#chkSelectRole');
        return false;
    }
    if (!isNotEmpty($("#selectCorp").val()) || $("#selectCorp").val() == -1) {
        layer.tips('请选择公司', '#checkSelectCorp');
        return false;
    }

    if (!isNotEmpty($("#validityTime").val())){
        layer.tips('请选择有效期', '#chkValidityTime');
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