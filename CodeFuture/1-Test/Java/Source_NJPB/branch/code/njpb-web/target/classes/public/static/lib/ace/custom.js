/**
 * 
 */
function dd_prize(type)
{
    switch(type)
    {
        case '1':
            return '【分】单一制 工商业及其他用电';
        case '2':
            return '【分】单一制 农业生产用电';
        case '3':
            return '【分】两部制 工商业及其他用电';
        case '4':
            return '【分】两部制 农业生产用电';
        case '5':
            return '【未】单一制 工商业及其他用电';
        case '6':
            return '【未】单一制 下水道动力用电';
        case '7':
            return '【未】单一制 农业生产用电';
        case '8':
            return '【未】单一制 农副业动力用电';
        case '9':
            return '【未】单一制 排灌动力用电';
        case '10':
            return '【未】两部制 工商业及其他用电';
        case '11':
            return '【未】两部制 铁合金烧碱用电';
        case '12':
            return '非居民 学校养老院用电';

        default:
            return '';
    }
}

function dd_voltage_level(role)
{
    switch(role)
    {
        case '1':
            return '380V';
        case '2':
            return '10KV';
        case '3':
            return '35KV';
        case '4':
            return '110KV';
        case '5':
            return '220KV';
        default:
            return '';
    }
}

function dd_policy(type)
{
    switch(type)
    {
        case '1':
            return '正常申报';
        case '2':
            return '临时性减容';
        case '3':
            return '暂停';
        case '4':
            return '两部制改类';
        case '5':
            return '40%装接容量申报';
        default:
            return '';
    }
}

function time_range(date1,date2)
{
    var ret = date1 + ' 至 ' +date2;
    ret = ret.replace('0000-00-00 至 0000-00-00','');
    ret = ret.replace('0000-00-00','0000-00-00');
    return ret;
}

function count_hour(date)
{
    var date1=new Date(2015,2,1);    //开始时间2015-03-01
    if(date!="0000-00-00")
        date1 = new Date(date);

    var date2=new Date();    //结束时间
    var date3=date2.getTime()-date1.getTime();

    var hours=Math.floor(date3/(3600*1000));
    $("#count_hour").html(hours);
}

function add_param(param,type,value)
{
    var i="&";
    if(param=="")
        i="?";

    param = param+i+type+"="+value;
    return param;
}

function dd_alert_type(d)
{
    switch(d)
    {
        case '1':
            return '三相电压过高';
        case '2':
            return '三相电压过低';
        case '3':
            return 'MD过高';
        case '4':
            return '线路温度过高';
        case '5':
            return '环境温度过高';
        case '6':
            return '环境湿度过高';
        case '7':
            return '负载功率过高';
        case '8':
            return '断电';
        case '9':
            return '水浸';
        case '10':
            return '累计功率因数过低';
        case '11':
            return '三相电流过高';
        case '12':
            return '失联';
        default:
            return '';
    }
}

function dd_cabinet_no(d)
{
    switch(d)
    {
        case '0':
            return '进线柜';
        default:
            return d+'号出线柜';
    }
}

function fpg_type(d)
{
    switch(d)
    {
        case '1':
            return '峰';
        case '2':
            return '平';
        case '3':
            return '谷';
        default:
            return '';
    }
}

function dd_remind_type(d)
{
    switch(d)
    {
        case '1':
            return '设置下月MD';
        case '2':
            return '推荐临时性减容策略';
        case '3':
            return '推荐减容策略';
        case '4':
            return '推荐改类策略';
        case '5':
            return '推荐正常申报策略';

        default:
            return '';
    }
}

function CurentTime()
{

    var now = new Date();

    var year = now.getFullYear(); //年
    var month = now.getMonth() + 1; //月
    var day = now.getDate(); //日

    var hh = now.getHours(); //时
    var mm = now.getMinutes(); //分

    var ss=now.getSeconds();


//秒

    var clock = year +"年";

    if(month < 10) clock +="0";
    clock += month +"月";

    if(day < 10) clock +="0";
    clock += day +"日 ";

    if(hh < 10) clock +="0";
    clock += hh +":";

    if (mm < 10) clock += '0';
    clock += mm+":";



    if (ss < 10) clock += '0';

    clock += ss;

    return(clock);
}

function playSound()
{
    var borswer = window.navigator.userAgent.toLowerCase();
    if ( borswer.indexOf( "ie" ) >= 0 )
    {
        //IE内核浏览器
        var strEmbed = '<embed name="embedPlay" src="/assets/alert.mp3" autostart="true" hidden="true" loop="false"></embed>';
        if ( $( "body" ).find( "embed" ).length <= 0 )
            $( "body" ).append( strEmbed );
        var embed = document.embedPlay;

        //浏览器不支持 audion，则使用 embed 播放
        embed.volume = 100;
        embed.play();
    } else
    {
        //非IE内核浏览器
        var strAudio = "<audio id='audioPlay' src='/assets/alert.mp3' hidden='true'>";
        if ( $( "body" ).find( "audio" ).length <= 0 )
            $( "body" ).append( strAudio );
        var audio = document.getElementById( "audioPlay" );

        //浏览器支持 audion
        audio.play();
    }
}

Date.prototype.Format = function(format){ 
var o = { 
"M+" : this.getMonth()+1, //month 
"d+" : this.getDate(), //day 
"h+" : this.getHours(), //hour 
"m+" : this.getMinutes(), //minute 
"s+" : this.getSeconds(), //second 
"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
"S" : this.getMilliseconds() //millisecond 
} 

if(/(y+)/.test(format)) { 
format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
} 

for(var k in o) { 
if(new RegExp("("+ k +")").test(format)) { 
format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
} 
} 
return format; 
}

