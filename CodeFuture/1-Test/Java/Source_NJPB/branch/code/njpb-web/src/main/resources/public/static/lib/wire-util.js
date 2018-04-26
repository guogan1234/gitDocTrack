/**
 * Created by pw on 2016/5/30.
 */
var CONSTANT = {
    S_LEVEL:1,
    W_LEVEL:3
}


function getJsonAmmeterTag(){
    var users = [];
    return {
        push_users:users,
        TG_watt_U_low:{
            value_w:null,
            value_s:null,
            enabled:null,
            push_type:null,
            ids:{
                S:[],
                W:[]
            }
        },
        TG_watt_U_high:{
            value_w:null,
            value_s:null,
            enabled:null,
            push_type:null,
            ids:{
                S:[],
                W:[]
            }
        },
        TG_watt_I:{
            value_w:null,
            value_s:null,
            enabled:null,
            push_type:null,
            ids:{
                S:[],
                W:[]
            }
        },
        TG_watt_Psum:{
            value_w:null,
            value_s:null,
            enabled:null,
            push_type:null,
            ids:{
                S:[],
                W:[]
            }
        },
        TG_watt_Pdemand:{
            value_w:null,
            value_s:null,
            enabled:null,
            push_type:null,
            ids:{
                S:[],
                W:[]
            }
        },
        TG_watt_t_cab:{
            push_type:null,
            value_w:null,
            value_s:null,
            id:null
        },
        TG_watt_t_ambient:{
            id:null,
            value_w:null,
            value_s:null,
            push_type:null
        },
        TG_watt_h_ambient:{
            id:null,
            value_w:null,
            value_s:null,
            push_type:null
        },
        TG_watt_dtu:{
            id:null,
            value_w:null,
            value_s:null,
            push_type:null
        },
        init:function(array,values){
            if(null != array && array.length > 0 ){
                for(var i = 0 ; i < array.length ; i++){
                    var tag = array[i];
                    if(tag.tag.indexOf('TG_watt_U') != -1){
                        if(tag.alarmTag.indexOf('_low') != -1){
                            this.TG_watt_U_low.push_type = tag.pushType;
                            this.TG_watt_U_low.enabled = tag.enabled;
                            if(tag.severity == CONSTANT.S_LEVEL){
                                //this.TG_watt_U_low.value_s = Number(tag.triggerMax) + 0.01;
                                this.TG_watt_U_low.value_s = getValue(tag.severity,tag.alarmTag,true);
                                this.TG_watt_U_low.ids.S.push(tag.id);
                            }else if(tag.severity == CONSTANT.W_LEVEL){
                                //this.TG_watt_U_low.value_w = tag.triggerMax;
                                this.TG_watt_U_low.value_w = getValue(tag.severity,tag.alarmTag,true);
                                this.TG_watt_U_low.ids.W.push(tag.id);
                            }
                        }else if(tag.alarmTag.indexOf('_high') != -1){
                            this.TG_watt_U_high.push_type = tag.pushType;
                            this.TG_watt_U_high.enabled = tag.enabled;
                            if(tag.severity == CONSTANT.S_LEVEL){
                                //this.TG_watt_U_high.value_s = tag.triggerMax;
                                this.TG_watt_U_high.value_s = getValue(tag.severity,tag.alarmTag,true);
                                this.TG_watt_U_high.ids.S.push(tag.id);
                            }else if(tag.severity == CONSTANT.W_LEVEL){
                                //this.TG_watt_U_high.value_w = Number(tag.triggerMax) + 0.01;
                                this.TG_watt_U_high.value_w = getValue(tag.severity,tag.alarmTag,true);
                                this.TG_watt_U_high.ids.W.push(tag.id);
                            }
                        }
                    }else if(tag.tag.indexOf('TG_watt_I') != -1){
                        this.TG_watt_I.push_type = tag.pushType;
                        this.TG_watt_I.enabled = tag.enabled;
                        if(tag.severity == CONSTANT.S_LEVEL){
                            //this.TG_watt_I.value_s = tag.triggerMax;
                            this.TG_watt_I.value_s = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_I.ids.S.push(tag.id);
                        }else if(tag.severity == CONSTANT.W_LEVEL){
                            //this.TG_watt_I.value_w = Number(tag.triggerMax) + 0.01;
                            this.TG_watt_I.value_w = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_I.ids.W.push(tag.id);
                        }
                    }else if('TG_watt_Pdemand' == tag.tag){
                        this.TG_watt_Pdemand.push_type = tag.pushType;
                        this.TG_watt_Pdemand.enabled = tag.enabled;
                        if(tag.severity == CONSTANT.S_LEVEL){
                            //this.TG_watt_Pdemand.value_s = tag.triggerMax;
                            this.TG_watt_Pdemand.value_s = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_Pdemand.ids.S.push(tag.id);
                        }else if(tag.severity == CONSTANT.W_LEVEL){
                            //this.TG_watt_Pdemand.value_w = Number(tag.triggerMax) + 0.01;
                            this.TG_watt_Pdemand.value_w = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_Pdemand.ids.W.push(tag.id);
                        }
                    }else if('TG_watt_Psum' == tag.tag){
                        this.TG_watt_Psum.push_type = tag.pushType;
                        this.TG_watt_Psum.enabled = tag.enabled;
                        if(tag.severity == CONSTANT.S_LEVEL){
                            //this.TG_watt_Psum.value_s = tag.triggerMax;
                            this.TG_watt_Psum.value_s = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_Psum.ids.S.push(tag.id);
                        }else if(tag.severity == CONSTANT.W_LEVEL){
                            //this.TG_watt_Psum.value_w = Number(tag.triggerMax) + 0.01;
                            this.TG_watt_Psum.value_w = getValue(tag.severity,tag.alarmTag,true);
                            this.TG_watt_Psum.ids.W.push(tag.id);
                        }
                    }else if("TG_watt_t_cab" == tag.tag){
                        this.TG_watt_t_cab.push_type = tag.pushType;
                        this.TG_watt_t_cab.id = tag.id;
                        //this.TG_watt_t_cab.value_w = tag.triggerMin;
                        //this.TG_watt_t_cab.value_s = tag.triggerMax;
                        this.TG_watt_t_cab.value_w = getValue(tag.severity,tag.alarmTag,false);
                        this.TG_watt_t_cab.value_s = getValue(tag.severity,tag.alarmTag,true);
                    }else if("TG_watt_t_ambient" == tag.tag){
                        this.TG_watt_t_ambient.push_type = tag.pushType;
                        this.TG_watt_t_ambient.id = tag.id;
                        //this.TG_watt_t_ambient.value_w = tag.triggerMin;
                        //this.TG_watt_t_ambient.value_s = tag.triggerMax;
                        this.TG_watt_t_ambient.value_w = getValue(tag.severity,tag.alarmTag,false);
                        this.TG_watt_t_ambient.value_s = getValue(tag.severity,tag.alarmTag,true);
                    }else if("TG_watt_h_ambient" == tag.tag){
                        this.TG_watt_h_ambient.push_type = tag.pushType;
                        this.TG_watt_h_ambient.id = tag.id;
                        //this.TG_watt_h_ambient.value_w = tag.triggerMin;
                        //this.TG_watt_h_ambient.value_s = tag.triggerMax;
                        this.TG_watt_h_ambient.value_w = getValue(tag.severity,tag.alarmTag,false);
                        this.TG_watt_h_ambient.value_s = getValue(tag.severity,tag.alarmTag,true);
                    }else if("TG_watt_dtu" == tag.tag){
                        this.TG_watt_dtu.push_type = tag.pushType;
                        this.TG_watt_dtu.id = tag.id;
                        //this.TG_watt_dtu.value_w = tag.triggerMin;
                        //this.TG_watt_dtu.value_s = tag.triggerMax;
                        this.TG_watt_dtu.value_w = getValue(tag.severity,tag.alarmTag,false);
                        this.TG_watt_dtu.value_s = getValue(tag.severity,tag.alarmTag,true);
                    }
                }
            }

            function getValue(severity,alarmTag,isMax){
                for(var i = 0 ; i < values.length ; i++){
                    var tag = values[i];
                    if(tag.severity == severity){
                        if(tag.alarmTag == alarmTag){
                            return isMax?tag.triggerMax:tag.triggerMin;
                        }
                    }
                }
                return 0;
            }
        }
    }
}

