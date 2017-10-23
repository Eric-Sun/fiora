package com.j13.fiora.core;

import com.j13.fiora.core.config.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class  FioraListener implements ServletContextListener {
    private static Logger LOG = LoggerFactory.getLogger(FioraListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        PropertiesConfiguration.getInstance().addResource("/fiora.properties");

        if (PropertiesConfiguration.getInstance().getStringValue("fetch.switch").equals("on")) {
            FetchTask fetchTask = new FetchTask(sce);
            new Thread(fetchTask).start();
            LOG.info("Fetch task started.");
        }else{
            LOG.info("Fetch task switch off");

        }



    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
