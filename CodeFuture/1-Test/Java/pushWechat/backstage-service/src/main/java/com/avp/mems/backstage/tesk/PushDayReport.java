package com.avp.mems.backstage.tesk;

import com.avp.mems.backstage.entity.push.Message;
import com.avp.mems.backstage.util.DateUtil;
import com.avp.mems.backstage.util.PicurlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2017/6/20.
 */
@Component
public class PushDayReport {
    @Value("${app.resource.push.url}")
    private String pushResourceUrl;

    RestTemplate restTemplate = new RestTemplate();


//    @Value("${wechat.push.picurlOfreport}")
//    private String picurl;

    @Value("${wechat.push.url}")
    private String url;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer count = 0;


    @Scheduled(cron = "${business.scheduled.pushdaycron}")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void yesterdayReoprt() {

        Date yesterdayStartDate = DateUtil.getBeforeDayBeginTIme();
        Message message = new Message();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateSrting = "日报:"+ formatter.format(yesterdayStartDate);
        List<String> targetUsers = new ArrayList();
        targetUsers.add("admin");
        List<String> targetApps = new ArrayList();
        targetApps.add("wechat");
        targetApps.add("6");
        message.setTargetApps(targetApps);
        message.setTargetUsers(targetUsers);
        message.setWechattitle(dateSrting);
        message.setIsWechatWorkorder("wechatworkorder");
        SimpleDateFormat formatterNow = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowSrting = formatterNow.format(new Date()) + " 您好日报，每天8点准时送达。";
        message.setWechatcontent(dateNowSrting);
        message.setUrl("http://" + url + "/skip?path=report/reportList");
        message.setPicurl(PicurlUtil.picurlRound(count,url));
        count++;
        logger.debug("pushToWechat,message:{}", message.toString());
        try {
            URI uri = UriComponentsBuilder.fromUriString(pushResourceUrl).path("/push").build().toUri();
            restTemplate.postForObject(uri, message, Object.class);
        } catch (Exception e) {
            System.out.println("Push create msg failed --" + e.getMessage());
        }

    }

}
