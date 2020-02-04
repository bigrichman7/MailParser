import java.io.IOException;
import java.util.Properties;

public class OracleProperties {
    private static OracleProperties instance;
    String driverName ;
    String db_host;
    String db_port;
    String db_sid;
    String db_login;
    String db_pass;

    public static OracleProperties getInstance() throws IOException {
        if (instance == null)
            instance = new OracleProperties();
        return instance;
    }

    private OracleProperties() throws IOException {
        Properties props = GetProperties.getProperties();
        this.driverName = "oracle.jdbc.driver.OracleDriver";
        this.db_host = props.getProperty("db.host");
        this.db_port = props.getProperty("db.port");
        this.db_sid = props.getProperty("db.sid");
        this.db_login = props.getProperty("db.login");
        this.db_pass = props.getProperty("db.password");
    }
}
