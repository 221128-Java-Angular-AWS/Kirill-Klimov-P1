package com.revature.javalin;
import com.revature.baseObjects.Log;
import com.revature.baseObjects.User;
import com.revature.persistence.LogDao;
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
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Set;


public class JavalinApp {
    private static Javalin app;
    private static UserService userService;
    private static LogService logService;
    private static ReimbursementService reimbursementService;


    private JavalinApp() {
    }

    public static Javalin getApp(int port) {
        if(app == null){
            logService = new LogService((new LogDao()));
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
        app.post("/users/user", JavalinApp::postNewUser);
        app.get("/users", JavalinApp::getAllUsers);
        app.post("/reimbursement", JavalinApp::addReimbursement);
        app.post("/login", JavalinApp::login);
        app.get("/logout", JavalinApp::logout);
        app.patch("/reimbursement/approveDeny", JavalinApp::approveOrDeny);
        app.get("/cookies", JavalinApp::getCookies);
        app.get("/reimbursement/denied", JavalinApp::getAllDeniedReimbursements);
        app.get("/reimbursement/pending", JavalinApp::getPendingReimbursements);
        app.get("/reimbursement/queue", JavalinApp::printQueue);
        app.get("/reimbursement", JavalinApp::getAllReimbursements);
        app.get("/reimbursement/approved", JavalinApp::getAllApprovedReimbursements);
        app.get("/logs", JavalinApp::viewLogs);
        app.patch("/users/title", JavalinApp::changeRole);
        app.get("/user/reimbursements", JavalinApp::getMyReimbursements);
    }

    public static boolean checkPermission(Context ctx, String userAccessor){
        //Map<String, String> cookieMap = ctx.cookieMap();
        if(ctx.cookieMap().containsValue(userAccessor)){
            return true;
        }
        else {
            ctx.result("You need to log in as a " +userAccessor+" to use this functionality!");
            ctx.status(401);
            return false;
        }
    }
    public static void ping(Context ctx){
        ctx.result("pong!!");
        ctx.status(200);
    }

    public static void getCookies(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        ctx.result(cookieMap.toString());
    }

    public static void postNewUser(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (checkPermission(ctx, "Manager")){
            String firstName = ctx.queryParam("firstName");
            String lastName = ctx.queryParam("lastName");
            String username = ctx.queryParam("username");
            String password = ctx.queryParam("password");
            String title;
            if (ctx.queryParam("title")==null || (ctx.queryParam("title")!="Manager" && ctx.queryParam("title")!="Employee")){
                title = "Employee";
            } else{
                title = ctx.queryParam("title");
            }
            if (userService.usernameExists(username)){
                ctx.result("This username is taken!");
                ctx.status(409);
            }else{
                User user = new User(firstName, lastName, username, password, title);
                ctx.result("Created new user: "+user.getUsername());
                userService.registerNewUser(user);
                Log log = new Log(username, "New account created");
                logService.addLog(log);
                ctx.status(201);
            }
        }

    }

    public static void changeRole(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (checkPermission(ctx, "Manager")){
            String newRole = ctx.queryParam("newRole");
            //if(newRole.equals("Manager") || newRole.equals("Employee")) {
                String username = ctx.queryParam("username");
                User user = userService.getUserWithUsername(username);
                user.setTitle(newRole);
                userService.updateUser(user);
                ctx.status(200);
            //}else{
            //    ctx.result("You must specify either Manager or Employee as update.");
            //    ctx.status(404);
           // }
        }
    }

    public static void getAllUsers(Context ctx){
        if (checkPermission(ctx, "Manager")) {
            Set<User> users = userService.getAllUsers();
            ctx.json(users);
            ctx.status(200);
        }
    }

    public static void getAllReimbursements(Context ctx) {
        if (checkPermission(ctx, "Manager")) {
            Set<Reimbursement> myReimbursements = reimbursementService.getAllReimbursements();
            ctx.json(myReimbursements);
            ctx.status(200);
        }
    }

    public static void getAllPendingReimbursements(Context ctx) {
        if (checkPermission(ctx, "Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Pending");
            ctx.json(myReimbursements);
            ctx.status(200);
        }
    }

    public static void printQueue(Context ctx){
        if (checkPermission(ctx, "Manager")){
            ArrayDeque<Reimbursement> arrDeque = reimbursementService.getQueueOfReimbursements();
            ctx.json(arrDeque);
            ctx.status(200);
        }
    }
    public static void getAllDeniedReimbursements(Context ctx) {
        if (checkPermission(ctx, "Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Denied");
            ctx.json(myReimbursements);
            ctx.status(200);
        }
    }
    public static void getAllApprovedReimbursements(Context ctx) {
        if (checkPermission(ctx, "Manager")){
            Set<Reimbursement> myReimbursements= reimbursementService.getAllReimbursementsFilterByApproval("Approved");
            ctx.json(myReimbursements);
            ctx.status(200);
        }
    }

    public static void approveOrDeny(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (checkPermission(ctx, "Manager")){
            Integer id = Integer.valueOf(ctx.queryParam("ticketId"));
            String username = cookieMap.keySet().iterator().next();
            String response = ctx.queryParam("decision");
            reimbursementService.approveDeny(id, response);
            Reimbursement reimbursement = reimbursementService.getReimbursementById(id);
            ctx.json(reimbursement);
            Log log = new Log(username, "Reimbursement decision made");
            logService.addLog(log);
            ctx.status(200);
        }
    }

    public static void addReimbursement(Context ctx) {
        Map<String, String> cookieMap = ctx.cookieMap();
        if (checkPermission(ctx, "Employee")){
            String username = cookieMap.keySet().iterator().next();
            Double amount = Double.valueOf(ctx.queryParam("amount"));
            String description = ctx.queryParam("description");
            Reimbursement reimbursement = new Reimbursement(amount, description, username);
            ctx.json(reimbursement);
            reimbursementService.createReimbursement(reimbursement);
            reimbursementService.addLastInputReimbursement();
            Log log = new Log(username, "Added a ticket");
            logService.addLog(log);
            ctx.status(201);
        }
    }

    public static void getPendingReimbursements(Context ctx) {
        if (checkPermission(ctx, "Manager")){
            ArrayDeque<Reimbursement> arrayReimbursements= reimbursementService.getQueueOfReimbursements();
            ctx.json(arrayReimbursements);
            ctx.status(200);
        }
    }

    public static void getMyReimbursements(Context ctx) {
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

    public static void viewLogs(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (checkPermission(ctx, "Employee")) {
            String username = cookieMap.keySet().iterator().next();
            ctx.json(logService.getMyLogs(username));
            ctx.status(200);
        }
    }
    public static void login(Context ctx) {

        if (!ctx.cookieMap().isEmpty()) {
            ctx.result("You are logged in already. Please logout before proceeding");
            ctx.status(400);
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
            ctx.result("You are not logged in at the moment");
            ctx.status(400);
        } else{
            Map<String, String> cookieMap = ctx.cookieMap();
            for(String key: cookieMap.keySet()){
                ctx.removeCookie(key);
                ctx.result(cookieMap.toString());
                ctx.status(200);
            }
        }
    }


}

