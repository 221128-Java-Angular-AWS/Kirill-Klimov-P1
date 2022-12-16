package com.revature.servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.baseObjects.User;
import com.revature.persistence.UserDao;
import com.revature.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

public class UserServlet extends HttpServlet {
    private UserService service;
    private ObjectMapper mapper;
    @Override
    public void init() throws ServletException {
        this.service = new UserService(new UserDao());
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<User> users = service.getAllUsers();
        String json = mapper.writeValueAsString(users);
        resp.setStatus(200);
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //we need to try to pull info out of request obj,
        //get json body and map into an object, then persist it into the database
        StringBuilder jsonBuilder = new StringBuilder();
        BufferedReader reader = req.getReader();
        while(reader.ready()){ //read out of the box that was sent in
            jsonBuilder.append(reader.readLine()); //moves through buffer.
            //read then pring with toString() method below
        }

        System.out.println("Json: " + jsonBuilder.toString());
        User user = mapper.readValue(jsonBuilder.toString(), User.class); //User.class shows what kind of object we want to create with jsob builder
        service.registerNewUser(user);
        System.out.println(user);
        resp.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
