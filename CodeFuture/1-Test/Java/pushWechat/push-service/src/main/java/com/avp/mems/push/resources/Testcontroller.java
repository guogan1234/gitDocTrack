package com.avp.mems.push.resources;

import com.avp.mems.push.domain.EnterpriseWeChat.WeChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by len on 2017/6/15.
 */
@RestController
public class Testcontroller {

    @Autowired
    WeChatMessageService weChatMessageService;


    @RequestMapping(value = "/pushwechat", method = GET)
    public void push() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = calendar1.getTime();
        Date t =  new Date();
        List s  = new ArrayList<>();
        s.add("zhoujiapeng");
        weChatMessageService.sendWeChatMsg("news", s, new ArrayList<>(), new ArrayList<>(), "test", "", "报表",
                "每日报表", "http://192.168.1.185:9008/skip?path=report/reportList", "http://chuantu.biz/t5/111/1497943544x2890174184.png", "0");

    }
}
