/**
 * Created by len on 2017/9/7.
 */
var dataTable = null;
var isBtn = '';
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var treeData = new Array();//树所需数据
var treeMap = new Array();//初始查询所有数据
var allData = new Map();//初始查询所有数据
var MAP_TEMPLATE = new Map();
var userId = "";
var roleTree = '';
var checkRole = "";

$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    // initTree();
    initLeftActive('role_list');
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
    dataTable = $('#role-list').dataTable({
        "columnDefs": [{
            orderable: false,//禁用排序
            targets: [0, 4]   //指定的列
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
        "iDisplayLength": 10,
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": "/business-resource/roles",
            "dataSrc": "results"
        },
        "columns": [
            {
                "data": "id"
            }, {
                "data": "roleName"
            }, {
                "data": "roleGrade"
            }, {
                "data": "roleDescription"
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
                    return '<input type="checkbox" onclick="chkClick()" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            }, {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    if (row.roleGrade == 0) {
                        return CONSTANTS.ROLE.CUSTOM;
                    }
                    return CONSTANTS.ROLE.RESERVED;

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
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.id + ',' + meta.row + ',' + row.roleGrade + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]
    });

    //复选框全选控制
    var active_class = 'active';
    $('#role-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {

        var th_checked = this.checked;
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}
function chkClick() {
    $('#role-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/roles";
    reloadTable(dataTable, '#role-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}

function initTree() {

    treeData = new Array();
    $.ajax({
        type: 'GET',
        url: '/business-resource/resources',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var nodes = new Array();
            for (var i = 0; i < retData.results.length; i++) {
                var data = retData.results[i];
                if (!isNotEmpty(data.parentId)) {
                    treeData.push({"id": data.id, "parent": "#", "text": data.resourceName, state: {opened: true}});//加载父节点
                }
                else {
                    treeData.push({
                        "id": data.id,
                        "parent": data.parentId,
                        "text": data.resourceName,
                        state: {opened: true}
                    });//加载子节点
                }
                treeMap.push(data);
            }


            roleTree = $('#role-tree').jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    'force_text': true,
                    "themes": {"stripes": true},
                    'data': treeData
                },
                "checkbox": {
                    "keep_selected_style": true
                },
                "types": {
                    "#": {"max_children": 1, "max_depth": 9, "valid_children": ["root"]},
                    "root": {"icon": "", "valid_children": ["default"]},
                    "default": {"icon": "/njpb/images/qx.png", "valid_children": ["default", "file"]},
                    "file": {"icon": "/njpb/images/qx.png", "valid_children": []}
                },
                "plugins": ["checkbox", "wholerow", "types"]
            });
        },

    });
}

function openNew() {
    isBtn = 'save';
    $('#role-tree').jstree("destroy");
    $('#role-tree').data('jstree', false).empty();
    initTree();
    $("#btnDr").show();
    $("#jsok").hide();
    $("#jsng").hide();
    $("#roleName").val('');
    $("#roleDescription").val('');
    $("#myModalLabel").html("新建角色");
    $("#roleName").attr("disabled", false);
    $("#roleDescription").attr("disabled", false);
    $("#addRoleForm").modal('show');
}

function emptyDiv() {
    // $("#addRoleForm").empty();
}

function checkRole() {
    $.ajax({
        type: 'GET',
        url: '/business-resource/roles/findByRoleName?roleName=' + $("#roleName").val(),
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data.message);
            if (!isNotEmpty(data.results)) {
                $("#jsok").show();
                $("#jsng").hide();
            }
            else {
                $("#jsok").hide();
                $("#jsng").show();
            }
        },
        error: function (e) {
            checkRole = 0;
            $("#right").hide();
            $("#error").show();
            layer.tips('角色已经存在', '#chkUSER');
            return false;
        },

    });
}

