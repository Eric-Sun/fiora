package com.j13.fiora.fetcher;

import java.util.LinkedList;
import java.util.List;

public class DZ {
    private String content;
    private String md5;
    private int sourceId;
    private long sourceDzId;
    private List<String> recentCommentList = new LinkedList<String>();
    private List<String> topcommentList = new LinkedList<String>();

    public List<String> getRecentCommentList() {
        return recentCommentList;
    }

    public void setRecentCommentList(List<String> recentCommentList) {
        this.recentCommentList = recentCommentList;
    }

    public List<String> getTopcommentList() {
        return topcommentList;
    }

    public void setTopcommentList(List<String> topcommentList) {
        this.topcommentList = topcommentList;
    }

    public long getSourceDzId() {
        return sourceDzId;
    }

    public void setSourceDzId(long sourceDzId) {
        this.sourceDzId = sourceDzId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
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

