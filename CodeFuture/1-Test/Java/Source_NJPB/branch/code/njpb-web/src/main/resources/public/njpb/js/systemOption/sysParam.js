/**
 * Created by len on 2017/9/19.
 */

var id = null;
$(function () {
    initParam();
    $('#dashboardMenu').removeClass('active');
    $('#left-wire').addClass('active').parent().css('display', 'block').parent().addClass('active open');
    initLeftActive('param_list');
})

function initParam() {
    $.ajax({
        type: 'GET',
        url: '/business-resource/sysParams',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (retData) {
            var data = retData.results[0];
            if(data != null){
                id = data.id;
                $("#oneRespondTime").val(data.workOrderLevelOneResponseTime);
                $("#oneRepairTime").val(data.workOrderLevelOneRepairTime);
                $("#twoRespondTime").val(data.workOrderLevelTwoResponseTime);
                $("#twoRepairTime").val(data.workOrderLevelTwoRepairTime);
                $("#threeRespondTime").val(data.workOrderLevelThreeResponseTime);
                $("#threeRepairTime").val(data.workOrderLevelThreeRepairTime);
                $("#urgencyRespondTime").val(data.workOrderLevelEmergencyResponseTime);
                $("#urgencyRepairTime").val(data.workOrderLevelEmergencyRepairTime);

                $("#dayBeginTime").val(data.workStartTime);
                $("#dayEndTime").val(data.workEndTime);
                $("#checkBikeBeginTime").val(data.stockCheckStartTime);
                $("#checkBikeEndTime").val(data.stockCheckEndTime);

                if (isNotEmpty(data.workStartTime)) {
                    $("#dayBeginTime").val(dateToStr(data.workStartTime, 'HH:mm'));
                }
                else {
                    $("#dayBeginTime").val('');
                }
                if (isNotEmpty(data.workEndTime)) {
                    $("#dayEndTime").val(dateToStr(data.workEndTime, 'HH:mm'));
                }
                else {
                    $("#dayEndTime").val('');
                }


                if (isNotEmpty(data.stockCheckStartTime)) {
                    $("#checkBikeBeginTime").val(dateToStr(data.stockCheckStartTime, 'YYYY-MM-DD HH:mm'));
                }
                else {
                    $("#checkBikeBeginTime").val('');
                }
                if (isNotEmpty(data.stockCheckEndTime)) {
                    $("#checkBikeEndTime").val(dateToStr(data.stockCheckEndTime, 'YYYY-MM-DD HH:mm'));
                }
                else {
                    $("#checkBikeEndTime").val('');
                }

                var stockCheckVersion = data.stockCheckVersion;

                var workPeriods = data.workPeriod.split("");
                for (var i = 0; i < workPeriods.length; i++) {
                    if (workPeriods[i] == "1") {
                        $("input[type='checkbox']")[i].checked = true;
                        $("#week" + (i + 1)).addClass("active");
                    } else {
                        $("input[type='checkbox']")[i].checked = false;
                        //$("#week" + (i + 1)).removeClass("active");
                    }
                }
            }

        }
    });
}

function save() {

    var workPeriodT = "";
    for (var i = 1; i < 8; i++) {
        if ($("#m" + i).is(':checked')) {
            workPeriodT += "1";
        } else {
            workPeriodT += "0";
        }
    }

    var dayBeginTime = $("#dayBeginTime").val();
    var today  = dateToStr(new Date, 'YYYY-MM-DD');

    if (isNotEmpty($("#dayBeginTime").val())) {
        dayBeginTime = new Date(today+" "+$("#dayBeginTime").val());
    }
    var dayEndTime = null;
    if (isNotEmpty($("#dayEndTime").val())) {
        dayEndTime = new Date(today+" "+$("#dayEndTime").val());
    }

    var checkBikeBeginTime = null;
    if (isNotEmpty($("#checkBikeBeginTime").val())) {
        checkBikeBeginTime = new Date($("#checkBikeBeginTime").val());
    }
    var checkBikeEndTime = null;
    if (isNotEmpty($("#checkBikeEndTime").val())) {
        checkBikeEndTime = new Date($("#checkBikeEndTime").val());
    }

    var param = {
        id: id,
        workPeriod: workPeriodT,
        workOrderLevelOneResponseTime: $("#oneRespondTime").val(),
        workOrderLevelOneRepairTime: $("#oneRepairTime").val(),
        workOrderLevelTwoResponseTime: $("#twoRespondTime").val(),
        workOrderLevelTwoRepairTime: $("#twoRepairTime").val(),
        workOrderLevelThreeResponseTime: $("#threeRespondTime").val(),
        workOrderLevelThreeRepairTime: $("#threeRepairTime").val(),
        workOrderLevelEmergencyResponseTime: $("#urgencyRespondTime").val(),
        workOrderLevelEmergencyRepairTime: $("#urgencyRepairTime").val(),
        workStartTime: dayBeginTime,
        workEndTime: dayEndTime,
        stockCheckStartTime: checkBikeBeginTime,
        stockCheckEndTime:checkBikeEndTime,
    }
    $.ajax({
        type: 'POST',
        url: '/business-resource/sysParams',
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == 0) {
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
            } else {
                $("#dangerMsg").html(data.responseJSON.message);
                $('.alert-danger').show();
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                //location.reload(true);
            }
        },
        error: function (e) {

            $("#dangerMsg").html(e.responseJSON.message);
            $('.alert-danger').show();
            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            //location.reload(true);
        }
    });

}