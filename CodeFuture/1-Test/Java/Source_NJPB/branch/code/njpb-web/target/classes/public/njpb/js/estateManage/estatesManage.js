/**
 * Created by len on 2017/9/2.
 */
var dataTableStation = null;
var dataTableBike = null;
var isBtn = '';
var logicalId = '';//更新时的KEY
var rowNo = '';//更新时的行号
var rowJson = '';//更新时行对应的json
var listIds = '';
var pageParam = {};
var estateMap = new Map();
var estateName = 0;
var stationEstateName = 0;
var chkBikeFrameNo = "";
var count = 0;
$(function () {


    pageSetUp();
    initStationDataTable();
    initBikeDataTable();
    addBtn();
    addBikeBtn()
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initSelect();
    initLeftActive('ES_estate');

})


function initEditSelect() {
    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1 + "&partsType=" + 1,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {

            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#stationEstateTypeNameEdit").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });

            $("#stationEstateTypeNameEdit").val(rowJson.estateTypeId).trigger("change");

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
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            }
            $("#stationEstateCorpEdit").select2({
                data: data,

                allowClear: true
            });
            $("#stationEstateCorpEdit").val(rowJson.projectId).trigger("change");
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });

    //查询站点
    $.ajax({
        type: 'GET',
        url: '/business-resource/stations/',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({
                    id: retData.results[i].id,
                    text: retData.results[i].stationNo + retData.results[i].stationName
                });
            }
            $("#stationEstateStationNameEdit").select2({
                data: data,

                allowClear: true
            });
            $("#stationEstateStationNameEdit").val(rowJson.stationId).trigger("change");
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });

}

function initAddSelect() {
    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1 + "&partsType=" + 1,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {

            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#stationEstateType").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });

            $("#stationEstateType").val(rowJson.estateTypeId).trigger("change");

            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });


    // //查询公司
    // $.ajax({
    //     type: 'GET',
    //     url: '/business-resource/corporations/findByUid',
    //     dataType: 'JSON',
    //     contentType: 'application/json',
    //     success: function (retData) {
    //         var data = [];
    //         for (var i = 0; i < retData.results.length; i++) {
    //             data.push({id: retData.results[i].id, text: retData.results[i].corpName});
    //         }
    //         $("#stationEstateCorp").select2({
    //             data: data,
    //             placeholder: '请选择',
    //             allowClear: true
    //         });
    //         $("#stationEstateCorp").val(rowJson.estateTypeId).trigger("change");
    //         $.fn.modal.Constructor.prototype.enforceFocus = function () {
    //         };
    //     },
    //     failure: function () {
    //     }
    // });


    //
    // //站点
    // $("#stationEstateStationName").select2({
    //     language: {
    //         inputTooShort: function (args) {
    //             // args.minimum is the minimum required length
    //             // args.input is the user-typed text
    //             return "请输入查询关键字";
    //         },
    //         inputTooLong: function (args) {
    //             // args.maximum is the maximum allowed length
    //             // args.input is the user-typed text
    //             return "You typed too much";
    //         },
    //         errorLoading: function () {
    //             return "加载错误";
    //         },
    //         loadingMore: function () {
    //             return "加载更多";
    //         },
    //         noResults: function () {
    //             return "没有查到结果";
    //         },
    //         searching: function () {
    //             return "查询中...";
    //         },
    //         maximumSelected: function (args) {
    //             // args.maximum is the maximum number of items the user may select
    //             return "加载错误";
    //         }
    //     },
    //     theme: "bootstrap",
    //     allowClear: true,
    //     placeholder: "请选择",
    //     ajax: {
    //         type: 'GET',
    //         url: '/business-resource/stations/findByStationNoNameLike',
    //         dataType: 'json',
    //         delay: 250,
    //         data: function (params) {
    //             return {
    //                 stationNoName: params.term, // search term
    //                 page: params.page,
    //                 size: 30
    //             };
    //         },
    //         processResults: function (data, params) {
    //             // parse the results into the format expected by Select2
    //             // since we are using custom formatting functions we do not need to
    //             // alter the remote JSON data, except to indicate that infinite
    //             // scrolling can be used
    //             params.page = params.page || 0;
    //
    //             return {
    //                 results: data.results,
    //                 pagination: {
    //                     more: (params.page * 30) < data.totalElements
    //                 }
    //             };
    //         },
    //         cache: true
    //     },
    //     escapeMarkup: function (markup) {
    //         return markup;
    //     }, // let our custom formatter work
    //     minimumInputLength: 1,
    //     templateResult: function (repo) {
    //         if (repo.loading)
    //             return repo.text;
    //         var markup = "<div class='select2-result-repository__description' id='s_pch_option_"
    //             + repo.id
    //             + "' >"
    //             + repo.stationNoName + "</div>";
    //
    //         return markup;
    //     }, // omitted for brevity, see the source of this page
    //     templateSelection: function (repo) {
    //         return repo.stationNoName;
    //     } // omitted for brevity, see the source of this page
    // });

}


