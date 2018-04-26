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
var url ="";
var messageFileUrl1 = null;
var messageFileUrl2 = null;
var messageFileUrl3 = null;
var phone = 0;
var cropName="";

$(function () {
    pageSetUp();
    initImgUpload();
    filedel();
    initDataTable();
    addBtn();
    // initDataTableUser();

    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');

    initLeftActive('message_list');
})

function filedel() {
    $('a.remove').each(function (index) {
        $(this).on("click", function () {
            var fileIndex = $(this).siblings('input').attr('name');
            var fid = $('input[name=' + fileIndex + ']').attr("fileId");
            if (fileIndex.indexOf("img") > 0) {
                imgMap.remove(fid);
            }

            refreshContainer();
        })
    })
}


function initImgUpload() {
    var $form = $('#main_form');
    var file_input = $form.find('input[jstype=img]');
    var fd = new FormData($form.get(0));

    file_input.ace_file_input({
        style: 'well',
        btn_choose: '请上传图片',
        btn_change: null,
        droppable: false,
        thumbnail: 'large',

        maxSize: 2200000,// bytes
        allowExt: ["jpg", "png"],// ["jpeg", "jpg", "png", "gif"],
        allowMime: ["image/jpg", "image/png"],
        before_change: function (files, dropped) {
            return true;
        },
        before_remove: function () {
        },
        preview_error: function (filename, code) {
        }
    });

    file_input.on('file.error.ace', function (ev, info) {
        if (info.error_count['ext'] || info.error_count['mime'])
            alert('文件类型不匹配!');
        if (info.error_count['size'])
            alert('请选择小于2M的图片');
    });

    file_input.on('change', function (e) {
        var files = $(this).data('ace_input_files');
        if (files && files.length > 0) {
            e.preventDefault();
            var formData = new FormData();
            formData.append("image", files[0]);

            var iWidth = 350; //弹出窗口的宽度;
            var iHeight = 120; //弹出窗口的高度;
            var iTop = (document.documentElement.clientHeight - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (document.documentElement.clientWidth - iWidth) / 2; //获得窗口的水平位置;

            $.blockUI({
                message: '<div><br/><img src="/njpb/images/loading.gif" /><br/><br/>文件上传中</div>',
                css: {
                    width: iWidth,
                    height: iHeight,
                    top: iTop,
                    left: iLeft,
                    border: 'none',
                    padding: '5px',
                    backgroundColor: '#333',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .6,
                    color: '#fff'
                }

            });
            //setTimeout(function(){$.unblockUI(); },1000);


//			var reader = new FileReader();
//			reader.readAsDataURL(files[0]);
//			reader.onload = function(e){
//				base64Map.put(1,this.result);
//			}
//			console.log(base64Map);
            $.ajax({
                url: '/business-resource/files/uploadImage',
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function (data) {
                    if (data.code == 10000) {
                        imgList.push(data.result);

                        imgMap.put(data.result.id, data.result);
                        refreshContainer();
                    }
                    $.unblockUI();
                },
                error: function (data) {
                    console.log(data);
                    $.unblockUI();
                }
            });
        }
    });
}

function refreshContainer() {
    $('#main_form').find('input[type=file]').ace_file_input('reset_input_ui');
    $('a.preview').remove();
    // $('video').remove();
    var n = 1;
    var array = imgMap.keySet();
    var array1 = imgList;
    for (var i in array) {
        var key = array[i];
        var v = imgMap.get(key);
//		$('input[name=pic' + n + '_img]')
//		.next()
//		.addClass('hide-placeholder selected')
//		.removeAttr('data-title')
//		.children('span')
//		.addClass('large')
//		.attr('data-title', v.fileName)
//		.html('<img class="middle" style="width:150px;height:117px;" src="'
//						+ base64Map.get(v.id)
//						+ '" /><i class="ace-icon fa fa-picture-o file-image"></i>');
        var thumbnailUrl = v.fileUrl;
        thumbnailUrl = thumbnailUrl.replace("resource/", "resource/thumb_")

        $('input[name=pic' + n + '_img]')
            .next()
            .addClass('hide-placeholder selected')
            .removeAttr('data-title')
            .children('span')
            .addClass('large')
            .attr('data-title', v.fileName)
            .html('<img class="middle" style="width:150px;height:117px;" src="'
                + thumbnailUrl
                + '" /><i class="ace-icon fa fa-picture-o file-image"></i>');
        $('input[name=pic' + n + '_img]').attr("fileId", v.id);
        //var src = 'data:image/' + tt + ';base64,' + rtn.returnOriginCode;
        $('input[name=pic' + n + '_img]').parent().append('<a title="查看原图" class="preview" href="#" data-toggle="modal" onclick="showPreviewModal(\'' + v.fileUrl + '\')"><i class="ace-icon fa fa-search-plus"></i></a>');
        n++;
    }

}

function showPreviewModal(src) {
    $('#previewContent').html('<img src="' + src + '" />');
    $('#previewOrigin').modal('show');
}


// function save() {
//     $("#dangerMsg").html('');
//     if (!saveCheck()) {
//         return;
//     }
//     var imgArray = new Array();
//
//
//     var array = imgMap.keySet();
//     for (var i in array) {
//         var key = array[i];
//         imgArray.push(key);
//     }
//     // var knowledge = new Object();
//     // knowledge.title = $("#txt_mc").val();
//     // knowledge.knowledgeTypeId = $("#add_zsfl").val();
//     // knowledge.estateTypeId = $("#add_sblx").val();
//     // knowledge.estateSubTypeId = $("#add_mklx").val();
//     // knowledge.knowledgeContent = $("#add_nr").val();
//     // knowledge.knowledgeCreationUsrId = $("#add_lrr").val();
//     // knowledge.knowledgeRemark = $("#add_bz").val();
//     //
//     // var knowledgeLabelIds = $("#add_zsbq").val();
//
//     // $.ajax({
//     //     type : 'POST',
//     //     url : CONSTANTS.BASE.PATH +'/business-resource/knowledges?knowledgeLabelIds='+knowledgeLabelIds+'&imageFilelds='+imgArray+'&videoFileIds='+videoArray,
//     //     dataType : 'JSON',
//     //     contentType : 'application/json',
//     //     data : JSON.stringify(knowledge),
//     //     success : function(retData) {
//     //         if(retData.resultCode == 20000)
//     //         {
//     //             $('.alert-success').show();
//     //             setTimeout("$('.alert-success').hide()",ALERTMESSAGE);
//     //             setTimeout("cancel()",ALERTMESSAGE);
//     //         }
//     //         else
//     //         {
//     //             $("#dangerMsg").html(retData.resultMassage);
//     //             $('.alert-danger').show();
//     //             setTimeout("$('.alert-danger').hide()",ALERTMESSAGE);
//     //             setTimeout("cancel()",ALERTMESSAGE);
//     //         }
//     //
//     //     },
//     //     error : function() {
//     //         $('.alert-danger').show();
//     //         setTimeout("$('.alert-danger').hide()",ALERTMESSAGE);
//     //     }
//     // });
//
// }


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
                    "url": url,
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


function reloadDataTable() {
    var button = $("#div_button").html();
    var url = "/business-resource/sysMessages";
    reloadTable(dataTable, '#message-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}


function openNew() {
    isBtn = 'save';
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data[0] = {id: -1, text: '请选择'};
            corpId = retData.results[0].id;
            cropName = retData.results[0].corpName;

            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            }

            $("#selectCorp").select2({
                data: data,
                placeholder:'请选择',
                allowClear:true

            });

            if(corpId == 1){

                url = "/business-resource/users/findByCorpId"
            }else{
                url = "/business-resource/users/findByCorpId?corpId=" + corpId;
            }
            initDataTableUser();

        }
    });
    $("#title").val('');
    $("#text").val('');
    $("#btn_save").show();
    $("#btn_send").show();
    $("#btn_sendAll").show();
    $("#up").val('');
    $("#up4").val('');
    $("#up2").val('');
    $("#up3").val('');

    $("#myModalLabel").html("新建消息");


    $("#addmessgeForm").modal('show');

    $('#main_form').find('input[type=file]').ace_file_input('reset_input_ui');
    $('a.preview').remove();
    imgMap = new Map();

    imgList = new Array();
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

                imgList.push(v);

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
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            data[0] = {id: -1, text: '请选择'};
            corpId = retData.results[0].id;
            cropName = retData.results[0].corpName;
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            }

            $("#selectCorp").select2({
                data: data,
                placeholder:'请选择',
                allowClear:true

            });

            if(corpId == 1){

                url = "/business-resource/users/findByCorpId"
            }else{
                url = "/business-resource/users/findByCorpId?corpId=" + corpId;
            }
            initDataTableUser();

        }
    });

    if (json.status == 0) {
        $("#btn_save").hide();
        $("#btn_send").hide();
        $("#btn_sendAll").hide();
    }
    else {
        $("#btn_save").show();
        $("#btn_send").show();
        $("#btn_sendAll").show();
    }


    $("#myModalLabel").html("详情");

    $("#addmessgeForm").modal('show');
}

