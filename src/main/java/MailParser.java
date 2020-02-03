import javax.mail.*;
import java.sql.SQLException;
import java.io.*;
import java.util.Properties;

public class MailParser {

    public static void main(String[] args) throws IOException, MessagingException, SQLException {
        UserProperties props = new UserProperties();
        MailService.connectToMail(props);

        OracleService.connectToOracle(props);
        OracleService.write(MailService.messages, props);

        MailService.closeMailConnection();
        OracleService.closeOracleConnection();
    }
}
