/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.resources;

import com.avp.mems.push.domain.Message;
import com.avp.mems.push.domain.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author GD
 */
@RestController
public class PushController {

    @Autowired
    @Qualifier("appPush")
    PushService appPush;

    @Autowired
    @Qualifier("weChatPush")
    PushService weChatPush;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/push", method = POST)
    public void push(@RequestBody Message message) {
        logger.debug("-------------plan push to app,message:{}",message.toString());

        appPush.push(message);

        logger.debug("-------------plan push to wechat,message:{}",message.toString());
        weChatPush.push(message);
    }




}
