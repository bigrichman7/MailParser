import java.io.IOException;
import java.util.Properties;

public class MailProperties {
    private static MailProperties instance;
    private int counterMail;
    private String login;
    private String password;
    private String host;
    private int port;
    public static boolean isNextMail;

    public static MailProperties getInstance() throws IOException {
        if (instance == null)
            instance = new MailProperties();
        return instance;
    }

    private MailProperties() {
        counterMail = 1;
        isNextMail = true;
        nextMail();
    }

    public void nextMail(){
        try {
            Properties props = GetProperties.getProperties();
            this.host = props.getProperty("mail.pop3.host" + counterMail);
            this.port = Integer.parseInt(props.getProperty("mail.pop3.port" + counterMail));
            this.login = props.getProperty("mail.login" + counterMail);
            this.password = props.getProperty("mail.password" + counterMail);
            counterMail++;
        } catch (Exception e) {
            isNextMail = false;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
