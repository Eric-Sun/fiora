package com.j13.fiora.fetcher;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.j13.fiora.api.jax.*;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.ResponseParser;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.JaxServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JaxManager {
    private static Logger LOG = LoggerFactory.getLogger(JaxManager.class);

    @Autowired
    JaxServerUtil jaxServerUtil;


    public int addDZ(int uid, String deviceId, String content, String md5, int fetchSource, long sourceDZId) throws FioraException, ErrorResponseException {
        Map<String, String> params = Maps.newHashMap();
        params.put("uid", uid + "");
        params.put("deviceId", deviceId);
        params.put("act", "dz.addFetchedDZ");
        params.put("content", content);
        params.put("md5", md5);
        params.put("fetchSource", fetchSource + "");
        params.put("sourceDZId", sourceDZId + "");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

//        LOG.info("Url = " + url + " params = " + paramString);
        String rawResponse = InternetUtil.post(url, params);
        IntegerResp resp = ResponseParser.parse(rawResponse, IntegerResp.class);
        return resp.getId();

    }

    public int addRecentComment(String content, int dzId, int hot, String commentId) throws FioraException, ErrorResponseException {


        Map<String, String> params = Maps.newHashMap();
        params.put("content", content);
        params.put("dzId", dzId + "");
        params.put("hot", hot + "");
        params.put("isTop", "0");
        params.put("sourceCommentId", commentId);
        params.put("act", "comment.addMachine");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

//        LOG.info("Url = " + url + " params = " + paramString);
        String rawResponse = InternetUtil.post(url, params);
        CommentAddMachineResp resp = ResponseParser.parse(rawResponse, CommentAddMachineResp.class);
        return resp.getId();
    }


    public int addTopComment(String content, int dzId, int hot, String commentId) throws FioraException, ErrorResponseException {
        Map<String, String> params = Maps.newHashMap();
        params.put("content", content);
        params.put("dzId", dzId + "");
        params.put("hot", hot + "");
        params.put("isTop", "1");
        params.put("sourceCommentId", commentId);
        params.put("act", "comment.addMachine");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

//        LOG.info("Url = " + url + " params = " + paramString);
        String rawResponse = InternetUtil.post(url, params);
        CommentAddMachineResp resp = null;
        resp = ResponseParser.parse(rawResponse, CommentAddMachineResp.class);
        return resp.getId();
    }

    public int addMachineUser(int uid, String deviceId, String userName, String fileName) throws FioraException, ErrorResponseException {


        Map<String, String> params = Maps.newHashMap();
        params.put("nickName", userName);
        params.put("mobile", "-1");
        params.put("password", "-1");
        params.put("isMachine", "1");

        params.put("uid", uid + "");
        params.put("deviceId", deviceId);
        params.put("act", "user.register");
        String url = jaxServerUtil.getBaseUrl();
        String paramString = JSON.toJSONString(params);

        String rawResponse = InternetUtil.postFile(url, params, fileName);
        DzAddResponse dzAddResponse = ResponseParser.parse(rawResponse, DzAddResponse.class);
//        return dzAddResponse.getData();
        return 0;

    }
}