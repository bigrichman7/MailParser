import java.util.ArrayList;
import java.util.List;

public class Report {
    private static int countAllMessages = 0;
    private static List<MailService> mailServices = new ArrayList<>();

    public synchronized static void counterAllMessages(int count) {
        countAllMessages = countAllMessages + count;
    }

    public synchronized static void mailServicesWithError(MailService mailService) {
        mailServices.add(mailService);
    }

    public static String getReport() {
        String report;
        if (countAllMessages != 0) report = countAllMessages + " messages was wrote successfully";
        else report = "No messages have been wrote";
        if (mailServices.size() != 0) {
            for (MailService oneService :
                    mailServices) {
                report = report + "\n" + oneService.props.getLogin() + " hasn't been connected";
            }
        }
        return report;
    }
}
