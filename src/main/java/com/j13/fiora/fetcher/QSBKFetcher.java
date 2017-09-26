package com.j13.fiora.fetcher;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.MD5Encrypt;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class QSBKFetcher implements Fetcher {

    private static Logger LOG = LoggerFactory.getLogger(QSBKFetcher.class);
    @Autowired
    private JaxManager jaxManager;


    private List<DZ> dzList = Lists.newLinkedList();
    private List<FetchedUser> userLIst = Lists.newLinkedList();

    @Override
    public void fetch() throws FioraException {
        loadInfo("http://www.qiushibaike.com/text/");
        loadOldInfo(2);
        LOG.info("[QSBK] all size = {}", dzList.size());
        save(dzList);

    }

    public void loadOldInfo(int size) throws FioraException {
        for (int i = 2; i <= size; i++) {
            loadInfo("http://www.qiushibaike.com/text/page/" + i + "/?s=4897932");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void save(List<DZ> dzList) throws FioraException {

        for (DZ dz : dzList) {
            int dzId = 0;
            try {
                dzId = jaxManager.addDZ(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID,
                        dz.getContent(), dz.getMd5(), dz.getSourceId(), dz.getSourceDzId());
            } catch (ErrorResponseException e) {
                LOG.error("", e);
            }

            LOG.info("add dz(QSBK). MD5={}, dzId={}", dz.getMd5(), dzId);
        }


//        for (FetchedUser user : userLIst) {
//            int dzId = jaxManager.addMachineUser(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID,
//                    user.getUserName(), user.getThumbUrl());
//            LOG.info("add user. userName={}, userId={}", user.getUserName(), dzId);
//        }


    }

    private void loadInfo(String url) throws FioraException {
        String rawResponse = InternetUtil.get(url);
        Iterator<String> iter = Splitter.on("class=\"article block untagged mb15\" id='qiushi_tag_").split(rawResponse).iterator();
        iter.next();
        while (iter.hasNext()) {
            String str = iter.next();
            int index = str.indexOf("'");

            // 找到dzId
            String dzId = str.substring(0, index);
            str = str.substring(index);

            // 找到thumb
            int index2 = str.indexOf("<img src=\"");
            str = str.substring(index2 + "<img src=\"".length());
            int index3 = str.indexOf("\"");
            String thumbUrl = str.substring(0, index3);
            str = str.substring(index3);

            // username
            int index4 = str.indexOf("alt=\"");
            str = str.substring(index4 + "alt=\"".length());
            int index5 = str.indexOf("\"");
            String userName = str.substring(0, index5);
            if (userName.equals("匿名用户")) {
                userName = null;
            }
            str = str.substring(index5);


            //content
            int index6 = str.indexOf("<div class=\"content\">");
            str = str.substring(index6 + "<div class=\"content\">".length());
            int index7 = str.indexOf("</div>");
            String content = str.substring(0, index7).trim();

//            if (LOG.isDebugEnabled()) {
//                LOG.debug("[QSBK] dzId={},userName={},thumb={},content={}", dzId, userName, thumbUrl, content);
//            }

            DZ dz = new DZ();
            dz.setContent(content);
            dz.setMd5(MD5Encrypt.encode(content));
            dz.setSourceId(FioraConstants.FetchSource.QSBK);
            dz.setSourceDzId(new Long(dzId));
            dzList.add(dz);

            if (userName != null
                    || thumbUrl.equals("http://pic.qiushibaike.com/system/avtnew/2054/20541577/medium/nopic.jpg")
                    || thumbUrl.indexOf("anony.png") > 0) {
                FetchedUser fetchedUser = new FetchedUser();
                fetchedUser.setThumbUrl(thumbUrl);
                fetchedUser.setUserName(userName);
                userLIst.add(fetchedUser);
            }

        }

    }


}
