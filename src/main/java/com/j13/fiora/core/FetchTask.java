package com.j13.fiora.core;

import com.j13.fiora.fetcher.Fetcher;
import com.j13.fiora.fetcher.dz.NHDZFetcher;
import com.j13.fiora.fetcher.mm131.MM131Fetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import java.util.Random;

public class FetchTask implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(FetchTask.class);
    private ServletContextEvent sce = null;

    public FetchTask(ServletContextEvent sce) {
        this.sce = sce;
    }

    @Override
    public void run() {
        WebApplicationContentHolder.setServletContext(sce.getServletContext());
//        NHDZFetcher fetcher1 = WebApplicationContentHolder.getApplicationContext().getBean(NHDZFetcher.class);
        // close qsbk    2016/8/29
//        QSBKFetcher fetcher2 = WebApplicationContentHolder.getApplicationContext().getBean(QSBKFetcher.class);

        MM131Fetcher fetcher1 = WebApplicationContentHolder.getApplicationContext().getBean(MM131Fetcher.class);
//        Fetcher fetcher1 = WebApplicationContentHolder.getApplicationContext().getBean(NHDZFetcher.class);
        try {
            fetcher1.fetch();
        } catch (FioraException e) {
            e.printStackTrace();
        }
    }
}
