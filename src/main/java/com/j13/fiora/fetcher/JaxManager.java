package com.j13.fiora.fetcher;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.j13.fiora.api.jax.DzAddResponse;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.JaxServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class JaxManager {
    private static Logger LOG = LoggerFactory.getLogger(JaxManager.class);

    @Autowired
    JaxServerUtil jaxServerUtil;


    public int addDZ(int uid, String deviceId, String content, String md5, int fetchSource, long sourceDZId) throws FioraException {
        Map<String, String> innerParams = Maps.newHashMap();
        innerParams.put("content", content);
        innerParams.put("md5", md5);
        innerParams.put("fetchSource", fetchSource + "");
        innerParams.put("sourceDZId", sourceDZId + "");


        Map<String, String> params = Maps.newHashMap();
        params.put("args", JSON.toJSONString(innerParams));
        params.put("uid", uid + "");
        params.put("deviceId", deviceId);
        params.put("act", "dz.add");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

        LOG.info("Url = " + url + " params = " + paramString);
        String rawResponse = InternetUtil.post(url, params);
        DzAddResponse dzAddResponse = JSON.parseObject(rawResponse, DzAddResponse.class);
        return dzAddResponse.getData();
    }

    public int addMachineUser(int uid, String deviceId, String userName, String fileName) throws FioraException {
        Map<String, String> innerParams = Maps.newHashMap();
        innerParams.put("nickName", userName);
        innerParams.put("mobile", "-1");
        innerParams.put("password", "-1");
        innerParams.put("isMachine", "1");


        Map<String, String> params = Maps.newHashMap();
        params.put("args", JSON.toJSONString(innerParams));
        params.put("uid", uid + "");
        params.put("deviceId", deviceId);
        params.put("act", "user.register");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

        String rawResponse = InternetUtil.postFile(url, params, fileName);
        DzAddResponse dzAddResponse = JSON.parseObject(rawResponse, DzAddResponse.class);
        return dzAddResponse.getData();

    }
}