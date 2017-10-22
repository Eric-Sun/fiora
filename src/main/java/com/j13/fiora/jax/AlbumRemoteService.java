package com.j13.fiora.jax;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.fetcher.Fetcher;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.JaxServerUtil;
import com.j13.jax.fetcher.req.FetcherCheckAlbumExistReq;
import com.j13.jax.fetcher.resp.FetcherGetAlbumIdResp;
import com.j13.poppy.core.CommonResultResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlbumRemoteService {

    @Autowired
    JaxServerUtil jaxServerUtil;

    public boolean checkAlbumExist(int remoteAlbumId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteAlbumId", remoteAlbumId);
        params.put("act", "fetcher.checkAlbumExist");
        String url = jaxServerUtil.getBaseUrl();

        String rawResponse = InternetUtil.post(url, params);
        CommonResultResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, CommonResultResp.class);
        } catch (ErrorResponseException e) {

        }
        return resp.getResult() == 0 ? true : false;
    }


    public int addAlbum(int sourceId, int remoteAlbumId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteAlbumId", remoteAlbumId);
        params.put("sourceId", sourceId);
        params.put("act", "fetcher.albumAdd");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        FetcherGetAlbumIdResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherGetAlbumIdResp.class);
            return resp.getId();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }

    public void addImg(String relationLocalPath, int albumId, String remoteUrl, int remoteImgId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("relationLocalPath", relationLocalPath);
        params.put("albumId", albumId);
        params.put("remoteUrl", remoteUrl);
        params.put("remoteImgId", remoteImgId);
        params.put("act", "fetcher.imgAdd");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        CommonResultResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, CommonResultResp.class);
        } catch (ErrorResponseException e) {

        }
    }


    public int getAlbumId(int remoteAlbumId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteAlbumId", remoteAlbumId);
        params.put("act", "fetcher.getAlbumId");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        FetcherGetAlbumIdResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherGetAlbumIdResp.class);
            return resp.getId();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }

    public boolean checkImgExist(String remoteImgUrl) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteImgUrl", remoteImgUrl);
        params.put("act", "fetcher.checkImgExist");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        CommonResultResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, CommonResultResp.class);
            return resp.getResult() == 0 ? true : false;
        } catch (ErrorResponseException e) {
            return false;
        }
    }
}
