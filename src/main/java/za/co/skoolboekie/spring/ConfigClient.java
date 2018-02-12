package za.co.skoolboekie.spring;

/**
 * Created by ryan on 2/10/2018.
 */
public interface ConfigClient {
    public String getProperty(String name);

    public Object getObject(String name, Class clazz);
}
