package com.avp.mem.njpb.web.web.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

/**
 * Created by boris feng on 2017/6/26.
 */
@Controller
public class WsController {
    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    @MessageMapping("/welcome")
    @SendTo("/topic/getAlarmCount")
    public String sayWelcome(String message) throws Exception{
        System.out.println("sayWelcome: " + message);
        Thread.sleep(10000);
        return "welcome to ws world, " + message;
    }

    public class NotifyEvent {
        public String getEvent() {
            return event;
        }

        public NotifyEvent(String event) {
            this.event = event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        private String event = "ff";

    }
    // 项目参考
    @SubscribeMapping("/welcomeBy/{uid}")
    @SendTo("/topic/getEvent/{uid}")
    public  NotifyEvent sayWelcomeBy(@DestinationVariable("uid") String uid, String message) throws Exception {
        System.out.println("sayWelcomeTo: " + uid + ", " + message);
        Thread.sleep(5000);
        return new NotifyEvent("welcome " + uid +" to ws world, " + message);
    }

    @MessageMapping("/hello")
    @SendTo("/queue/getResponse/{uid}")
    public void sayHello(String message, java.security.Principal principal) {
        System.out.println("sayHello: " + 0 + ", " + message);
        System.out.println("sayHello pricipal: " + principal);
        messagingTemplate.convertAndSend("/queue/getResponse/0", "hello, " + message);
    }
}
