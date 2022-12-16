package com.revature.servlet;
import com.revature.persistence.UserDao;
import com.revature.service.UserService;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
public class DependencyLoaderListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
            System.out.println("Servlet container init...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
            System.out.println("Servlet container destroy...");
    }
}
