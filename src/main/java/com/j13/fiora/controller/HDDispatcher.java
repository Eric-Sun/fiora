package com.j13.fiora.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.j13.fiora.core.RequestData;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

@Service
public class HDDispatcher implements ApplicationContextAware {


    List<String> whiteList = Lists.newArrayList();

    private ApplicationContext applicationContext;
    private final static String SUFFIX = "Service";

    public Object handle(String act, int uid, String deviceId, String args, FileItem file, String remoteIp) throws Exception {
        Object responseData = null;
        Iterator<String> iter = Splitter.on(".").split(act).iterator();
        String mod = iter.next();
        act = iter.next();
        StringBuilder sb = new StringBuilder();
        sb.append(mod.substring(0, 1).toUpperCase());
        sb.append(mod.substring(1));
        sb.append(SUFFIX);

        RequestData requestData = JSON.parseObject(args, RequestData.class);
        Object beanObj = applicationContext.getBean(sb.toString());
        Class clazz = beanObj.getClass();
        requestData.setUid(uid);
        requestData.setFile(file);
        requestData.setDeviceId(deviceId);
        try {
            Method m = clazz.getMethod(act, new Class[]{RequestData.class});

            responseData = m.invoke(beanObj, new Object[]{requestData});
            return responseData;

        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
