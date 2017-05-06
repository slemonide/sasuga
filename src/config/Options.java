package config;

import java.io.IOException;
import java.util.Properties;

/**
 * Contains general configurable options
 */
// Thanks to https://www.bartbusschots.ie/s/2006/12/30/simple-java-configuration-files-and-no-xml-in-sight/
public class Options {
    private Properties configFile;
    private static Options instance;

    private Options() {
        configFile = new Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("sasuga.conf"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Failed to load configuration file.");
            System.exit(1);
        }
    }

    public static Options getInstance() {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(configFile.getProperty(key));
    }

    public float getFloat(String key) {
        return Float.parseFloat(configFile.getProperty(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(configFile.getProperty(key));
    }
}
