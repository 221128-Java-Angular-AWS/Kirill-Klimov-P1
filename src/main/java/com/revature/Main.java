package com.revature;
import com.revature.service.UserService;

import com.revature.persistence.Connector;
import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        try {
            Connector connector = new Connector();
            Connection connection = connector.makeConnection();
            System.out.println();
            System.out.println(connection);
            connection.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
