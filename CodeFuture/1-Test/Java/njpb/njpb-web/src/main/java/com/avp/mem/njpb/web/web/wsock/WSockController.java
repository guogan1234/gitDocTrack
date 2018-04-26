package com.avp.mem.njpb.web.web.wsock;

import com.avp.mem.njpb.web.web.wsock.message.CommandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by boris feng on 2017/6/27.
 */

@Controller
public class WSockController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;

//    @Autowired
//    private RestTemplate restTemplate;

    //命令--广播
    @MessageMapping("/command/{msgSn}")
    @SendTo("/topic/response/{msgSn}")
    public CommandResponse sendMessage(@DestinationVariable("msgSn") String msgSn, CommandResponse message)  {
        //1. 调用business，其功能支持：
        //1.1. 判断是否重发？
        //2. 取到business的返回，发送命令至EQI
        // wait();
        //3. 取到返回值后，跟新business，并返回结果
        return null;
    }

    // 其他命令


    //命令-结束

    // alarm count --
    //@SendTo("/topic/alarm/count/{pid}")
    @Scheduled(fixedDelay = 30000)
    public void getAlarmCountByProject() {

        // 1. business支持按照项目聚集报警
        // 2. web调用bussiess查询结果

        // 3. convertAndSend(/topic/alarms/countBy/{pid}, count)

    }
}
