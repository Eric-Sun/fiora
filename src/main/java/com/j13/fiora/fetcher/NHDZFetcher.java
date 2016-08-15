package com.j13.fiora.fetcher;

import com.google.common.base.Splitter;
import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class NHDZFetcher implements Fetcher {

    private static Logger LOG = LoggerFactory.getLogger(NHDZFetcher.class);

    @Autowired
    JaxManager jaxManager;

    @Override
    public void fetch() throws FioraException {
        Iterator<String> idIter = getAndParseDZId();
        List<DZ> dzList = parsePerDZ(idIter);
        save(dzList);
    }

    private List<DZ> parsePerDZ(Iterator<String> idIter) throws FioraException {
        List<DZ> dzList = new LinkedList<DZ>();
        while (idIter.hasNext()) {
            String id = idIter.next();
            String rawResponse = InternetUtil.get("http://neihanshequ.com/p" + id);
//            LOG.info("response length size={}", rawResponse.length());
            Iterator<String> tmpIter = Splitter.on("<h1 class=\"title\">").split(rawResponse).iterator();
            tmpIter.next();
            String tmp = tmpIter.next();
            String dzContent = Splitter.on("</h1>").split(tmp).iterator().next();

            DZ dz = new DZ();
            dz.setContent(dzContent.trim().replaceAll("<p>", "").replaceAll("</p>", ""));
            dz.setMd5(MD5Encrypt.encode(dzContent));
            dz.setSourceId(FioraConstants.FetchSource.NHDZ);
            dz.setSourceDzId(new Long(id));
            dzList.add(dz);

        }
        return dzList;
    }


    private Iterator<String> getAndParseDZId() throws FioraException {
        String rawResponse = InternetUtil.get("http://www.neihanshequ.com");
        int idx = rawResponse.indexOf("var gGroupIdList = ['");
        String tmp1 = rawResponse.substring(idx + 21, rawResponse.length());
        int idx2 = tmp1.indexOf("',-1];");
        String tmp2 = tmp1.substring(0, idx2);
        LOG.info("idList = " + tmp2);
        Iterator<String> iter = Splitter.on("','").split(tmp2).iterator();
        return iter;
    }

    private void save(List<DZ> recordList) throws FioraException {
        for (DZ dz : recordList) {
            int dzId = 0;
            try {
                dzId = jaxManager.addDZ(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID,
                        dz.getContent(), dz.getMd5(), FioraConstants.FetchSource.NHDZ, dz.getSourceId());
            } catch (Exception e) {
                LOG.info("begin to try again.");
                dzId = jaxManager.addDZ(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID,
                        dz.getContent(), dz.getMd5(), FioraConstants.FetchSource.NHDZ, dz.getSourceId());
                LOG.info("try again finished.");
            }
            LOG.info("add dz(NHDZ). MD5=" + dz.getMd5() + " dzId=" + dzId);
        }
    }


}