function initSelect() {

    //设备类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1 + "&partsType=" + 1,
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
            // for (var i = 0; i < retData.results.length; i++) {
            //     data.push({id: retData.results[i].id, text: retData.results[i].corpName});
            // }
            // $("#selectCorp").select2({
            //     data: data,
            //     placeholder: '请选择',
            //     allowClear: true
            // });
            //
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


            $("#stationEstateCorp").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });
            $("#stationEstateCorp").val(corpId).trigger("change");
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }


    });


    var data = [];
    $("#stationId").select2({
        data: data,
        placeholder: '请先选择公司',
        allowClear: true
    });
    // $("#stationId").select2({
    //
    //  });

}

function dynamicUrls() {
    if (isNotEmpty($("#selectCorp").val())) {
        //站点
        $("#stationId").empty();
        $("#stationId").select2({
            language: {
                inputTooShort: function (args) {
                    // args.minimum is the minimum required length
                    // args.input is the user-typed text
                    // if (!isNotEmpty($("#selectCorp").val())) {
                    //     url =dynamicUrls();
                    //     return "请选择公司。"
                    // } else {
                    //     url =dynamicUrls();
                    return "请输入查询关键字";
                    // }

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
            ajax: ({
                type: 'GET',
                url: '/business-resource/stations/findByStationNoNameLike?corpId=' + $("#selectCorp").val(),
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
            }),
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

    } else {
        layer.tips('请选择公司', '#cheCorp');
    }
}

function dynamicUrls2() {
    if (isNotEmpty($("#stationEstateCorp").val())) {
        t = $("#stationEstateCorp").val();
        //站点
        $("#stationEstateStationName").empty();
        $("#stationEstateStationName").select2({
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
            ajax: ({
                type: 'GET',
                url: '/business-resource/stations/findByStationNoNameLike?corpId=' + $("#stationEstateCorp").val(),
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
            }),
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

    } else {
        layer.tips('请选择公司', '#chkStationEstateCorp');

    }
}

/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#mytoolbox').append(dataPlugin);
}


/*DataTable 添加 添加按钮*/
function addBikeBtn() {
    var dataPluginBike =
        '<div id="div_bike_button" class="pull-left dateRange" style="margin-left: 10px"> ' +
        '</div> ';
    $('#mytoolboxBike').append(dataPluginBike);
}

/* DataTable 加载 */
function initStationDataTable() {
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "estateNo";
                        break;
                    case 2:
                        param.orderColumn = "estateSn";
                        break;
                    //         case 4:
                    //             param.orderColumn = "organizationName";
                    //             break;
                    //         case 5:
                    //             param.orderColumn = "estateTypeName";
                    //             break;
                    //         case 6:
                    //             param.orderColumn = "orderTypeNameCn";
                    //             break;
                    //         case 7:
                    //             param.orderColumn = "faultDescriptionNameCn";
                    //             break;
                    //         case 8:
                    //             param.orderColumn = "workOrderStatusNameCn";
                    //             break;
                    // case 9:
                    //     param.orderColumn = "reportTime";
                    //     break;
                    //         case 10:
                    //             param.orderColumn = "assignEmployeeUserName";
                    //             break;
                    //         case 11:
                    //             param.orderColumn = "repairEmployeeUserName";
                    //             break;
                    //         default:
                    //             param.orderColumn = "serialNo";
                    //             break;
                }
                param.orderDir = data.order[0].dir;
            }
            //排序
            param.sort = param.orderColumn + ',' + param.orderDir;
            param.corpId = pageParam.corpId;
            param.stationId = pageParam.stationId;
            param.category = 0;//类别
            param.estateTypeId = pageParam.estateTypeId;
            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTableStation = $('#estateMange-list').dataTable({
        "searching": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
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
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/estates/findByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.recordsTotal = result.page.totalElements;
                    returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);
                }
            });
        },


        "columns": [{
            "data": "id"
        }, {
            "data": "estateNo"
        }, {
            "data": "estateSn"
        },
            //     {
            //     "data": "bicycleStakeBarCode"
            // },
            {
                "data": "estateTypeName"
            }, {
                "data": "estateName"
            }, {
                "data": "stationName"
            }, {
                "data": "corpName"
            }],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input onclick="chkClick()" type="checkbox" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            },
            {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,

                'render': function (data, type, row, meta) {
                    var tm = row.estateNo;
                    return null2black(tm);
                }
            },
            {
                'targets': 2,
                'searchable': false,
                'orderable': true,
                'className': 'dt-body-center',
                'bSortable': true,
                'render': function (data, type, row, meta) {
                    var tm = row.estateSn;
                    if (isNotEmpty(tm)) {
                        return null2black(tm);
                    } else if (isNotEmpty(row.bicycleStakeBarCode)) {
                        return row.bicycleStakeBarCode;
                    } else {
                        return "";
                    }

                }
            },
            // {
            //     'targets': 3,
            //     'className': 'dt-body-center',
            //     'render': function (data, type, row, meta) {
            //         var tm = row.bicycleStakeBarCode;
            //         return null2black(tm);
            //     }
            // },
            {
                'searchable': false,
                'orderable': false,
                'targets': 3,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.estateTypeName;
                    return null2black(tm);
                }
            },
            {
                'searchable': false,
                'orderable': false,
                'targets': 4,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.estateName;
                    return null2black(tm);
                }
            }, {
                'searchable': false,
                'orderable': false,
                'targets': 5,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.stationName;
                    return null2black(tm);
                }
            }, {
                'searchable': false,
                'orderable': false,
                'targets': 6,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.corpName;
                    return null2black(tm);
                }
            },
            {
                'targets': 7,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail(' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]
    }).api();
    //复选框全选控制
    var active_class = 'active';
    $('#estateMange-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}

function initBikeDataTable() {
    var workOrder = {
        getQueryCondition: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 1:
                        param.orderColumn = "estateNo";
                        break;
                    case 2:
                        param.orderColumn = "bikeFrameNo";
                        break;
                    //         case 4:
                    //             param.orderColumn = "organizationName";
                    //             break;
                    //         case 5:
                    //             param.orderColumn = "estateTypeName";
                    //             break;
                    //         case 6:
                    //             param.orderColumn = "orderTypeNameCn";
                    //             break;
                    //         case 7:
                    //             param.orderColumn = "faultDescriptionNameCn";
                    //             break;
                    //         case 8:
                    //             param.orderColumn = "workOrderStatusNameCn";
                    //             break;
                    // case 9:
                    //     param.orderColumn = "reportTime";
                    //     break;
                    //         case 10:
                    //             param.orderColumn = "assignEmployeeUserName";
                    //             break;
                    //         case 11:
                    //             param.orderColumn = "repairEmployeeUserName";
                    //             break;
                    //         default:
                    //             param.orderColumn = "serialNo";
                    //             break;
                }
                param.orderDir = data.order[0].dir;
            }
            //排序
            param.sort = param.orderColumn + ',' + param.orderDir;
            param.category = 1;//类别
            //组装分页参数 page size
            param.size = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length);//当前页码

            return param;
        }
    };
    dataTableBike = $('#bike-list').dataTable({
        "searching": false,
        "processing": true,
        "serverSide": true,
        "autoWidth": true,
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
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolboxBike'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = workOrder.getQueryCondition(data);
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/business-resource/estates/findBikesByConditions",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.recordsTotal = result.page.totalElements;
                    returnData.recordsFiltered = result.page.totalElements;
                    returnData.data = result.results;
                    allData = result.results;
                    callback(returnData);
                }
            });
        },


        "columns": [{
            "data": "id"
        }, {
            "data": "estateNo"
        }, {
            "data": "bikeFrameNo"
        }, {
            "data": "estateTypeName"
        }, {
            "data": "estateName"
        }, {
            "data": "supplierName"
        }],
        "columnDefs": [
            {
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return '<input onclick="chkClick()" type="checkbox" name="ids" value=' + row.id + ' id="chk' + row.id + '"  class="ace"/>';
                }
            },
            {
                'searchable': false,
                'orderable': true,
                'targets': 1,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.estateNo;
                    return null2black(tm);
                }
            },
            {
                'searchable': false,
                'orderable': false,
                'targets': 2,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.bikeFrameNo;
                    return null2black(tm);
                }
            },

            {
                'searchable': false,
                'orderable': false,
                'targets': 3,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.estateTypeName)
                    return tm;
                }
            }, {
                'searchable': false,
                'orderable': false,
                'targets': 4,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = null2black(row.estateName)
                    return tm;
                }
            },

            {
                'searchable': false,
                'orderable': false,
                'targets': 5,
                'className': 'dt-body-center',
                'render': function (data, type, row, meta) {
                    var tm = row.supplierName;
                    return null2black(tm);
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
                        + '" data-toggle="modal" onclick="openDialogDetailBike(' + row.id + ',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]
    }).api();
    //复选框全选控制
    var active_class = 'active';
    $('#estateMange-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function () {
            var row = this;
            if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


function chkClick() {
    $('#estateMange-list> thead > tr > th input[type=checkbox]').prop('checked', false);
}

//查询
function search() {
    pageParam.corpId = $("#selectCorp").val();//公司

    pageParam.stationId = $("#stationId").val();// 站点

    pageParam.estateTypeId = $("#estateTypeId").val();// 设备类型
    dataTableStation.ajax.reload()
}


//查询
function searchBike() {

    dataTableBike.ajax.reload()
}
//打开新建
function openNew() {
    rowJson = new Object();

    initAddSelect();
    $("#stationEstateName").val("");
    $("#myModalLabel").html("新建设备");
    $("#addEstateFromStation").modal('show');
}


function openBikeNew() {

    rowJson = new Object();
    $("#bike_frame_no").val("");
    $("#bikeCount").val("");
    $("#bike_frame_no_end").val("");


    $.ajax({
        type: 'GET',
        url: '/business-resource/estates/findBikesByCategory',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (retData.code == 40000) {
                $("#bike_frame_no_oldEnd").val(retData.result.bikeFrameNo);
            } else {
                $("#bike_frame_no_oldEnd").val("0");
            }
        },
        error: function (e) {
            alert(e);
        }

    });


    //供应商
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateSuppliers',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].supplierName});
            }
            $("#bikeModuleEstateSupplier").select2({
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

    //自行车部件类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1 + '&partsType= ' + 2,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#bikeModuleEstateType").select2({
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


    $("#myModalLabel2").html("新建设备");
    $("#addEstateFromBike").modal('show');

}


function openDialogDetail(domId, rowid) {
    logicalId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;

    $("#stationEstateNo").val(json.estateNo);
    $("#stationEstateTypeNameEdit").val(json.estateTypeName);
    // $("#stationEstateNameEdit").val(json.estateName);


    initEditSelect();
    $("#stationEstateNameEdit").val(json.estateName);
    $("#editModalLabel").html("设备详情");
    $("#editForm").modal('show');
}

function sumCount() {

    var t_b = $("#bike_frame_no").val();
    var t_s = $("#bikeCount").val();
    if (t_b == "") {
        layer.tips('只能为数字', '#chkBikeEstateEn');
        return;
    }
    if (t_s == "") {
        layer.tips('只能为数字', '#chkBikeCount');
        return;
    }
    ;


    var t = Number(t_b);
    var t2 = Number(t_s);
    var t3 = t + t2 - 1;
    $("#bike_frame_no_end").val(t3);

}

function checkBikeFrameNo() {
    var t_b = $("#bike_frame_no").val();
    if (t_b == "") {
        layer.tips('只能为数字', '#chkBikeEstateEn');
        return;
    } else {
        $.ajax({
            type: 'GET',
            url: '/business-resource/estates/checkBikeFrameNo?bikeFrameNo=' + $("#bike_frame_no").val(),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                var t_b = $("#bike_frame_no").val();
                var t_s = $("#bikeCount").val();
                if (t_s != "") {

                    var t = Number(t_b);
                    var t2 = Number(t_s);
                    var t3 = t + t2 - 1;
                    $("#bike_frame_no_end").val(t3);
                }

                if (data.code > 0) {
                    chkBikeFrameNo = 1;
                }
                else {
                    chkBikeFrameNo = 0;
                    layer.tips('不能为空', '#chkBikeFrameNo');
                    //return false;
                }
            }, error: function (e) {
                chkBikeFrameNo = 0;
                layer.tips('不能为空', '#chkBikeFrameNo');

            }
        });


    }

}

