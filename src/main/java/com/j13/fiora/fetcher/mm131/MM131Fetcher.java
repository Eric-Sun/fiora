package com.j13.fiora.fetcher.mm131;

import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.config.PropertiesConfiguration;
import com.j13.fiora.fetcher.Fetcher;
import com.j13.fiora.jax.AlbumRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MM131Fetcher implements Fetcher {
    public static int SOURCE_ID = 1;

    private static Logger LOG = LoggerFactory.getLogger(MM131Fetcher.class);

    @Autowired
    AlbumRemoteService albumRemoteService;

    @Override
    public void fetch() throws FioraException {

        String urlTemplate = "http://img1.mm131.com/pic/%s/%s.jpg";
        String picDir = PropertiesConfiguration.getInstance().getStringValue("pic.dir");
        Random r = new Random();
        int last = 0;
//        int from = albumRemoteService.getLastIndex(SOURCE_ID);
        int from = 1000;
        LOG.info("fetch from = {}", from);
        for (int i = from; i < 10000; i++) {

//            if (last != 0) {
//                LOG.info("find last = {}", last);
//                albumRemoteService.updateLastIndex(SOURCE_ID, last);
//                break;
//            }
            // 检测如果这个album已经插入过了，就直接continue
            int albumRemoteId = i;
            if (albumRemoteService.checkAlbumExist(albumRemoteId)) {
                LOG.info("album has been saved. id={}", albumRemoteId);
                continue;
            }
            for (int j = 1; j < 100; j++) {

                String url = String.format(urlTemplate, i, j);
                LOG.info("url is {}", url);
                try {
                    String path = downLoadFromUrl(url, i + "", j + ".jpg", picDir);

                    LOG.info("path = {}", path);
                    if (path != null) {
                        int albumId = 0;
                        // check album .and add
                        if (!albumRemoteService.checkAlbumExist(albumRemoteId)) {

                            // 分析页面获取title，并且创建event，关联用户，填入title，确定tag
                            // 第一张图通过

                            Map<Integer, String> urlIndexMap = new HashMap<Integer, String>() {{
                                put(1, "xinggan");
                                put(2, "qingchun");
                                put(3, "qipao");
                                put(4, "xiaohua");
                                put(5, "chemo");
                                put(6, "mingxing");
                            }};
                            int tagId = 1;
                            String title = "";
                            for (int urlIndex = 1; urlIndex <= 5; urlIndex++) {
                                String tagUrl = "http://www.mm131.com/" + urlIndexMap.get(urlIndex) + "/" + i + ".html";
                                String urlContent = loadWebPage(tagUrl);
                                if (urlContent != null) {
                                    tagId = urlIndex;
                                    int i1 = urlContent.indexOf("<h5>");
                                    int i2 = i1 + 4;
                                    int i3 = urlContent.indexOf("</h5>");
                                    title = urlContent.substring(i2, i3);
                                    LOG.info("tag = {}, title = {}", urlIndexMap.get(urlIndex), title);
                                    break;
                                }
                            }


                            albumId = albumRemoteService.addAlbum(SOURCE_ID, albumRemoteId, tagId, title);
                            LOG.info("add success. albumId = {}", albumId);

                            // 随机用户，添加event
                            // 总为0
                            int randomUserId = albumRemoteService.randomUser();
                            LOG.info("random user success. userId = {}", randomUserId);
                            albumRemoteService.addEvent(randomUserId, 1, -1, "", albumId + "");
                            LOG.info("add event. ");

                        } else {
                            albumId = albumRemoteService.getAlbumId(albumRemoteId);
                        }

                        // add img
                        if (!albumRemoteService.checkImgExist(url)) {
                            albumRemoteService.addImg(path, albumId, url, j);
                        } else {
                            LOG.info("img existed. remoteUrl={}", url);
                        }
                    } else {
//                        if (j == 1 && last == 0) {
//                            last = i - 1;
//                        }
                        break;
                    }


                } catch (IOException e) {
                    LOG.error("", e);
                }
            }
        }


    }


    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static String downLoadFromUrl(String urlStr, String albumId, String fileName, String savePath) throws IOException {
        String savePathStr = null;
        File albumFolder = new File(savePath, albumId);
        if (!albumFolder.exists())
            albumFolder.mkdir();

        int r = 0;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        //设置超时间为3秒
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        conn.setRequestProperty("Referer", "http://www.mm131.com/qingchun/2194.html");

        //防止屏蔽程序抓取而返回403错误
        int retCode = 0;
        try {
            retCode = conn.getResponseCode();
        } catch (Exception e) {
            LOG.info("maybe 404.");
            return savePathStr;
        }
        LOG.info("response code is {}", retCode);
        if (retCode == 404)
            return savePathStr;
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + albumId + File.separator + fileName);
        savePathStr = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        DataInputStream in = new DataInputStream(conn.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[4096];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
        conn.disconnect();
        return savePathStr;
    }

    public String loadWebPage(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        //设置超时间为3秒
        conn.setConnectTimeout(2000);
        conn.setReadTimeout(1000);
        conn.setRequestProperty("Referer", "http://www.mm131.com/qingchun/2194.html");

        //防止屏蔽程序抓取而返回403错误
        int retCode = 0;
        try {
            retCode = conn.getResponseCode();
        } catch (Exception e) {
            retCode = 404;
        }
        LOG.info("response code is {}", retCode);
        if (retCode == 404)
            return null;

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GB2312"));//GB2312可以根据需要替换成要读取网页的编码
        String a = "";
        StringBuilder sb = new StringBuilder();
        while ((a = in.readLine()) != null) {
            sb.append(a);
        }
        return sb.toString();
    }


}
