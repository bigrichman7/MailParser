import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {
    public static Properties getProperties() throws IOException {
        Properties props = new Properties();
        //File jarPath = new File(MailParser.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        //FileInputStream fis = new FileInputStream(propertiesPath + File.separator + "config.properties");
        FileInputStream fis = new FileInputStream("/home/mamba/IdeaProjects/MailParser/src/main/resources/config.properties");
        props.load(fis);
        return props;
    }
}
