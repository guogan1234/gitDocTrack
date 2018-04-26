/**
 * Created by len on 2017/9/19.
 */

var corpId = null;
var refresh = 0;
var data = new Array();
var username = "";
var userPhone = "";
var corpName = "";
var marker = "";
var content = "";
var map = "";
var url1 = "";
var locationdsff = "";
var url = "";
$(function () {
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('map');
    initSelect();
})

function initMap() {

    // 百度地图API功能
    // var map = new BMap.Map("njpbMap");    // 创建Map实例
    // map.centerAndZoom(new BMap.Point(118.780, 32.040), 15);  // 初始化地图,设置中心点坐标和地图级别
    // map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP ]}));   //添加地图类型控件
    // map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
    // map.enableScrollWheelZoom(true);
    // if (refresh == 1) {
    //     map.clearOverlays();
    // }

    //查询工作人员
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (isNotEmpty(retData)) {


                // for (var i = 0; i < retData.results.length; i++) {
                //     var data = retData.results[i];
                //     var point = new BMap.Point(data.longitude, data.latitude);
                //
                //     var marker = new BMap.Marker(point);  // 创建标注
                //     map.addOverlay(marker);
                //
                //
                //     var opts = {
                //         width: 240,     // 信息窗口宽度
                //         height: 120,     // 信息窗口高度
                //         title: "<strong>在线员工：</strong><br>", // 信息窗口标题
                //     }
                //
                //     var recordTime = "";
                //     if (isNotEmpty(data.recordTime)) {
                //         recordTime = dateToStr(new Date(data.recordTime), 'YYYY-MM-DD HH:mm:ss');
                //     }
                //
                //     content = '<div id="LoginBox">'
                //         // + '<span  style="height: 15%;;">'+ '姓名:'+ data.stationName+'</span>'
                //
                //         + '<div  style="width: 200px;height: 15%;;">' + '姓名:' + data.userName + '</div>'
                //         + '<div style="width: 200px;height: 15%;"> ' + '手机:' + data.userPhone + ' </div>'
                //         + '<div  style="width: 200px;height: 15%;">  ' + '所在公司:' + data.corpName + '</div>'
                //         + '<div  style="width: 240px;height: 15%;"> ' + '上次同步时间:' + recordTime + '</div>'
                //         + '<div  style="width: 30%;height: 15%;display: inline; float:left; "> 位置信息: </div>'
                //         + '<div style="width: 10%;height:10%; float:left; ">'
                //         + '<input onclick="workDetails(this)" type="button" id=' + data.userId + ' value="一天轨迹详情" >' + '</div>'
                //         + '</div>';
                //
                //     infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                //
                //     marker.addEventListener("click", function () {
                //         map.openInfoWindow(infoWindow, point); //开启信息窗口
                //
                //         userName = data.userName;
                //         userPhone = data.userPhone;
                //         corpName = data.corpName;
                //         recordTimeLast = recordTime;
                //     });
                // }
                var data = retData.results[0];
                if (isNotEmpty(data)) {
                    // 百度地图API功能
                    var map = new BMap.Map("njpbMap");    // 创建Map实例
                    map.centerAndZoom(new BMap.Point(data.longitude, data.latitude), 15);  // 初始化地图,设置中心点坐标和地图级别
                    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]}));   //添加地图类型控件
                    //map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
                    map.enableScrollWheelZoom(true);
                } else {
                    // 百度地图API功能
                    var map = new BMap.Map("njpbMap");    // 创建Map实例
                    map.centerAndZoom(new BMap.Point(118.780, 32.040), 15);  // 初始化地图,设置中心点坐标和地图级别
                    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]}));   //添加地图类型控件
                    map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
                    map.enableScrollWheelZoom(true);

                }

                if (refresh == 1) {
                    map.clearOverlays();
                }


                var data_info = new Array();
                for (var i = 0; i < retData.results.length; i++) {
                    var data = retData.results[i];
                    var datas = new Array();
                    var recordTime = "";
                    if (isNotEmpty(data.recordTime)) {
                        recordTime = dateToStr(new Date(data.recordTime), 'YYYY-MM-DD HH:mm:ss');
                    }
                    datas.push(data.longitude);
                    datas.push(data.latitude);
                    var t = recordTime.split(" ");
                    var stringName = "" + data.userName + "," + data.userPhone + "," + data.corpName + "," + t[0] + "," + t[1];
                    content = '<div id="LoginBox">'
                        // + '<span  style="height: 15%;;">'+ '姓名:'+ data.stationName+'</span>'

                        + '<div  style="width: 200px;height: 15%;;">' + '姓名:' + data.userName + '</div>'
                        + '<div style="width: 200px;height: 15%;"> ' + '手机:' + data.userPhone + ' </div>'
                        + '<div  style="width: 200px;height: 15%;">  ' + '所在公司:' + data.corpName + '</div>'
                        + '<div  style="width: 240px;height: 15%;"> ' + '上次同步时间:' + recordTime + '</div>'
                        + '<div  style="width: 30%;height: 15%;display: inline; float:left; "> 位置信息: </div>'
                        + '<div style="width: 10%;height:10%; float:left; ">'
                        + '<input onclick="workDetails(this)" type="button" id=' + data.userId + ' name =' + stringName + '  value="一天轨迹详情" />' + '</div>'
                        + '</div>';

                    datas.push(content);
                    data_info.push(datas)
                }


                var opts = {
                    width: 250,     // 信息窗口宽度
                    height: 120,     // 信息窗口高度
                    title: "信息窗口", // 信息窗口标题
                    enableMessage: true//设置允许信息窗发送短息
                }


                for (var i = 0; i < data_info.length; i++) {
                    var marker = new BMap.Marker(new BMap.Point(data_info[i][0], data_info[i][1]));  // 创建标注

                    var content = data_info[i][2];
                    map.addOverlay(marker);               // 将标注添加到地图中
                    addClickHandler(content, marker);
                }
                function addClickHandler(content, marker) {
                    marker.addEventListener("click", function (e) {
                            openInfo(content, e)

                        }
                    );
                }

                function openInfo(content, e) {
                    var p = e.target;
                    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                    var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                    map.openInfoWindow(infoWindow, point); //开启信息窗口

                }
            }
        }
    });
}

