package za.co.tshimx.fnb.api.testcases;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class BaseTest {
    final static Logger logger = Logger.getLogger(BaseTest.class);
    Properties env_prop;

    public BaseTest(){
        logger.info("API BaseTest : loading properties file " );
        loadProperties();
        logger.info("API BaseTest :  properties loaded  Successfully " );
    }

   
    public void loadProperties()  {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rest_config.properties");
            env_prop = new Properties();
            env_prop.load(inputStream);
            inputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
