package client.model;

import client.parsers.ConfigParser;
import server.model.WorldManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages the client configuration
 */
public class ConfigManager {
    private static final String CONFIG_FILE = "jlife.conf";
    private Map<String, String> configOptions;

    private static ConfigManager instance;

    private ConfigManager() {
        configOptions = new HashMap<>();
        setupDefaults(configOptions);

        ConfigParser parser = new ConfigParser(CONFIG_FILE);
        try {
            parser.parse();
        } catch (IOException e) {
            System.out.println("ERROR: cannot load config file: " + CONFIG_FILE);
            e.printStackTrace();
        }
    }

    /**
     * Singleton pattern
     * @return instance
     */
    public static ConfigManager getInstance() {
        if(instance == null){
            instance = new ConfigManager();
        }
        return instance;
    }

    private void setupDefaults(Map<String, String> upDefaults) {
        // stub
    }
}
