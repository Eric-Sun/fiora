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
        NHDZFetcher fetcher = WebApplicationContentHolder.getApplicationContext().getBean(NHDZFetcher.class);
        Random random = new Random();
        while (true) {
            try {

                fetcher.fetch();
            } catch (Exception e) {
                LOG.info("", e);
            }
            try {
                int i = random.nextInt(15) + 5;
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
