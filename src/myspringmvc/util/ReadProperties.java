package myspringmvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    protected static String getPackageName() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("springmvc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties.getProperty("basePackage").replace(".", "/");
    }
}
