import javax.mail.*;
import java.sql.SQLException;
import java.io.*;

public class MailParser {

    public static void main(String[] args) throws IOException, MessagingException, SQLException {
        UserProperties props = new UserProperties();
        MailConnection mailConnection = new MailConnection(props);
        OracleService oracleService = new OracleService(props);

        oracleService.connectToOracle();
        oracleService.write(mailConnection.getMessages());

        mailConnection.closeMailConnection();
        oracleService.closeOracleConnection();
    }
}