function initUserMap(dataB) {
    //查询工作人员
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            if (isNotEmpty(retData)) {

                // var data = retData.results[0];
                if (isNotEmpty(dataB)) {
                    // 百度地图API功能
                    var map = new BMap.Map("njpbMap");    // 创建Map实例
                    map.centerAndZoom(new BMap.Point(dataB.longitude, dataB.latitude), 15);  // 初始化地图,设置中心点坐标和地图级别
                    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]}));   //添加地图类型控件

                    map.enableScrollWheelZoom(true);
                } else {
                    // 百度地图API功能
                    var map = new BMap.Map("njpbMap");    // 创建Map实例
                    map.centerAndZoom(new BMap.Point(118.780, 32.040), 15);  // 初始化地图,设置中心点坐标和地图级别
                    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP]}));   //添加地图类型控件
                    map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
                    map.enableScrollWheelZoom(true);

                }

                if (refresh == 1) {
                    map.clearOverlays();
                }


                var data_info = new Array();
                for (var i = 0; i < retData.results.length; i++) {
                    var data = retData.results[i];
                    var datas = new Array();
                    var recordTime = "";
                    if (isNotEmpty(data.recordTime)) {
                        recordTime = dateToStr(new Date(data.recordTime), 'YYYY-MM-DD HH:mm:ss');
                    }
                    datas.push(data.longitude);
                    datas.push(data.latitude);
                    var t = recordTime.split(" ");
                    var stringName = "" + data.userName + "," + data.userPhone + "," + data.corpName + "," + t[0] + "," + t[1];
                    content = '<div id="LoginBox">'
                        // + '<span  style="height: 15%;;">'+ '姓名:'+ data.stationName+'</span>'

                        + '<div  style="width: 200px;height: 15%;;">' + '姓名:' + data.userName + '</div>'
                        + '<div style="width: 200px;height: 15%;"> ' + '手机:' + data.userPhone + ' </div>'
                        + '<div  style="width: 200px;height: 15%;">  ' + '所在公司:' + data.corpName + '</div>'
                        + '<div  style="width: 240px;height: 15%;"> ' + '上次同步时间:' + recordTime + '</div>'
                        + '<div  style="width: 30%;height: 15%;display: inline; float:left; "> 位置信息: </div>'
                        + '<div style="width: 10%;height:10%; float:left; ">'
                        + '<input onclick="workDetails(this)" type="button" id=' + data.userId + ' name =' + stringName + '  value="一天轨迹详情" />' + '</div>'
                        + '</div>';

                    datas.push(content);
                    data_info.push(datas)
                }


                var opts = {
                    width: 250,     // 信息窗口宽度
                    height: 120,     // 信息窗口高度
                    title: "信息窗口", // 信息窗口标题
                    enableMessage: true//设置允许信息窗发送短息
                }


                for (var i = 0; i < data_info.length; i++) {
                    var marker = new BMap.Marker(new BMap.Point(data_info[i][0], data_info[i][1]));  // 创建标注

                    var content = data_info[i][2];
                    map.addOverlay(marker);               // 将标注添加到地图中
                    addClickHandler(content, marker);
                }
                function addClickHandler(content, marker) {
                    marker.addEventListener("click", function (e) {
                            openInfo(content, e)

                        }
                    );
                }

                function openInfo(content, e) {
                    var p = e.target;
                    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                    var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                    map.openInfoWindow(infoWindow, point); //开启信息窗口

                }
            }
        }
    });
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
            selectUserName();
            url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId;
            initMap();
            if (retData.results.length <= 1) {
                $("#chkCorp").css('display', 'none');
            }


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
}


