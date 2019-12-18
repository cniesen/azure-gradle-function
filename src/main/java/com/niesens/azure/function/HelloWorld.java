package com.niesens.azure.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP trigger.
 */
public class HelloWorld {
    /**
     * This function will listen at HTTP endpoint "/api/HelloWorld". Two approaches to invoke it using "curl" command in bash:
     *   1. curl -d "Http Body" {your host}/api/HelloWorld
     *   2. curl {your host}/api/HelloWorld?name=HTTP%20Query
     */
    @FunctionName("HelloWorld")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }
}
