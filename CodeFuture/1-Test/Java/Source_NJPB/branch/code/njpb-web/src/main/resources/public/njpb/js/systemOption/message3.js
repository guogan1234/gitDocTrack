/**
 * Created by len on 2017/12/5.
 */

/**
 * Created by twg on 2017/3/7.
 */

var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var imgMap = new Map();
var pageTemp = 0;
var imgList = new Array();

var messageFileUrl1 = null;
var messageFileUrl2 = null;
var messageFileUrl3 = null;
var phone = 0;
$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    initDataTableUser();

    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');

    initLeftActive('message_list');
})



function cancel() {
    $("#addmessgeForm").modal('hide');
}


/* DataTable 加载 */
function initDataTableUser() {
    //查询公司
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            corpId = retData.results[0].id;
            dataTable2 = $('#user-list').dataTable({
                "destroy": true,
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
                    "url": "/business-resource/users/findByCorpId?corpId" + corpId,
                    "dataSrc": "results"
                },
                "columns": [{
                    "data": "id"
                }, {
                    "data": "userName"
                }, {
                    "data": "corpName"
                }, {
                    "data": "userPhone"
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
                            return '<input onclick="chkplClick()"  type="checkbox" name="plsbids" value=' + row.id + ' id="plchk' + row.id + '"  class="ace"/>';
                        }
                    },
                    {
                        'targets': 4,
                        'searchable': false,
                        'orderable': false,
                        'className': 'dt-body-center',
                        'bSortable': false,
                        'render': function (data, type, row, meta) {
                            var tm = null2black(row.createTime)
                            if (isNotEmpty(tm)) {
                                return dateToStr(new Date(tm), 'YYYY-MM-DD');
                            } else {
                                return tm;
                            }

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
        },

    });

}

function chkplClick() {
    $('#user-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#mytoolbox').append(dataPlugin);
}

/* DataTable 加载 */
function initDataTable() {
    dataTable = $('#message-list').dataTable({
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
        "aaSorting": [[2, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": "/business-resource/sysMessages",
            "dataSrc": "results"
        },
        "columns": [
            {
                "data": "id"
            }, {
                "data": "messageTitle"
            }, {
                "data": "lastUpdateTime"
            }, {
                "data": "messageAuthor"
            }, {
                "data": "status"
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
            },
            {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.lastUpdateTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }
                    return "";

                }
            },
            {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.messageAuthor);
                }
            },
            {
                'targets': 4,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    if (row.status == 0) {
                        return "已发送";
                    } else if (row.status == 1) {
                        return "未发送";
                    } else {
                        return "";
                    }
                }
            },
            {
                'targets': 5,
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
    $('#message-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {


        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });

    });

    $('#message-list').on("page.dt", function () {

        if (pageTemp == 1) {
            var t = document.getElementsByName("ids");


        }

    });


}




