/**
 * Created by len on 2017/9/19.
 */
var dataTable = null;
var dataTable_details = null;
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var allData = null;
var USERNAME = "";
var pageParam = {};
var corpId = "";


$(function () {
    pageSetUp();
    initDataTable();
    addBtn();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('report_bikeCheck');
    initSelect();
})

$(function () {
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByUserId?userId=' + USER_ID,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.result != null) {
                var rr = retData.result;
                USERNAME = rr.userName;
                console.log(USERNAME);
            }
        },
        failure: function () {
        }
    });

});
function addBtn() {
    var dataPluginCD =
        '<div id="rep" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#reportBtns').append(dataPluginCD);
}

function initSelect() {
    //查询公司
    $.ajax({
        type: 'GET',
        url: '/business-resource/corporations/findByUid',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            corpId = retData.results[0].id;
            selectStation();
            selectOperator();
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

}

function chkStation() {
    corpId = $("#selectCorp").val();
    selectStation();
}
function selectStation() {
    var url = "";
    if(corpId == 1){
        url = '/business-resource/stations/findByStationNoNameLike';
    }else if(corpId >1){
        url = '/business-resource/stations/findByStationNoNameLike?corpId=' + corpId;
    }else{
        layer.tips("请选择公司","#chkCorp");
        return;
    }
    //站点
    $("#stationId").select2({
        language: {
            inputTooShort: function (args) {
                // args.minimum is the minimum required length
                // args.input is the user-typed text
                return "请输入查询关键字";
            },
            inputTooLong: function (args) {
                // args.maximum is the maximum allowed length
                // args.input is the user-typed text
                return "You typed too much";
            },
            errorLoading: function () {
                return "加载错误";
            },
            loadingMore: function () {
                return "加载更多";
            },
            noResults: function () {
                return "没有查到结果";
            },
            searching: function () {
                return "查询中...";
            },
            maximumSelected: function (args) {
                // args.maximum is the maximum number of items the user may select
                return "加载错误";
            }
        },
        theme: "bootstrap",
        allowClear: true,
        placeholder: "请选择",
        ajax: {
            type: 'GET',
            url: url,
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    stationNoName: params.term, // search term
                    page: params.page,
                    size: 30
                };
            },
            processResults: function (data, params) {
                // parse the results into the format expected by Select2
                // since we are using custom formatting functions we do not need to
                // alter the remote JSON data, except to indicate that infinite
                // scrolling can be used
                // params.page = params.page || 0;

                return {
                    results: data.results,
                    pagination: {
                        more: (params.page * 30) < data.totalElements
                    }
                };
            },
            cache: true
        },
        escapeMarkup: function (markup) {
            return markup;
        }, // let our custom formatter work
        minimumInputLength: 1,
        templateResult: function (repo) {
            if (repo.loading)
                return repo.text;
            var markup = "<div class='select2-result-repository__description' id='s_pch_option_"
                + repo.id
                + "' >"
                + repo.stationNoName + "</div>";

            return markup;
        }, // omitted for brevity, see the source of this page
        templateSelection: function (repo) {
            return repo.stationNoName;
        } // omitted for brevity, see the source of this page
    });
}

