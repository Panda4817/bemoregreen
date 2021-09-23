package dev.kmunton.bemoregreen.index;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/")
public class IndexController {
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Server is running";
    }

    @Get("api")
    @Produces(MediaType.TEXT_PLAIN)
    public String apiIndex() {
        return "API is running";
    }

}