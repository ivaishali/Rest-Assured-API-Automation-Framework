package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertyUtils {

    static Properties properties = new Properties();
    static Logger logger = LogManager.getLogger(PropertyUtils.class);

    public static void loadProperties() {
        try {
            // load locators properties from 'resources' folder
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/ConfigProperties");
            File[] files = file.listFiles();
            for (File f : files) {
                properties.load(new FileInputStream(f));
            }

            logger.trace("This is demo logger msg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        loadProperties();
    }
}
