package com.j13.fiora.core;

import com.j13.fiora.fetcher.Fetcher;
import com.j13.fiora.fetcher.mm131.MM131Fetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

public class FetchTask implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(FetchTask.class);
    private ServletContextEvent sce = null;

    private Class fetcherClazz = null;

    public FetchTask(ServletContextEvent sce, Class fetcherClazz) {
        this.sce = sce;
        this.fetcherClazz = fetcherClazz;
    }

    @Override
    public void run() {
        WebApplicationContentHolder.setServletContext(sce.getServletContext());
        Fetcher fetcher = (Fetcher) WebApplicationContentHolder.getApplicationContext().getBean(fetcherClazz);
        try {
            while (true) {
                fetcher.fetch();
            }
        } catch (FioraException e) {
            e.printStackTrace();
        }
    }
}
