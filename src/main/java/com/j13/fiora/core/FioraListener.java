package com.j13.fiora.core;

import com.j13.fiora.core.config.PropertiesConfiguration;
import com.j13.fiora.fetcher.NHDZFetcher;
import com.j13.fiora.fetcher.QSBKFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Random;

public class FioraListener implements ServletContextListener {
    private static Logger LOG = LoggerFactory.getLogger(FioraListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        PropertiesConfiguration.getInstance().addResource("/fiora.properties");

        WebApplicationContentHolder.setServletContext(sce.getServletContext());
        NHDZFetcher fetcher1 = WebApplicationContentHolder.getApplicationContext().getBean(NHDZFetcher.class);
        QSBKFetcher fetcher2 = WebApplicationContentHolder.getApplicationContext().getBean(QSBKFetcher.class);
        Random random = new Random();
        while (true) {
            try {
                fetcher1.fetch();
                fetcher2.fetch();
            } catch (Exception e) {
                LOG.info("", e);
            }
            try {
                int i = random.nextInt(5) + 5;
                Thread.sleep(i * 60 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
