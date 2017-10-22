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
import java.net.URL;
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

        for (int i = 1; i < 10000; i++) {

            for (int j = 1; j < 100; j++) {

                String url = String.format(urlTemplate, i, j);
                LOG.info("url is {}", url);
                try {
                    String path = downLoadFromUrl(url, i + "", j + ".jpg", picDir);
                    int albumRemoteId = i;
                    LOG.info("path = {}", path);
                    if (path != null) {
                        int albumId = 0;
                        // check album .and add
                        if (!albumRemoteService.checkAlbumExist(albumRemoteId)) {

                            // 分析页面获取title，并且创建event，关联用户，填入title，确定tag

                            albumId = albumRemoteService.addAlbum(SOURCE_ID, albumRemoteId);
                            LOG.info("add success. albumId = {}", albumId);
                        } else {
                            albumId = albumRemoteService.getAlbumId(albumRemoteId);
                        }

                        // add img

                        if (!albumRemoteService.checkImgExist(url)) {
                            albumRemoteService.addImg(path,albumId,url,j);
                        } else {
                            LOG.info("img existed. remoteUrl={}", url);
                        }
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
        conn.setConnectTimeout(2000);
        conn.setReadTimeout(1000);
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
        LOG.info("1");
        byte[] buffer = new byte[4096];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            LOG.info("2");
            out.write(buffer, 0, count);
            LOG.info("3");
        }
        out.close();
        in.close();
        LOG.info("4");
        conn.disconnect();
        return savePathStr;
    }


}
