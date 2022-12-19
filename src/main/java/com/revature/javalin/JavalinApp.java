package com.revature.javalin;
import com.revature.baseObjects.User;
import com.revature.persistence.UserDao;
import com.revature.persistence.ReimbursementDao;
import io.javalin.Javalin;
import com.revature.service.*;
import com.revature.exceptions.UserNotFoundException;
import io.javalin.http.Context;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.baseObjects.Reimbursement;

import com.revature.service.ReimbursementService;
import io.javalin.http.Cookie;
import io.javalin.http.HttpStatus;
import io.javalin.http.util.CookieStore;


import java.util.ArrayDeque;
import java.util.Map;
import java.util.Set;
public class JavalinApp {
    private static Javalin app;
    private static UserService userService;
    private static ReimbursementService reimbursementService;

    private JavalinApp() {
    }

    public static Javalin getApp(int port) {
        if(app == null){
            userService = new UserService(new UserDao());
            reimbursementService = new ReimbursementService(new ReimbursementDao());
            reimbursementService.initializeArrayDeque();
            init(port);
        }
        return app;
    }

    private static void init(int port) {
        app = Javalin.create().start(port);
        app.get("/ping", JavalinApp::ping);
        app.post("/createUser", JavalinApp::postNewUser);
        app.get("/getAllUsers", JavalinApp::getAllUsers);
        app.post("/addTicket", JavalinApp::addReimbursement);
        app.post("/login", JavalinApp::login);
        app.get("/redirect",  JavalinApp:: redirectEx1);
        app.post("/addCk", JavalinApp::addCookies);
        app.get("/logout", JavalinApp::logout);
        app.post("/approveDeny", JavalinApp::approveOrDeny);
        app.post("/getCk", JavalinApp::getCookies);
        app.get("/getAllReimbursements/denied", JavalinApp::getAllDeniedReimbursements);
        //app.get("/getAllReimbursements/pending", JavalinApp::getAllPendingReimbursements);
        app.get("/getAllReimbursements/pending", JavalinApp::getPendingReimbursements);
        app.get("/viewReimbursementPendingQueue", JavalinApp::printQueue);
        app.get("/getAllReimbursements", JavalinApp::getAllReimbursements);
        //app.get("/getIncompleteReimbursements", JavalinApp::getPendingReimbursements);
        app.get("/getAllReimbursements/approved", JavalinApp::getAllApprovedReimbursements);
        app.get("/viewMyTickets", JavalinApp::getReimbursements);
    }

    public static void ping(Context ctx){

        ctx.result("pong!!");
        ctx.removeCookie("ve");
        ctx.status(200);
    }

    public static void redirectEx1(Context ctx){
        ctx.redirect("/redirectNew", HttpStatus.ACCEPTED);
    }

