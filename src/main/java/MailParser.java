import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.io.*;

import oracle.jdbc.*;

public class MailParser {

    public static void main(String[] args) throws IOException, MessagingException, SQLException {
        UserProperties props = new UserProperties();
        MailConnection mailConnection = new MailConnection(props);
        OracleConnection oracleConnection = new OracleConnection(props);
        WriteToOracle writeToOracle = new WriteToOracle(props, mailConnection.getMessages());



        mailConnection.closeMailConnection();
        oracleConnection.closeOracleConnection();
    }
}
