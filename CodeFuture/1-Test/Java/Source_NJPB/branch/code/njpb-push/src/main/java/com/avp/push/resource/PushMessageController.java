package com.avp.push.resource;


import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.push.entity.ObjDevicePush;
import com.avp.push.entity.PushMessage;
import com.avp.push.repositories.ObjDevicePushRepository;
import com.avp.push.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by boris feng on 2017/1/20.
 */
@RestController
public class PushMessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushMessageController.class);

    @Autowired
    private PushService pushService;

    @Autowired
    ObjDevicePushRepository objDevicePushRepository;

    /**
     * 手机信息注册
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "pushInfos/register", method = RequestMethod.POST)
    ResponseEntity<ObjDevicePush> registerPushInfo(@RequestBody ObjDevicePush info) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("用户：【{}】推送设备注册，设备clientID为：{}", info.getUserId(), info.getDeviceId());
            if (info == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("info为空，参数缺失");
            } else {
                LOGGER.debug("registerPushInfo save deviceId:{}, os:{}", info.getDeviceId(), info.getOs());
                ObjDevicePush e = objDevicePushRepository.findOneByDeviceId(info.getDeviceId());

                if (e == null) {
                    objDevicePushRepository.save(info);
                } else {
                    e.setOs(info.getOs());
                    e.setUserId(info.getUserId());
                    e.setVendor(info.getVendor());

                    objDevicePushRepository.save(e);
                }
                LOGGER.debug("手机信息注册成功!");
                builder.setResponseCode(ResponseCode.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("pushMessage {}", e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED, "注册失败");
        }
        return builder.getResponseEntity();
    }

    /**
     * 消息推送
     *
     * @param message
     * @return
     */
    @RequestMapping(value = "messages/push", method = RequestMethod.POST)
    ResponseEntity<Integer> pushMessage(@RequestBody PushMessage message) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("messages/push，推送人员ID：{}，推送内容：{}，推送Title：{}", message.getUserIds(), message.getContent(), message.getTitle());
            pushService.pushMessage(message);
            builder.setResponseCode(ResponseCode.OK, "");
        } catch (Exception e) {
            LOGGER.error("pushMessage {}", e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }

        return builder.getResponseEntity();
    }


    /**
     * 消息推送
     *

     * @return
     */
    @RequestMapping(value = "messages/pu", method = RequestMethod.GET)
    ResponseEntity<Integer> pushMesge() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            builder.setResponseCode(ResponseCode.OK, "");
        } catch (Exception e) {
            LOGGER.error("pushMessage {}", e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }

        return builder.getResponseEntity();
    }


}