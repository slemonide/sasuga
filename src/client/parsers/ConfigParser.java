package client.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Parses the config file (jlife.conf)
 */
public class ConfigParser {
    private String fileName;

    public ConfigParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the config txt file
     */
    public void parse() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        System.out.println(content);
    }
}
