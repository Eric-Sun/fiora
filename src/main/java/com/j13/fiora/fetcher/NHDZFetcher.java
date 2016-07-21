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
            Iterator<String> tmpIter = Splitter.on("<h1 class=\"title\">").split(rawResponse).iterator();
            tmpIter.next();
            String tmp = tmpIter.next();
            String dzContent = Splitter.on("</h1>").split(tmp).iterator().next();

            DZ dz = new DZ();
            dz.setContent(dzContent.trim().replaceAll("<p>", "").replaceAll("</p>", ""));
            dz.setMd5(MD5Encrypt.encode(dzContent));
            dz.setSourceId(new Long(id));
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
            LOG.info("add dz(HNDZ). MD5=" + dz.getMd5() + " dzId=" + dzId);
        }
    }


    private class DZ {
        private String content;
        private String md5;
        private long sourceId;

        public long getSourceId() {
            return sourceId;
        }

        public void setSourceId(long sourceId) {
            this.sourceId = sourceId;
        }

        private List<Comment> commentList = new LinkedList<Comment>();

        public List<Comment> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<Comment> commentList) {
            this.commentList = commentList;
        }

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

    }

    private class Comment {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

