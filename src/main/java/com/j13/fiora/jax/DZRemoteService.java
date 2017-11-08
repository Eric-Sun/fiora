package com.j13.fiora.jax;

import com.google.common.collect.Maps;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.JaxServerUtil;
import com.j13.jax.fetcher.resp.FetcherAddCommentResp;
import com.j13.jax.fetcher.resp.FetcherDZAddResp;
import com.j13.poppy.core.CommonResultResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DZRemoteService {
    private static Logger LOG = LoggerFactory.getLogger(DZRemoteService.class);

    @Autowired
    JaxServerUtil jaxServerUtil;

    public int addComment(int dzId, String content, int userId, int isHot) throws FioraException, ErrorResponseException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("dzId", dzId);
        params.put("content", content);
        params.put("userId", userId);
        params.put("act", "fetcher.addComment");
        params.put("isHot", isHot);
        String url = jaxServerUtil.getBaseUrl();

        String rawResponse = InternetUtil.post(url, params);
        FetcherAddCommentResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherAddCommentResp.class);
        } catch (ErrorResponseException e) {
            throw e;
        }
        return resp.getDzCommentId();
    }


    public int addDZ(int userId, String content, int sourceId, String sourceDZId) throws FioraException, ErrorResponseException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sourceId", sourceId);
        params.put("sourceDZId", sourceDZId);
        params.put("content", content);
        params.put("userId", userId);
        params.put("act", "fetcher.dzAdd");
        String url = jaxServerUtil.getBaseUrl();

        String rawResponse = InternetUtil.post(url, params);
        FetcherDZAddResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherDZAddResp.class);
        } catch (ErrorResponseException e) {
            throw e;
        }
        return resp.getDzId();
    }

}
