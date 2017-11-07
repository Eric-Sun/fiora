package com.j13.fiora.core;

import com.j13.fiora.core.config.PropertiesConfiguration;
import com.j13.fiora.fetcher.mm131.MM131Fetcher;
import com.j13.fiora.fetcher.nhdz.NHDZFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FioraListener implements ServletContextListener {
    private static Logger LOG = LoggerFactory.getLogger(FioraListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        PropertiesConfiguration.getInstance().addResource("/fiora.properties");

        if (PropertiesConfiguration.getInstance().getStringValue("fetcher.mm131.switch").equals("on")) {
            FetchTask fetchTask = new FetchTask(sce, MM131Fetcher.class);
            new Thread(fetchTask).start();
            LOG.info("MM131 fetcher task started.");
        } else {
            LOG.info("MM131 fetcher task switch off");
        }


        if (PropertiesConfiguration.getInstance().getStringValue("fetcher.nhdz.switch").equals("on")) {
            FetchTask fetchTask = new FetchTask(sce, NHDZFetcher.class);
            new Thread(fetchTask).start();
            LOG.info("nhdz fetcher task started.");
        } else {
            LOG.info("nhdz fetcher task switch off");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
