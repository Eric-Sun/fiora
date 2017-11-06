package com.j13.fiora.jax;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.fetcher.Fetcher;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.JaxServerUtil;
import com.j13.jax.fetcher.resp.FetcherGetLastIndexResp;
import com.j13.jax.fetcher.resp.FetcherGetMVAlbumIdResp;
import com.j13.jax.fetcher.resp.FetcherMVAlbumAddResp;
import com.j13.jax.user.resp.UserRandomUserResp;
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
        params.put("act", "fetcher.checkMVAlbumExist");
        String url = jaxServerUtil.getBaseUrl();

        String rawResponse = InternetUtil.post(url, params);
        CommonResultResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, CommonResultResp.class);
        } catch (ErrorResponseException e) {

        }
        return resp.getResult() == 0 ? true : false;
    }


    public int addAlbum(int sourceId, int remoteAlbumId, int tagId, String title, int userId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteAlbumId", remoteAlbumId);
        params.put("sourceId", sourceId);
        params.put("tagId", tagId);
        params.put("title", title);
        params.put("act", "fetcher.mvAlbumAdd");
        params.put("userId", userId);
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        FetcherMVAlbumAddResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherMVAlbumAddResp.class);
            return resp.getMvAlbumId();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }

    public void addImg(String relationLocalPath, int albumId, String remoteUrl, int remoteImgId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("relationLocalPath", relationLocalPath);
        params.put("mvAlbumId", albumId);
        params.put("remoteUrl", remoteUrl);
        params.put("remoteImgId", remoteImgId);
        params.put("act", "fetcher.mvImgAdd");
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
        params.put("act", "fetcher.getMVAlbumId");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        FetcherGetMVAlbumIdResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherGetMVAlbumIdResp.class);
            return resp.getId();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }

    public boolean checkImgExist(String remoteImgUrl) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("remoteImgUrl", remoteImgUrl);
        params.put("act", "fetcher.checkMVImgExist");
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


    public int getLastIndex(int sourceId) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sourceId", sourceId);
        params.put("act", "fetcher.getLastIndex");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        FetcherGetLastIndexResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, FetcherGetLastIndexResp.class);
            return resp.getIndex();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }


    public boolean updateLastIndex(int sourceId, int lastIndex) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sourceId", sourceId);
        params.put("lastIndex", lastIndex);
        params.put("act", "fetcher.updateLastIndex");
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


    public int randomUser() throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("act", "user.randomUser");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        UserRandomUserResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, UserRandomUserResp.class);
            return resp.getUid();
        } catch (ErrorResponseException e) {
            return 0;
        }
    }


    public void addEvent(int userId, int familyId, int type, String title, String content) throws FioraException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userId);
        params.put("familyId", familyId);
        params.put("type", type);
        params.put("title", title);
        params.put("content", content);
        params.put("act", "event.add");
        String url = jaxServerUtil.getBaseUrl();
        String rawResponse = InternetUtil.post(url, params);
        CommonResultResp resp = null;
        try {
            resp = ResponseParser.parse(rawResponse, CommonResultResp.class);
        } catch (ErrorResponseException e) {
        }
    }
}
