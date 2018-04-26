/**
 * Created by len on 2017/9/7.
 */
var dataTable = null;
var dataTable2 = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var treeData = new Array();//树所需数据
var treeMap = new Array();//初始查询所有数据
var allData = new Map();//初始查询所有数据
var MAP_TEMPLATE = new Map();
var clickEstateType = '';
var clickEstateTypeName = '';
var clickModuleType = '';
var clickModuleTypeName = '';
//var pid = '';
var checkName = 1;

$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initTree();
    initLeftActive('moduleType_list');
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
    dataTable = $('#moduleType-list').dataTable({
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

    });

    //复选框全选控制
    var active_class = 'active';
    $('#moduleType-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function initTree() {
    treeData = new Array();

    $.ajax({
        type: 'GET',
        url: '/business-resource/estateSubTypes/getTreeDatas',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            //treeMap.push(retData.results);
            for (var i = 0; i < retData.results.length; i++) {
                var data = retData.results[i];
                if (data.parent == "#") {
                    treeData.push({"id": data.id, "parent": "#", "text": data.text, state: {opened: true}});//加载父节点
                }
                else {
                    treeData.push({
                        "id": data.id,
                        "parent": data.parent,
                        "text": data.text,
                        state: {opened: true}
                    });//加载子节点
                }
                treeMap.push(data);
            }


            $('#moduleType-tree').jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    'force_text': true,
                    "themes": {"stripes": true},
                    'data': retData.results
                },
                "types": {
                    "#": {"max_children": 1, "max_depth": 4, "valid_children": ["root"]},
                    "root": {"icon": "/njpb/images/mk.png", "valid_children": ["default"]},
                    "default": {"icon": "/njpb/images/mk.png", "valid_children": ["default", "file"]},
                    "file": {"icon": "/njpb/images/mk.png", "valid_children": []}
                },
                "plugins": ["wholerow", "types", "search"]
            });
            $('#moduleType-tree').jstree(true).refresh();
        },
        failure: function () {
        }
    });


    var to = false;
    $('#txt_search').keyup(function () {
        if (to) {
            clearTimeout(to);
        }
        to = setTimeout(function () {
            var v = $('#txt_search').val();
            $('#moduleType-tree').jstree(true).search(v);
        }, 250);
    });

    //节点点击事件
    $('#moduleType-tree').bind("activate_node.jstree", function (obj, e) {
        // 处理代码
        clickEstateType = '';
        clickEstateTypeName = '';
        clickModuleType = '';
        clickModuleTypeName = '';
        // 获取当前节点
        var currentNode = e.node;
        var id = currentNode.id.split("_").pop();

        var dtdata = new Array();
        dataTable.fnClearTable();
        MAP_TEMPLATE = new Map();

        if (currentNode.parent == '#') {
            clickEstateType = id;
            clickEstateTypeName = currentNode.text;
           // pid = '';

        }
        else {
            currentNode.parent.split("_").pop();
            clickEstateType = currentNode.parent.split("_").pop();
            // clickEstateTypeName =  currentNode.state.sbText;
            clickModuleType = id;
            clickModuleTypeName = currentNode.text;

            console.log(clickEstateType);
            //console.log(clickEstateTypeName);
            console.log(clickModuleType);
            console.log(clickModuleTypeName);


           // pid = id;
            //alert("您点击了部件");
            //reloadDT(dataTable, '#user-list', '../../njpb/js/jcsjgl/json.json');
            for (var i = 0; i < treeMap.length; i++) {
                var json = treeMap[i];
                // var t = json.id.split("_");
                var temp_moduleType =   json.id.split("_").pop() ;
                var temp_estateType = json.parent.split("_").pop();
                if (id == temp_moduleType &&  temp_estateType== clickEstateType) {
                    dtdata.push(json);
                    MAP_TEMPLATE.put(json.id, json);
                }
            }
        }

        if (dtdata.length > 0) {
            var vwEstateModuleType = dtdata[0].vwEstateModuleType;
            var data = new Array();
            data.push(vwEstateModuleType);
            var button = $("#div_button").html();
            var oldOptions = dataTable.fnSettings();
            dataTable.fnDestroy();
            $('#moduleType-list').dataTable({
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

                "data": data,
                "columns": [{
                    "data": "id"
                }, {
                    "data": "moduleName"
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
                    }, {
                        'targets': 2,
                        'searchable': false,
                        'orderable': false,
                        'className': 'dt-body-center',
                        'bSortable': false,
                        'render': function (data, type, row, meta) {
                            partsType = null2black(row.partsType);
                            if (partsType == 1) {
                                return CONSTANTS.MODULEPARTTYPE.STATION;
                            } else if (partsType == 2) {
                                return CONSTANTS.MODULEPARTTYPE.BICYCLE;
                            } else {
                                return partsType;
                            }
                        }
                    }, {
                        'targets': 3,
                        'searchable': false,
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
                            return '<button id="btnEdit_' + row.moduleTypeId + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                                + '" data-toggle="modal" onclick="openDialogDetail(' + row.moduleTypeId + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                        }
                    }]
            });
            addBtn();
            $('#div_button').append(button);
        }
    });
}