function chkClick() {
    $('#message-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}





function emptyDiv() {
    // $("#addmessgeForm").empty();
}

function openDialogDetail(domId, rowid) {
    id = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    $("#title").val(json.messageTitle);
    $("#text").val(json.messageText);
    $("#id").val(id);
    // $("#up").val(null2black(json.messageFile1Url));
    $('#main_form').find('input[type=file]').ace_file_input('reset_input_ui');
    $('a.preview').remove();
    //图片
    $.ajax({
        type: 'GET',
        url: '/business-resource/sysMessages/findByMessageId?messageId=' + id,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            console.log(retData);
            for (var i = 0; i < retData.results.length; i++) {
                var n = i + 1;
                var v = retData.results[i];
                imgMap.put(v.id, v);
                var thumbnailUrl = v.fileUrl;
                thumbnailUrl = thumbnailUrl.replace("resource/", "resource/thumb_")


                $('input[name=pic' + n + '_img]')
                    .next()
                    .addClass('hide-placeholder selected')
                    .removeAttr('data-title')
                    .children('span')
                    .addClass('large')
                    .attr('data-title', '')
                    .html('<img class="middle" style="width:150px;height:117px;" src="'
                        + thumbnailUrl
                        + '" /><i class="ace-icon fa fa-picture-o file-image"></i>');
                $('input[name=pic' + n + '_img]').attr("fileId", v.id);
                $('input[name=pic' + n + '_img]').parent().append('<a title="查看原图" class="preview" href="#" data-toggle="modal" onclick="showPreviewModal(\'' + v.fileUrl + '\')"><i class="ace-icon fa fa-search-plus"></i></a>');
            }
        },

    });


    if (json.status == 0) {
        $("#btn_save").hide();
        $("#btn_send").hide();
    }
    else {
        $("#btn_save").show();
        $("#btn_send").show();
    }


    $("#myModalLabel").html("详情");

    $("#addmessgeForm").modal('show');
}

/*保存*/
function saveMessage() {
    $("#dangerMsg").html('');
    if (!checkMessage()) {
        return;
    }

    var str = '';
    for (var i = 0; i < document.getElementsByName('plsbids').length; i++) {
        if (document.getElementsByName('plsbids')[i].checked) {
            if (str == '') str += document.getElementsByName('plsbids')[i].value;
            else str += ',' + document.getElementsByName('plsbids')[i].value;
        }
    }
    var imgArray = new Array;
    var array = imgMap.keySet();
    for (var i in array) {
        var key = array[i];
        var image = imgMap.container[key];
        imgArray.push(image);
    }

    if (imgArray.length == 1) {
        messageFileUrl1 = imgArray[0].fileUrl;
    }
    if (imgArray.length == 2) {
        messageFileUrl1 = imgArray[0].fileUrl;
        messageFileUrl2 = imgArray[1].fileUrl;
    }
    if (imgArray.length == 3) {
        messageFileUrl1 = imgArray[0].fileUrl;
        messageFileUrl2 = imgArray[1].fileUrl;
        messageFileUrl3 = imgArray[2].fileUrl;
    }
    if (str == '') {
        layer.tips("请选择下发人员 ", "#the")
        return;
    } else {
        var param = {
            messageTitle: $("#title").val(),
            messageText: $("#text").val(),
            messageFile1Url: messageFileUrl1,
            messageFile2Url: messageFileUrl2,
            messageFile3Url: messageFileUrl3,
        }
        if (isBtn == "save") {
            $.ajax({
                type: 'POST',
                url: '/business-resource/sysMessages/saveMessage?receiveUserIds=' + str,
                dataType: 'JSON',
                data: JSON.stringify(param),
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 0) {
                        reloadDataTable();
                        $('.alert-success').show();
                        setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    } else {
                        $("#dangerMsg").html(data.responseJSON.message);
                        $('.alert-danger').show();
                        setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                    }
                },
                error: function (e) {
                    $("#dangerMsg").html(e.responseJSON.message);
                    $('.alert-danger').show();
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            });
        } else {
            var param = {
                id: id,
                messageTitle: $("#title").val(),
                messageText: $("#text").val(),
                messageFile1Url: messageFileUrl1,
                messageFile2Url: messageFileUrl2,
                messageFile3Url: messageFileUrl3,
            }
            $.ajax({
                type: 'PUT',
                url: '/business-resource/sysMessages/saveMessageEdit?receiveUserIds=' + str,
                dataType: 'JSON',
                data: JSON.stringify(param),
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 0) {
                        reloadDataTable();
                        $('.alert-success').show();
                        setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    } else {
                        $("#dangerMsg").html(data.responseJSON.message);
                        $('.alert-danger').show();
                        setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                    }
                },
                error: function (e) {
                    $("#dangerMsg").html(e.responseJSON.message);
                    $('.alert-danger').show();
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            });
        }


        $("#addmessgeForm").modal('hide');
    }

}






function checkMessage() {
    if (!isNotEmpty($("#title").val())) {
        layer.tips('不能为空', '#chk_title');
        return false;
    }
    if (!isNotEmpty($("#text").val())) {
        layer.tips('不能为空', '#chktext');
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

