package lab1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationProvider {
    public static Configuration load() { // read configuration from resources
        ObjectMapper objectMapper = new ObjectMapper();
        Configuration configuration = null;
            try {
                InputStream inputStream = Main.class.getResourceAsStream("parameters.json");
                configuration = objectMapper.readValue(inputStream, Configuration.class);
            } catch (IOException ignored) {
                System.out.println("Can not read file!");
            }
        return configuration;
    }
}
