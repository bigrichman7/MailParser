import javax.mail.*;
import java.sql.SQLException;
import java.io.*;

public class MailParser {

    public static void main(String[] args) throws IOException, MessagingException, SQLException {
        MailProperties mailProperties = MailProperties.getInstance();
        OracleProperties props = OracleProperties.getInstance();

        OracleService.connectToOracle(props);

        do {
            MailService.connectToMail(mailProperties);
            OracleService.write(MailService.messages, props);
            MailService.closeMailConnection();
            try {
                MailProperties.getInstance().nextMail();
            } catch (Exception e) {
                return;
            }
        } while (MailProperties.isNextMail);

        OracleService.closeOracleConnection();
    }
}
