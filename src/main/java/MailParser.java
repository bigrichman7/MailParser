import javax.mail.*;
import java.sql.SQLException;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MailParser {

    public static void main(String[] args) throws IOException, MessagingException, SQLException, InterruptedException {
        OracleService.connectToOracle(OracleProperties.getInstance());

        if (OracleService.isConnected) {
            ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(GetProperties.getProperties().getProperty("countThreads")));
            while (MailProperties.isNextMail())
                executorService.submit(new MailService(new MailProperties()));
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println(Report.getReport());
        } else {
            System.out.println("Check database sittings");
        }
    }
}