    public static void getCookies(Context ctx){
        Map<String, String> cookiemap = ctx.cookieMap();
        ctx.result(cookiemap.toString());
    }
    public static void addCookies(Context ctx){

        //ctx.cookie("ask", "new", 500000);
        Cookie cookie0 = new Cookie("ve","blue");
        cookie0.setMaxAge(9000000);
        ctx.cookie(cookie0);
        ctx.result(ctx.cookieMap().toString());
    }
    public static void postNewUser(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (ctx.cookieMap().isEmpty() || ctx.cookieMap().containsValue("Employee")){
            ctx.result("You need to log in as a manager to create a user!");
            ctx.status(401);
        }
        else if (cookieMap.containsValue("Manager")){
            //ctx.result(ctx.queryParam("lastName")+ ctx.queryParam("lastName") + ctx.queryParam("firstName"));
            String firstName = ctx.queryParam("firstName");
            String lastName = ctx.queryParam("lastName");
            String username = ctx.queryParam("username");
            String password = ctx.queryParam("password");
            String title  = ctx.queryParam("title");
            User user = new User(firstName, lastName, username, password, title);
            ctx.json(user);
            //User user = ctx.bodyAsClass(User.class);
            userService.registerNewUser(user);
            ctx.status(201);
        }
        else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }

    }

    public static void getAllUsers(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (ctx.cookieMap().isEmpty() || ctx.cookieMap().containsValue("Employee")){
            ctx.result("You need to log in as a manager to see the request.");
            ctx.status(401);
        }
        else if (cookieMap.containsValue("Manager")){
            Set<User> users = userService.getAllUsers();
            ctx.json(users);
            ctx.status(200);
        }
        else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }

    public static void getAllReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursements();
            ctx.json(myReimbursements);
        }
    }
    public static void getAllPendingReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Pending");
            ctx.json(myReimbursements);
        }else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }

    public static void printQueue(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")) {
            ArrayDeque<Reimbursement> arrDeque = reimbursementService.getQueueOfReimbursements();
            ctx.json(arrDeque);
        }
    }
    public static void getAllDeniedReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Denied");
            ctx.json(myReimbursements);
        }else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }
    public static void getAllApprovedReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Approved");
            ctx.json(myReimbursements);
        }else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }

    public static void approveOrDeny(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")) {
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        } else if (cookieMap.containsValue("Manager")){
            Integer id = Integer.valueOf(ctx.queryParam("ticketId"));
            String response = ctx.queryParam("decision");
            reimbursementService.approveDeny(id, response);
        }else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }
    /*
    public static void checkLoginCookies(Context ctx, e ,u){

    }*/
    public static void addReimbursement(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Manager")){
            ctx.result("You must be signed in as an Employee to use this feature");
            ctx.status(401);
        }else if (cookieMap.containsValue("Employee")){
            String username = cookieMap.keySet().iterator().next();
            //Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
            Double amount = Double.valueOf(ctx.queryParam("amount"));
            String description = ctx.queryParam("description");
            Reimbursement reimbursement = new Reimbursement(amount, description, username);
            //ctx.result(reimbursement.getDescription() + reimbursement.getUsername() + username);
            reimbursementService.createReimbursement(reimbursement);
            reimbursementService.addLastInputReimbursement();
            ctx.status(200);
        }else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }

    public static void getPendingReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Employee")){
            ctx.result("You must be signed in as a Manager to use this feature");
            ctx.status(401);
        }else if (cookieMap.containsValue("Manager")) {
            ArrayDeque<Reimbursement> arrayReimbursements= reimbursementService.getQueueOfReimbursements();
            ctx.json(arrayReimbursements);
            ctx.status(201);
        }
    }

    public static void getReimbursements(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.isEmpty() || cookieMap.containsValue("Manager")){
            ctx.result("You must be signed in as an Employee to use this feature");
            ctx.status(401);
        }else if (cookieMap.containsValue("Employee")){
            String username = cookieMap.keySet().iterator().next();
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsForAUser(username);
            ctx.json(myReimbursements);
            ctx.status(200);
        }
        else {
            ctx.result("This user is not recognized as an Employee or Manager");
            ctx.status(400);
        }
    }
    public static void login(Context ctx) {

        if (!ctx.cookieMap().isEmpty()) {
            ctx.result("You are logged in already. Please logout before proceeding");
            ctx.status(401);
        } else {
            String username = ctx.queryParam("username");
            String password = ctx.queryParam("password");

            try {
                User user = userService.authenticateUser(username, password);
                //ctx.result(user.getPassword()+"  username: "+ user.getUsername());
                String loginUsername = user.getUsername();
                String title = user.getTitle();
                Cookie cookieUser = new Cookie(loginUsername, title);
                cookieUser.setMaxAge(9000000);
                ctx.cookie(cookieUser);
                ctx.result(loginUsername + title);
                ctx.status(201);
            } catch (IncorrectPasswordException e) {
                ctx.status(401);
                ctx.result("User not found!");
            } catch (UserNotFoundException e) {
                ctx.status(401);
                ctx.result("User was not found in database.");
            }
        }
    }

    public static void logout(Context ctx) {
        if(ctx.cookieMap().isEmpty()){
            ctx.result("You are not logged in atm");
            ctx.status(200);
        } else{
            Map<String, String> cookieMap = ctx.cookieMap();
            for(String key: cookieMap.keySet()){
                ctx.removeCookie(key);
                ctx.result(cookieMap.toString());
                ctx.status(200);
            }
        }
    }
    /*
    public static void getTaskById(Context ctx) {
        int id = Integer.parseInt(ctx.queryParam("task_id"));
        Task task = taskService.getTask(id);

        ctx.json(task);
        ctx.status(200);
    }
     */


}

