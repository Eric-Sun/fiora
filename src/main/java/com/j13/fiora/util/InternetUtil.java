package com.j13.fiora.util;

import com.google.common.collect.Lists;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.config.PropertiesConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public class InternetUtil {

    public static String get(String url) throws FioraException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.86 Safari/537.36");
            httpGet.addHeader("Cookie", "uuid=\"w:f6bdcea4338c47ac89e503ae0153c988\"; __utma=101886750.1946652733.1464254094.1467133998.1467133998.1; __utmz=101886750.1467133998.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); tt_webid=16873306519; _gat=1; csrftoken=4d661dc9bbe498098e40beb48cb184f8; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1467714269,1468071983,1468493730,1468772301; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1468772487; _ga=GA1.2.1946652733.1464254094");
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String rawResponse = EntityUtils.toString(entity);
            return rawResponse;
        } catch (IOException e) {
            throw new FioraException("http error. Url=" + url, e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new FioraException("http error. Url=" + url, e);
                }

            }
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new FioraException("http error. Url=" + url, e);
            }
        }
    }


    public static String post(String url, Map<String, String> params) throws FioraException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = Lists.newLinkedList();

            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }


            HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8"));

            httpPost.setEntity(httpEntity);

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String rawResponse = EntityUtils.toString(entity);
            return rawResponse;
        } catch (IOException e) {
            throw new FioraException("http error. Url=" + url, e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new FioraException("http error. Url=" + url, e);
                }

            }
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new FioraException("http error. Url=" + url, e);
            }
        }
    }


    public static String postFile(String url, Map<String, String> params, String fileName) throws FioraException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            FileBody fileBody = new FileBody(new File(fileName));
            HttpPost httpPost = new HttpPost(url);
            MultipartEntity reqEntity = new MultipartEntity();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                reqEntity.addPart(key, new StringBody(params.get(key), Charset.forName("UTF-8")));
            }
            reqEntity.addPart("file", fileBody);

            httpPost.setEntity(reqEntity);

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String rawResponse = EntityUtils.toString(entity);
            return rawResponse;
        } catch (IOException e) {
            throw new FioraException("http error. Url=" + url, e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new FioraException("http error. Url=" + url, e);
                }

            }
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new FioraException("http error. Url=" + url, e);
            }
        }
    }


    public static String getAndSaveFile(String url, String dir) throws FioraException {
        Random ran = new Random();
        int i = ran.nextInt(1000);
        String fileName = System.currentTimeMillis() + i + ".jpg";
        String file = dir + File.separator + fileName;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.86 Safari/537.36");
            httpGet.addHeader("Cookie", "uuid=\"w:f6bdcea4338c47ac89e503ae0153c988\"; __utma=101886750.1946652733.1464254094.1467133998.1467133998.1; __utmz=101886750.1467133998.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); tt_webid=16873306519; _gat=1; csrftoken=4d661dc9bbe498098e40beb48cb184f8; Hm_lvt_773f1a5aa45c642cf87eef671e4d3f6a=1467714269,1468071983,1468493730,1468772301; Hm_lpvt_773f1a5aa45c642cf87eef671e4d3f6a=1468772487; _ga=GA1.2.1946652733.1464254094");
            response = httpclient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            File f = new File(file);
            FileOutputStream out = new FileOutputStream(f);

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();

            return fileName;
        } catch (IOException e) {
            throw new FioraException("http error. Url=" + url, e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new FioraException("http error. Url=" + url, e);
                }

            }
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new FioraException("http error. Url=" + url, e);
            }
        }
    }


    public static void cleanTmpFile(String dir, String fileName) {
        String file = dir + File.separator + fileName;
        new File(file).delete();
    }

}
