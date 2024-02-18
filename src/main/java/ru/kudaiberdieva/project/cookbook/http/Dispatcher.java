package ru.kudaiberdieva.project.cookbook.http;

import ru.kudaiberdieva.project.cookbook.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownRequestProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("GET /auth", new UserRequestProcessor());
        this.router.put("POST /register", new UserPOSTRequestProcessor());
        this.router.put("PUT /user", new UserPUTRequestProcessor());
        this.router.put("DELETE /delete", new UserDELETERequestProcessor());
        this.router.put("GET /recipe", new RecipeRequestProcessor());
        this.router.put("POST /recipe", new RecipePOSTRequestProcessor());
        this.router.put("PUT /recipe", new RecipePUTRequestProcessor());
        this.router.put("DELETE /recipe", new RecipeDELETERequestProcessor());
        this.router.put("GET /category", new CategoryRequestProcessor());
        this.router.put("POST /category", new CategoryPOSTRequestProcessor());
        this.router.put("PUT /category", new CategoryPUTRequestProcessor());
        this.router.put("DELETE /category", new CategoryDELETERequestProcessor());
        this.unknownRequestProcessor = new UnknownRequestProcessor();

    }

    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String s = httpRequest.getRoute();
        System.out.println(s);
        if (!router.containsKey(httpRequest.getRoute())) {
            unknownRequestProcessor.execute(httpRequest, output);
            return;
        }
        router.get(httpRequest.getRoute()).execute(httpRequest, output);
    }
}
