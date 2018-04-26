package com.avp.mem.njpb.api.wsock;

import com.avp.mem.njpb.api.wsock.service.RegisterRequest;
import com.avp.mem.njpb.api.wsock.service.RegisterResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by boris feng on 2017/6/27.
 */
public class WSockUtils {
    private static final Logger logger = LoggerFactory.getLogger(WSockUtils.class);

    static protected  <T> T toMessage(Class<T> cls, Map<String, ?> map) {
        try {
            T obj = cls.newInstance();
            BeanUtils.populate(obj, map);
            return obj;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    static public AbstraceMessage toMessage(String json) {
        if (StringUtils.isEmpty(json))
            return  null;

        AbstraceMessage message = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map =  mapper.readValue(json, new TypeReference<Map<String, String>>(){});

            Integer msgId = (Integer)map.get("msgId");
            if(map.containsKey("respTime")) {
                // response:register
                if (msgId.equals(AbstraceMessage.MessageBook.REGISTER.value())) {
                    message = toMessage(RegisterResponse.class, map);
                }
            } else if (map.containsKey("reqTime")){
                // request:register
                if (msgId.equals(AbstraceMessage.MessageBook.REGISTER.value())) {
                    message = toMessage(RegisterRequest.class, map);
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        return message;
    }

    static public String toJson(AbstraceMessage message) {
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }
}
