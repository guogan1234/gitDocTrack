/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.rest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amber on 2017/09/22.
 */
@Service
@SuppressWarnings("unchecked")
public class RestClientService {

    private final Logger logger = LoggerFactory.getLogger(RestClientService.class);

    RestTemplate restTemplate = new RestTemplate();

    static public class DateConverter implements Converter {
        private ThreadLocal<SimpleDateFormat[]> tlsFormatters = new ThreadLocal<SimpleDateFormat[]>() {
            @Override
            protected SimpleDateFormat[] initialValue() {
                return new SimpleDateFormat[]{
                        // "2016-01-19T11:09:54.555+0000"
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),

                        // "2016-01-19"
                        new SimpleDateFormat("yyyy-MM-dd"),

                        // "11:09:54"
                        new SimpleDateFormat("HH:mm:ss")
                };
            }
        };

        @Override
        public synchronized <T> T convert(Class<T> paramClass, Object paramObject) {
            if (paramObject == null) {
                return null;
            }

            if (paramObject instanceof Long) {
                return (T) new Date((long) paramObject);
            }

            for (SimpleDateFormat formatter : tlsFormatters.get()) {
                try {
                    Date date = formatter.parse(paramObject.toString());
                    if (date != null) {
                        return (T) date;
                    }
                } catch (ParseException e) {
                }
            }
            return null;
        }
    }

//    protected boolean MapToObject(Object object, Map<String, ?> map) {
//        try {
//            BeanUtils.populate(object, map);
//            return true;
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return false;
//        }
//    }

    protected <T> T mapToNewObject(Class<T> cls, Map<String, ?> map) {
        try {
            T obj = cls.newInstance();
            BeanUtils.populate(obj, map);
            return obj;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public <T> T getOneByUri(URI uri, Class<T> cls) {
        HashMap<String, Object> map = restTemplate.getForObject(uri, HashMap.class);
        return this.mapToNewObject(cls, map);
    }

    @Deprecated
    public void putByUri(URI uri, Object request) {
        restTemplate.put(uri, request);
    }

    public <T> T postByUri(URI uri, Object request, Class<T> cls) {
        HashMap<String, Object> map = restTemplate.postForObject(uri, request, HashMap.class);
        return this.mapToNewObject(cls, map);
    }

    public void postByUri(URI uri, Object request) {
        restTemplate.postForLocation(uri, request);
    }

    public <T> List<T> getListByUri(URI uri, String key, Class<T> cls) {
        List<Map<String, Object>> mapList;
        if (key == null) {
            mapList = restTemplate.getForObject(uri, List.class);
        } else {
            HashMap<String, Object> result = restTemplate.getForObject(uri, HashMap.class);
            mapList = (List<Map<String, Object>>) ((HashMap<String, Object>) result.get("_embedded")).get(key);
        }

        List<T> result = new ArrayList<T>();
        for (Map<String, Object> property : mapList) {
            T view = this.mapToNewObject(cls, property);
            result.add(view);
        }

        return result;
    }

}
