package com.avp.mems.push.domain.EnterpriseWeChat;

import com.alibaba.fastjson.JSONObject;
import com.avp.mems.push.domain.AbstractPushService;
import com.avp.mems.push.domain.Message;
import com.avp.mems.push.entities.PushInfo;
import com.avp.mems.push.repositories.PushInfoWechatRepository;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2017/5/31.
 */
@Service(value = "weChatPush")
public class WeChatMessageService extends AbstractPushService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMessageService.class);


    @Autowired
    PushInfoWechatRepository pushInfoWechatRepository;
    // 发送消息类型
    private final static String MSGTYPE = "text";
    @Value("${wechat.push.AgentId}")
    private String AGENTID;
    // 发送消息分组所有成员
    private final static String TOPARTY = "@all";
    @Value("${wechat.push.CorpID}")
    private String CORPID;
    @Value("${wechat.push.Secret}")
    private String CORPSECRET;

    // 获取访问权限码URL
    private final static String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    // 创建会话请求URL
    private final static String CREATE_SESSION_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    // 获取接口访问权限码
    public String getAccessToken() {
        HttpClient client = new HttpClient();
        String param = ACCESS_TOKEN_URL + "?corpid=" + CORPID + "&corpsecret=" + CORPSECRET;
        GetMethod get = new GetMethod(param);
        // 设置策略，防止报cookie错误
        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
        String result = "";
        try {
            client.executeMethod(get);
            result = new String(get.getResponseBodyAsString().getBytes("gbk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将数据转换成json
        JSONObject jasonObject = JSONObject.parseObject(result);
        result = (String) jasonObject.get("access_token");
        // System.out.println(result);
        get.releaseConnection();
        return result;
    }

    /**
     * 此方法可以发送任意类型消息
     *
     * @param msgType     text|image|voice|video|file|news
     * @param tousers     成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，
     *                    则向关注该企业应用的全部成员发送
     * @param topartys    部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     * @param totags      标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
     * @param content     msgType=text时 ,文本消息内容
     * @param mediaId     msgType=image|voice|video时 ,对应消息信息ID（--------）
     * @param title       msgType=news|video时，消息标题
     * @param description msgType=news|video时，消息描述
     * @param url         msgType=news时，消息链接
     * @param picurl      msgType=news时，图片路径
     * @param safe        表示是否是保密消息，0表示否，1表示是，默认0
     */
    public void sendWeChatMsg(String msgType, List<String> tousers, List<String> topartys, List<String> totags, String content, String mediaId, String title,
                              String description, String url, String picurl, String safe) {

        logger.debug("sendWeChatMsg ..... ");

        URL uRl;
        String ACCESS_TOKEN = getAccessToken();
        logger.debug("ACCESS_TOKEN:{}", ACCESS_TOKEN);
        // 拼接请求串
        String action = CREATE_SESSION_URL + ACCESS_TOKEN;

        logger.debug("action:{}", action);

        List<String> TouserTopartyTotag = checkTouserTopartyTotag(tousers, topartys, totags);

        // 封装发送消息请求json
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"touser\":" + "\"" + TouserTopartyTotag.get(0) + "\",");
        sb.append("\"toparty\":" + "\"" + TouserTopartyTotag.get(1) + "\",");
        sb.append("\"totag\":" + "\"" + TouserTopartyTotag.get(2) + "\",");
        if (msgType.equals("text")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"text\":" + "{");
            sb.append("\"content\":" + "\"" + content + "\"");
            sb.append("}");
        } else if (msgType.equals("image")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"image\":" + "{");
            sb.append("\"media_id\":" + "\"" + mediaId + "\"");
            sb.append("}");
        } else if (msgType.equals("voice")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"voice\":" + "{");
            sb.append("\"media_id\":" + "\"" + mediaId + "\"");
            sb.append("}");
        } else if (msgType.equals("video")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"video\":" + "{");
            sb.append("\"media_id\":" + "\"" + mediaId + "\",");
            sb.append("\"title\":" + "\"" + title + "\",");
            sb.append("\"description\":" + "\"" + description + "\"");
            sb.append("}");
        } else if (msgType.equals("file")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"file\":" + "{");
            sb.append("\"media_id\":" + "\"" + mediaId + "\"");
            sb.append("}");
        } else if (msgType.equals("news")) {
            sb.append("\"msgtype\":" + "\"" + msgType + "\",");
            sb.append("\"news\":" + "{");
            sb.append("\"articles\":" + "[");
            sb.append("{");
            sb.append("\"title\":" + "\"" + title + "\",");
            sb.append("\"description\":" + "\"" + description + "\",");
            sb.append("\"url\":" + "\"" + url + "\",");
            sb.append("\"picurl\":" + "\"" + picurl + "\"");
            sb.append("}");
            sb.append("]");
            sb.append("}");
        }
        sb.append(",\"safe\":" + "\"" + safe + "\",");
        sb.append("\"agentid\":" + "\"" + AGENTID + "\",");
        sb.append("\"debug\":" + "\"" + "1" + "\"");
        sb.append("}");
        String json = sb.toString();

        logger.debug("发送消息json为:{}", json);

        try {

            uRl = new URL(action);

            HttpsURLConnection http = (HttpsURLConnection) uRl.openConnection();

            http.setRequestMethod("POST");

            http.setRequestProperty("Content-Type",

                    "application/json;charset=UTF-8");

            http.setDoOutput(true);

            http.setDoInput(true);

            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//
            // 连接超时30秒

            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //
            // 读取超时30秒

            http.connect();

            OutputStream os = http.getOutputStream();

            os.write(json.getBytes("UTF-8"));// 传入参数

            InputStream is = http.getInputStream();

            int size = is.available();

            logger.debug("size-------------------:{}", size);

            byte[] jsonBytes = new byte[size];

            is.read(jsonBytes);

            String result = new String(jsonBytes, "UTF-8");

            logger.debug("result ----- :{}", result);

            //System.out.println("请求返回结果:" + result);

            os.flush();

            os.close();

            logger.debug("处理完成......");
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * 企业接口向下属关注用户发送微信消息
     *
     * @param tousers  成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，
     *                 则向关注该企业应用的全部成员发送
     * @param topartys 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
     * @param totags   标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
     * @param content  消息内容
     * @param safe     消息是否保密
     * @return
     */

    public void sendWeChatMsgText(List<String> tousers, List<String> topartys, List<String> totags, String content, String safe) {

        URL uRl;
        String ACCESS_TOKEN = getAccessToken();
        // 拼接请求串
        String action = CREATE_SESSION_URL + ACCESS_TOKEN;
        //检验推送人员，部门，标签
        List<String> TouserTopartyTotag = checkTouserTopartyTotag(tousers, topartys, totags);

        // 封装发送消息请求json
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"touser\":" + "\"" + TouserTopartyTotag.get(0) + "\",");
        sb.append("\"toparty\":" + "\"" + TouserTopartyTotag.get(1) + "\",");
        sb.append("\"totag\":" + "\"" + TouserTopartyTotag.get(2) + "\",");

        sb.append("\"msgtype\":" + "\"" + MSGTYPE + "\",");
        sb.append("\"text\":" + "{");
        sb.append("\"content\":" + "\"" + content + "\"");
        sb.append("}");

        sb.append(",\"safe\":" + "\"" + safe + "\",");
        sb.append("\"agentid\":" + "\"" + AGENTID + "\",");
        sb.append("\"debug\":" + "\"" + "1" + "\"");
        sb.append("}");
        String json = sb.toString();
        try {

            uRl = new URL(action);

            HttpsURLConnection http = (HttpsURLConnection) uRl.openConnection();

            http.setRequestMethod("POST");

            http.setRequestProperty("Content-Type",

                    "application/json;charset=UTF-8");

            http.setDoOutput(true);

            http.setDoInput(true);

            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//
            // 连接超时30秒

            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //
            // 读取超时30秒

            http.connect();

            OutputStream os = http.getOutputStream();

            os.write(json.getBytes("UTF-8"));// 传入参数

            InputStream is = http.getInputStream();

            int size = is.available();

            byte[] jsonBytes = new byte[size];

            is.read(jsonBytes);

            String result = new String(jsonBytes, "UTF-8");

            //System.out.println("请求返回结果:" + result);

            os.flush();

            os.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static List<String> checkTouserTopartyTotag(List<String> tousers, List<String> topartys, List<String> totags) {
        List<String> TouserTopartyTotagList = new ArrayList<>();
        String touser = "";
        String toparty = "";
        String totag = "";
        if (tousers == null || tousers.isEmpty()) {
            touser = TOPARTY;
        } else {
            for (int i = 0; i < tousers.size(); i++) {
                if (i < tousers.size() - 1) {
                    touser = touser + tousers.get(i) + "|";
                } else {
                    touser = touser + tousers.get(i);
                }
            }
        }

        if (touser.equals(TOPARTY)) {
            toparty = "";
        } else {
            if (topartys == null || topartys.isEmpty()) {
                toparty = "";
            } else {
                for (int i = 0; i < topartys.size(); i++) {
                    if (i < topartys.size() - 1) {
                        toparty = toparty + topartys.get(i) + "|";
                    } else {
                        toparty = toparty + topartys.get(i);
                    }
                }
            }
        }

        if (touser.equals(TOPARTY)) {
            totag = "";
        } else {
            if (totags == null || totags.isEmpty()) {
                totag = "";
            } else {
                for (int i = 0; i < totags.size(); i++) {
                    if (i < totags.size() - 1) {
                        totag = totag + totags.get(i) + "|";
                    } else {
                        totag = totag + totags.get(i);
                    }
                }
            }
        }
        TouserTopartyTotagList.add(touser);
        TouserTopartyTotagList.add(toparty);
        TouserTopartyTotagList.add(totag);

        return TouserTopartyTotagList;
    }


    /**
     * 根据username获取wechatid(企业微信推送人员)
     *
     * @param message
     */

    @Override
    protected void pushToUsers(Message message, List<PushInfo> clientIds) {
        if (message.getIsWechatWorkorder().equals("wechatworkorder") ) {
            logger.debug("pushToUsers ....{}", message.getIsWechatWorkorder());
            String appName = message.getTargetApps().get(0);
            String progectId = message.getTargetApps().get(1);
            String statusId = message.getStatus();

            List<String> status_appList = new ArrayList<String>();
            status_appList.add("4");
            status_appList.add("7");
            status_appList.add("9");
            status_appList.add("10"); //app工单的4种推送状态
            boolean status_app = status_appList.contains(statusId) && "mems".equals(appName) && "6".equals(progectId);

            if (("wechat".equals(appName) && "6".equals(progectId)) || status_app) {
                logger.debug("pushToUsers ....{}", new Date());
                sendWeChatMsg("news", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "", "", "" + message.getWechattitle(),
                        "" + message.getWechatcontent(), "" + message.getUrl(), "" + message.getPicurl(), "0");
            }
        }
    }

    @Override
    protected void pushToApp(Message message) {

    }
}
