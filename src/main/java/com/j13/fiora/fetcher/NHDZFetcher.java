package com.j13.fiora.fetcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.j13.fiora.core.ErrorCode;
import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.FioraException;
import com.j13.fiora.core.exception.ErrorResponseException;
import com.j13.fiora.util.InternetUtil;
import com.j13.fiora.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class NHDZFetcher implements Fetcher {

    private static Logger LOG = LoggerFactory.getLogger(NHDZFetcher.class);
    private Random random = new Random();

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
            if (rawResponse.indexOf("您访问的页面不存在") > 0)
                continue;
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


            // comment
            List<Comment> recentCommentList = Lists.newLinkedList();
            List<Comment> topCommentList = Lists.newLinkedList();
            String commentResponse = InternetUtil.get("http://neihanshequ.com/m/api/get_essay_comments/?group_id=" + id
                    + "&app_name=neihanshequ_web&offset=0");

            JSONObject o1 = JSON.parseObject(commentResponse);
            JSONObject o2 = o1.getJSONObject("data");
            JSONArray recentCommentsArray = o2.getJSONArray("recent_comments");
            for (Object o : recentCommentsArray.toArray()) {
                JSONObject o3 = (JSONObject) o;
                String content = o3.getString("text");
                String commentId = o3.getString("comment_id");
                Comment c = new Comment();
                c.setContent(content);
                c.setId(commentId);
                recentCommentList.add(c);
            }

            JSONArray topCommentsArray = o2.getJSONArray("top_comments");
            for (Object o : topCommentsArray.toArray()) {
                JSONObject o3 = (JSONObject) o;
                String content = o3.getString("text");
                String commentId = o3.getString("comment_id");
                Comment c = new Comment();
                c.setContent(content);
                c.setId(commentId);
                topCommentList.add(c);
            }

            dz.setRecentCommentList(recentCommentList);
            dz.setTopcommentList(topCommentList);

        }
        return dzList;
    }


    private Iterator<String> getAndParseDZId() throws FioraException {
        String rawResponse = InternetUtil.get("http://www.neihanshequ.com");
        int idx = rawResponse.indexOf("var gGroupIdList = ");
        String tmp1 = rawResponse.substring(idx + 21, rawResponse.length());
        LOG.info("c : " + tmp1.substring(0, 200));
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
                        dz.getContent(), dz.getMd5(), FioraConstants.FetchSource.NHDZ, dz.getSourceDzId());
            } catch (Exception e) {
                LOG.info("begin to try again.");
                dzId = jaxManager.addDZ(FioraConstants.SYSTEM_FETCHER_USER_ID, FioraConstants.SYSTEM_FETCHER_DEFAULT_DEVICEID,
                        dz.getContent(), dz.getMd5(), FioraConstants.FetchSource.NHDZ, dz.getSourceDzId());
                LOG.info("try again finished.");
            }
            // 无论dz是否存在都会尝试插入评论
            String sourceCommentId = "";
            try {
                for (Comment c : dz.getRecentCommentList()) {
                    sourceCommentId = c.getId();
                    jaxManager.addRecentComment(c.getContent(), dzId, randomHot(), c.getId());
                    LOG.info("dz's comment added to recent. dzId={}, sourceCommentId={}", dzId, sourceCommentId);
                }
                for (Comment c : dz.getTopcommentList()) {
                    sourceCommentId = c.getId();
                    jaxManager.addTopComment(c.getContent(), dzId, randomTopHot(), c.getId());
                    LOG.info("dz's comment added to top. dzId={}, sourceCommentId={}", dzId, sourceCommentId);
                }
            } catch (ErrorResponseException e) {
                if (e.getCode() == ErrorCode.Comment.MACHINE_COMMENT_EXISTED) {
                    LOG.info("dz's comment existed. dzId={}, sourceCommentId={}", dzId, sourceCommentId);
                } else {
                    LOG.error(e.toErrorString());
                }
            }

            LOG.info("add dz(NHDZ). MD5={},dzId={},recentCommentSize={},topCommentSize={}", dz.getMd5(), dzId,
                    dz.getRecentCommentList().size(), dz.getTopcommentList().size());
        }
    }


    private int randomHot() {
        return random.nextInt(10);
    }

    private int randomTopHot() {
        return random.nextInt(100) + 100;
    }
}