function chkClick() {
    $('#moduleType-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function openNew() {
    if (!isNotEmpty(clickEstateType) && !isNotEmpty(clickModuleType)) {
        BootstrapDialog.show({
            title: '提示',
            message: '您没有选择任何设备类型',
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
        rowJson = new Object();
        isBtn = 'save';
 
        $("#sblx").val(clickEstateTypeName);
        $("#mklx").val(clickModuleTypeName);
        $("#moduleTypeNewModalLabel").html("新建部件类型");
        $("#moduleTypeForm").modal('show');
    }
}
function openBatchNew() {
    rowJson = new Object();
    isBtn = 'save';
    $("#moduleTypeName").val('');
    // $("#partsType").val('');
    var data = [];
    data.push({id: 1, text: "站点"});
    data.push({id: 2, text: "车辆"});

    $("#partsType").select2({
        data: data,
        placeholder: '请选择',
        allowClear: true
    });




    $("#workpoints").val('0');

    initDataTable_batchNew();
    $("#moduleTypeNewModalLabel2").html("新建部件类型");
    $("#moduleTypeForm2").modal('show');

}

/* DataTable 加载 */
function initDataTable_batchNew() {
    dataTable2 = $('#bdfs-list').dataTable({
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
            },    {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm =   null2black(row.partsType);
                    if(tm == 1){
                        return "站点"
                    }else{
                        return "车辆"
                    }
                }
            },
            {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                   var tm = null2black(row.createTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }else{
                       return tm;
                    }

                }
            }]
    });
    //复选框全选控制
    var active_class = 'active';
    $('#bdfs-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function chkplClick() {
    $('#bdfs-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}
function openDialogDetail(domId, rowid) {
    logicalId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    checkName = 1;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(json);

    $("#moduleTypeNameEdit").val(json.moduleName);

        if(json.partsType ==1){
            $("#modulePartsType").val("站点");
        }else if(json.partsType ==2){
            $("#modulePartsType").val("车辆");
        }else{
        $("#modulePartsType").val("");
    }

    $("#workpointsEdit").val(json.workpoints);
    $("#fatherEstate").val(json.estateName);

    $("#moduleTypeNewModalLabel").html("部件类型详情");
    $("#moduleTypeForm").modal('show');
}

function verifyForm() {
    var regmcz = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
    var regmcy = /[a-zA-Z]/;

    if (!isNotEmpty($("#moduleTypeNameEdit").val())) {
        layer.tips('不能为空', '#chkModuleTypeNameEdit');
        return false;
    }
    if (checkName == 0) {
        layer.tips('部件类型已经存在', '#chkModuleTypeNameEdit');
        return false;
    }
    if($("#workpointsEdit").val()>10 || $("#workpointsEdit").val()< 0 ){
        layer.tips('部件类型工分最大10分，最小0分', '#chkWorkPointsEdit');
        return false;
    }
    if( $("#workpointsEdit").val().indexOf(".")>0){
        layer.tips('部件类型工分不可以是小数', '#chkWorkPointsEdit');
        return false;
    }

    return true;
}
function verifyForm2() {
    var regmcz = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
    var regmcy = /[a-zA-Z]/;

    if (!isNotEmpty($("#moduleTypeName").val())) {
        layer.tips('不能为空', '#chkModuleTypeName');
        return false;
    }
    if (checkName == 0) {
        layer.tips('部件类型已经存在', '#chkModuleTypeName');
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

function checkModuleName() {
    if ('updata' == isBtn && $("#moduleTypeName").val() == rowJson.moduleName) {
        checkName = 1;
    }
    else {
        var moduleName = $("#moduleTypeName").val()

        // $.ajax({
        //     type: 'GET',
        //     url: '/business-resource/estateTypes/checkByEstateTypeNameAndCategory?estateTypeName=' + $("#moduleTypeName").val() + '&category=' + 2,
        //     dataType: 'JSON',
        //     contentType: 'application/json',
        //     success: function (data) {
        //         console.log(data.message);
        //         if (data.code > 0) {
        //             checkName = 1;
        //         }
        //         else {
        //             checkName = 0;
        //         }
        //     }, error: function (e) {
        //         checkName = 0;
        //     }
        // });
    }
}

function checkModuleNameEdit() {
    if ('updata' == isBtn && $("#moduleTypeNameEdit").val() == rowJson.moduleName) {
        checkName = 1;
    }
    else {
        var moduleName = $("#moduleTypeNameEdit").val()

        $.ajax({
            type: 'GET',
            url: '/business-resource/estateTypes/checkByEstateTypeNameAndCategory?estateTypeName=' + $("#moduleTypeNameEdit").val() + '&category=' + 2,
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (data) {
                console.log(data.message);
                if (data.code > 0) {
                    checkName = 1;
                }
                else {
                    checkName = 0;
                }

            }, error: function (e) {
                checkName = 0;
            }
        });
    }
}


function moduleSave() {
    $("#dangerMsg").html('');
    if (!verifyForm()) {
        return;
    }

    var param = rowJson;
    param.name = $("#moduleTypeNameEdit").val();
    param.workpoints = $("#workpointsEdit").val();
    param.category =  2;
    $.ajax({
        type: 'PUT',
        url: '/business-resource/estateSubTypes/' + logicalId,
        dataType: 'JSON',
        contentType: 'application/json',
        data: JSON.stringify(param),
        success: function (data) {
            //数据修改
            console.log(data);
            if (data.code == 30000) {
                var myTable = $('#moduleType-list').DataTable();

                myTable.cell(rowNo, 1).data(param.name);//更新行
                rowJson.moduleName = param.name;

                myTable.cell(rowNo, 2).data(param.partsType);//更新行
                rowJson.partsType = param.partsType;

                myTable.cell(rowNo, 3).data(param.workpoints);//更新行
                rowJson.workpoints = param.workpoints;

                // myTable.cell(rowNo, 4).data(param.estateName);//更新行
                // rowJson.estateName = param.estateName;

                $("#btnEdit_" + logicalId).attr('json', JSON.stringify(rowJson).replace(/"/g, '\\\''))
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                setTimeout("location.reload()",ALERTMESSAGE);
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
    // }

    $("#moduleTypeForm").modal('hide');
}

function moduleBatchSave() {
    $("#dangerMsg").html('');
    if (!verifyForm2()) {
        return;
    }
    var str = [];
    for (var i = 0; i < document.getElementsByName('plsbids').length; i++) {
        if (document.getElementsByName('plsbids')[i].checked) {
            str.push(document.getElementsByName('plsbids')[i].value);
        }
    }
    console.log(str);
    if (str.length == 0) {
        // BootstrapDialog.show({
        //     title: '提示',
        //     message: '您没有选择任何内容',
        //     type: BootstrapDialog.TYPE_DANGER, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
        //     closable: true, // <-- Default value is false
        //     draggable: true, // <-- Default value is false
        //     buttons: [{
        //         label: '确定',
        //         cssClass: 'btn-warning',
        //         action: function (dialogItself) {
        //             dialogItself.close();
        //         }
        //     }]
        // });
        layer.tips("请选择设备类型","#the")

    }
    else {
        var param = {
            name: $("#moduleTypeName").val(),
            partsType: $("#partsType").val(),
            workpoints: $("#workpoints").val(),
            category: 2,
        }

        $.ajax({
            type: 'POST',
            url: '/business-resource/estateSubTypes?estateTypeIds=' + str,
            dataType: 'JSON',
            contentType: 'application/json',
            data: JSON.stringify(param),
            success: function (data) {
                //新增
                console.log(data);
                if (data.code > 0) {
                    $('.alert-success').show();
                    setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                    setTimeout("location.reload()", 1000);
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

        $("#moduleTypeForm2").modal('hide');
    }

}

function moduleCancel() {
    $("#moduleTypeForm").modal('hide');
}
function moduleBatchCancel() {
    $("#moduleTypeForm2").modal('hide');
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
                    //ajax提交
                    $.ajax({
                        type: 'DELETE',
                        url: '/business-resource/estateSubTypes/' + str,
                        dataType: 'JSON',
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.code == 20000) {
                                setTimeout("location.reload()", ALERTMESSAGET);
                                var idArray = str.split(',');
                                //页面树和dt更新
                                for (i = 0; i < idArray.length; i++) {
                                    var did = idArray[i];
                                    var  temp = 'estateTypeId_'+clickEstateType+'_moduleTypeId_'+clickModuleType
                                    // var t1 =clickEstateType ;
                                    //
                                    // var t = clickModuleType;
                                    //更新树
                                    var newTreeMap = new Array();
                                    for (var i = 0; i < treeMap.length; i++) {
                                        var j = treeMap[i];
                                        if (temp != j.id) {
                                            newTreeMap.push(j);
                                        }
                                    }
                                    treeMap = newTreeMap;

                                    var newTreeData = new Array();
                                    for (var i = 0; i < treeData.length; i++) {
                                        var k = treeData[i];
                                        if (temp != k.id) {
                                            newTreeData.push(k);
                                        }
                                    }
                                    treeData = newTreeData;

                                    $('#moduleType-tree').jstree(true).settings.core.data = treeData;
                                    $('#moduleType-tree').jstree(true).refresh();



                                    var button = $("#div_button").html();
                                    var oldOptions = dataTable.fnSettings();
                                    dataTable.fnDestroy();
                                    $('#moduleType-list').dataTable({
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
                                        "data": dtdata,
                                        "columns": [{
                                            "data": "id"
                                        }, {
                                            "data": "moduleName"
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
                                            }, {
                                                'targets': 2,
                                                'searchable': false,
                                                'orderable': false,
                                                'className': 'dt-body-center',
                                                'bSortable': false,
                                                'render': function (data, type, row, meta) {
                                                    partsType = null2black(row.partsType);
                                                    if (partsType == 1) {
                                                        return CONSTANTS.MODULEPARTTYPE.STATION;
                                                    } else if (partsType == 2) {
                                                        return CONSTANTS.MODULEPARTTYPE.BICYCLE;
                                                    } else {
                                                        return partsType;
                                                    }
                                                }
                                            }, {
                                                'targets': 3,
                                                'searchable': false,
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
                                                    return '<button id="btnEdit_' + row.moduleTypeId + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                                                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.moduleTypeId + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                                                }
                                            }]
                                    });
                                    addBtn();
                                    $('#div_button').append(button);
                                }
                                $('.alert-success').show();
                                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
                            }
                            else {
                                $("#dangerMsg").html(data.responseJSON.message);
                                $('.alert-danger').show();
                                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                            }
                            console.log(data);
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
    var url = "/business-resource/estateTypes";
    reloadTable(dataTable, '#moduleType-list', url, 'results');
    addBtn();
    $('#div_button').append(button);
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}