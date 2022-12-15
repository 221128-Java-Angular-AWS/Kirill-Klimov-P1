import io.javalin.Javalin;
import com.revature.baseObjects.User;

import java.util.Map;
import java.util.List;
import com.revature.persistence.UserDao;
import java.util.Set;

import com.revature.service.UserService;
public class Main {
    static UserDao userDao;
    static UserService userService;
    public static void main(String[] args) {
        initialize();

        Javalin webApp = Javalin.create();
        webApp = webApp.start(8080);

        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
            //ctx.result(ctx.body());
            ctx.status(200);
        });
        webApp.post("/createUser", ctx -> {
            String firstName = ctx.formParam("firstName");
            String lastName = ctx.formParam("lastName");
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            String title = ctx.formParam("title");
            System.out.println(ctx.formParamMap().keySet());
            //System.out.println(firstName + lastName + username + password + title);
            User user = ctx.bodyAsClass(User.class);
            System.out.println(user.getFirstName() + user.getLastName());
            //System.out.println(user.getUserId() +password + user.getPassword());
            userService.createUser(user);
            ctx.status(201);
        });
        webApp.post("/storeData", ctx -> {
            String name = ctx.formParam("objectName");
            Object obj = ctx.bodyAsClass(Object.class);
            System.out.println(name);
            DataStore.storeObject(name, obj);
            ctx.status(201);
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

        webApp.get("/retrieveDataByPathName/{name}", ctx -> {
            String name = ctx.pathParam("name");
            Object obj = DataStore.getObject(name);
            ctx.json(obj);
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
            userService = new UserService(userDao);
        }
}
