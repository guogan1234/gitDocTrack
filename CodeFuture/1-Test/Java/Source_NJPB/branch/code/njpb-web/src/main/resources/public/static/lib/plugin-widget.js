/**
 * Created by pw on 2016/3/29.
 * version 0.0.1
 */


/**
 * 开始时间跟结束时间的插件 ， 时间控制结束时间必须大于开始时间
 * @param start 开始时间选取元素key
 * @param end 结束时间选取元素key
 * @param key 时间显示格式(yy.mm.dd)
*/
function date_start_end(start,end,key){
    $(start).datepicker({
        dateFormat : key,
        prevText : '<i class="fa fa-chevron-left"></i>',
        nextText : '<i class="fa fa-chevron-right"></i>',
        onSelect : function(selectedDate) {
            $(end).datepicker('option', 'minDate', selectedDate);
        }
    });

    $(end).datepicker({
        dateFormat : key,
        prevText : '<i class="fa fa-chevron-left"></i>',
        nextText : '<i class="fa fa-chevron-right"></i>',
        onSelect : function(selectedDate) {
            $(start).datepicker('option', 'maxDate', selectedDate);
        }
    });

}

/* 元素设置Date  */
function date_setDom(doms,key){
    $(doms).datepicker({
        dateFormat : key,
        prevText : '<i class="fa fa-chevron-left"></i>',
        nextText : '<i class="fa fa-chevron-right"></i>'
    });
}

/*select 控件加载值*/
function setSelectOptionUrlById(url,param,domKey,index){
    AJAX_GET_ONE(url,param,function(retData){
        setSelectOptionById(JSON.parse(retData.jdoc),domKey,index)
    });
}

function setSelectOptionById(json,domKey,index){
    //json = JSON.parse(json);
    var html = "";
    if (!isNotEmpty(index)){
        index = 0;
    }
    for(var i = 0 ; i < json.length ; i++){
        if(i == index){
            html += "<option id='"+domKey+"_option_"+json[i].id+"' value="+json[i].id+" selected=''>"+json[i].name+"</option>";
        }else{
            html += "<option id='"+domKey+"_option_"+json[i].id+"' value="+json[i].id+">"+json[i].name+"</option>";
        }
    }
    $("#"+domKey).html(html);
}