function selectUserName() {
    var corp = $("#selectCorp").val();
    if (isNotEmpty(corp) && corp != 1) {
        url1 = '/business-resource/users/findByCorpId?corpId=' + corp;
    } else if (corp == 1) {
        url1 = '/business-resource/users/findByCorpId';
    } else {
        if (corpId == 1) {
            url1 = '/business-resource/users/findByCorpId';
        } else {
            url1 = '/business-resource/users/findByCorpId?corpId=' + corpId;
        }
    }

    //查询在线人
    $.ajax({
        type: 'GET',
        url: url1,
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = [];
            corpId = retData.results[0].id;
            $("#userNameOnline").empty();
            data.push({id: 0, text: '请选择'})
            for (var i = 0; i < retData.results.length; i++) {
                data.push({id: retData.results[i].id, text: retData.results[i].userName});
            }
            $("#userNameOnline").select2({
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


function workDetails(data) {
    var str = data.name.split(",");
    $("#userName").val(str[0]);
    $("#userPhone").val(str[1]);
    $("#userCorp").val(str[2]);
    $("#reportTime").val(str[3] + " " + str[4]);
    userId = data.id;
    initDataTable();


    $("#moduleTypeNewModalLabel2").html("员工工作轨迹");
    $("#moduleTypeForm2").modal('show');
}
/* DataTable 加载 */
function initDataTable() {
    var beginTime = $("#beginTime").val();
    var tableUrl = "";
    if (isNotEmpty(beginTime)) {
        var startTime = beginTime + " " + "00:00:00";
        var endTime = beginTime + " " + "23:59:59";
        tableUrl = '/business-resource/userPositionRecords/findByUserId?userId=' + userId + "&startTime=" + startTime + "&endTime=" + endTime;
    } else {
        tableUrl = '/business-resource/userPositionRecords/findByUserId?userId=' + userId;
    }

    dataTable2 = $('#workDetails-list').dataTable({
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
        "aaSorting": [[0, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
        "t" +
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
        "ajax": {
            "url": tableUrl,
            "dataSrc": "results"
        },

        "columns": [{
            "data": "recordTime"
        }, {
            "data": "location"
        }, {
            "data": "case1"
        }, {
            "data": "serialNo"
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
                    var tm = null2black(row.recordTime)
                    if (isNotEmpty(tm)) {
                        return dateToStr(new Date(tm), 'YYYY-MM-DD HH:mm:ss');
                    } else {
                        return tm;
                    }
                }
            },
            {
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.location);

                }
            }, {
                'targets': 2,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.case1);

                }
            }, {
                'targets': 3,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'bSortable': false,
                'render': function (data, type, row, meta) {
                    return null2black(row.serialNo);

                }
            },
        ]
    });
    // dataTable = $('#workDetails-list').dataTable({
    //
    //     "processing": true,
    //     "serverSide": false,
    //     "autoWidth": true,
    //     "oLanguage": {
    //         "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
    //         "sLengthMenu": "每页 _MENU_ 条记录",
    //         'sZeroRecords': '没有数据',
    //         'sInfo': '第 _PAGE_ 页 / 总 _PAGES_ 页,共 _TOTAL_ 项',
    //         'sInfoEmpty': '没有数据',
    //         'sInfoFiltered': '(过滤总件数 _MAX_ 条)',
    //         "oPaginate": {
    //             "sPrevious": "上一页",
    //             "sNext": "下一页"
    //         }
    //     },
    //     "iDisplayLength": 10,
    //     "aaSorting": [[1, 'desc']],
    //     "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
    //     "dom": "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>" +
    //     "t" +
    //     "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>",
    //     "ajax": {
    //         "url": '/business-resource/userPositionRecords/findByUserId?userId=' + 1,
    //         "dataSrc": "results"
    //     },
    //
    //     "columns": [{
    //         "data": "recordTime"
    //     }, {
    //         "data": "longitude"
    //     }, {
    //         "data": "case1"
    //     }, {
    //         "data": "serialNo"
    //     }
    //     ],
    //     "columnDefs": [
    //         {
    //             'targets': 0,
    //             'searchable': false,
    //             'orderable': false,
    //             'className': 'dt-body-center',
    //             'bSortable': false,
    //             'render': function (data, type, row, meta) {
    //                 var tm = null2black(row.recordTime)
    //                 if (isNotEmpty(tm)) {
    //                     return dateToStr(new Date(tm), 'YYYY-MM-DD');
    //                 } else {
    //                     return tm;
    //                 }
    //             }
    //         }, {
    //             'targets': 1,
    //             'searchable': false,
    //             'orderable': false,
    //             'className': 'dt-body-center',
    //             'bSortable': false,
    //             'render': function (data, type, row, meta) {
    //                  // getLocationSelf(row.longitude,row.latitude);
    //                 var tm ="";
    //                 return 12;
    //             }
    //         }, {
    //             'targets': 2,
    //             'searchable': false,
    //             'orderable': false,
    //             'className': 'dt-body-center',
    //             'bSortable': false,
    //             'render': function (data, type, row, meta) {
    //                 return null2black(row.case1);
    //
    //             }
    //         },
    //     ]
    // });
}

function doSerach() {
    refresh = 1;

    corpId = $("#selectCorp").val();
    var beginTime = $("#beginTime").val();

    if (isNotEmpty(beginTime)) {
        var startTime = beginTime + " " + "00:00:00";
        var endTime = beginTime + " " + "23:59:59";
        if (isNotEmpty(corpId) || corpId != "") {
            url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId + "&startTime=" + startTime + "&endTime=" + endTime;
            //initMap();
            var userNameOnline = $("#userNameOnline").val();
            if (isNotEmpty(userNameOnline) && userNameOnline != "" && userNameOnline > 0) {
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline + "&startTime=" + startTime + "&endTime=" + endTime,
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data1 = retData.result;
                        if (data1 == null) {
                            layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                            return;
                            // initMap();
                        } else {
                            initUserMap(data1);
                        }
                    },
                    error: function (e) {
                        layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                        return;
                    }

                });
            } else {
                initMap();
            }
        } else {

            var userNameOnline = $("#userNameOnline").val();

            if (isNotEmpty(userNameOnline) && userNameOnline != "" && userNameOnline > 0) {
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline + "&startTime=" + startTime + "&endTime=" + endTime,
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data = retData.result;

                        if (data == null) {
                            layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                            return;
                            // initMap();
                        } else {
                            url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + data.corpId + "&startTime=" + startTime + "&endTime=" + endTime;
                            initUserMap(data);
                        }
                    },
                    error: function (e) {
                        layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                        return;
                    }

                });

            } else {
                //查询公司
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/corporations/findByUid',
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data = [];
                        corpId = retData.results[0].id;
                        url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId + "&startTime=" + startTime + "&endTime=" + endTime;
                        initMap();
                    },

                });
            }
        }


    } else {
        if (isNotEmpty(corpId) || corpId != "") {
            url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId;
            //initMap();
            var userNameOnline = $("#userNameOnline").val();
            if (isNotEmpty(userNameOnline) && userNameOnline != "" && userNameOnline > 0) {
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline,
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data1 = retData.result;
                        if (data1 == null) {
                            layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                            return;
                            // initMap();
                        } else {
                            initUserMap(data1);
                        }
                    },
                    error: function (e) {
                        layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                        return;
                    }

                });
            } else {
                initMap();
            }
        } else {

            var userNameOnline = $("#userNameOnline").val();

            if (isNotEmpty(userNameOnline) && userNameOnline != "" && userNameOnline > 0) {
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline,
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data = retData.result;

                        if (data == null) {
                            layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                            return;
                            // initMap();
                        } else {
                            url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + data.corpId;
                            initUserMap(data);
                        }
                    },
                    error: function (e) {
                        layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
                        return;
                    }

                });

            } else {
                //查询公司
                $.ajax({
                    type: 'GET',
                    url: '/business-resource/corporations/findByUid',
                    dataType: 'JSON',
                    contentType: 'application/json',
                    success: function (retData) {
                        var data = [];
                        corpId = retData.results[0].id;
                        url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId;
                        initMap();
                    },

                });
            }
        }
    }
    //
    // if (isNotEmpty(corpId) || corpId != "") {
    //     url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId;
    //     //initMap();
    //     var userNameOnline = $("#userNameOnline").val();
    //     if (isNotEmpty(userNameOnline) && userNameOnline != "" &&　userNameOnline>0) {
    //         $.ajax({
    //             type: 'GET',
    //             url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline,
    //             dataType: 'JSON',
    //             contentType: 'application/json',
    //             success: function (retData) {
    //                 var data1= retData.result;
    //                 if (data1 == null) {
    //                     layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
    //                     return;
    //                     // initMap();
    //                 }else{
    //                     initUserMap(data1);
    //                 }
    //             },
    //             error:function (e) {
    //                 layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
    //                 return;
    //             }
    //
    //         });
    //     }else{
    //         initMap();
    //     }
    // } else {
    //
    //     var userNameOnline = $("#userNameOnline").val();
    //
    //     if (isNotEmpty(userNameOnline) && userNameOnline != "" &&　userNameOnline>0) {
    //             $.ajax({
    //                 type: 'GET',
    //                 url: '/business-resource/userPositionRecords/findLastPositionByUserId?userId=' + userNameOnline,
    //                 dataType: 'JSON',
    //                 contentType: 'application/json',
    //                 success: function (retData) {
    //                     var data= retData.result;
    //
    //                     if (data == null) {
    //                         layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
    //                         return;
    //                         // initMap();
    //                     }else{
    //                         url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + data.corpId;
    //                         initUserMap(data);
    //                     }
    //                 },
    //                 error:function (e) {
    //                     layer.tips('此工作人员还没有位置信息', '#chkUserNameOnline');
    //                     return;
    //                 }
    //
    //             });
    //
    //     }else{
    //         //查询公司
    //         $.ajax({
    //             type: 'GET',
    //             url: '/business-resource/corporations/findByUid',
    //             dataType: 'JSON',
    //             contentType: 'application/json',
    //             success: function (retData) {
    //                 var data = [];
    //                 corpId = retData.results[0].id;
    //                 url = "/business-resource/userPositionRecords/findLastPositionByCorpId?corpId=" + corpId;
    //                 initMap();
    //             },
    //
    //         });
    //     }
    // }
}


function getLocationSelf(lng, lat) {
    var myGeo = new BMap.Geocoder();
    var point = new BMap.Point(lng, lat)
    myGeo.getLocation(point, function (rs) {
        locationdsff = rs.address;
    });
    //return locationdsff;
}

function null2black(val) {
    if (!isNotEmpty(val) || 'null' == val) {
        return '';
    }
    return val;
}