function saveTagAlarmMapping(wire,ammeter,TEMPLATE_ALARM_MAPPING, clientModel){
    if(isNotEmpty(TEMPLATE_ALARM_MAPPING)){
        var tags = TEMPLATE_ALARM_MAPPING.container['ALARM_MAPPING_160510'];
        var values = TEMPLATE_ALARM_MAPPING.container['ALARM_MAPPING_JSON_160510'];
        if(isNotEmpty(tags) && isNotEmpty(values)){
            tags = JSON.stringify(tags);
            values = JSON.stringify(values);
            tags = tags.replace(/"_LOGICAL_ID"/g,ammeter.logicalId);
            values = values.replace(/_CLIENT_NAME_/g,clientModel);
            values = values.replace(/_AMMETER_NAME_/g,ammeter.model);
            tags = JSON.parse(tags);
            values = JSON.parse(values);

            var promises = [];
            var time = new Date();
            for(var i = 0 ; i < tags.length ; i++){
                tags[i].updateTime = time;
                promises.push(
                    $.ajax({ type: 'POST', url: BASE_WEB + "/acscada/objAlarmMappings",
                        dataType : 'JSON', contentType: 'application/json',
                        data : JSON.stringify(tags[i])
                    })
                );
            }
            $.when.apply(null, promises ).done(function() {
                var newDatas = [];
                for(var i = 0 ; i < arguments.length; i++){
                    if("success" == arguments[i][1]){
                        var json = arguments[i][2].responseJSON;
                        delete json._links;
                        newDatas.push(json);
                    }
                }
                var entity = getJsonAmmeterTag();
                entity.init(newDatas,values);
                // var necessaryWire = {client_name:wire.model, wire_way_no:wire.sn};
                var jdoc = values;
                console.log(jdoc);
                var jsonb = { logicalId: ammeter.logicalId, recordTime: new Date(),
                    propName:"pqtm_alarm_tag_mapping", jdoc: JSON.stringify(jdoc) };
                AJAX_POST_ONE("/acscada/insertObjJsonProperty",jsonb,function(){
                    log("inset tag json" + tags.length);
                });
            })
        }
    }
}

