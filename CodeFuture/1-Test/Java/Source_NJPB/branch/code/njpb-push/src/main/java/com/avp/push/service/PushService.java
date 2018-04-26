package com.avp.push.service;

import com.avp.push.entity.ObjDevicePush;
import com.avp.push.entity.PushMessage;
import com.avp.push.repositories.ObjDevicePushRepository;
import com.gexin.rp.sdk.base.IPushResult;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by boris feng on 2017/1/17.
 */

@Service
@EnableScheduling
public class PushService {
    private static final Logger logger = LoggerFactory.getLogger(PushService.class);

    @Autowired
    @Value("${push.app.mem-hm.url}")
    private String url;

    @Autowired
    @Value("${push.app.mem-hm.appkey}")
    private String appkey;

    @Autowired
    @Value("${push.app.mem-hm.appid}")
    private String appId;

    @Autowired
    @Value("${push.app.mem-hm.app-secret}")
    private String appSecret;

    @Autowired
    @Value("${push.app.mem-hm.master-secret}")
    private String masterSecret;

    @Autowired
    ObjDevicePushRepository objDevicePushRepository;
//

    //    private Integer thousand = MagicNumber.THOUSAND;
    //non-blocking safe array
    private Queue<PushMessage> fifoMessages = new ConcurrentLinkedQueue<>();

    public boolean pushMessage(PushMessage message) {
        return this.fifoMessages.add(message);
    }

    @Scheduled(fixedDelay = 10000)
    protected void pushMessageImpl() {
        // launch push
        while (!fifoMessages.isEmpty()) {
            try {
                PushMessage msg = fifoMessages.poll();

//                List<Target> ios = new ArrayList<>();
                List<Target> android = new ArrayList<>();

                logger.debug("pushMessage users: {}, msg: {}", msg.getUserIds(), msg.getContent());
                List<ObjDevicePush> pushInfos = objDevicePushRepository.findByUserIdIn(msg.getUserIds());

                for (ObjDevicePush term : pushInfos) {

                    logger.debug("待推送设备ID：{}，操作系统：{}", term.getDeviceId(), term.getOs());

                    Target t = new Target();
                    t.setAppId(this.appId);
                    t.setClientId(term.getDeviceId());
                    //term.getDeviceId()
                    if ("android".equalsIgnoreCase(term.getOs())) {
                        android.add(t);
                    }
                    /* else if ("ios".equalsIgnoreCase(term.getOs())) {
                        ios.add(t);
                    }*/
                    else {
                        logger.warn("未知的操作系统类型： {}", term.getOs());
                    }
                }

                IGtPush push = new IGtPush(this.url, this.appkey, this.masterSecret);

                pushAndroid(push, android, msg);
                logger.debug("推送task执行完成，推送设备数量：{}", android.size());
//                pushIOS(push, ios, msg);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("pushMessageImpl执行失败：{}", e.getMessage());
            }
        }
        logger.debug("execute pushMessageImpl finish...");
    }

    private void pushAndroid(IGtPush push, List<Target> targets, PushMessage msg) {
        try {
            ListMessage lstmsg = createAndroidMessage(msg);

            String taskId = push.getContentId(lstmsg, "任务别名_LIST");
            IPushResult result = push.pushMessageToList(taskId, targets);
            logger.debug("pushAndroid finish，返回信息：{}", result.getResponse().toString());
        } catch (Exception e) {
            logger.error("pushAndroid失败 {}", e.getMessage());
            throw e;
        }
    }

    private void pushIOS(IGtPush push, List<Target> targets, PushMessage msg) {
        try {
            ListMessage lstmsg = createIOSMessage(msg);

            String taskId = push.getContentId(lstmsg, "任务别名_LIST");
            IPushResult result = push.pushMessageToList(taskId, targets);
            logger.debug("pushIOS {}", result.getResponse().toString());
        } catch (Exception e) {
            logger.error("pushIOS {}", e.getMessage());
            throw e;
        }
    }

    private ListMessage createIOSMessage(PushMessage msg) {
        ListMessage message = new ListMessage();
        message.setData(createTransmissionTemplate(msg));
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 3600 * 24);

        return message;
    }

    private ListMessage createAndroidMessage(PushMessage msg) {
        ListMessage message = new ListMessage();
        message.setData(createNotificationTemplate(msg));
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 3600 * 24);

        return message;
    }

    // it is for android
    private NotificationTemplate createNotificationTemplate(PushMessage msg) {
        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(this.appId);
        template.setAppkey(this.appkey);
        template.setTitle(msg.getTitle());
        template.setText(msg.getContent());
        template.setNotifyStyle(1); //0:android style, 1:getui style
        //template.setLogo("APP.png");

        template.setTransmissionContent("");
        template.setTransmissionType(2);
        //1为立即启动(强制启动打开），2则广播等待客户端自启动（强制启动关闭）
        return template;
    }

    // it is for ios
    private TransmissionTemplate createTransmissionTemplate(PushMessage msg) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(this.appId);
        template.setAppkey(this.appkey);
        template.setTransmissionContent("");
        template.setTransmissionType(1);
        //1为立即启动，2则广播等待客户端自启动
        APNPayload payload = new APNPayload();
        //payload.setBadge(1);
        //payload.setAutoBadge("1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        //payload.setCategory("$由客户端定义");
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg.getContent()));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        return template;
    }

}
