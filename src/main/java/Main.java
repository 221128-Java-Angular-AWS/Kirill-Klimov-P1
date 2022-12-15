import SQLInterface.DBConnection;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("OOO");
        Javalin webApp = Javalin.create();
        webApp = webApp.start(8080);

        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
            //ctx.result(ctx.body());
            ctx.status(200);
        });

        webApp.post("/storeData", ctx -> {
            String name = ctx.formParam("objectName");
            Object obj = ctx.bodyAsClass(Object.class);
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
}