function selectOperator(){
    //查询操作人
    $.ajax({
        type: 'GET',
        url: '/business-resource/users/findByCorpId?corpId='+ corpId,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            corpId = retData.results[0].id;
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].userName});
            }
            $("#operator").select2({
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
}


function doSearch() {

    var beginTime1 = $("#beginTime").val()+" "+"00:00:00";
    var endtime1= $("#endtime").val()+" "+"23:59:59";

    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if ($("#stationId").val() != null && $("#stationId").val() > 0 && $("#stationId").val() != "") {
        pageParam.stationId = $("#stationId").val();//状态
    } else {
        pageParam.stationId = null;
    }
    if ($("#selectCorp").val() != null && $("#selectCorp").val() > 0 && $("#selectCorp").val() != "") {
        pageParam.corpId = $("#selectCorp").val();//状态
    } else {
        pageParam.corpId = null;
    }
    
    
    if ($("#operator").val() != null && $("#operator").val() > 0 && $("#operator").val() != "") {
        pageParam.userId = $("#operator").val();//状态
    } else {
        pageParam.operator = null;
    }
    if (isNotEmpty($("#beginTime").val())) {
        pageParam.beginTime = beginTime1;//开始时间
    }
    if (isNotEmpty($("#endtime").val())) {
        pageParam.endTime = endtime1;//结束时间
    }
    dataTable.ajax.reload();
}



function detailCancel() {
    $("#detailForm").modal('hide');
}

/* DataTable 加载 */
function initDataTable() {
    // var $wrapper = $('#div-table-container');
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            param.corpId = pageParam.corpId;
            param.stationId = pageParam.stationId;
            param.startDate = pageParam.beginTime;
            param.endDate = pageParam.endTime;
            param.userId = pageParam.userId;

            return param;
        }
    };
    dataTable = $('#bikeCheck-list').dataTable({
        //"searching": false,
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        // "oLanguage": {
        //     "sProcessing": "<span style='color:#ff0000;'><img src='/njpb/images/loading.gif'></span>",
        //     "sLoadingRecords": "载入中...",
        //     "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
        //     "sLengthMenu": "每页 _MENU_ 条记录",
        //     'sZeroRecords': '没有数据',
        //     'sInfo': '第 _PAGE_ 页 / 总 _PAGES_ 页,共 _TOTAL_项',
        //     'sInfoEmpty': '没有数据',
        //     'sInfoFiltered': '(过滤总件数 _MAX_ 条)',
        //     "oPaginate": {
        //         "sPrevious": "上一页",
        //         "sNext": "下一页"
        //     },
        //     "oAria": {
        //         "sSortAscending": ": 以升序排列此列",
        //         "sSortDescending": ": 以降序排列此列"
        //     }
        // },

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


        "iDisplayLength": 5,
        "aaSorting": [[5, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#reportBtns'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/vwInventoryCheckRecord/findByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    var returnData = {};
                    //
                    //
                    // returnData.recordsTotal = result.page.totalElements;
                    // returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);

                }
            });
        },


        //列表表头字段
        columns: [
            {"data": "id"},
            {"data": "checkUserName"},
            {"data": "corpName"},
            {"data": "stationNoName"},
            {"data": "count"},
            {"data": "checkTime"},
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
            },
            {
                'targets': 1,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.checkUserName);
                }
            },
            {
                'targets': 2,
                'orderable': false,
                'searchable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.corpName);
                }
            },
            {
                'targets': 5,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.checkTime);
                    if(isNotEmpty(tm)){
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    }else{
                        return "";
                    }

                }
            }]

    }).api();

    //复选框全选控制
    var active_class = 'active';
    $('#bikeCheck-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}
function chkClick() {
    $('#bikeCheck-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

function openDialogDetail(typeId, domId, rowid) {

    logicalId = domId;
    rowNo = rowid;
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;
    console.log(rowJson);
    pageParam.estateTypeId = typeId;

    dataTable_details.ajax.reload();
    $("#detailModalLabel").html("站点故障明细");
    $("#detailForm").modal('show');
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}

function exportExcel() {

    var beginTime1 = $("#beginTime").val()+" "+"00:00:00";
    var endtime1= $("#endtime").val()+" "+"23:59:59";
    // if(!isNotEmpty($("#selectCorp").val())){
    //     layer.tips('请先选择公司', '#chkCorp');
    //     return;
    // }
    var url = '/business-resource/bikeCheckRecord/exportCheckStatistics?1=1';
    if (beginTime1 > endtime1 && isNotEmpty($("#endtime").val())) {
        layer.tips('开始时间不能大于结束时间', '#beginTime');
        return;
    }

    if(isNotEmpty($("#selectCorp").val()))
    {
        if($("#selectCorp").val() != 1){
            url += '&corpId='+$("#selectCorp").val();
        }

    } else {
        if (corpId != 1) {
            url += '&projectId=' + corpId;
        }
    }

    if(isNotEmpty($("#stationId").val()))
    {
        url += '&stationId='+$("#stationId").val();
    }

    if(isNotEmpty($("#operator").val()))
    {
        url += '&userId='+$("#operator").val();
    }

    if(isNotEmpty($("#beginTime").val()))
    {
        url += '&startDate='+beginTime1;
    }

    if(isNotEmpty($("#endtime").val()))
    {
        url += '&endDate='+endtime1;
    }

    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.submit();
}