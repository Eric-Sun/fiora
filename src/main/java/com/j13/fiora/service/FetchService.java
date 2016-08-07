package com.j13.fiora.service;

import com.google.common.base.Splitter;
import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.RequestData;
import com.j13.fiora.core.config.PropertiesConfiguration;
import com.j13.fiora.fetcher.JaxManager;
import com.j13.fiora.util.InternetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service("FetchService")
public class FetchService {
    private static Logger LOG = LoggerFactory.getLogger(FetchService.class);

    @Autowired
    JaxManager jaxManager;

    public int userFetch(RequestData requestData) throws FioraException {
        int start = requestData.getInteger("start");
        int end = requestData.getInteger("end");

        int count = 0;
        for (int id = start; id <= end; id++) {
            String rawResponse = InternetUtil.get("http://www.meipai.com/user/" + id);
            if (rawResponse.indexOf("<title>出错啦") > 0)
                continue;


            Iterator<String> iter = Splitter.on("<meta content=\"website\" property=\"og:type\"><meta content=\"").
                    split(rawResponse).iterator();

            iter.next();
            String s1 = iter.next();
            int i = s1.indexOf("<img src=\"");
            s1 = s1.substring(i + "<img src=\"".length(), s1.length());
            int i2 = s1.indexOf("\"");
            String url = s1.substring(0, i2).trim();
            if (url.indexOf("default") > 0) {
                continue;
            }


            int ii1 = s1.indexOf("<a hidefocus href=\"/user/" + id + "\" class=\"js-convert-emoji c535353\">");
            s1 = s1.substring(ii1 + ("<a hidefocus href=\"/user/" + id + "\" class=\"js-convert-emoji c535353\">").length());
            int ii2 = s1.indexOf("</a>");
            String userName = s1.substring(0, ii2).trim();


            String dir = PropertiesConfiguration.getInstance().getStringValue("thumb.tmp.dir");

            String fileName = InternetUtil.getAndSaveFile(url, dir);

            int data = jaxManager.addMachineUser(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID
                    , userName, fileName);
            LOG.info("[MEIPAI] name={},url={},file={},response={},id={}", userName, url, fileName, data, id);

            InternetUtil.cleanTmpFile(dir, fileName);
            LOG.info("delete tmp file. filename={}", fileName);
            count++;
        }
        return count;
    }

}
