import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class UserProperties{
    //Переменные config.properties для почтового сервера
    final String login;
    final String password;
    final String host;
    final int port;
    //Переменные config.properties для сервера БД
    final String driverName ;
    final String db_host;
    final String db_port;
    final String db_sid;
    final String db_login;
    final String db_pass;

    public UserProperties() throws IOException {
        this.login = getProperties().getProperty("mail.login");
        this.password = getProperties().getProperty("mail.password");
        this.host = getProperties().getProperty("mail.pop3.host");
        this.port = Integer.parseInt(getProperties().getProperty("mail.pop3.port"));
        this.driverName = "oracle.jdbc.driver.OracleDriver";
        this.db_host = getProperties().getProperty("db.host");
        this.db_port = getProperties().getProperty("db.port");
        this.db_sid = getProperties().getProperty("db.sid");
        this.db_login = getProperties().getProperty("db.login");
        this.db_pass = getProperties().getProperty("db.password");
    }

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