function openDialogDetailBike(domId, rowid) {
    logicalId = domId;
    rowNo = rowid;
    isBtn = 'updata';
    var json = $("#btnEdit_" + domId).attr('json').replace(/\\'/g, '"');
    json = JSON.parse(json);
    rowJson = json;

    $("#bike_frame_no_edit").val(json.bikeFrameNo);
    $("#bikeModuleEstateNameEdit").val(json.estateName);

    //供应商
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateSuppliers',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].supplierName});
            }
            $("#bikeModuleEstateSupplierEdit").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });

            $("#bikeModuleEstateSupplierEdit").val(rowJson.supplierId).trigger("change");

            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });

    //自行车部件类型
    $.ajax({
        type: 'GET',
        url: '/business-resource/estateTypes?category=' + 1 + '&partsType= ' + 2,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].name});
            }
            $("#bikeModuleEstateTypeEdit").select2({
                data: data,
                placeholder: '请选择',
                allowClear: true
            });
            $("#bikeModuleEstateTypeEdit").val(rowJson.estateTypeId).trigger("change");
            $.fn.modal.Constructor.prototype.enforceFocus = function () {
            };
        },
        failure: function () {
        }
    });

    $("#editModalLabel2").html("设备详情");
    $("#editFormBike").modal('show');
}

