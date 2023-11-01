package edu.bbte.idde.ohim2065.hardware.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigFactory {
    private static Config config;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    public static synchronized Config getConfig() {
        if (config == null) {
            InputStream inputStream;
            inputStream = Config.class.getResourceAsStream("/application.yml");
            try {
                config = new ObjectMapper(new YAMLFactory()).readValue(inputStream, Config.class);
            } catch (IOException e) {
                config = new Config();
            }
        }
        LOGGER.info(config.toString());
        return config;
    }
}

