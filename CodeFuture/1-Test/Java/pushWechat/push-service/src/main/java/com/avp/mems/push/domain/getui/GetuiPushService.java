/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.domain.getui;

import com.alibaba.fastjson.JSON;
import com.avp.mems.push.domain.AbstractPushService;
import com.avp.mems.push.domain.Message;
import com.avp.mems.push.entities.PushInfo;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GD
 */
@Service(value = "appPush")
public class GetuiPushService extends AbstractPushService {

    private static final Logger logger = LoggerFactory.getLogger(GetuiPushService.class);


    @Autowired
    Environment environment;


    //TODO: 以下配置项改为代码动态组合键值
    @Value("${app.push.host}")
    private String host;

    @Override
    protected void pushToApp(Message pushMessage) {
        if (pushMessage.getTargetApps().get(0).equals("mems")) {
            String appName = getAppName(pushMessage);

            IGtPush push = new IGtPush(host, getAppKey(appName), getMasterSecret(appName));
            NotificationTemplate template = createNotificationTemplate(pushMessage);
            template.setAppId(getAppId(appName));
            template.setAppkey(getAppKey(appName));

            AppMessage message = new AppMessage();
            message.setData(template);
            message.setOffline(true);
            message.setOfflineExpireTime(24 * 1000 * 3600);

            List<String> appIdList = new ArrayList<>();
            appIdList.add(getAppId(appName));

            message.setAppIdList(appIdList);

            IPushResult ret = push.pushMessageToApp(message, "任务别名_toApp");
            System.out.println(ret.getResponse().toString());
        }
    }

    @Override
    protected void pushToUsers(Message pushMessage, List<PushInfo> pushInfos) {
        if (pushMessage.getTargetApps().get(0).equals("mems") ) {
            if (pushInfos == null || pushInfos.isEmpty())
                return;

            pushMssage(pushMessage, pushInfos, getAppName(pushMessage));
        }


    }

    private String getAppName(Message pushMessage) {
        String appName = pushMessage.getTargetApps().get(0);
        return appName.toLowerCase();
    }

    private String getAppId(String appName) {
        return environment.getProperty("app.push.apps." + appName + ".appId");
    }

    private String getAppKey(String appName) {
        return environment.getProperty("app.push.apps." + appName + ".appkey");
    }

    private String getMasterSecret(String appName) {
        return environment.getProperty("app.push.apps." + appName + ".masterSecret");
    }

    private String getPayloadString(Message pushMessage) {
        Payload payload = new Payload();
        payload.pushCmd = pushMessage.getCommand();
        payload.sender = pushMessage.getSender();
        payload.args = pushMessage.getArgs();

        String payloadString = JSON.toJSONString(payload);

        return payloadString;
    }

    private class Payload {
        String sender = "";
        String pushCmd = "";
        Map<String, String> args = new LinkedHashMap<>();
    }

    private NotificationTemplate createNotificationTemplate(Message pushMessage) {
        NotificationTemplate template = new NotificationTemplate();

        template.setTitle(pushMessage.getTitle());
        template.setText(pushMessage.getContent());
        template.setTransmissionContent(pushMessage.getContent());
        template.setTransmissionType(1);
        template.setLogo("icon.png");
        return template;
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------


    private void pushMssage(Message pushMessage, List<PushInfo> pushInfos, String appName) {
        if (null != pushInfos && pushInfos.size() > 0) {
            List<Target> ios = new ArrayList<>();
            List<Target> android = new ArrayList<>();

            for (PushInfo info : pushInfos) {
                Target t = new Target();
                t.setAppId(getAppId(appName));
                t.setClientId(info.getDeviceId());
                if ("android".equalsIgnoreCase(info.getPushInfoPK().getOs())) {
                    android.add(t);
                } else if ("ios".equalsIgnoreCase(info.getPushInfoPK().getOs())) {
                    ios.add(t);
                } else {
                    logger.warn("unknown os type {}", info.getPushInfoPK().getOs());
                }
            }
            IGtPush push = new IGtPush(host, getAppKey(appName), getMasterSecret(appName));
            if (android.size() > 0)
                pushAndroid(push, android, pushMessage.getTitle(), pushMessage.getContent(), appName);
            if (ios.size() > 0) pushIOS(push, ios, pushMessage.getContent(), appName);
            if (ios.size() > 0) pushIOSMassage(push, ios, pushMessage, appName);


        }
    }

    private void pushIOSMassage(IGtPush push, List<Target> targets, Message pushMessage, String appName) {
        NotificationTemplate template = createNotificationTemplate(pushMessage);
        template.setAppId(getAppId(appName));
        template.setAppkey(getAppKey(appName));
        template.setTitle(pushMessage.getTitle());
        template.setText(pushMessage.getContent());
        template.setTransmissionContent(getPayloadString(pushMessage));

        ListMessage message = new ListMessage();
        message.setData(template);
        message.setOffline(false);
        message.setOfflineExpireTime(1000 * 3600 * 24);

        String taskId = push.getContentId(message, "任务别名_LIST");
        IPushResult ret = push.pushMessageToList(taskId, targets);
        System.out.println(ret.getResponse().toString());
    }


    private void pushAndroid(IGtPush push, List<Target> targets, String title, String content, String appName) {
        try {
            ListMessage lstmsg = createAndroidMessage(title, content, appName);

            String taskId = push.getContentId(lstmsg, "任务别名_LIST");
            IPushResult result = push.pushMessageToList(taskId, targets);
            logger.debug("pushAndroid {}", result.getResponse().toString());
        } catch (Exception e) {
            logger.error("pushAndroid {}", e.getMessage());
            throw e;
        }
    }

    private void pushIOS(IGtPush push, List<Target> targets, String content, String appName) {
        try {
            ListMessage lstmsg = createIOSMessage(content, appName);

            String taskId = push.getContentId(lstmsg, "任务别名_LIST");
            IPushResult result = push.pushMessageToList(taskId, targets);
            logger.debug("pushIOS {}", result.getResponse().toString());
        } catch (Exception e) {
            logger.error("pushIOS {}", e.getMessage());
            throw e;
        }
    }


    private ListMessage createIOSMessage(String content, String appName) {
        ListMessage message = new ListMessage();
        message.setData(createTransmissionTemplate(content, appName));
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 3600 * 24);
        return message;
    }

    private ListMessage createAndroidMessage(String title, String content, String appName) {
        ListMessage message = new ListMessage();
        message.setData(createNotificationTemplate(title, content, appName));
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 3600 * 24);
        return message;
    }


    // it is for android
    private NotificationTemplate createNotificationTemplate(String title, String content, String appName) {
        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(getAppId(appName));
        template.setAppkey(getAppKey(appName));
        template.setTitle(title);
        template.setText(content);
        template.setNotifyStyle(1); //0:android style, 1:getui style
        //template.setLogo("APP.png");

        template.setTransmissionContent("");
        template.setTransmissionType(1);//1为立即启动，2则广播等待客户端自启动
        return template;
    }

    // it is for ios
    private TransmissionTemplate createTransmissionTemplate(String content, String appName) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(getAppId(appName));
        template.setAppkey(getAppKey(appName));
        template.setTransmissionContent("");
        template.setTransmissionType(1);//1为立即启动，2则广播等待客户端自启动
        APNPayload payload = new APNPayload();
        //payload.setBadge(1);
        //payload.setAutoBadge("1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        //payload.setCategory("$由客户端定义");
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        return template;
    }
}