function saveTagMapping(ammeter ,TEMPLATE_TAG_MAPPING){
    if(isNotEmpty(TEMPLATE_TAG_MAPPING)){
        var tags = TEMPLATE_TAG_MAPPING.container['TAG_MAPPING_160510'];
        if(isNotEmpty(tags)){
            tags = JSON.stringify(tags);
            tags = tags.replace(/"_LOGICAL_ID"/g,ammeter.logicalId);
            tags = JSON.parse(tags);
            var time = new Date();
            for(var i = 0 ; i < tags.length ; i++){
                tags[i].updateTime = time;
                AJAX_POST_ONE("/acscada/objTagMappings",tags[i],function(){});
            }
        }
    }
}

function saveChartsView(logicalId,TEMPLATE_CHARTS_VIEW){
    if(isNotEmpty(TEMPLATE_CHARTS_VIEW)){
        if(isNotEmpty(logicalId)){
            for(key in TEMPLATE_CHARTS_VIEW.container){
                var jdoc = TEMPLATE_CHARTS_VIEW.container[key];
                var chartsView = { logicalId: logicalId, recordTime: new Date(), propName: CONSTANTS.CHARTS.pqtm_CHARTS_PAGE, jdoc:JSON.stringify(jdoc)};
                AJAX_POST_ONE("/acscada/insertObjJsonProperty",chartsView,function(retData){log('insert into charts view !~')});
            }
        }
    }
}

function saveModbusGlobal(logicalId,TEMPLATE) {
    if(isNotEmpty(TEMPLATE)){
        var jdoc = TEMPLATE.container['MODBUS_GLOBAL'];
        if(isNotEmpty(jdoc)){
            var objJson = { recordTime:new Date(),logicalId:logicalId, propName:"modbus-global",jdoc:JSON.stringify(jdoc)};
            AJAX_POST_ONE("/acscada/insertObjJsonProperty",objJson,function(retData) {log("insert modbus ->"+retData)});
        }
    }
}

function saveModbusHybridRead(wire,ammeter_logicalId,TEMPLATE){
    if(isNotEmpty(TEMPLATE)){
        if(isNotEmpty(wire.dtu.ammeter.name)){
            var jdoc = TEMPLATE.container[wire.dtu.ammeter.name];
            if(isNotEmpty(jdoc)){
                jdoc = JSON.stringify(jdoc);
                jdoc = jdoc.replace(/"_CLIENT_ID"/g,client.logicalId);
                jdoc = jdoc.replace(/"_CIRCUIT_TYPE_ID"/g, ammeter_logicalId);
                var jsonb = { logicalId: ammeter_logicalId, recordTime: new Date(),
                    propName: "modbus-hybrid-read", jdoc: jdoc }
                AJAX_POST_ONE("/acscada/insertObjJsonProperty",jsonb,function(retData){
                    log("ammeter-info == >"+retData);
                });
            }
        }
    }
}









