package com.j13.fiora.fetcher;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.FioraException;
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


    @Override
    public void fetch() throws FioraException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet("http://www.qiushibaike.com");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.86 Safari/537.36");
            response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            String rawResponse = EntityUtils.toString(entity1);

            List<Record> recordList = parseRawResponse(rawResponse);

            saveToDB(recordList);
        } catch (IOException e) {
            throw new FioraException("QSBK fetch. fetch action error.", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new FioraException("QSBK fetch. response close action error.", e);
                }

            }
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new FioraException("QSBK fetch. httpclient close action error.", e);
            }
        }
    }

    private void saveToDB(List<Record> recordList) {
        for (Record r : recordList) {
            if (r.getContent().indexOf("糗") > 0) {
                LOG.info("QSBK Record have 糗. drop it.");
                continue;
            }
            r.setMd5(MD5Encrypt.encode(r.getContent()));
//            if (!DZDAO.checkExist(r.getMd5())) {
//                long imgId = -1;
//                if (r.getImgUrl() != null) {
//                    imgId = imgDAO.insert(r.getImgUrl());
//                }
//                long dzId = DZDAO.insert(FioraConstants.SYSTEM_FETCHER_USER_ID, r.getContent(),
//                        imgId, r.getMd5(), FioraConstants.FetchSource.QSBK);
//                LOG.info("QSBK Record saved. dzId=" + dzId);
//            } else {
//                LOG.info("QSBK Record exists in db");
//            }

        }


    }


    private List<Record> parseRawResponse(String rawResponse) {
        List<Record> recordList = Lists.newArrayList();
        Iterator<String> strArr = Splitter.on("<div class=\"content\">").split(rawResponse).iterator();
        strArr.next();
        while (strArr.hasNext()) {
            Record r = new Record();
            // </div>之前的是内容
            String t = strArr.next();
            Iterator<String> i = Splitter.on("</div>").split(t).iterator();
            String s1 = i.next().trim().replace("<br/>", "");
            String s2 = i.next().trim();
            r.setContent(s1);
            // 如果有thumb
            Iterator<String> i2 = Splitter.on("<img src=\"").split(s2).iterator();
            i2.next();
            if (i2.hasNext()) {
                LOG.info("QSBK Record have img. drop it.");
                continue;
//                String s3 = i2.next().trim();
//                String s4 = Splitter.on("\"").split(s3).iterator().next().trim();
//                r.setImgUrl(s4);
            }
            recordList.add(r);
        }
        return recordList;
    }


    private class Record {
        private String content;
        private String imgUrl;
        private String md5;

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
