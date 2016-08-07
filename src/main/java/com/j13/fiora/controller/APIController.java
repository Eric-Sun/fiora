package com.j13.fiora.controller;

import com.alibaba.fastjson.JSON;
import com.j13.fiora.core.HDConstants;
import com.j13.fiora.core.ResponseData;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class APIController {
    private static Logger LOG = LoggerFactory.getLogger(APIController.class);

    @Autowired
    HDDispatcher hdDispatcher;


    @RequestMapping("/")
    @ResponseBody
    public String dispatch(HttpServletRequest request, HttpServletResponse response) {

        FileItem file = null;
        //3、判断提交上来的数据是否是上传表单的数据
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try

        {
            String act = request.getParameter("act");
            int uid = 0;
            if (request.getParameter("uid") != null) {
                uid = new Integer(request.getParameter("uid"));
            }
            String args = request.getParameter("args");
            String deviceId = null;
            if (request.getParameter("deviceId") != null) {
                deviceId = request.getParameter("deviceId");
            }

            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list) {
                    if (!item.isFormField()) {
                        file = item;
                    } else {
                        if (item.getFieldName().equals("act")) {
                            act = item.getString();
                        } else if (item.getFieldName().equals("uid")) {
                            uid = new Integer(item.getString());
                        } else if (item.getFieldName().equals("args")) {
                            args = item.getString();
                        } else if (item.getFieldName().equals("deviceId")) {
                            deviceId = item.getString();
                        }
                    }
                }

            }

            LOG.info(JSON.toJSONString(request.getParameterMap()));
            Object resp = hdDispatcher.handle(act, uid, deviceId, args, file, request.getRemoteAddr());
            String respContent = JSON.toJSONString(new HDResponse(resp, uid, HDConstants.ResponseStatus.SUCCESS));
            LOG.info(respContent);
            response.getWriter().write(respContent);
            response.flushBuffer();
            return null;
        } catch (Exception e) {
            try {
                LOG.error("", e);
                response.getWriter().write(JSON.toJSONString(new HDResponse(new ResponseData(), 0, HDConstants.ResponseStatus.FAILURE)));
                response.flushBuffer();

            } catch (IOException e1) {
                LOG.error("", e);
            }
            return null;
        }
    }
}

class HDResponse {
    private int uid;
    private int status;
    private Object data;

    public HDResponse(Object data, int uid, int status) {
        this.data = data;
        this.uid = uid;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
