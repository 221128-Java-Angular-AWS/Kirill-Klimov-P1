package com.revature.javalin;
import com.revature.baseObjects.User;
import com.revature.persistence.UserDao;
import com.revature.persistence.ReimbursementDao;
import com.revature.service.UserService;
import io.javalin.Javalin;
import com.revature.exceptions.UserNotFoundException;
import io.javalin.http.Context;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.baseObjects.Reimbursement;

import com.revature.service.ReimbursementService;
import io.javalin.http.Cookie;
import io.javalin.http.HttpStatus;
import io.javalin.http.util.CookieStore;

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
            init(port);
        }
        return app;
    }

    private static void init(int port) {
        app = Javalin.create().start(port);
        app.get("/ping", JavalinApp::ping);
        app.post("/createUser", JavalinApp::postNewUser);
        app.get("/getAllUsers", JavalinApp::getAllUsers);
        app.post("/user/auth", JavalinApp::login);
        app.get("/redirect",  JavalinApp:: redirectEx1);
        app.post("/addCk", JavalinApp::addCookies);
        app.post("/getCk", JavalinApp::getCookies);
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
        //ctx.cookieStore("sd", "sdf");
        //ctx.cookie("ask", "new", 500000);
        Cookie cookie0 = new Cookie("ve","blue");
        cookie0.setMaxAge(9000000);
        ctx.cookie(cookie0);
        ctx.result(ctx.cookieMap().toString());
    }
    public static void postNewUser(Context ctx){
        User user = ctx.bodyAsClass(User.class);
        userService.registerNewUser(user);
        ctx.status(201);
    }

    public static void getAllUsers(Context ctx){
        Map<String, String> cookieMap = ctx.cookieMap();
        if (cookieMap.values(0)=="Manager"){

        }
        Set<User> users = userService.getAllUsers();
        ctx.json(users);
        ctx.status(200);
    }

    public static void login(Context ctx){

        if(!ctx.cookieMap().isEmpty()){
            ctx.result("You are logged in already. Please log out before proceeding");
            ctx.status(401);
        } else {
            String username = ctx.queryParam("username");
            String password = ctx.queryParam("password");

            try{
                User user = userService.authenticateUser(username, password);
                Cookie cookieUsername = new Cookie(user.getUsername(), user.getTitle());
                ctx.cookie(cookieUsername);
            } catch (IncorrectPasswordException e) {
                ctx.status(401);
                ctx.result("User not found!");
            } catch (UserNotFoundException e)  {
                ctx.status(401);
                ctx.result("User was not found in database.");
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

}

