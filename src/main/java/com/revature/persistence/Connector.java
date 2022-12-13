package com.revature.persistence;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connection;
    private static String resourcesFile = "InfoResources1.properties";
    public Connector(){

    }

    public static Connection makeConnection(){
        if (connection == null){
            connect();
        }
        return connection;
    }

    private static void connect(){
        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream(resourcesFile);
            Properties props = new Properties();
            props.load(inputStream);

            StringBuilder blder = new StringBuilder();
            Class.forName(props.getProperty("driver"));

            blder.append("jdbc:postgresql://");
            blder.append(props.getProperty("host"));
            blder.append(":");
            blder.append(props.getProperty("port"));
            blder.append("/");
            blder.append(props.getProperty("dbname"));
            blder.append("?user=");
            blder.append(props.getProperty("username"));
            blder.append("&password=");
            blder.append((props.getProperty("password")));
            connection = DriverManager.getConnection(blder.toString());
            //run here ?

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
