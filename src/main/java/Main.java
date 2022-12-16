import io.javalin.Javalin;
import com.revature.javalin.JavalinApp;
import com.revature.persistence.UserDao;

import java.util.Set;

import com.revature.service.UserService;
public class Main {
    static UserDao userDao;
    static UserService userService;
    public static void main(String[] args) {
        // initialize();
        Javalin app = JavalinApp.getApp(8080);
/*
        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
            //ctx.result(ctx.body());
            ctx.status(200);
        });

        webApp.post("/storeUser", ctx -> {
            String name = ctx.formParam("objectName"); //query param.
            Object obj = ctx.bodyAsClass(Object.class);
            DataStore.storeObject(name, obj);
            ctx.status(201);
        });


        webApp.get("/retrieveData", ctx ->{
            String name = ctx.formParam("objectName");
            Object obj = DataStore.getObject(name);
            ctx.json(obj);  //calls result(), and sets c type to json.
            ctx.status(200);
        });



        webApp.post("/testFormParams", ctx -> {
            System.out.println(ctx.formParams("test"));

            Map<String, List<String>> paramsMap = ctx.formParamMap();
            Set<String> keys = paramsMap.keySet();

            for (String key : keys) {
                System.out.println(paramsMap.get(key));
            }
        });
        }

        public static void initialize(){
            userDao = new UserDao();
            userService = new UserService(userDao);*/
      }

    }
