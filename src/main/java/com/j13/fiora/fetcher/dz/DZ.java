package com.j13.fiora.fetcher.dz;

import java.util.LinkedList;
import java.util.List;

public class DZ {
    private String content;
    private String md5;
    private int sourceId;
    private long sourceDzId;
    private List<Comment> recentCommentList = new LinkedList<Comment>();
    private List<Comment> topcommentList = new LinkedList<Comment>();

    public List<Comment> getRecentCommentList() {
        return recentCommentList;
    }

    public void setRecentCommentList(List<Comment> recentCommentList) {
        this.recentCommentList = recentCommentList;
    }

    public List<Comment> getTopcommentList() {
        return topcommentList;
    }

    public void setTopcommentList(List<Comment> topcommentList) {
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

