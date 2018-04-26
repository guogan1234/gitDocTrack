var dataTable = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var corpId = "";
$(function () {
    pageSetUp();
    //initDataTable();
    initSelect();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('stockDetails_list');

})

/* DataTable 加载 */
function initDataTable() {
    dataTable = $('#stockDetails-list').dataTable({
        "columnDefs": [{
            orderable: false,//禁用排序
            targets: [0]   //指定的列
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
        "aaSorting": [[8, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": "/business-resource/stockWorkOrderDetails/findConditions?corpId=" + corpId,
            "dataSrc": "results"
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
            "data": "stockWorkOrderTypeName"
        }, {
            "data": "corpName"
        }, {
            "data": "applyUserName"
        }, {
            "data": "processUserName"
        }, {
            "data": "applyTime"
        }],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input type="checkbox" onclick ="chkClick()" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            }, {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.category)
                    if (tm == 1) {
                        return "站点";
                    } else if (tm == 2) {
                        return "车辆";
                    } else {
                        return "";
                    }
                }
            },
            {
                'targets':7,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                   return null2black(row.processUserName);

                }
            },



            {
                'targets': 8,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.applyTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }
                    return "";

                }
            },
            // {
            //     'targets': 9,
            //     'searchable': false,
            //     'orderable': false,
            //     'className': 'dt-body-center',
            //     'bSortable': false,
            //     'render': function (data, type, row, meta) {
            //         return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
            //             + '" data-toggle="modal" onclick="openDialogDetail(' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
            //     }
            // }
            ]
    });


    //复选框全选控制
    var active_class = 'active';
    $('#stockDetails-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function initSelect() {
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
            if (retData.results.length > 1) {
                for (var i = 0; i < retData.results.length; i++) {
                    data.push({id: retData.results[i].id, text: retData.results[i].corpName});
                }
                $("#selectCorp").select2({
                    data: data,
                    placeholder: '请选择',
                    allowClear: true
                });

            } else {
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


    var data2 = [];
    data2.push({id: 1, text: "领料"});
    data2.push({id: 2, text: "归还"});
    // data2.push({id: 4, text: "报废"});
    $("#stockWorkOrderTypeId").select2({
        data: data2,
        placeholder: '请选择',
        allowClear: true
    });
}

//查询
function search() {
    var selectCorp = $("#selectCorp").val();
    var category = $("#category").val();
    var estateTypeId = $("#estateTypeId").val();
    var stockWorkOrderTypeId = $("#stockWorkOrderTypeId").val();

    if (selectCorp == "") {
        layer.tips('不能为空', '#cheCorp');
        return;
    }
    if (category == "") {
        category = null;
    }
    if (estateTypeId == "") {
        estateTypeId == null;
    }
    if (stockWorkOrderTypeId == "") {
        stockWorkOrderTypeId = null;
    }
    var oldoptions = dataTable.fnSettings();
    var newoptions = $.extend(oldoptions, {
        "ajax": {
            url: "/business-resource/stockWorkOrderDetails/findConditions",
            data: {
                corpId: $("#selectCorp").val(),
                category: category,
                estateTypeId: estateTypeId,
                stockWorkOrderTypeId: stockWorkOrderTypeId
            },
            dataSrc: 'results'
        },
        "iDisplayLength": Number($('select[name=stockDetails-list_length]').val()),
        fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        }
    })
    dataTable.fnDestroy();
    $('#stockDetails-list').dataTable(newoptions);

}
function openDialogDetail(domId, rowid) {
    stockWorkOrderId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    //照片
    $.ajax({
        type: 'GET',
        url: '/business-resource/stockWorkOrderResources/findByStockWorkOrderId?stockWorkOrderId=' + stockWorkOrderId,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {

            var scrapPicture = [];

            for (var i = 0; i < retData.results.length; i++) {
                scrapPicture.push(retData.results[i].fileUrl);
            }
            if (scrapPicture.length > 0) {
                var html = '';
                for (var i = 0; i < reportPicture.length; i++) {
                    html += '<span class="col-sm-2"> <img src= ' + scrapPicture[i] + '  width="128px" height="128px" onclick="javascript:window.open(this.src);" style="cursor:pointer;"/></span>';
                }
                $('#scrapPictureList').html(html);
            }
            else {
                $("#scrapPicture").css('display', 'none');
            }


        }
    });


    $("#myModalLabel").html("照片详情");
    $("#addSupplierFrom").modal('show');
}
function chkClick() {
    $('#stockDetails-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function save() {
    $("#addGysForm").modal('hide');
}
function cancel() {
    $("#addGysForm").modal('hide');
}
function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}