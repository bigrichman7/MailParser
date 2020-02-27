import javax.mail.*;
import java.io.IOException;
import java.sql.SQLException;

public class MailService implements Runnable{
    private Store store;
    private Folder inbox;
    public Message[] messages;
    public MailProperties props;

    public MailService(MailProperties props) {
        this.props = props;
    }

    @Override
    public void run() {
        try {
            connectToMail();
            OracleService oracleService = new OracleService();
            oracleService.write(messages, OracleProperties.getInstance());
            closeMailConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectToMail() throws IOException {
        Session session = null;
        session = Session.getDefaultInstance(GetProperties.getProperties(), null);
        try {
            assert session != null;
            store = session.getStore("pop3s");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            store.connect(props.getHost(), props.getPort(), props.getLogin(), props.getPassword());
        } catch (MessagingException e) {
            Report.mailServicesWithError(this);
        }
        try {
            openINBOX();
        } catch (MessagingException e) {
            //e.printStackTrace();
        }
        Report.counterAllMessages(messages.length);
    }

    private void openINBOX() throws MessagingException {
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        messages = inbox.getMessages();
    }

    public void closeMailConnection() throws MessagingException {
        inbox.close(Boolean.parseBoolean(props.getMailRemover()));
        store.close();
    }
}
