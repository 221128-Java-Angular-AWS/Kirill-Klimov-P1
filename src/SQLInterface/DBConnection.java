package SQLInterface;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnection {
    //jdbc:postgresql://hostname:port/databaseName

    public void test() {
        connect();
    }
    private static Connection connect() {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader(); //for accessing files not visible when running for stream;
        InputStream inputStream = loader.getResourceAsStream("InfoResources1.properties");

        try {
            props.load(inputStream);
            StringBuilder bldr = new StringBuilder();

            bldr.append("jdbc:postgresql://");
            bldr.append(props.getProperty("host"));
            bldr.append(":");
            bldr.append(props.getProperty("port"));
            bldr.append("/");
            bldr.append(props.getProperty("dbname"));
            bldr.append("?user=");
            bldr.append(props.getProperty("username"));
            bldr.append("&");
            bldr.append((props.getProperty("password")));

            System.out.println(bldr.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