function stationEstateNameCheck() {

    $.ajax({
        type: 'GET',
        url: '/business-resource/estates/findStationIdAndEstateName?stationId=' + $("#stationEstateStationName").val() + "&estateName=" + $("#stationEstateName").val(),
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.resulte.length > 0) {
                stationEstateName = 1;
            }
            else {
                stationEstateName = 0;
            }
        }, error: function (e) {
            stationEstateName = 0;
        }
    });

}
//新建车辆设备保存
function saveBike() {
    $("#dangerMsg").html('');
    if (!addVerifyFormBike()) {
        return;
    }

    var param = {
        bikeFrameNo: $("#bike_frame_no").val(),
        estateTypeId: $("#bikeModuleEstateType").val(),
        supplierId: $("#bikeModuleEstateSupplier").val(),

    };
    console.log(param);
    $.ajax({
        type: 'POST',
        url: '/business-resource/bikes?count=' + $("#bikeCount").val(),
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.code == 10000) {
                searchBike();
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
    $("#addEstateFromBike").modal('hide');
}

function cancelBike() {
    $("#addEstateFromBike").modal('hide');
}

function test() {
    if ($("#stationEstateStationName").val() == "" || $("#stationEstateStationName").val() == null) {
        layer.tips('请选择站点', '#chkStationEstateStationName');
        return false;
    }

}
function addVerifyFormBike() {
    if (!isNotEmpty($("#bike_frame_no").val())) {
        layer.tips('不能为空', '#chkBikeFrameNo');
        return false;
    }
    ;
    if (!isNotEmpty($("#bikeCount").val())) {
        layer.tips('不能为空', '#chkbikeCount');
        return false;
    }
    ;
    // if (!isNotEmpty($("#bikeEstateName").val())) {
    //     layer.tips('不能为空', '#chkbikeEstateName');
    //     return false;
    // }

    // if(chkCardId == 0){
    //     layer.tips('芯片号已经存在', '#chkbikeCount');
    //     return false;
    // }


    if (chkBikeFrameNo == 0) {
        layer.tips('车架号已经存在', '#chkBikeFrameNo');
        return false;
    }
    if (!isNotEmpty($("#bikeModuleEstateType").val()) || $("#bikeModuleEstateType").val() == -1 || $("#bikeModuleEstateType").val() == 0) {
        layer.tips('不能为空', '#chkBikeModuleEstateType');
        return false;
    }

    if (!isNotEmpty($("#bikeModuleEstateSupplier").val()) || $("#bikeModuleEstateSupplier").val() == -1 || $("#bikeModuleEstateSupplier").val() == 0) {
        layer.tips('不能为空', '#chkBikeModuleEstateSupplier');
        return false;
    }


    return true;
}

function editVerifyFormBike() {
    // if (!isNotEmpty($("#bikeEstateNameEdit").val())) {
    //     layer.tips('不能为空', '#chkbikeEstateNameEdit');
    //     return false;
    // }

    if (!isNotEmpty($("#bikeModuleEstateSupplierEdit").val()) || $("#bikeModuleEstateSupplierEdit").val() == -1 || $("#bikeModuleEstateSupplierEdit").val() == 0) {
        layer.tips('不能为空', '#chkBikeModuleEstateSupplierEdit');
        return false;
    }
    if (!isNotEmpty($("#bikeModuleEstateTypeEdit").val()) || $("#bikeModuleEstateTypeEdit").val() == -1 || $("#bikeModuleEstateTypeEdit").val() == 0) {
        layer.tips('不能为空', '#chkBikeModuleEstateTypeEdit');
        return false;
    }
    return true;
}

//自行车设备修改页面保存
function edit_bike_save() {
    $("#dangerMsg").html('');
    if (!editVerifyFormBike()) {
        return;
    }
    var param = rowJson;
    param.estateTypeId = $("#bikeModuleEstateTypeEdit").val();
    param.supplierId = $("#bikeModuleEstateSupplierEdit").val();

    console.log(param);
    $.ajax({
        type: 'PUT',
        url: '/business-resource/estates/' + logicalId,
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            console.log(data.message);
            if (data.code == 30000) {
                searchBike();
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
    $("#editFormBike").modal('hide');
}
//自行车设备修改页面取消
function edit_bike_cancel() {
    $("#editFormBike").modal('hide');
}


//新建保存
function save() {
    $("#dangerMsg").html('');

    if (!addVerifyForm()) {
        return;
    }

    var param = {
        estateTypeId: $("#stationEstateType").val(),
        estateName: $("#stationEstateName").val(),
        projectId: $("#stationEstateCorp").val(),
        stationId: $("#stationEstateStationName").val(),
        category: 0,

    };
    console.log(param);
    $.ajax({
        type: 'POST',
        url: '/business-resource/estates',
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            if (data.code == 10000) {
                search();
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
    $("#addEstateFromStation").modal('hide');
}
//新建取消
function cancel() {
    $("#addEstateFromStation").modal('hide');
}


//站点设备修改页面保存
function edit_save() {
    $("#dangerMsg").html('');
    if (!editVerifyForm()) {
        return;
    }
    var param = rowJson;
    param.estateTypeId = $("#stationEstateTypeNameEdit").val();
    param.estateName = $("#stationEstateNameEdit").val();
    param.projectId = $("#stationEstateCorpEdit").val();
    param.stationId = $("#stationEstateStationNameEdit").val();

    console.log(param);
    $.ajax({
        type: 'PUT',
        url: '/business-resource/estates/' + logicalId,
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            console.log(data.message);
            if (data.code == 30000) {
                search();
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
    $("#editForm").modal('hide');
}
//站点设备修改页面取消
function edit_cancel() {
    $("#editForm").modal('hide');
}


function addVerifyForm() {
    if (!isNotEmpty($("#stationEstateName").val())) {
        layer.tips('不能为空', '#chkStationEstateName');
        return false;
    }

    if (!isNotEmpty($("#stationEstateType").val()) || $("#stationEstateType").val() == -1 || $("#stationEstateType").val() == 0) {
        layer.tips('不能为空', '#chkStationEstateType');
        return false;
    }

    if (!isNotEmpty($("#stationEstateCorp").val()) || $("#stationEstateCorp").val() == -1 || $("#stationEstateCorp").val() == 0) {
        layer.tips('不能为空', '#chkStationEstateCorp');
        return false;
    }

    if (!isNotEmpty($("#stationEstateStationName").val()) || $("#stationEstateStationName").val() == -1 || $("#stationEstateStationName").val() == 0) {
        layer.tips('不能为空', '#chkStationEstateStationName');
        return false;
    }
    if (stationEstateName == 1) {
        layer.tips('设备名已存在', '#chkStationEstateName');
        return false;
    }


    return true;
}

function editVerifyForm() {
    if (!isNotEmpty($("#stationEstateTypeNameEdit").val())) {
        layer.tips('不能为空', '#chkStationEstateTypeNameEdit');
        return false;
    }
    if (!isNotEmpty($("#stationEstateNameEdit").val())) {
        layer.tips('不能为空', '#chkStationEstateNameEdit');
        return false;
    }
    if (!isNotEmpty($("#stationEstateCorpEdit").val()) || $("#stationEstateCorpEdit").val() == -1 || $("#stationEstateCorpEdit").val() == 0) {
        layer.tips('不能为空', '#chkStationEstateCorpEdit');
        return false;
    }
    if (!isNotEmpty($("#stationEstateStationNameEdit").val()) || $("#stationEstateStationNameEdit").val() == -1 || $("#stationEstateStationNameEdit").val() == 0) {
        layer.tips('不能为空', '#chkStationEstateStationNameEdit');
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