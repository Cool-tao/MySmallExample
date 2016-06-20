package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by taoshuang on 2016/6/20.
 */
public class PropertiesDemo {
    public static final String TAG = "PropertiesDemo";

    public static final void main(String args[]) {
        Properties prop = new Properties();
        prop.put("Prop1", "Prop1");
        prop.put("Prop2", "Prop2");
        saveConfig("C:/Users/taoshuang/Desktop/prop.txt", prop);
        System.out.println("OK");
        prop = loadConfig("C:/Users/taoshuang/Desktop/prop.txt");
        String prop1 = prop.getProperty("Prop1");
        System.out.println("Prop1=" + prop1);
    }

    public static Properties loadConfig(String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveConfig(String file, Properties properties) {
        try {
            FileOutputStream s = new FileOutputStream(file, false);
            properties.store(s, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
