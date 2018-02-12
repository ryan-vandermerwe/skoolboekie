package za.co.skoolboekie.spring;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

/**
 * Created by ryan on 2/10/2018.
 */

@Resource
public class FileConfigClient implements ConfigClient {

    private ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

    // Read a json file from disk
    // Ability to search for json object / property in file
    // Return an object representing this object

    public FileConfigClient(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
    }

    @Override
    public String getProperty(String name) {
        return "";
    }

    @Override
    public Object getObject(String name, Class clazz) {
        return null;
    }
}