function openDialogDetail(domId, rowid, roleGrade) {
    $('#role-tree').jstree("destroy");
    roleId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;

    //通过角色找到资源
    $.ajax({
        type: 'GET',
        url: '/business-resource/resourceTreeDatas/findByRoleId?roleId=' + json.id,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data.message);

            var nodes = new Array();
            for (var i = 0; i < data.results.length; i++) {
                nodes.push(data.results[i].id);
            }

            treeData = new Array();
            $.ajax({
                type: 'GET',
                url: '/business-resource/resources',
                dataType: 'JSON',
                contentType: 'application/json',
                success: function (retData) {
                    for (var i = 0; i < retData.results.length; i++) {
                        var data = retData.results[i];
                        if (!isNotEmpty(data.parentId)) {
                            treeData.push({
                                "id": data.id,
                                "parent": "#",
                                "text": data.resourceName,
                                state: {opened: true}
                            });//加载父节点
                        }
                        else {
                            treeData.push({
                                "id": data.id,
                                "parent": data.parentId,
                                "text": data.resourceName,
                                state: {opened: true}
                            });//加载子节点
                        }
                        treeMap.push(data);
                    }
                    $('#role-tree').jstree("destroy");

                    $('#role-tree').jstree({
                        "core": {
                            "animation": 0,
                            "check_callback": true,
                            'force_text': true,
                            "themes": {"stripes": true},
                            'data': treeData
                        },
                        "checkbox": {
                            "tie_selection": true,
                            "whole_node": true,
                            "keep_selected_style": false
                        },
                        "types": {
                            "#": {"max_children": 1, "max_depth": 9, "valid_children": ["root"]},
                            "root": {"icon": "", "valid_children": ["default"]},
                            "default": {"icon": "/njpb/images/qx.png", "valid_children": ["default", "file"]},
                            "file": {"icon": "/njpb/images/qx.png", "valid_children": []}
                        },
                        "plugins": ["checkbox", "wholerow", "types"]
                    }).bind("ready.jstree", function (event, data) {
                        $('#role-tree').jstree(true).select_node(nodes);
                    });

                },
                failure: function () {
                }
            });
        }
    });

    $("#roleName").val(json.roleName);
    $("#roleDescription").val(json.roleDescription);
    $("#jsok").hide();
    $("#jsng").hide();
    if (roleGrade == 1) {
        // $('#btnDr').css("display", "none");
        $("#btnDr").hide();
    }else{
        $("#btnDr").show();
        // $('#btnDr').css("display","block") ;
    }

    $("#myModalLabel").html("角色详情");
    $("#roleName").attr("disabled", true);
    $("#addRoleForm").modal('show');
}

/*保存*/
function save() {
    $("#dangerMsg").html('');
    if (!verifyForm()) {
        return;
    }
    var ref = $('#role-tree').jstree(true),
        sel = ref.get_selected();


    var param = {

        roleName: $("#roleName").val(),
        roleGrade: 0,
        roleDescription: $("#roleDescription").val()
    }

    if ('save' == isBtn) {
        $.ajax({
            type: 'POST',
            url: '/business-resource/roles?resourceIds=' + sel,
            dataType: 'JSON',
            data: JSON.stringify(param),
            contentType: 'application/json',
            success: function (data) {
                console.log(data.message);
                if (data.code == 10000) {
                    reloadDataTable();
                    $('.alert-success').show();
                    setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                }
                else {
                    $('.alert-danger').show();
                    $("#dangerMsg").html(data.responseJSON.message);
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            },
            error: function (data) {
                $('.alert-danger').show();
                $("#dangerMsg").html(data.responseJSON.message);
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        });
    }
    else {
        $.ajax({
            type: 'PUT',
            url: '/business-resource/roles/' + roleId + '?resourceIds=' + sel,
            dataType: 'JSON',
            data: JSON.stringify(param),
            contentType: 'application/json',
            success: function (data) {
                console.log(data.message);
                if (data.code == 30000) {
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
                $('.alert-danger').show();
                $("#dangerMsg").html(data.responseJSON.message
                );
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        });
    }

    $("#addRoleForm").modal('hide');
}
function cancel() {
    $("#addRoleForm").modal('hide');
    // $("#addRoleForm").empty();
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
                        url: '/business-resource/roles/deleteMore?ids=' + str,
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
                        error: function () {
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
    if (!isNotEmpty($("#roleName").val())) {
        layer.tips('不能为空', '#chkJSMC');
        return false;
    }
    return true;
}