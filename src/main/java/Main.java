import SQLInterface.DBConnection;
import io.javalin.Javalin;
public class Main {
    public static void main(String[] args) {
        Javalin webApp = Javalin.create();
        webApp = webApp.start(8080);
        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
        });

        webApp.post("/storeData", ctx -> {
            ctx.formParam("objectName");
            ctx.bodyAsClass(Object.class);
            DataStore.storeObject( name, obj);
        });

        webApp.get("/retrieveData", ctx ->{
            String name = ctx.formParam("objectName");
            Object obj = DataStore.getObject()
        })
    }
}
