/**
 * Created by len on 2017/9/4.
 */


// 百度地图API功能
function G(id) {
    return document.getElementById(id);
}

map = new BMap.Map("l-map");
map.centerAndZoom("南京", 12);                   // 初始化地图,设置城市和地图级别。


var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
    {
        "input": "suggestId"
        , "location": map
    });


ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
    var str = "";
    var _value = e.fromitem.value;
    var value = "";
    if (e.fromitem.index > -1) {
        value = _value.province + _value.city + _value.district + _value.street + _value.business;
    }
    str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

    value = "";
    if (e.toitem.index > -1) {
        _value = e.toitem.value;
        value = _value.province + _value.city + _value.district + _value.street + _value.business;
    }
    str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;

    G("searchResultPanel").innerHTML = str;
});

var myValue;
ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
    var _value = e.item.value;
    myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
    G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

    setPlace();
});

function setPlace() {
    map.clearOverlays();    //清除地图上所有覆盖物
    function myFun() {
        var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
        map.centerAndZoom(pp, 18);
        map.addOverlay(new BMap.Marker(pp));    //添加标注
        $('[id$=longitude]').val(pp.lng);
        $('[id$=latitude]').val(pp.lat);
    }

    var local = new BMap.LocalSearch(map, { //智能搜索
        onSearchComplete: myFun
    });
    local.search(myValue);

}

setTimeout(function () {
    map.setZoom(14);
}, 2000);  //2秒后放大到14级
map.enableScrollWheelZoom(true);
var lng;
var lat;
map.addEventListener("click", function (e) {
    var zoom = map.getZoom();
    map.clearOverlays();
    var point = new BMap.Point(e.point.lng, e.point.lat);
    var lng = e.point.lng;
    var lat = e.point.lat;
    var marker = new BMap.Marker(point, zoom);
    map.addOverlay(marker);

    $('#longitude').val(lng);
    $('#latitude').val(lat);

})

map.panBy(405, 165);

// 用经纬度设置地图中心点
function theLocation() {
    if (document.getElementById("longitude").value != "" && document.getElementById("latitude").value != "") {
          lng =document.getElementById("longitude").value;
          lat = document.getElementById("latitude").value;
        map.clearOverlays();
        var new_point = new BMap.Point(lng, lat);
        var marker = new BMap.Marker(new_point);  // 创建标注
        map.addOverlay(marker);              // 将标注添加到地图中
        map.panTo(new_point);

        map.centerAndZoom((lng, lat), 12);
        //map.setCenter(document.getElementById("longitude").value,document.getElementById("latitude").value)
        map.enableScrollWheelZoom(true);

        map.panBy(445, 165);
    } else {
        map.centerAndZoom("南京", 12);
        map.enableScrollWheelZoom(true);
    }
}
function clearMap(){
    map.clearOverlays();
    map.centerAndZoom("南京", 12);
    map.enableScrollWheelZoom(true);
}