function  loadUserTable() {
    corpId = $("#selectCorp").val();
    if(corpId == 1){
        url = "/business-resource/users/findByCorpId"
    }else{
        url = "/business-resource/users/findByCorpId?corpId=" + corpId;
    }
    reloadTable(dataTable2, '#user-list', url, 'results');

}


function sendMessageAll(){
     // $("#addmessgeForm111").modal('show');bel1
    $("#bel1").css("display",'block');
     var t =  $("#selectCorp").val();
    var checkText=$("#selectCorp").find("option:selected").text(); //获取Select选择的Text
     if(t==""){
         if(cropName =="总公司"){
             $("#myModalLabel1").html("是否发送所有人");
         }else{
             $("#myModalLabel1").html("是否发送"+ cropName +"所有人");
         }
     }else if(t=="总公司"){
         $("#myModalLabel1").html("是否发送所有人");
     }
     else{
         $("#myModalLabel1").html("是否发送"+ checkText +"所有人");
     }


}

function sendMess(){
    MessageAll();
}

function deleSend(){
    $("#bel1").css("display",'none');

}

function MessageAll() {

    $("#dangerMsg").html('');

    if (!checkMessage()) {
        return;
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
                url: '/business-resource/sysMessages/sendMessageAll?corpId='+corpId,
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
                url: '/business-resource/sysMessages/sendMessageEditAll?corpId='+corpId,
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
    $("#bel1").css("display",'none');
         $("#addmessgeForm").modal('hide');

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
    // if (str == '') {
    //     layer.tips("请选择下发人员 ", "#the")
    //     return;
    // } else {
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
  $("#bel1").css("display",'none');
    // }

}

function sendMessage() {
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
    if (str == "") {
        layer.tips("请选择下发人员 ", "#the")

        // return;
    }
    else {

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
                url: '/business-resource/sysMessages/sendMessage?receiveUserIds=' + str,
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
                url: '/business-resource/sysMessages/sendMessageEdit?receiveUserIds=' + str,
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
      $("#bel1").css("display",'none');
    }
}


function cancel() {
    $("#title").val("");
    $("#text").val("");
    $("#addmessgeForm").modal('hide');

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
                        url: '/business-resource/messages/deleteMore?ids=' + str,
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


function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

