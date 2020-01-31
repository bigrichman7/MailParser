import javax.mail.*;
import java.io.IOException;

public class MailConnection {
    Store store;
    Folder inbox;
    Message[] messages;
    UserProperties props;

    public MailConnection(UserProperties props) throws IOException, MessagingException {
        this.props = props;
        connectToMail();
        openINBOX();
        System.out.println("Finded " + messages.length + " new messages");
        System.out.println();
    }

    private void connectToMail() {
        Session session = null;
        try {
            session = Session.getDefaultInstance(props.getProperties(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            store = session.getStore("pop3s");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        System.out.println("Connecting to " + props.host + "...");
        try {
            store.connect(props.host, props.port, props.login, props.password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Connected!");
    }

    private void openINBOX() throws MessagingException {
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        messages = inbox.getMessages();
    }

    public void closeMailConnection() throws MessagingException {
        inbox.close(true);
        store.close();
        System.exit(0);
    }

    public Message[] getMessages() {
        return messages;
    }
}
