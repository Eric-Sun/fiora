package com.j13.fiora.core;

import org.apache.commons.fileupload.FileItem;

import java.util.HashMap;

public class RequestData extends HashMap<String, Object> {

    private int uid;
    private String deviceId;
    private FileItem file;

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getString(String key) {
        return new String(get(key) + "");
    }

    public Integer getInteger(String key) {
        return new Integer(get(key) + "");
    }

    public Long getLong(String key) {
        return new Long(get(key) + "");
    }
}
