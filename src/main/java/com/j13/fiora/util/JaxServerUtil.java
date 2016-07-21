package com.j13.fiora.util;

import com.j13.fiora.core.FioraConstants;
import com.j13.fiora.core.config.PropertiesConfiguration;
import org.springframework.stereotype.Service;

@Service
public class JaxServerUtil {


    public String getBaseUrl() {
        String url = PropertiesConfiguration.getInstance().getStringValue(FioraConstants.JAX_SERVER_URL_KEY);
        return url;
    }


}
